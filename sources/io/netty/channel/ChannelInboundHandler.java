package io.netty.channel;

public interface ChannelInboundHandler extends ChannelHandler {
    void channelActive(ChannelHandlerContext channelHandlerContext);

    void channelInactive(ChannelHandlerContext channelHandlerContext);

    void channelRead(ChannelHandlerContext channelHandlerContext, Object obj);

    void channelReadComplete(ChannelHandlerContext channelHandlerContext);

    void channelRegistered(ChannelHandlerContext channelHandlerContext);

    void channelUnregistered(ChannelHandlerContext channelHandlerContext);

    void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext);

    void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th);

    void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj);
}
