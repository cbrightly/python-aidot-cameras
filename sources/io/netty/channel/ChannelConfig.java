package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import java.util.Map;

public interface ChannelConfig {
    ByteBufAllocator getAllocator();

    int getConnectTimeoutMillis();

    int getMaxMessagesPerRead();

    MessageSizeEstimator getMessageSizeEstimator();

    <T> T getOption(ChannelOption<T> channelOption);

    Map<ChannelOption<?>, Object> getOptions();

    RecvByteBufAllocator getRecvByteBufAllocator();

    int getWriteBufferHighWaterMark();

    int getWriteBufferLowWaterMark();

    int getWriteSpinCount();

    @Deprecated
    boolean isAutoClose();

    boolean isAutoRead();

    ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Deprecated
    ChannelConfig setAutoClose(boolean z);

    ChannelConfig setAutoRead(boolean z);

    ChannelConfig setConnectTimeoutMillis(int i);

    ChannelConfig setMaxMessagesPerRead(int i);

    ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    <T> boolean setOption(ChannelOption<T> channelOption, T t);

    boolean setOptions(Map<ChannelOption<?>, ?> map);

    ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    ChannelConfig setWriteBufferHighWaterMark(int i);

    ChannelConfig setWriteBufferLowWaterMark(int i);

    ChannelConfig setWriteSpinCount(int i);
}
