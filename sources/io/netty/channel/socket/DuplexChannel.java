package io.netty.channel.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;

public interface DuplexChannel extends Channel {
    boolean isInputShutdown();

    boolean isOutputShutdown();

    ChannelFuture shutdownOutput();

    ChannelFuture shutdownOutput(ChannelPromise channelPromise);
}
