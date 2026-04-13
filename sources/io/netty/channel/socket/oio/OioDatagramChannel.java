package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;

public class OioDatagramChannel extends AbstractOioMessageChannel implements DatagramChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName((Class<?>) DatagramPacket.class) + ", " + StringUtil.simpleClassName((Class<?>) AddressedEnvelope.class) + '<' + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) SocketAddress.class) + ">, " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) OioDatagramChannel.class);
    private RecvByteBufAllocator.Handle allocHandle;
    private final OioDatagramChannelConfig config;
    private final MulticastSocket socket;
    private final java.net.DatagramPacket tmpPacket;

    private static MulticastSocket newSocket() {
        try {
            return new MulticastSocket((SocketAddress) null);
        } catch (Exception e) {
            throw new ChannelException("failed to create a new socket", e);
        }
    }

    public OioDatagramChannel() {
        this(newSocket());
    }

    public OioDatagramChannel(MulticastSocket socket2) {
        super((Channel) null);
        this.tmpPacket = new java.net.DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
        try {
            socket2.setSoTimeout(1000);
            socket2.setBroadcast(false);
            if (1 == 0) {
                socket2.close();
            }
            this.socket = socket2;
            this.config = new DefaultOioDatagramChannelConfig(this, socket2);
        } catch (SocketException e) {
            throw new ChannelException("Failed to configure the datagram socket timeout.", e);
        } catch (Throwable th) {
            if (0 == 0) {
                socket2.close();
            }
            throw th;
        }
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket.isBound());
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        this.socket.bind(localAddress);
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    /* access modifiers changed from: protected */
    public void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        if (localAddress != null) {
            this.socket.bind(localAddress);
        }
        try {
            this.socket.connect(remoteAddress);
            if (1 == 0) {
                try {
                    this.socket.close();
                    return;
                } catch (Throwable t) {
                    logger.warn("Failed to close a socket.", t);
                    return;
                }
            } else {
                return;
            }
        } catch (Throwable t2) {
            logger.warn("Failed to close a socket.", t2);
        }
        throw th;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        this.socket.disconnect();
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        this.socket.close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> buf) {
        DatagramChannelConfig config2 = config();
        RecvByteBufAllocator.Handle allocHandle2 = this.allocHandle;
        if (allocHandle2 == null) {
            RecvByteBufAllocator.Handle newHandle = config2.getRecvByteBufAllocator().newHandle();
            allocHandle2 = newHandle;
            this.allocHandle = newHandle;
        }
        ByteBuf data = config2.getAllocator().heapBuffer(allocHandle2.guess());
        try {
            this.tmpPacket.setAddress((InetAddress) null);
            this.tmpPacket.setData(data.array(), data.arrayOffset(), data.capacity());
            this.socket.receive(this.tmpPacket);
            int readBytes = this.tmpPacket.getLength();
            allocHandle2.record(readBytes);
            buf.add(new DatagramPacket(data.writerIndex(readBytes), localAddress(), (InetSocketAddress) this.tmpPacket.getSocketAddress()));
            if (0 != 0) {
                data.release();
            }
            return 1;
        } catch (SocketTimeoutException e) {
            if (1 != 0) {
                data.release();
            }
            return 0;
        } catch (SocketException e2) {
            if (e2.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                if (1 != 0) {
                    data.release();
                }
                return -1;
            }
            throw e2;
        } catch (Throwable th) {
            if (1 != 0) {
                data.release();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: io.netty.buffer.ByteBuf} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doWrite(io.netty.channel.ChannelOutboundBuffer r9) {
        /*
            r8 = this;
        L_0x0000:
            java.lang.Object r0 = r9.current()
            if (r0 != 0) goto L_0x0008
            return
        L_0x0008:
            boolean r1 = r0 instanceof io.netty.channel.AddressedEnvelope
            if (r1 == 0) goto L_0x001b
            r1 = r0
            io.netty.channel.AddressedEnvelope r1 = (io.netty.channel.AddressedEnvelope) r1
            java.net.SocketAddress r2 = r1.recipient()
            java.lang.Object r3 = r1.content()
            r1 = r3
            io.netty.buffer.ByteBuf r1 = (io.netty.buffer.ByteBuf) r1
            goto L_0x001f
        L_0x001b:
            r1 = r0
            io.netty.buffer.ByteBuf r1 = (io.netty.buffer.ByteBuf) r1
            r2 = 0
        L_0x001f:
            int r3 = r1.readableBytes()
            if (r2 == 0) goto L_0x002b
            java.net.DatagramPacket r4 = r8.tmpPacket     // Catch:{ Exception -> 0x006f }
            r4.setSocketAddress(r2)     // Catch:{ Exception -> 0x006f }
            goto L_0x0037
        L_0x002b:
            boolean r4 = r8.isConnected()     // Catch:{ Exception -> 0x006f }
            if (r4 == 0) goto L_0x0069
            java.net.DatagramPacket r4 = r8.tmpPacket     // Catch:{ Exception -> 0x006f }
            r5 = 0
            r4.setAddress(r5)     // Catch:{ Exception -> 0x006f }
        L_0x0037:
            boolean r4 = r1.hasArray()     // Catch:{ Exception -> 0x006f }
            if (r4 == 0) goto L_0x0050
            java.net.DatagramPacket r4 = r8.tmpPacket     // Catch:{ Exception -> 0x006f }
            byte[] r5 = r1.array()     // Catch:{ Exception -> 0x006f }
            int r6 = r1.arrayOffset()     // Catch:{ Exception -> 0x006f }
            int r7 = r1.readerIndex()     // Catch:{ Exception -> 0x006f }
            int r6 = r6 + r7
            r4.setData(r5, r6, r3)     // Catch:{ Exception -> 0x006f }
            goto L_0x005e
        L_0x0050:
            byte[] r4 = new byte[r3]     // Catch:{ Exception -> 0x006f }
            int r5 = r1.readerIndex()     // Catch:{ Exception -> 0x006f }
            r1.getBytes((int) r5, (byte[]) r4)     // Catch:{ Exception -> 0x006f }
            java.net.DatagramPacket r5 = r8.tmpPacket     // Catch:{ Exception -> 0x006f }
            r5.setData(r4)     // Catch:{ Exception -> 0x006f }
        L_0x005e:
            java.net.MulticastSocket r4 = r8.socket     // Catch:{ Exception -> 0x006f }
            java.net.DatagramPacket r5 = r8.tmpPacket     // Catch:{ Exception -> 0x006f }
            r4.send(r5)     // Catch:{ Exception -> 0x006f }
            r9.remove()     // Catch:{ Exception -> 0x006f }
            goto L_0x0073
        L_0x0069:
            java.nio.channels.NotYetConnectedException r4 = new java.nio.channels.NotYetConnectedException     // Catch:{ Exception -> 0x006f }
            r4.<init>()     // Catch:{ Exception -> 0x006f }
            throw r4     // Catch:{ Exception -> 0x006f }
        L_0x006f:
            r4 = move-exception
            r9.remove(r4)
        L_0x0073:
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.socket.oio.OioDatagramChannel.doWrite(io.netty.channel.ChannelOutboundBuffer):void");
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object msg) {
        if ((msg instanceof DatagramPacket) || (msg instanceof ByteBuf)) {
            return msg;
        }
        if ((msg instanceof AddressedEnvelope) && (((AddressedEnvelope) msg).content() instanceof ByteBuf)) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return joinGroup(multicastAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        ensureBound();
        try {
            this.socket.joinGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return joinGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        ensureBound();
        try {
            this.socket.joinGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    private void ensureBound() {
        if (!isActive()) {
            throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
        }
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return leaveGroup(multicastAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            this.socket.leaveGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return leaveGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        try {
            this.socket.leaveGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }
}
