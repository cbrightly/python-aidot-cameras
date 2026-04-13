package io.netty.channel.pool;

import io.netty.channel.Channel;

public interface ChannelPoolHandler {
    void channelAcquired(Channel channel);

    void channelCreated(Channel channel);

    void channelReleased(Channel channel);
}
