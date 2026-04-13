package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueueIO;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainEnabledTransport;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.ChannelConfigurator;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.utils.Futures;

public final class UDPNIOTransport extends NIOTransport implements FilterChainEnabledTransport {
    public static final ChannelConfigurator DEFAULT_CHANNEL_CONFIGURATOR = new DefaultChannelConfigurator();
    private static final String DEFAULT_TRANSPORT_NAME = "UDPNIOTransport";
    static final Logger LOGGER = Grizzly.logger(UDPNIOTransport.class);
    protected final AsyncQueueIO<SocketAddress> asyncQueueIO;
    private final UDPNIOBindingHandler bindingHandler;
    private final UDPNIOConnectorHandler connectorHandler;
    protected final RegisterChannelCompletionHandler registerChannelCompletionHandler;
    protected final Collection<UDPNIOServerConnection> serverConnections;
    private final Filter transportFilter;

    public UDPNIOTransport() {
        this(DEFAULT_TRANSPORT_NAME);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UDPNIOTransport(String name) {
        super(name != null ? name : DEFAULT_TRANSPORT_NAME);
        this.connectorHandler = new TransportConnectorHandler();
        this.bindingHandler = new UDPNIOBindingHandler(this);
        this.readBufferSize = -1;
        this.writeBufferSize = -1;
        this.registerChannelCompletionHandler = new RegisterChannelCompletionHandler();
        this.asyncQueueIO = AsyncQueueIO.Factory.createImmutable(new UDPNIOAsyncQueueReader(this), new UDPNIOAsyncQueueWriter(this));
        this.transportFilter = new UDPNIOTransportFilter(this);
        this.serverConnections = new ConcurrentLinkedQueue();
    }

    /* access modifiers changed from: protected */
    public TemporarySelectorIO createTemporarySelectorIO() {
        return new TemporarySelectorIO(new UDPNIOTemporarySelectorReader(this), new UDPNIOTemporarySelectorWriter(this));
    }

    /* access modifiers changed from: protected */
    public void listen() {
        for (UDPNIOServerConnection serverConnection : this.serverConnections) {
            try {
                serverConnection.register();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(serverConnection), e);
            }
        }
    }

    public synchronized boolean addShutdownListener(GracefulShutdownListener shutdownListener) {
        Transport.State state = getState().getState();
        if (state == Transport.State.STOPPING && state == Transport.State.STOPPED) {
            return false;
        }
        if (this.shutdownListeners == null) {
            this.shutdownListeners = new HashSet();
        }
        return this.shutdownListeners.add(shutdownListener);
    }

    public UDPNIOServerConnection bind(int port) {
        return bind((SocketAddress) new InetSocketAddress(port));
    }

    public UDPNIOServerConnection bind(String host, int port) {
        return bind(host, port, 50);
    }

    public UDPNIOServerConnection bind(String host, int port, int backlog) {
        return bind((SocketAddress) new InetSocketAddress(host, port), backlog);
    }

    public UDPNIOServerConnection bind(SocketAddress socketAddress) {
        return bind(socketAddress, 4096);
    }

    public UDPNIOServerConnection bind(SocketAddress socketAddress, int backlog) {
        return this.bindingHandler.bind(socketAddress, backlog);
    }

    public Connection<?> bindToInherited() {
        return this.bindingHandler.bindToInherited();
    }

    public UDPNIOServerConnection bind(String host, PortRange portRange, int backlog) {
        return (UDPNIOServerConnection) this.bindingHandler.bind(host, portRange, backlog);
    }

    public UDPNIOServerConnection bind(String host, PortRange portRange, boolean randomStartPort, int backlog) {
        return (UDPNIOServerConnection) this.bindingHandler.bind(host, portRange, randomStartPort, backlog);
    }

