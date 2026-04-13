package io.netty.channel.pool;

import io.netty.channel.Channel;

public abstract class AbstractChannelPoolHandler implements ChannelPoolHandler {
    public void channelAcquired(Channel ch) {
    }

    public void channelReleased(Channel ch) {
    }
}
