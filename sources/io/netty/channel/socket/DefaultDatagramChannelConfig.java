package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;

public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig {
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultDatagramChannelConfig.class);
    private volatile boolean activeOnOpen;
    private final DatagramSocket javaSocket;

    public DefaultDatagramChannelConfig(DatagramChannel channel, DatagramSocket javaSocket2) {
        super(channel);
        if (javaSocket2 != null) {
            this.javaSocket = javaSocket2;
            setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
            return;
        }
        throw new NullPointerException("javaSocket");
    }

    /* access modifiers changed from: protected */
    public final DatagramSocket javaSocket() {
        return this.javaSocket;
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == ChannelOption.SO_BROADCAST) {
            return Boolean.valueOf(isBroadcast());
        }
        if (option == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return Integer.valueOf(getSendBufferSize());
        }
        if (option == ChannelOption.SO_REUSEADDR) {
            return Boolean.valueOf(isReuseAddress());
        }
        if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return Boolean.valueOf(isLoopbackModeDisabled());
        }
        if (option == ChannelOption.IP_MULTICAST_ADDR) {
            return getInterface();
        }
        if (option == ChannelOption.IP_MULTICAST_IF) {
            return getNetworkInterface();
        }
        if (option == ChannelOption.IP_MULTICAST_TTL) {
            return Integer.valueOf(getTimeToLive());
        }
        if (option == ChannelOption.IP_TOS) {
            return Integer.valueOf(getTrafficClass());
        }
        if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return Boolean.valueOf(this.activeOnOpen);
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == ChannelOption.SO_BROADCAST) {
            setBroadcast(((Boolean) value).booleanValue());
            return true;
        } else if (option == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) value).booleanValue());
            return true;
        } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            setLoopbackModeDisabled(((Boolean) value).booleanValue());
            return true;
        } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
            setInterface((InetAddress) value);
            return true;
        } else if (option == ChannelOption.IP_MULTICAST_IF) {
            setNetworkInterface((NetworkInterface) value);
            return true;
        } else if (option == ChannelOption.IP_MULTICAST_TTL) {
            setTimeToLive(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) value).intValue());
            return true;
        } else if (option != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return super.setOption(option, value);
        } else {
            setActiveOnOpen(((Boolean) value).booleanValue());
            return true;
        }
    }

    private void setActiveOnOpen(boolean activeOnOpen2) {
        if (!this.channel.isRegistered()) {
            this.activeOnOpen = activeOnOpen2;
            return;
        }
        throw new IllegalStateException("Can only changed before channel was registered");
    }

    public boolean isBroadcast() {
        try {
            return this.javaSocket.getBroadcast();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setBroadcast(boolean broadcast) {
        if (broadcast) {
            try {
                if (!this.javaSocket.getLocalAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
                    InternalLogger internalLogger = logger;
                    internalLogger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
                }
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        }
        this.javaSocket.setBroadcast(broadcast);
        return this;
    }

    public InetAddress getInterface() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getInterface();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setInterface(interfaceAddress);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isLoopbackModeDisabled() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getLoopbackMode();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setLoopbackMode(loopbackModeDisabled);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public NetworkInterface getNetworkInterface() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getNetworkInterface();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setNetworkInterface(networkInterface);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setReuseAddress(boolean reuseAddress) {
        try {
            this.javaSocket.setReuseAddress(reuseAddress);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        try {
            this.javaSocket.setReceiveBufferSize(receiveBufferSize);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setSendBufferSize(int sendBufferSize) {
        try {
            this.javaSocket.setSendBufferSize(sendBufferSize);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getTimeToLive() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getTimeToLive();
            } catch (IOException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setTimeToLive(int ttl) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setTimeToLive(ttl);
                return this;
            } catch (IOException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setTrafficClass(int trafficClass) {
        try {
            this.javaSocket.setTrafficClass(trafficClass);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public DatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    public DatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public DatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    public DatagramChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    public DatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    public DatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }
}
