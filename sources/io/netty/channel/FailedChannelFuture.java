package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.PlatformDependent;

public final class FailedChannelFuture extends CompleteChannelFuture {
    private final Throwable cause;

    FailedChannelFuture(Channel channel, EventExecutor executor, Throwable cause2) {
        super(channel, executor);
        if (cause2 != null) {
            this.cause = cause2;
            return;
        }
        throw new NullPointerException("cause");
    }

    public Throwable cause() {
        return this.cause;
    }

    public boolean isSuccess() {
        return false;
    }

    public ChannelFuture sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
}
