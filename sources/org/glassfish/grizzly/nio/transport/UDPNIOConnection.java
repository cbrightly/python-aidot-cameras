package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.JdkVersion;
import org.glassfish.grizzly.utils.NullaryFunction;

public class UDPNIOConnection extends NIOConnection {
    private static final boolean IS_MULTICAST_SUPPORTED;
    private static final Method JOIN_METHOD;
    private static final Method JOIN_WITH_SOURCE_METHOD;
    private static final Logger LOGGER = Grizzly.logger(UDPNIOConnection.class);
    private static final Method MK_BLOCK_METHOD;
    private static final Method MK_DROP_METHOD;
    private static final Method MK_GET_NETWORK_INTERFACE_METHOD;
    private static final Method MK_GET_SOURCE_ADDRESS_METHOD;
    private static final Method MK_UNBLOCK_METHOD;
    Holder<SocketAddress> localSocketAddressHolder;
    private Map<InetAddress, Set<Object>> membershipKeysMap;
    private final Object multicastSync;
    Holder<SocketAddress> peerSocketAddressHolder;
    private int readBufferSize = -1;
    private int writeBufferSize = -1;

    static {
        boolean isInitialized = false;
        Method join = null;
        Method joinWithSource = null;
        Method mkGetNetworkInterface = null;
        Method mkGetSourceAddress = null;
        Method mkDrop = null;
        Method mkBlock = null;
        Method mkUnblock = null;
        if (JdkVersion.parseVersion("1.7.0").compareTo(JdkVersion.getJdkVersion()) <= 0) {
            try {
                join = DatagramChannel.class.getMethod("join", new Class[]{InetAddress.class, NetworkInterface.class});
                joinWithSource = DatagramChannel.class.getMethod("join", new Class[]{InetAddress.class, NetworkInterface.class, InetAddress.class});
                Class membershipKeyClass = loadClass("java.nio.channels.MembershipKey");
                mkGetNetworkInterface = membershipKeyClass.getDeclaredMethod("networkInterface", new Class[0]);
                mkGetSourceAddress = membershipKeyClass.getDeclaredMethod("sourceAddress", new Class[0]);
                mkDrop = membershipKeyClass.getDeclaredMethod("drop", new Class[0]);
                mkBlock = membershipKeyClass.getDeclaredMethod("block", new Class[]{InetAddress.class});
                mkUnblock = membershipKeyClass.getDeclaredMethod("unblock", new Class[]{InetAddress.class});
                isInitialized = true;
            } catch (Throwable t) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_UDPMULTICASTING_EXCEPTIONE(), t);
            }
        }
        if (isInitialized) {
            IS_MULTICAST_SUPPORTED = true;
            JOIN_METHOD = join;
            JOIN_WITH_SOURCE_METHOD = joinWithSource;
            MK_GET_NETWORK_INTERFACE_METHOD = mkGetNetworkInterface;
            MK_GET_SOURCE_ADDRESS_METHOD = mkGetSourceAddress;
            MK_DROP_METHOD = mkDrop;
            MK_BLOCK_METHOD = mkBlock;
            MK_UNBLOCK_METHOD = mkUnblock;
            return;
        }
        IS_MULTICAST_SUPPORTED = false;
        MK_UNBLOCK_METHOD = null;
        MK_BLOCK_METHOD = null;
        MK_DROP_METHOD = null;
        MK_GET_SOURCE_ADDRESS_METHOD = null;
        MK_GET_NETWORK_INTERFACE_METHOD = null;
        JOIN_WITH_SOURCE_METHOD = null;
        JOIN_METHOD = null;
    }

    public UDPNIOConnection(UDPNIOTransport transport, DatagramChannel channel) {
        super(transport);
        this.channel = channel;
        resetProperties();
        this.multicastSync = IS_MULTICAST_SUPPORTED ? new Object() : null;
    }

    public boolean isConnected() {
        return this.channel != null && ((DatagramChannel) this.channel).isConnected();
    }

    public void join(InetAddress group, NetworkInterface networkInterface) {
        join(group, networkInterface, (InetAddress) null);
    }

    public void join(InetAddress group, NetworkInterface networkInterface, InetAddress source) {
        if (!IS_MULTICAST_SUPPORTED) {
            throw new UnsupportedOperationException("JDK 1.7+ required");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        } else if (networkInterface != null) {
            synchronized (this.multicastSync) {
                Object membershipKey = join0((DatagramChannel) this.channel, group, networkInterface, source);
                if (this.membershipKeysMap == null) {
                    this.membershipKeysMap = new HashMap();
                }
                Set<Object> keySet = this.membershipKeysMap.get(group);
                if (keySet == null) {
                    keySet = new HashSet<>();
                    this.membershipKeysMap.put(group, keySet);
                }
                keySet.add(membershipKey);
            }
        } else {
            throw new IllegalArgumentException("networkInterface parameter can't be null");
        }
    }

    public void drop(InetAddress group, NetworkInterface networkInterface) {
        drop(group, networkInterface, (InetAddress) null);
    }

    public void drop(InetAddress group, NetworkInterface networkInterface, InetAddress source) {
        if (!IS_MULTICAST_SUPPORTED) {
            throw new UnsupportedOperationException("JDK 1.7+ required");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        } else if (networkInterface != null) {
            synchronized (this.multicastSync) {
                Map<InetAddress, Set<Object>> map = this.membershipKeysMap;
                if (map != null) {
                    Set<Object> set = map.get(group);
                    Set<Object> keys = set;
                    if (set != null) {
                        Iterator<Object> it = keys.iterator();
                        while (it.hasNext()) {
                            Object key = it.next();
                            if (networkInterface.equals(networkInterface0(key)) && ((source == null && sourceAddress0(key) == null) || (source != null && source.equals(sourceAddress0(key))))) {
                                drop0(key);
                                it.remove();
                            }
                            if (keys.isEmpty()) {
                                this.membershipKeysMap.remove(group);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("networkInterface parameter can't be null");
        }
    }

    public void dropAll(InetAddress group, NetworkInterface networkInterface) {
        if (!IS_MULTICAST_SUPPORTED) {
            throw new UnsupportedOperationException("JDK 1.7+ required");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        } else if (networkInterface != null) {
            synchronized (this.multicastSync) {
                Map<InetAddress, Set<Object>> map = this.membershipKeysMap;
                if (map != null) {
                    Set<Object> set = map.get(group);
                    Set<Object> keys = set;
                    if (set != null) {
                        Iterator<Object> it = keys.iterator();
                        while (it.hasNext()) {
                            Object key = it.next();
                            if (networkInterface.equals(networkInterface0(key))) {
                                drop0(key);
                                it.remove();
                            }
                        }
                        if (keys.isEmpty()) {
                            this.membershipKeysMap.remove(group);
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("networkInterface parameter can't be null");
        }
    }

    public void block(InetAddress group, NetworkInterface networkInterface, InetAddress source) {
        if (!IS_MULTICAST_SUPPORTED) {
            throw new UnsupportedOperationException("JDK 1.7+ required");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        } else if (networkInterface != null) {
            synchronized (this.multicastSync) {
                Map<InetAddress, Set<Object>> map = this.membershipKeysMap;
                if (map != null) {
                    Set<Object> set = map.get(group);
                    Set<Object> keys = set;
                    if (set != null) {
                        for (Object key : keys) {
                            if (networkInterface.equals(networkInterface0(key)) && sourceAddress0(key) == null) {
                                block0(key, source);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("networkInterface parameter can't be null");
        }
    }

    public void unblock(InetAddress group, NetworkInterface networkInterface, InetAddress source) {
        if (!IS_MULTICAST_SUPPORTED) {
            throw new UnsupportedOperationException("JDK 1.7+ required");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        } else if (networkInterface != null) {
            synchronized (this.multicastSync) {
                Map<InetAddress, Set<Object>> map = this.membershipKeysMap;
                if (map != null) {
                    Set<Object> set = map.get(group);
                    Set<Object> keys = set;
                    if (set != null) {
                        for (Object key : keys) {
                            if (networkInterface.equals(networkInterface0(key)) && sourceAddress0(key) == null) {
                                unblock0(key, source);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("networkInterface parameter can't be null");
        }
    }

    /* access modifiers changed from: protected */
    public void setSelectionKey(SelectionKey selectionKey) {
        super.setSelectionKey(selectionKey);
    }

    /* access modifiers changed from: protected */
    public void setSelectorRunner(SelectorRunner selectorRunner) {
        super.setSelectorRunner(selectorRunner);
    }

    /* access modifiers changed from: protected */
    public boolean notifyReady() {
        return NIOConnection.connectCloseSemaphoreUpdater.compareAndSet(this, (Object) null, NIOConnection.NOTIFICATION_INITIALIZED);
    }

    public SocketAddress getPeerAddress() {
        return this.peerSocketAddressHolder.get();
    }

    public SocketAddress getLocalAddress() {
        return this.localSocketAddressHolder.get();
    }

    /* access modifiers changed from: protected */
    public final void resetProperties() {
        if (this.channel != null) {
            setReadBufferSize(this.transport.getReadBufferSize());
            setWriteBufferSize(this.transport.getWriteBufferSize());
            int transportMaxAsyncWriteQueueSize = this.transport.getAsyncQueueIO().getWriter().getMaxPendingBytesPerConnection();
            setMaxAsyncWriteQueueSize(transportMaxAsyncWriteQueueSize == -2 ? getWriteBufferSize() * 4 : transportMaxAsyncWriteQueueSize);
            this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction<SocketAddress>() {
                public SocketAddress evaluate() {
                    return ((DatagramChannel) UDPNIOConnection.this.channel).socket().getLocalSocketAddress();
                }
            });
            this.peerSocketAddressHolder = Holder.lazyHolder(new NullaryFunction<SocketAddress>() {
                public SocketAddress evaluate() {
                    return ((DatagramChannel) UDPNIOConnection.this.channel).socket().getRemoteSocketAddress();
                }
            });
        }
    }

    public int getReadBufferSize() {
        int i = this.readBufferSize;
        if (i >= 0) {
            return i;
        }
        try {
            this.readBufferSize = ((DatagramChannel) this.channel).socket().getReceiveBufferSize();
        } catch (IOException e) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION(), e);
            this.readBufferSize = 0;
        }
        return this.readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize2) {
        if (readBufferSize2 > 0) {
            try {
                if (readBufferSize2 > ((DatagramChannel) this.channel).socket().getReceiveBufferSize()) {
                    ((DatagramChannel) this.channel).socket().setReceiveBufferSize(readBufferSize2);
                }
                this.readBufferSize = readBufferSize2;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION(), e);
            }
        }
    }

    public int getWriteBufferSize() {
        int i = this.writeBufferSize;
        if (i >= 0) {
            return i;
        }
        try {
            this.writeBufferSize = ((DatagramChannel) this.channel).socket().getSendBufferSize();
        } catch (IOException e) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION(), e);
            this.writeBufferSize = 0;
        }
        return this.writeBufferSize;
    }

    public void setWriteBufferSize(int writeBufferSize2) {
        if (writeBufferSize2 > 0) {
            try {
                if (writeBufferSize2 > ((DatagramChannel) this.channel).socket().getSendBufferSize()) {
                    ((DatagramChannel) this.channel).socket().setSendBufferSize(writeBufferSize2);
                }
                this.writeBufferSize = writeBufferSize2;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION(), e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void enableInitialOpRead() {
        super.enableInitialOpRead();
    }

    /* access modifiers changed from: protected */
    public final void onConnect() {
        NIOConnection.notifyProbesConnect(this);
    }

    /* access modifiers changed from: protected */
    public final void onRead(Buffer data, int size) {
        if (size > 0) {
            NIOConnection.notifyProbesRead(this, data, size);
        }
        checkEmptyRead(size);
    }

    /* access modifiers changed from: protected */
    public final void onWrite(Buffer data, int size) {
        NIOConnection.notifyProbesWrite(this, data, (long) size);
    }

    public boolean canWrite() {
        return this.transport.getWriter((Connection) this).canWrite(this);
    }

    @Deprecated
    public boolean canWrite(int length) {
        return this.transport.getWriter((Connection) this).canWrite(this);
    }

    public void notifyCanWrite(WriteHandler writeHandler) {
        this.transport.getWriter((Connection) this).notifyWritePossible(this, writeHandler);
    }

    @Deprecated
    public void notifyCanWrite(WriteHandler handler, int length) {
        this.transport.getWriter((Connection) this).notifyWritePossible(this, handler);
    }

    /* access modifiers changed from: package-private */
    public void setMonitoringProbes(ConnectionProbe[] monitoringProbes) {
        this.monitoringConfig.addProbes(monitoringProbes);
    }

    public String toString() {
        return "UDPNIOConnection" + "{localSocketAddress=" + this.localSocketAddressHolder + ", peerSocketAddress=" + this.peerSocketAddressHolder + '}';
    }

    private static Object join0(DatagramChannel channel, InetAddress group, NetworkInterface networkInterface, InetAddress source) {
        if (source == null) {
            return invoke(channel, JOIN_METHOD, group, networkInterface);
        }
        return invoke(channel, JOIN_WITH_SOURCE_METHOD, group, networkInterface, source);
    }

    private static NetworkInterface networkInterface0(Object membershipKey) {
        return (NetworkInterface) invoke(membershipKey, MK_GET_NETWORK_INTERFACE_METHOD, new Object[0]);
    }

    private static InetAddress sourceAddress0(Object membershipKey) {
        return (InetAddress) invoke(membershipKey, MK_GET_SOURCE_ADDRESS_METHOD, new Object[0]);
    }

    private static void drop0(Object membershipKey) {
        invoke(membershipKey, MK_DROP_METHOD, new Object[0]);
    }

    private static void block0(Object membershipKey, InetAddress sourceAddress) {
        invoke(membershipKey, MK_BLOCK_METHOD, sourceAddress);
    }

    private static void unblock0(Object membershipKey, InetAddress sourceAddress) {
        invoke(membershipKey, MK_UNBLOCK_METHOD, sourceAddress);
    }

    private static Object invoke(Object object, Method method, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw ((RuntimeException) t);
            }
            throw Exceptions.makeIOException(t);
        } catch (Throwable t2) {
            throw Exceptions.makeIOException(t2);
        }
    }

    private static Class<?> loadClass(String cname) {
        return ClassLoader.getSystemClassLoader().loadClass(cname);
    }
}
