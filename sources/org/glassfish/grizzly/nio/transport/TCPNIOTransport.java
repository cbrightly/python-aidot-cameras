package org.glassfish.grizzly.nio.transport;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransport;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.AsyncQueueIO;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainEnabledTransport;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.ChannelConfigurator;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport;

public final class TCPNIOTransport extends NIOTransport implements AsyncQueueEnabledTransport, FilterChainEnabledTransport, TemporarySelectorsEnabledTransport {
    public static final ChannelConfigurator DEFAULT_CHANNEL_CONFIGURATOR = new DefaultChannelConfigurator();
    public static final boolean DEFAULT_KEEP_ALIVE = true;
    public static final int DEFAULT_LINGER = -1;
    public static final int DEFAULT_SERVER_CONNECTION_BACKLOG = 4096;
    public static final boolean DEFAULT_TCP_NO_DELAY = true;
    private static final String DEFAULT_TRANSPORT_NAME = "TCPNIOTransport";
    static final Logger LOGGER;
    public static final int MAX_RECEIVE_BUFFER_SIZE;
    public static final int MAX_SEND_BUFFER_SIZE;
    final AsyncQueueIO<SocketAddress> asyncQueueIO;
    private final TCPNIOBindingHandler bindingHandler;
    private final TCPNIOConnectorHandler connectorHandler;
    private final Filter defaultTransportFilter;
    boolean isKeepAlive;
    int linger;
    final RegisterChannelCompletionHandler selectorRegistrationHandler;
    int serverConnectionBackLog;
    final Collection<TCPNIOServerConnection> serverConnections;
    boolean tcpNoDelay;

    static {
        Class<TCPNIOTransport> cls = TCPNIOTransport.class;
        LOGGER = Grizzly.logger(cls);
        MAX_RECEIVE_BUFFER_SIZE = Integer.getInteger(cls.getName() + ".max-receive-buffer-size", Integer.MAX_VALUE).intValue();
        MAX_SEND_BUFFER_SIZE = Integer.getInteger(cls.getName() + ".max-send-buffer-size", Integer.MAX_VALUE).intValue();
    }

