package io.netty.channel;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {
    void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise);

    void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise);

    void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

    void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise);

    void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise);

    void flush(ChannelHandlerContext channelHandlerContext);

    void read(ChannelHandlerContext channelHandlerContext);

    void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise);
}
