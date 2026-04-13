package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannelConfig;
import java.net.InetAddress;
import java.net.NetworkInterface;

public interface OioDatagramChannelConfig extends DatagramChannelConfig {
    int getSoTimeout();

    OioDatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioDatagramChannelConfig setAutoClose(boolean z);

    OioDatagramChannelConfig setAutoRead(boolean z);

    OioDatagramChannelConfig setBroadcast(boolean z);

    OioDatagramChannelConfig setConnectTimeoutMillis(int i);

    OioDatagramChannelConfig setInterface(InetAddress inetAddress);

    OioDatagramChannelConfig setLoopbackModeDisabled(boolean z);

    OioDatagramChannelConfig setMaxMessagesPerRead(int i);

    OioDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

    OioDatagramChannelConfig setReceiveBufferSize(int i);

    OioDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioDatagramChannelConfig setReuseAddress(boolean z);

    OioDatagramChannelConfig setSendBufferSize(int i);

    OioDatagramChannelConfig setSoTimeout(int i);

    OioDatagramChannelConfig setTimeToLive(int i);

    OioDatagramChannelConfig setTrafficClass(int i);

    OioDatagramChannelConfig setWriteBufferHighWaterMark(int i);

    OioDatagramChannelConfig setWriteBufferLowWaterMark(int i);

    OioDatagramChannelConfig setWriteSpinCount(int i);
}
