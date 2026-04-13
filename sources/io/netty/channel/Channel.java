package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.AttributeMap;
import java.net.SocketAddress;

public interface Channel extends AttributeMap, Comparable<Channel> {

    public interface Unsafe {
        void beginRead();

        void bind(SocketAddress socketAddress, ChannelPromise channelPromise);

        void close(ChannelPromise channelPromise);

        void closeForcibly();

        void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

        void deregister(ChannelPromise channelPromise);

        void disconnect(ChannelPromise channelPromise);

        void flush();

        SocketAddress localAddress();

        ChannelOutboundBuffer outboundBuffer();

        void register(EventLoop eventLoop, ChannelPromise channelPromise);

        SocketAddress remoteAddress();

        ChannelPromise voidPromise();

        void write(Object obj, ChannelPromise channelPromise);
    }

    ByteBufAllocator alloc();

    ChannelFuture bind(SocketAddress socketAddress);

    ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise);

    ChannelFuture close();

    ChannelFuture close(ChannelPromise channelPromise);

    ChannelFuture closeFuture();

    ChannelConfig config();

    ChannelFuture connect(SocketAddress socketAddress);

    ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

    ChannelFuture deregister();

    ChannelFuture deregister(ChannelPromise channelPromise);

    ChannelFuture disconnect();

    ChannelFuture disconnect(ChannelPromise channelPromise);

    EventLoop eventLoop();

    Channel flush();

    boolean isActive();

    boolean isOpen();

    boolean isRegistered();

    boolean isWritable();

    SocketAddress localAddress();

    ChannelMetadata metadata();

    ChannelFuture newFailedFuture(Throwable th);

    ChannelProgressivePromise newProgressivePromise();

    ChannelPromise newPromise();

    ChannelFuture newSucceededFuture();

    Channel parent();

    ChannelPipeline pipeline();

    Channel read();

    SocketAddress remoteAddress();

    Unsafe unsafe();

    ChannelPromise voidPromise();

    ChannelFuture write(Object obj);

    ChannelFuture write(Object obj, ChannelPromise channelPromise);

    ChannelFuture writeAndFlush(Object obj);

    ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise);
}
