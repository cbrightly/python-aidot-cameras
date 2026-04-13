package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.MembershipKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName((Class<?>) DatagramPacket.class) + ", " + StringUtil.simpleClassName((Class<?>) AddressedEnvelope.class) + '<' + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) SocketAddress.class) + ">, " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private RecvByteBufAllocator.Handle allocHandle;
    private final DatagramChannelConfig config;
    private Map<InetAddress, List<MembershipKey>> memberships;

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openDatagramChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider provider, InternetProtocolFamily ipFamily) {
        if (ipFamily == null) {
            return newSocket(provider);
        }
        checkJavaVersion();
        try {
            return provider.openDatagramChannel(ProtocolFamilyConverter.convert(ipFamily));
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static void checkJavaVersion() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }

    public NioDatagramChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioDatagramChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioDatagramChannel(InternetProtocolFamily ipFamily) {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER, ipFamily));
    }

    public NioDatagramChannel(SelectorProvider provider, InternetProtocolFamily ipFamily) {
        this(newSocket(provider, ipFamily));
    }

    public NioDatagramChannel(java.nio.channels.DatagramChannel socket) {
        super((Channel) null, socket, 1);
        this.config = new NioDatagramChannelConfig(this, socket);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        java.nio.channels.DatagramChannel ch = javaChannel();
        return ch.isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || ch.socket().isBound());
    }

    public boolean isConnected() {
        return javaChannel().isConnected();
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel) super.javaChannel();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        doBind0(localAddress);
    }

    private void doBind0(SocketAddress localAddress) {
        if (PlatformDependent.javaVersion() >= 7) {
            SocketUtils.bind(javaChannel(), localAddress);
        } else {
            javaChannel().socket().bind(localAddress);
        }
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        if (localAddress != null) {
            doBind0(localAddress);
        }
        boolean success = false;
        try {
            javaChannel().connect(remoteAddress);
            success = true;
            return true;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() {
        throw new Error();
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        javaChannel().disconnect();
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> buf) {
        java.nio.channels.DatagramChannel ch = javaChannel();
        DatagramChannelConfig config2 = config();
        RecvByteBufAllocator.Handle allocHandle2 = this.allocHandle;
        if (allocHandle2 == null) {
            RecvByteBufAllocator.Handle newHandle = config2.getRecvByteBufAllocator().newHandle();
            allocHandle2 = newHandle;
            this.allocHandle = newHandle;
        }
        ByteBuf data = allocHandle2.allocate(config2.getAllocator());
        try {
            ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
            int pos = nioData.position();
            InetSocketAddress remoteAddress = (InetSocketAddress) ch.receive(nioData);
            if (remoteAddress == null) {
                if (1 != 0) {
                    data.release();
                }
                return 0;
            }
            int readBytes = nioData.position() - pos;
            data.writerIndex(data.writerIndex() + readBytes);
            allocHandle2.record(readBytes);
            buf.add(new DatagramPacket(data, localAddress(), remoteAddress));
            if (0 != 0) {
                data.release();
            }
            return 1;
        } catch (Throwable cause) {
            if (1 != 0) {
                data.release();
            }
            throw cause;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: io.netty.buffer.ByteBuf} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean doWriteMessage(java.lang.Object r7, io.netty.channel.ChannelOutboundBuffer r8) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof io.netty.channel.AddressedEnvelope
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.netty.channel.AddressedEnvelope r0 = (io.netty.channel.AddressedEnvelope) r0
            java.net.SocketAddress r1 = r0.recipient()
            java.lang.Object r2 = r0.content()
            r0 = r2
            io.netty.buffer.ByteBuf r0 = (io.netty.buffer.ByteBuf) r0
            goto L_0x0017
        L_0x0013:
            r0 = r7
            io.netty.buffer.ByteBuf r0 = (io.netty.buffer.ByteBuf) r0
            r1 = 0
        L_0x0017:
            int r2 = r0.readableBytes()
            r3 = 1
            if (r2 != 0) goto L_0x001f
            return r3
        L_0x001f:
            int r4 = r0.readerIndex()
            java.nio.ByteBuffer r4 = r0.internalNioBuffer(r4, r2)
            if (r1 == 0) goto L_0x0032
            java.nio.channels.DatagramChannel r5 = r6.javaChannel()
            int r5 = r5.send(r4, r1)
            goto L_0x003a
        L_0x0032:
            java.nio.channels.DatagramChannel r5 = r6.javaChannel()
            int r5 = r5.write(r4)
        L_0x003a:
            if (r5 <= 0) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r3 = 0
        L_0x003e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.socket.nio.NioDatagramChannel.doWriteMessage(java.lang.Object, io.netty.channel.ChannelOutboundBuffer):boolean");
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object msg) {
        if (msg instanceof DatagramPacket) {
            DatagramPacket p = (DatagramPacket) msg;
            ByteBuf content = (ByteBuf) p.content();
            if (isSingleDirectBuffer(content)) {
                return p;
            }
            return new DatagramPacket(newDirectBuffer(p, content), (InetSocketAddress) p.recipient());
        } else if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (isSingleDirectBuffer(buf)) {
                return buf;
            }
            return newDirectBuffer(buf);
        } else {
            if (msg instanceof AddressedEnvelope) {
                AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope) msg;
                if (e.content() instanceof ByteBuf) {
                    ByteBuf content2 = (ByteBuf) e.content();
                    if (isSingleDirectBuffer(content2)) {
                        return e;
                    }
                    return new DefaultAddressedEnvelope(newDirectBuffer(e, content2), e.recipient());
                }
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    private static boolean isSingleDirectBuffer(ByteBuf buf) {
        return buf.isDirect() && buf.nioBufferCount() == 1;
    }

    /* access modifiers changed from: protected */
    public boolean continueOnWriteError() {
        return true;
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return joinGroup(multicastAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            return joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress) null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
            return promise;
        }
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return joinGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress) null, promise);
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return joinGroup(multicastAddress, networkInterface, source, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        MembershipKey key;
        checkJavaVersion();
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            if (source == null) {
                try {
                    key = javaChannel().join(multicastAddress, networkInterface);
                } catch (Throwable e) {
                    promise.setFailure(e);
                }
            } else {
                key = javaChannel().join(multicastAddress, networkInterface, source);
            }
            synchronized (this) {
                List<MembershipKey> keys = null;
                Map<InetAddress, List<MembershipKey>> map = this.memberships;
                if (map == null) {
                    this.memberships = new HashMap();
                } else {
                    keys = map.get(multicastAddress);
                }
                if (keys == null) {
                    keys = new ArrayList<>();
                    this.memberships.put(multicastAddress, keys);
                }
                keys.add(key);
            }
            promise.setSuccess();
            return promise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return leaveGroup(multicastAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            return leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress) null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
            return promise;
        }
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return leaveGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress) null, promise);
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return leaveGroup(multicastAddress, networkInterface, source, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        List<MembershipKey> keys;
        checkJavaVersion();
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            synchronized (this) {
                Map<InetAddress, List<MembershipKey>> map = this.memberships;
                if (!(map == null || (keys = map.get(multicastAddress)) == null)) {
                    Iterator<MembershipKey> keyIt = keys.iterator();
                    while (keyIt.hasNext()) {
                        MembershipKey key = keyIt.next();
                        if (networkInterface.equals(key.networkInterface()) && ((source == null && key.sourceAddress() == null) || (source != null && source.equals(key.sourceAddress())))) {
                            key.drop();
                            keyIt.remove();
                        }
                    }
                    if (keys.isEmpty()) {
                        this.memberships.remove(multicastAddress);
                    }
                }
            }
            promise.setSuccess();
            return promise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        checkJavaVersion();
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (sourceToBlock == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface != null) {
            synchronized (this) {
                Map<InetAddress, List<MembershipKey>> map = this.memberships;
                if (map != null) {
                    for (MembershipKey key : map.get(multicastAddress)) {
                        if (networkInterface.equals(key.networkInterface())) {
                            try {
                                key.block(sourceToBlock);
                            } catch (IOException e) {
                                promise.setFailure(e);
                            }
                        }
                    }
                }
            }
            promise.setSuccess();
            return promise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return block(multicastAddress, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        try {
            return block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
            return promise;
        }
    }

    /* access modifiers changed from: protected */
    public void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }

    /* access modifiers changed from: protected */
    public boolean closeOnReadError(Throwable cause) {
        if (cause instanceof SocketException) {
            return false;
        }
        return super.closeOnReadError(cause);
    }
}
