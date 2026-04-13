package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.AttributeMap;
import io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;

public interface ChannelHandlerContext extends AttributeMap {
    ByteBufAllocator alloc();

    ChannelFuture bind(SocketAddress socketAddress);

    ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise);

    Channel channel();

    ChannelFuture close();

    ChannelFuture close(ChannelPromise channelPromise);

    ChannelFuture connect(SocketAddress socketAddress);

    ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2);

    ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

    ChannelFuture deregister();

    ChannelFuture deregister(ChannelPromise channelPromise);

    ChannelFuture disconnect();

    ChannelFuture disconnect(ChannelPromise channelPromise);

    EventExecutor executor();

    ChannelHandlerContext fireChannelActive();

    ChannelHandlerContext fireChannelInactive();

    ChannelHandlerContext fireChannelRead(Object obj);

    ChannelHandlerContext fireChannelReadComplete();

    ChannelHandlerContext fireChannelRegistered();

    ChannelHandlerContext fireChannelUnregistered();

    ChannelHandlerContext fireChannelWritabilityChanged();

    ChannelHandlerContext fireExceptionCaught(Throwable th);

    ChannelHandlerContext fireUserEventTriggered(Object obj);

    ChannelHandlerContext flush();

    ChannelHandler handler();

    boolean isRemoved();

    String name();

    ChannelFuture newFailedFuture(Throwable th);

    ChannelProgressivePromise newProgressivePromise();

    ChannelPromise newPromise();

    ChannelFuture newSucceededFuture();

    ChannelPipeline pipeline();

    ChannelHandlerContext read();

    ChannelPromise voidPromise();

    ChannelFuture write(Object obj);

    ChannelFuture write(Object obj, ChannelPromise channelPromise);

    ChannelFuture writeAndFlush(Object obj);

    ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise);
}
