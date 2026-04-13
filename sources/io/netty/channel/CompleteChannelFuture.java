package io.netty.channel;

import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class CompleteChannelFuture extends CompleteFuture<Void> implements ChannelFuture {
    private final Channel channel;

    protected CompleteChannelFuture(Channel channel2, EventExecutor executor) {
        super(executor);
        if (channel2 != null) {
            this.channel = channel2;
            return;
        }
        throw new NullPointerException("channel");
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        EventExecutor e = super.executor();
        if (e == null) {
            return channel().eventLoop();
        }
        return e;
    }

    public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener(listener);
        return this;
    }

    public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener(listener);
        return this;
    }

    public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.removeListeners(listeners);
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        return this;
    }

    public ChannelFuture sync() {
        return this;
    }

    public ChannelFuture await() {
        return this;
    }

    public ChannelFuture awaitUninterruptibly() {
        return this;
    }

    public Channel channel() {
        return this.channel;
    }

    public Void getNow() {
        return null;
    }
}
