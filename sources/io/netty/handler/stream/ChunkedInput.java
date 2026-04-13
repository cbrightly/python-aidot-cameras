package io.netty.handler.stream;

import io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
    void close();

    boolean isEndOfInput();

    B readChunk(ChannelHandlerContext channelHandlerContext);
}