    public void unbind(Connection connection) {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        if (connection != null) {
            try {
                if (this.serverConnections.remove(connection)) {
                    FutureImpl<Closeable> future = Futures.createSafeFuture();
                    ((UDPNIOServerConnection) connection).unbind(Futures.toCompletionHandler(future));
                    future.get(1000, TimeUnit.MILLISECONDS);
                    future.recycle(false);
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_UNBINDING_CONNECTION_EXCEPTION(connection), e);
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }
        lock.unlock();
    }

    public void unbindAll() {
        Connection serverConnection;
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            Iterator<UDPNIOServerConnection> it = this.serverConnections.iterator();
            while (it.hasNext()) {
                serverConnection = it.next();
                unbind(serverConnection);
            }
            this.serverConnections.clear();
            lock.unlock();
        } catch (Exception e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                logger.log(level, "Exception occurred when closing server connection: " + serverConnection, e);
            }
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    public GrizzlyFuture<Connection> connect() {
        return this.connectorHandler.connect();
    }

    public GrizzlyFuture<Connection> connect(String host, int port) {
        return this.connectorHandler.connect(host, port);
    }

    public GrizzlyFuture<Connection> connect(SocketAddress remoteAddress) {
        return this.connectorHandler.connect(remoteAddress);
    }

    public void connect(SocketAddress remoteAddress, CompletionHandler<Connection> completionHandler) {
        this.connectorHandler.connect(remoteAddress, completionHandler);
    }

    public GrizzlyFuture<Connection> connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return this.connectorHandler.connect(remoteAddress, localAddress);
    }

