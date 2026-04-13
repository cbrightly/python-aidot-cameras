package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.SocketChannelConfig;

public interface OioSocketChannelConfig extends SocketChannelConfig {
    int getSoTimeout();

    OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioSocketChannelConfig setAllowHalfClosure(boolean z);

    OioSocketChannelConfig setAutoClose(boolean z);

    OioSocketChannelConfig setAutoRead(boolean z);

    OioSocketChannelConfig setConnectTimeoutMillis(int i);

    OioSocketChannelConfig setKeepAlive(boolean z);

    OioSocketChannelConfig setMaxMessagesPerRead(int i);

    OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    OioSocketChannelConfig setReceiveBufferSize(int i);

    OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioSocketChannelConfig setReuseAddress(boolean z);

    OioSocketChannelConfig setSendBufferSize(int i);

    OioSocketChannelConfig setSoLinger(int i);

    OioSocketChannelConfig setSoTimeout(int i);

    OioSocketChannelConfig setTcpNoDelay(boolean z);

    OioSocketChannelConfig setTrafficClass(int i);

    OioSocketChannelConfig setWriteBufferHighWaterMark(int i);

    OioSocketChannelConfig setWriteBufferLowWaterMark(int i);

    OioSocketChannelConfig setWriteSpinCount(int i);
}
