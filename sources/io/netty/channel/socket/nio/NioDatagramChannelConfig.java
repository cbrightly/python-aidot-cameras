package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;

public class NioDatagramChannelConfig extends DefaultDatagramChannelConfig {
    private static final Method GET_OPTION;
    private static final Object IP_MULTICAST_IF;
    private static final Object IP_MULTICAST_LOOP;
    private static final Object IP_MULTICAST_TTL;
    private static final Method SET_OPTION;
    private final DatagramChannel javaChannel;

    static {
        ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        Class<?> socketOptionType = null;
        try {
            socketOptionType = Class.forName("java.net.SocketOption", true, classLoader);
        } catch (Exception e) {
        }
        Class<?> stdSocketOptionType = null;
        try {
            stdSocketOptionType = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        } catch (Exception e2) {
        }
        Object ipMulticastTtl = null;
        Object ipMulticastIf = null;
        Object ipMulticastLoop = null;
        Method getOption = null;
        Method setOption = null;
        if (socketOptionType != null) {
            try {
                ipMulticastTtl = stdSocketOptionType.getDeclaredField("IP_MULTICAST_TTL").get((Object) null);
                try {
                    ipMulticastIf = stdSocketOptionType.getDeclaredField("IP_MULTICAST_IF").get((Object) null);
                    try {
                        ipMulticastLoop = stdSocketOptionType.getDeclaredField("IP_MULTICAST_LOOP").get((Object) null);
                        Class<?> networkChannelClass = null;
                        try {
                            networkChannelClass = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
                        } catch (Throwable th) {
                        }
                        if (networkChannelClass == null) {
                            getOption = null;
                            setOption = null;
                        } else {
                            try {
                                getOption = networkChannelClass.getDeclaredMethod("getOption", new Class[]{socketOptionType});
                                try {
                                    setOption = networkChannelClass.getDeclaredMethod("setOption", new Class[]{socketOptionType, Object.class});
                                } catch (Exception e3) {
                                    throw new Error("cannot locate the setOption() method", e3);
                                }
                            } catch (Exception e4) {
                                throw new Error("cannot locate the getOption() method", e4);
                            }
                        }
                    } catch (Exception e5) {
                        throw new Error("cannot locate the IP_MULTICAST_LOOP field", e5);
                    }
                } catch (Exception e6) {
                    throw new Error("cannot locate the IP_MULTICAST_IF field", e6);
                }
            } catch (Exception e7) {
                throw new Error("cannot locate the IP_MULTICAST_TTL field", e7);
            }
        }
        IP_MULTICAST_TTL = ipMulticastTtl;
        IP_MULTICAST_IF = ipMulticastIf;
        IP_MULTICAST_LOOP = ipMulticastLoop;
        GET_OPTION = getOption;
        SET_OPTION = setOption;
    }

    NioDatagramChannelConfig(NioDatagramChannel channel, DatagramChannel javaChannel2) {
        super(channel, javaChannel2.socket());
        this.javaChannel = javaChannel2;
    }

    public int getTimeToLive() {
        return ((Integer) getOption0(IP_MULTICAST_TTL)).intValue();
    }

    public DatagramChannelConfig setTimeToLive(int ttl) {
        setOption0(IP_MULTICAST_TTL, Integer.valueOf(ttl));
        return this;
    }

    public InetAddress getInterface() {
        NetworkInterface inf = getNetworkInterface();
        if (inf == null) {
            return null;
        }
        Enumeration<InetAddress> addresses = SocketUtils.addressesFromNetworkInterface(inf);
        if (addresses.hasMoreElements()) {
            return addresses.nextElement();
        }
        return null;
    }

    public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
        try {
            setNetworkInterface(NetworkInterface.getByInetAddress(interfaceAddress));
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface) getOption0(IP_MULTICAST_IF);
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        setOption0(IP_MULTICAST_IF, networkInterface);
        return this;
    }

    public boolean isLoopbackModeDisabled() {
        return ((Boolean) getOption0(IP_MULTICAST_LOOP)).booleanValue();
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        setOption0(IP_MULTICAST_LOOP, Boolean.valueOf(loopbackModeDisabled));
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
        ((NioDatagramChannel) this.channel).setReadPending(false);
    }

    private Object getOption0(Object option) {
        Method method = GET_OPTION;
        if (method != null) {
            try {
                return method.invoke(this.javaChannel, new Object[]{option});
            } catch (Exception e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void setOption0(Object option, Object value) {
        Method method = SET_OPTION;
        if (method != null) {
            try {
                method.invoke(this.javaChannel, new Object[]{option, value});
            } catch (Exception e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
