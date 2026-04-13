package io.netty.channel;

import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class DefaultChannelProgressivePromise extends DefaultProgressivePromise<Void> implements ChannelProgressivePromise, ChannelFlushPromiseNotifier.FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public DefaultChannelProgressivePromise(Channel channel2) {
        this.channel = channel2;
    }

    public DefaultChannelProgressivePromise(Channel channel2, EventExecutor executor) {
        super(executor);
        this.channel = channel2;
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

    public ChannelProgressivePromise setSuccess() {
        return setSuccess((Void) null);
    }

    public ChannelProgressivePromise setSuccess(Void result) {
        super.setSuccess(result);
        return this;
    }

    public boolean trySuccess() {
        return trySuccess(null);
    }

    public ChannelProgressivePromise setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    public ChannelProgressivePromise setProgress(long progress, long total) {
        super.setProgress(progress, total);
        return this;
    }

    public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener((GenericFutureListener) listener);
        return this;
    }

    public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.addListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener((GenericFutureListener) listener);
        return this;
    }

    public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.removeListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ChannelProgressivePromise sync() {
        super.sync();
        return this;
    }

    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ChannelProgressivePromise await() {
        super.await();
        return this;
    }

    public ChannelProgressivePromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public long flushCheckpoint() {
        return this.checkpoint;
    }

    public void flushCheckpoint(long checkpoint2) {
        this.checkpoint = checkpoint2;
    }

    public ChannelProgressivePromise promise() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        if (channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
}
