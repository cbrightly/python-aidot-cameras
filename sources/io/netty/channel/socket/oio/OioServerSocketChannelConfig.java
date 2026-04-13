package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ServerSocketChannelConfig;

public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {
    int getSoTimeout();

    OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioServerSocketChannelConfig setAutoClose(boolean z);

    OioServerSocketChannelConfig setAutoRead(boolean z);

    OioServerSocketChannelConfig setBacklog(int i);

    OioServerSocketChannelConfig setConnectTimeoutMillis(int i);

    OioServerSocketChannelConfig setMaxMessagesPerRead(int i);

    OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    OioServerSocketChannelConfig setReceiveBufferSize(int i);

    OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioServerSocketChannelConfig setReuseAddress(boolean z);

    OioServerSocketChannelConfig setSoTimeout(int i);

    OioServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    OioServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    OioServerSocketChannelConfig setWriteSpinCount(int i);
}