    public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler<Connection> completionHandler) {
        this.connectorHandler.connect(remoteAddress, localAddress, completionHandler);
    }

    /* access modifiers changed from: protected */
    public void closeConnection(Connection connection) {
        SelectableChannel nioChannel = ((NIOConnection) connection).getChannel();
        if (nioChannel != null) {
            try {
                nioChannel.close();
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "UDPNIOTransport.closeChannel exception", e);
            }
        }
        AsyncQueueIO<SocketAddress> asyncQueueIO2 = this.asyncQueueIO;
        if (asyncQueueIO2 != null) {
            AsyncQueueReader reader = asyncQueueIO2.getReader();
            if (reader != null) {
                reader.onClose(connection);
            }
            AsyncQueueWriter writer = this.asyncQueueIO.getWriter();
            if (writer != null) {
                writer.onClose(connection);
            }
        }
    }

    public synchronized void configureStandalone(boolean isStandalone) {
        if (this.isStandalone != isStandalone) {
            this.isStandalone = isStandalone;
            if (isStandalone) {
                this.processor = StandaloneProcessor.INSTANCE;
                this.processorSelector = StandaloneProcessorSelector.INSTANCE;
            } else {
                this.processor = null;
                this.processorSelector = null;
            }
        }
    }

    public Filter getTransportFilter() {
        return this.transportFilter;
    }

    public AsyncQueueIO getAsyncQueueIO() {
        return this.asyncQueueIO;
    }

    public TemporarySelectorIO getTemporarySelectorIO() {
        return this.temporarySelectorIO;
    }

    public void fireIOEvent(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener listener) {
        ProcessorExecutor.execute(Context.create(connection, connection.obtainProcessor(ioEvent), ioEvent, listener));
    }

    public Reader getReader(Connection connection) {
        return getReader(connection.isBlocking());
    }

    public Reader getReader(boolean isBlocking) {
        if (isBlocking) {
            return getTemporarySelectorIO().getReader();
        }
        return getAsyncQueueIO().getReader();
    }

    public Writer getWriter(Connection connection) {
        return getWriter(connection.isBlocking());
    }

    public Writer getWriter(boolean isBlocking) {
        if (isBlocking) {
            return getTemporarySelectorIO().getWriter();
        }
        return getAsyncQueueIO().getWriter();
    }

    private int readConnected(UDPNIOConnection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> currentResult) {
        int read;
        int oldPos = buffer.position();
        boolean z = false;
        if (buffer.isComposite()) {
            ByteBufferArray array = buffer.toByteBufferArray();
            int size = array.size();
            read = (int) ((DatagramChannel) connection.getChannel()).read((ByteBuffer[]) array.getArray(), 0, size);
            array.restore();
            array.recycle();
        } else {
            read = ((DatagramChannel) connection.getChannel()).read(buffer.toByteBuffer());
        }
        if (read > 0) {
            z = true;
        }
        boolean hasRead = z;
        if (hasRead) {
            buffer.position(oldPos + read);
        }
        if (hasRead && currentResult != null) {
            currentResult.setMessage(buffer);
            currentResult.setReadSize(currentResult.getReadSize() + read);
            currentResult.setSrcAddressHolder(connection.peerSocketAddressHolder);
        }
        return read;
    }

    private int readNonConnected(UDPNIOConnection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> currentResult) {
        DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();
        try {
            ByteBuffer directByteBuffer = ioRecord.allocate(buffer.limit());
            int initialBufferPos = directByteBuffer.position();
            SocketAddress peerAddress = ((DatagramChannel) connection.getChannel()).receive(directByteBuffer);
            int read = directByteBuffer.position() - initialBufferPos;
            if (read > 0) {
                directByteBuffer.flip();
                buffer.put(directByteBuffer);
            }
            if ((read > 0) && currentResult != null) {
                currentResult.setMessage(buffer);
                currentResult.setReadSize(currentResult.getReadSize() + read);
                currentResult.setSrcAddress(peerAddress);
            }
            return read;
        } finally {
            ioRecord.release();
        }
    }

    public int read(UDPNIOConnection connection, Buffer buffer) {
        return read(connection, buffer, (ReadResult<Buffer, SocketAddress>) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        if (r0 <= 0) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        r7.dispose();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0030, code lost:
        r7.allowBufferDispose(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        if (-1 <= 0) goto L_0x002c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(org.glassfish.grizzly.nio.transport.UDPNIOConnection r6, org.glassfish.grizzly.Buffer r7, org.glassfish.grizzly.ReadResult<org.glassfish.grizzly.Buffer, java.net.SocketAddress> r8) {
        /*
            r5 = this;
            r0 = 0
            r1 = 1
            if (r7 != 0) goto L_0x0008
            if (r8 == 0) goto L_0x0008
            r2 = r1
            goto L_0x0009
        L_0x0008:
            r2 = 0
        L_0x0009:
            if (r2 == 0) goto L_0x0015
            org.glassfish.grizzly.memory.MemoryManager r3 = r5.memoryManager
            int r4 = r6.getReadBufferSize()
            org.glassfish.grizzly.Buffer r7 = r3.allocateAtLeast(r4)
        L_0x0015:
            boolean r3 = r6.isConnected()     // Catch:{ Exception -> 0x0041, all -> 0x0034 }
            if (r3 == 0) goto L_0x0020
            int r3 = r5.readConnected(r6, r7, r8)     // Catch:{ Exception -> 0x0041, all -> 0x0034 }
            goto L_0x0024
        L_0x0020:
            int r3 = r5.readNonConnected(r6, r7, r8)     // Catch:{ Exception -> 0x0041, all -> 0x0034 }
        L_0x0024:
            r0 = r3
            r6.onRead(r7, r0)     // Catch:{ Exception -> 0x0041, all -> 0x0034 }
            if (r2 == 0) goto L_0x0048
            if (r0 > 0) goto L_0x0030
        L_0x002c:
            r7.dispose()
            goto L_0x0048
        L_0x0030:
            r7.allowBufferDispose(r1)
            goto L_0x0048
        L_0x0034:
            r3 = move-exception
            if (r2 == 0) goto L_0x0040
            if (r0 > 0) goto L_0x003d
            r7.dispose()
            goto L_0x0040
        L_0x003d:
            r7.allowBufferDispose(r1)
        L_0x0040:
            throw r3
        L_0x0041:
            r3 = move-exception
            r0 = -1
            if (r2 == 0) goto L_0x0048
            if (r0 > 0) goto L_0x0030
            goto L_0x002c
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.transport.UDPNIOTransport.read(org.glassfish.grizzly.nio.transport.UDPNIOConnection, org.glassfish.grizzly.Buffer, org.glassfish.grizzly.ReadResult):int");
    }

    public long write(UDPNIOConnection connection, SocketAddress dstAddress, WritableMessage message) {
        return write(connection, dstAddress, message, (WriteResult<WritableMessage, SocketAddress>) null);
    }

    public long write(UDPNIOConnection connection, SocketAddress dstAddress, WritableMessage message, WriteResult<WritableMessage, SocketAddress> currentResult) {
        long written;
        if (message instanceof Buffer) {
            Buffer buffer = (Buffer) message;
            int oldPos = buffer.position();
            if (dstAddress != null) {
                written = (long) ((DatagramChannel) connection.getChannel()).send(buffer.toByteBuffer(), dstAddress);
            } else if (buffer.isComposite()) {
                ByteBufferArray array = buffer.toByteBufferArray();
                int size = array.size();
                long written2 = ((DatagramChannel) connection.getChannel()).write((ByteBuffer[]) array.getArray(), 0, size);
                array.restore();
                array.recycle();
                written = written2;
            } else {
                written = (long) ((DatagramChannel) connection.getChannel()).write(buffer.toByteBuffer());
            }
            if (written > 0) {
                buffer.position(((int) written) + oldPos);
            }
            connection.onWrite(buffer, (int) written);
        } else if (message instanceof FileTransfer) {
            written = ((FileTransfer) message).writeTo((DatagramChannel) connection.getChannel());
        } else {
            throw new IllegalStateException("Unhandled message type");
        }
        if (currentResult != null) {
            currentResult.setMessage(message);
            currentResult.setWrittenSize(currentResult.getWrittenSize() + written);
            currentResult.setDstAddressHolder(connection.peerSocketAddressHolder);
        }
        return written;
    }

    public ChannelConfigurator getChannelConfigurator() {
        ChannelConfigurator cc = this.channelConfigurator;
        return cc != null ? cc : DEFAULT_CHANNEL_CONFIGURATOR;
    }

    /* access modifiers changed from: package-private */
    public UDPNIOConnection obtainNIOConnection(DatagramChannel channel) {
        UDPNIOConnection connection = new UDPNIOConnection(this, channel);
        configureNIOConnection(connection);
        return connection;
    }

    /* access modifiers changed from: package-private */
    public UDPNIOServerConnection obtainServerNIOConnection(DatagramChannel channel) {
        UDPNIOServerConnection connection = new UDPNIOServerConnection(this, channel);
        configureNIOConnection(connection);
        return connection;
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.nio.transport.jmx.UDPNIOTransport", this, UDPNIOTransport.class);
    }

    public class RegisterChannelCompletionHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        protected RegisterChannelCompletionHandler() {
        }

        public void completed(RegisterChannelResult result) {
            SelectionKey selectionKey = result.getSelectionKey();
            UDPNIOConnection connection = (UDPNIOConnection) UDPNIOTransport.this.getSelectionKeyHandler().getConnectionForKey(selectionKey);
            if (connection != null) {
                SelectorRunner selectorRunner = result.getSelectorRunner();
                connection.setSelectionKey(selectionKey);
                connection.setSelectorRunner(selectorRunner);
            }
        }
    }

    public class TransportConnectorHandler extends UDPNIOConnectorHandler {
        public TransportConnectorHandler() {
            super(UDPNIOTransport.this);
        }

        public Processor getProcessor() {
            return UDPNIOTransport.this.getProcessor();
        }

        public ProcessorSelector getProcessorSelector() {
            return UDPNIOTransport.this.getProcessorSelector();
        }
    }

    public static class DefaultChannelConfigurator implements ChannelConfigurator {
        private DefaultChannelConfigurator() {
        }

        public void preConfigure(NIOTransport transport, SelectableChannel channel) {
            UDPNIOTransport udpNioTransport = (UDPNIOTransport) transport;
            DatagramChannel datagramChannel = (DatagramChannel) channel;
            DatagramSocket datagramSocket = datagramChannel.socket();
            datagramChannel.configureBlocking(false);
            try {
                datagramSocket.setReuseAddress(udpNioTransport.isReuseAddress());
            } catch (IOException e) {
                UDPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(Boolean.valueOf(udpNioTransport.isReuseAddress())), e);
            }
        }

        public void postConfigure(NIOTransport transport, SelectableChannel channel) {
            UDPNIOTransport udpNioTransport = (UDPNIOTransport) transport;
            DatagramChannel datagramChannel = (DatagramChannel) channel;
            DatagramSocket datagramSocket = datagramChannel.socket();
            int soTimeout = datagramChannel.isConnected() ? udpNioTransport.getClientSocketSoTimeout() : udpNioTransport.getServerSocketSoTimeout();
            try {
                datagramSocket.setSoTimeout(soTimeout);
            } catch (IOException e) {
                UDPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(Integer.valueOf(soTimeout)), e);
            }
        }
    }
}
