package io.netty.channel.pool;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;

public interface ChannelHealthChecker {
    public static final ChannelHealthChecker ACTIVE = new ChannelHealthChecker() {
        public Future<Boolean> isHealthy(Channel channel) {
            return channel.eventLoop().newSucceededFuture(channel.isActive() ? Boolean.TRUE : Boolean.FALSE);
        }
    };

    Future<Boolean> isHealthy(Channel channel);
}
