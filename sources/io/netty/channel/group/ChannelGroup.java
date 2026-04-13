package io.netty.channel.group;

import io.netty.channel.Channel;
import java.util.Set;

public interface ChannelGroup extends Set<Channel>, Comparable<ChannelGroup> {
    ChannelGroupFuture close();

    ChannelGroupFuture close(ChannelMatcher channelMatcher);

    @Deprecated
    ChannelGroupFuture deregister();

    @Deprecated
    ChannelGroupFuture deregister(ChannelMatcher channelMatcher);

    ChannelGroupFuture disconnect();

    ChannelGroupFuture disconnect(ChannelMatcher channelMatcher);

    ChannelGroup flush();

    ChannelGroup flush(ChannelMatcher channelMatcher);

    @Deprecated
    ChannelGroupFuture flushAndWrite(Object obj);

    @Deprecated
    ChannelGroupFuture flushAndWrite(Object obj, ChannelMatcher channelMatcher);

    String name();

    ChannelGroupFuture write(Object obj);

    ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher);

    ChannelGroupFuture writeAndFlush(Object obj);

    ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher);
}
