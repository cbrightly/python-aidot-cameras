package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;

public interface EventLoopGroup extends EventExecutorGroup {
    EventLoop next();

    ChannelFuture register(Channel channel);

    ChannelFuture register(Channel channel, ChannelPromise channelPromise);
}
