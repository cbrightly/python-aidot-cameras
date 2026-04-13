package io.netty.util.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

public final class SocketUtils {
    private SocketUtils() {
    }

    public static void connect(final Socket socket, final SocketAddress remoteAddress, final int timeout) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() {
                    socket.connect(remoteAddress, timeout);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static void bind(final Socket socket, final SocketAddress bindpoint) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() {
                    socket.bind(bindpoint);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static boolean connect(final SocketChannel socketChannel, final SocketAddress remoteAddress) {
        try {
            return ((Boolean) AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>() {
                public Boolean run() {
                    return Boolean.valueOf(socketChannel.connect(remoteAddress));
                }
            })).booleanValue();
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static void bind(final SocketChannel socketChannel, final SocketAddress address) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() {
                    socketChannel.bind(address);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static SocketChannel accept(final ServerSocketChannel serverSocketChannel) {
        try {
            return (SocketChannel) AccessController.doPrivileged(new PrivilegedExceptionAction<SocketChannel>() {
                public SocketChannel run() {
                    return serverSocketChannel.accept();
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static void bind(final DatagramChannel networkChannel, final SocketAddress address) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() {
                    networkChannel.bind(address);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getCause());
        }
    }

    public static SocketAddress localSocketAddress(final ServerSocket socket) {
        return (SocketAddress) AccessController.doPrivileged(new PrivilegedAction<SocketAddress>() {
            public SocketAddress run() {
                return socket.getLocalSocketAddress();
            }
        });
    }

    public static InetAddress addressByName(final String hostname) {
        try {
            return (InetAddress) AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress>() {
                public InetAddress run() {
                    return InetAddress.getByName(hostname);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((UnknownHostException) e.getCause());
        }
    }

    public static InetAddress[] allAddressesByName(final String hostname) {
        try {
            return (InetAddress[]) AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress[]>() {
                public InetAddress[] run() {
                    return InetAddress.getAllByName(hostname);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((UnknownHostException) e.getCause());
        }
    }

    public static InetSocketAddress socketAddress(final String hostname, final int port) {
        return (InetSocketAddress) AccessController.doPrivileged(new PrivilegedAction<InetSocketAddress>() {
            public InetSocketAddress run() {
                return new InetSocketAddress(hostname, port);
            }
        });
    }

    public static Enumeration<InetAddress> addressesFromNetworkInterface(final NetworkInterface intf) {
        return (Enumeration) AccessController.doPrivileged(new PrivilegedAction<Enumeration<InetAddress>>() {
            public Enumeration<InetAddress> run() {
                return intf.getInetAddresses();
            }
        });
    }

    public static byte[] hardwareAddressFromNetworkInterface(final NetworkInterface intf) {
        try {
            return (byte[]) AccessController.doPrivileged(new PrivilegedExceptionAction<byte[]>() {
                public byte[] run() {
                    return intf.getHardwareAddress();
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((SocketException) e.getCause());
        }
    }
}
