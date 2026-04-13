package io.netty.channel;

import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;

public class DefaultChannelPromise extends DefaultPromise<Void> implements ChannelPromise, ChannelFlushPromiseNotifier.FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public DefaultChannelPromise(Channel channel2) {
        this.channel = (Channel) ObjectUtil.checkNotNull(channel2, "channel");
    }

    public DefaultChannelPromise(Channel channel2, EventExecutor executor) {
        super(executor);
        this.channel = (Channel) ObjectUtil.checkNotNull(channel2, "channel");
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        EventExecutor e = super.executor();
        if (e == null) {
            return channel().eventLoop();
        }
        return e;
    }

    public Channel channel() {
        return this.channel;
    }

    public ChannelPromise setSuccess() {
        return setSuccess((Void) null);
    }

    public ChannelPromise setSuccess(Void result) {
        super.setSuccess(result);
        return this;
    }

    public boolean trySuccess() {
        return trySuccess(null);
    }

    public ChannelPromise setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    public ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener((GenericFutureListener) listener);
        return this;
    }

    public ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.addListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener((GenericFutureListener) listener);
        return this;
    }

    public ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.removeListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ChannelPromise sync() {
        super.sync();
        return this;
    }

    public ChannelPromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ChannelPromise await() {
        super.await();
        return this;
    }

    public ChannelPromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public long flushCheckpoint() {
        return this.checkpoint;
    }

    public void flushCheckpoint(long checkpoint2) {
        this.checkpoint = checkpoint2;
    }

    public ChannelPromise promise() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        if (channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
}