    public TCPNIOTransport() {
        this(DEFAULT_TRANSPORT_NAME);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TCPNIOTransport(String name) {
        super(name != null ? name : DEFAULT_TRANSPORT_NAME);
        this.linger = -1;
        this.serverConnectionBackLog = 4096;
        this.tcpNoDelay = true;
        this.isKeepAlive = true;
        this.connectorHandler = new TransportConnectorHandler();
        this.bindingHandler = new TCPNIOBindingHandler(this);
        this.readBufferSize = -1;
        this.writeBufferSize = -1;
        this.selectorRegistrationHandler = new RegisterChannelCompletionHandler();
        this.asyncQueueIO = AsyncQueueIO.Factory.createImmutable(new TCPNIOAsyncQueueReader(this), new TCPNIOAsyncQueueWriter(this));
        this.attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.defaultTransportFilter = new TCPNIOTransportFilter(this);
        this.serverConnections = new ConcurrentLinkedQueue();
    }

    /* access modifiers changed from: protected */
    public TemporarySelectorIO createTemporarySelectorIO() {
        return new TemporarySelectorIO(new TCPNIOTemporarySelectorReader(this), new TCPNIOTemporarySelectorWriter(this));
    }

    /* access modifiers changed from: protected */
    public void listen() {
        for (TCPNIOServerConnection serverConnection : this.serverConnections) {
            try {
                listenServerConnection(serverConnection);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(serverConnection), e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getDefaultSelectorRunnersCount() {
        return Runtime.getRuntime().availableProcessors() + 1;
    }

    /* access modifiers changed from: package-private */
    public void listenServerConnection(TCPNIOServerConnection serverConnection) {
        serverConnection.listen();
    }

    public TCPNIOServerConnection bind(int port) {
        return bind((SocketAddress) new InetSocketAddress(port));
    }

    public TCPNIOServerConnection bind(String host, int port) {
        return bind(host, port, this.serverConnectionBackLog);
    }

    public TCPNIOServerConnection bind(String host, int port, int backlog) {
        return bind((SocketAddress) new InetSocketAddress(host, port), backlog);
    }

    public TCPNIOServerConnection bind(SocketAddress socketAddress) {
        return bind(socketAddress, this.serverConnectionBackLog);
    }

    public TCPNIOServerConnection bind(SocketAddress socketAddress, int backlog) {
        return this.bindingHandler.bind(socketAddress, backlog);
    }

    public TCPNIOServerConnection bindToInherited() {
        return this.bindingHandler.bindToInherited();
    }

    public TCPNIOServerConnection bind(String host, PortRange portRange, int backlog) {
        return (TCPNIOServerConnection) this.bindingHandler.bind(host, portRange, backlog);
    }

    public TCPNIOServerConnection bind(String host, PortRange portRange, boolean randomStartPort, int backlog) {
        return (TCPNIOServerConnection) this.bindingHandler.bind(host, portRange, randomStartPort, backlog);
    }

    public void unbind(Connection connection) {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        if (connection != null) {
            try {
                if (this.serverConnections.remove(connection)) {
                    GrizzlyFuture future = connection.close();
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
            Iterator<TCPNIOServerConnection> it = this.serverConnections.iterator();
            while (it.hasNext()) {
                serverConnection = it.next();
                unbind(serverConnection);
            }
            this.serverConnections.clear();
            lock.unlock();
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            logger.log(level, "Exception occurred when closing server connection: " + serverConnection, e);
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
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
                LOGGER.log(Level.FINE, "TCPNIOTransport.closeChannel exception", e);
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

    /* access modifiers changed from: package-private */
    public TCPNIOConnection obtainNIOConnection(SocketChannel channel) {
        TCPNIOConnection connection = new TCPNIOConnection(this, channel);
        configureNIOConnection(connection);
        return connection;
    }

    /* access modifiers changed from: package-private */
    public TCPNIOServerConnection obtainServerNIOConnection(ServerSocketChannel channel) {
        TCPNIOServerConnection connection = new TCPNIOServerConnection(this, channel);
        configureNIOConnection(connection);
        return connection;
    }

    public ChannelConfigurator getChannelConfigurator() {
        ChannelConfigurator cc = this.channelConfigurator;
        return cc != null ? cc : DEFAULT_CHANNEL_CONFIGURATOR;
    }

    public AsyncQueueIO<SocketAddress> getAsyncQueueIO() {
        return this.asyncQueueIO;
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

    public int getLinger() {
        return this.linger;
    }

    public void setLinger(int linger2) {
        this.linger = linger2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public boolean isKeepAlive() {
        return this.isKeepAlive;
    }

    public void setKeepAlive(boolean isKeepAlive2) {
        this.isKeepAlive = isKeepAlive2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay2) {
        this.tcpNoDelay = tcpNoDelay2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public int getServerConnectionBackLog() {
        return this.serverConnectionBackLog;
    }

    public void setServerConnectionBackLog(int serverConnectionBackLog2) {
        this.serverConnectionBackLog = serverConnectionBackLog2;
    }

    public Filter getTransportFilter() {
        return this.defaultTransportFilter;
    }

    public TemporarySelectorIO getTemporarySelectorIO() {
        return this.temporarySelectorIO;
    }

    public void fireIOEvent(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener listener) {
        if (ioEvent == IOEvent.SERVER_ACCEPT) {
            try {
                ((TCPNIOServerConnection) connection).onAccept();
            } catch (ClosedByInterruptException cbie) {
                failProcessingHandler(ioEvent, connection, listener, cbie);
                try {
                    rebindAddress(connection);
                } catch (IOException ioe) {
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, LogMessages.SEVERE_GRIZZLY_TRANSPORT_LISTEN_INTERRUPTED_REBIND_EXCEPTION(connection.getLocalAddress()), ioe);
                    }
                }
            } catch (IOException e) {
                failProcessingHandler(ioEvent, connection, listener, e);
            }
        } else if (ioEvent == IOEvent.CLIENT_CONNECTED) {
            try {
                ((TCPNIOConnection) connection).onConnect();
            } catch (IOException e2) {
                failProcessingHandler(ioEvent, connection, listener, e2);
            }
        } else {
            ProcessorExecutor.execute(Context.create(connection, connection.obtainProcessor(ioEvent), ioEvent, listener));
        }
    }

    public Reader<SocketAddress> getReader(Connection connection) {
        return getReader(connection.isBlocking());
    }

    public Reader<SocketAddress> getReader(boolean isBlocking) {
        if (isBlocking) {
            return getTemporarySelectorIO().getReader();
        }
        return getAsyncQueueIO().getReader();
    }

    public Writer<SocketAddress> getWriter(Connection connection) {
        return getWriter(connection.isBlocking());
    }

    public Writer<SocketAddress> getWriter(boolean isBlocking) {
        if (isBlocking) {
            return getTemporarySelectorIO().getWriter();
        }
        return getAsyncQueueIO().getWriter();
    }

    public Buffer read(Connection connection, Buffer buffer) {
        int read;
        int read2;
        TCPNIOConnection tcpConnection = (TCPNIOConnection) connection;
        if (buffer == null) {
            try {
                buffer = TCPNIOUtils.allocateAndReadBuffer(tcpConnection);
                read2 = buffer.position();
                tcpConnection.onRead(buffer, read2);
            } catch (Exception e) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (logger.isLoggable(level)) {
                    logger.log(level, "TCPNIOConnection (" + connection + ") (allocated) read exception", e);
                }
                read2 = -1;
            }
            if (read2 == 0) {
                return null;
            }
            if (read2 >= 0) {
                return buffer;
            }
            IOException e2 = new EOFException();
            tcpConnection.terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.REMOTELY, e2));
            throw e2;
        } else if (buffer.hasRemaining() == 0) {
            return buffer;
        } else {
            try {
                read = TCPNIOUtils.readBuffer(tcpConnection, buffer);
            } catch (Exception e3) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "TCPNIOConnection (" + connection + ") (existing) read exception", e3);
                }
                read = -1;
            }
            tcpConnection.onRead(buffer, read);
            if (read >= 0) {
                return buffer;
            }
            IOException e4 = new EOFException();
            tcpConnection.terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.REMOTELY, e4));
            throw e4;
        }
    }

    public int write(TCPNIOConnection connection, WritableMessage message) {
        return write(connection, message, (WriteResult) null);
    }

    public int write(TCPNIOConnection connection, WritableMessage message, WriteResult currentResult) {
        int written;
        if (message.remaining() == 0) {
            return 0;
        }
        if ((message instanceof Buffer) != 0) {
            Buffer buffer = (Buffer) message;
            try {
                if (buffer.isComposite()) {
                    written = TCPNIOUtils.writeCompositeBuffer(connection, (CompositeBuffer) buffer);
                } else {
                    written = TCPNIOUtils.writeSimpleBuffer(connection, buffer);
                }
                boolean hasWritten = written >= 0;
                connection.onWrite(buffer, (long) written);
                if (hasWritten && currentResult != null) {
                    currentResult.setMessage(message);
                    currentResult.setWrittenSize(currentResult.getWrittenSize() + ((long) written));
                    currentResult.setDstAddressHolder(connection.peerSocketAddressHolder);
                }
                return written;
            } catch (IOException e) {
                connection.terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.REMOTELY, e));
                throw e;
            }
        } else if (message instanceof FileTransfer) {
            return (int) ((FileTransfer) message).writeTo((SocketChannel) connection.getChannel());
        } else {
            throw new IllegalStateException("Unhandled message type");
        }
    }

    private static void failProcessingHandler(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener processingHandler, IOException e) {
        if (processingHandler != null) {
            try {
                processingHandler.onError(Context.create(connection, (Processor) null, ioEvent, processingHandler), e);
            } catch (IOException e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.nio.transport.jmx.TCPNIOTransport", this, TCPNIOTransport.class);
    }

    public class RegisterChannelCompletionHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        RegisterChannelCompletionHandler() {
        }

        public void completed(RegisterChannelResult result) {
            SelectionKey selectionKey = result.getSelectionKey();
            TCPNIOConnection connection = (TCPNIOConnection) TCPNIOTransport.this.getSelectionKeyHandler().getConnectionForKey(selectionKey);
            if (connection != null) {
                SelectorRunner selectorRunner = result.getSelectorRunner();
                connection.setSelectionKey(selectionKey);
                connection.setSelectorRunner(selectorRunner);
            }
        }
    }

    public class TransportConnectorHandler extends TCPNIOConnectorHandler {
        public TransportConnectorHandler() {
            super(TCPNIOTransport.this);
        }

        public Processor getProcessor() {
            return TCPNIOTransport.this.getProcessor();
        }

        public ProcessorSelector getProcessorSelector() {
            return TCPNIOTransport.this.getProcessorSelector();
        }
    }

    public static class DefaultChannelConfigurator implements ChannelConfigurator {
        private DefaultChannelConfigurator() {
        }

        public void preConfigure(NIOTransport transport, SelectableChannel channel) {
            TCPNIOTransport tcpNioTransport = (TCPNIOTransport) transport;
            if (channel instanceof SocketChannel) {
                SocketChannel sc = (SocketChannel) channel;
                Socket socket = sc.socket();
                sc.configureBlocking(false);
                boolean reuseAddress = tcpNioTransport.isReuseAddress();
                try {
                    socket.setReuseAddress(reuseAddress);
                } catch (IOException e) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(Boolean.valueOf(reuseAddress)), e);
                }
            } else {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                ServerSocket serverSocket = serverSocketChannel.socket();
                serverSocketChannel.configureBlocking(false);
                try {
                    serverSocket.setReuseAddress(tcpNioTransport.isReuseAddress());
                } catch (IOException e2) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(Boolean.valueOf(tcpNioTransport.isReuseAddress())), e2);
                }
            }
        }

        public void postConfigure(NIOTransport transport, SelectableChannel channel) {
            TCPNIOTransport tcpNioTransport = (TCPNIOTransport) transport;
            if (channel instanceof SocketChannel) {
                Socket socket = ((SocketChannel) channel).socket();
                int linger = tcpNioTransport.getLinger();
                if (linger >= 0) {
                    try {
                        socket.setSoLinger(true, linger);
                    } catch (IOException e) {
                        TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_LINGER_EXCEPTION(Integer.valueOf(linger)), e);
                    }
                }
                boolean keepAlive = tcpNioTransport.isKeepAlive();
                try {
                    socket.setKeepAlive(keepAlive);
                } catch (IOException e2) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_KEEPALIVE_EXCEPTION(Boolean.valueOf(keepAlive)), e2);
                }
                boolean tcpNoDelay = tcpNioTransport.isTcpNoDelay();
                try {
                    socket.setTcpNoDelay(tcpNoDelay);
                } catch (IOException e3) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TCPNODELAY_EXCEPTION(Boolean.valueOf(tcpNoDelay)), e3);
                }
                try {
                    socket.setSoTimeout(tcpNioTransport.getClientSocketSoTimeout());
                } catch (IOException e4) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(Integer.valueOf(tcpNioTransport.getClientSocketSoTimeout())), e4);
                }
            } else {
                try {
                    ((ServerSocketChannel) channel).socket().setSoTimeout(tcpNioTransport.getServerSocketSoTimeout());
                } catch (IOException e5) {
                    TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(Integer.valueOf(tcpNioTransport.getServerSocketSoTimeout())), e5);
                }
            }
        }
    }

    private void rebindAddress(Connection connection) {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            if (Thread.currentThread().isInterrupted()) {
                Thread.interrupted();
            }
            if (this.serverConnections.remove(connection)) {
                bind((SocketAddress) connection.getLocalAddress());
            }
        } finally {
            lock.unlock();
        }
    }
}
