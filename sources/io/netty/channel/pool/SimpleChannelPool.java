package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import java.util.Deque;

public class SimpleChannelPool implements ChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final IllegalStateException FULL_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("ChannelPool full"), SimpleChannelPool.class, "releaseAndOffer(...)"));
    private static final AttributeKey<SimpleChannelPool> POOL_KEY = AttributeKey.newInstance("channelPool");
    private final Bootstrap bootstrap;
    private final Deque<Channel> deque;
    private final ChannelPoolHandler handler;
    private final ChannelHealthChecker healthCheck;
    private final boolean lastRecentUsed;
    private final boolean releaseHealthCheck;

    static {
        Class<SimpleChannelPool> cls = SimpleChannelPool.class;
    }

    public SimpleChannelPool(Bootstrap bootstrap2, ChannelPoolHandler handler2) {
        this(bootstrap2, handler2, ChannelHealthChecker.ACTIVE);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, ChannelPoolHandler handler2, ChannelHealthChecker healthCheck2) {
        this(bootstrap2, handler2, healthCheck2, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, ChannelPoolHandler handler2, ChannelHealthChecker healthCheck2, boolean releaseHealthCheck2) {
        this(bootstrap2, handler2, healthCheck2, releaseHealthCheck2, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, final ChannelPoolHandler handler2, ChannelHealthChecker healthCheck2, boolean releaseHealthCheck2, boolean lastRecentUsed2) {
        this.deque = PlatformDependent.newConcurrentDeque();
        this.handler = (ChannelPoolHandler) ObjectUtil.checkNotNull(handler2, "handler");
        this.healthCheck = (ChannelHealthChecker) ObjectUtil.checkNotNull(healthCheck2, "healthCheck");
        this.releaseHealthCheck = releaseHealthCheck2;
        Bootstrap clone = ((Bootstrap) ObjectUtil.checkNotNull(bootstrap2, "bootstrap")).clone();
        this.bootstrap = clone;
        clone.handler(new ChannelInitializer<Channel>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<SimpleChannelPool> cls = SimpleChannelPool.class;
            }

            /* access modifiers changed from: protected */
            public void initChannel(Channel ch) {
                if (ch.eventLoop().inEventLoop()) {
                    handler2.channelCreated(ch);
                    return;
                }
                throw new AssertionError();
            }
        });
        this.lastRecentUsed = lastRecentUsed2;
    }

    /* access modifiers changed from: protected */
    public Bootstrap bootstrap() {
        return this.bootstrap;
    }

    /* access modifiers changed from: protected */
    public ChannelPoolHandler handler() {
        return this.handler;
    }

    /* access modifiers changed from: protected */
    public ChannelHealthChecker healthChecker() {
        return this.healthCheck;
    }

    /* access modifiers changed from: protected */
    public boolean releaseHealthCheck() {
        return this.releaseHealthCheck;
    }

    public final Future<Channel> acquire() {
        return acquire(this.bootstrap.group().next().newPromise());
    }

    public Future<Channel> acquire(Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        return acquireHealthyFromPoolOrNew(promise);
    }

    private Future<Channel> acquireHealthyFromPoolOrNew(final Promise<Channel> promise) {
        try {
            final Channel ch = pollChannel();
            if (ch == null) {
                Bootstrap bs = this.bootstrap.clone();
                bs.attr(POOL_KEY, this);
                ChannelFuture f = connectChannel(bs);
                if (f.isDone()) {
                    notifyConnect(f, promise);
                } else {
                    f.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) {
                            SimpleChannelPool.this.notifyConnect(future, promise);
                        }
                    });
                }
                return promise;
            }
            EventLoop loop = ch.eventLoop();
            if (loop.inEventLoop()) {
                doHealthCheck(ch, promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        SimpleChannelPool.this.doHealthCheck(ch, promise);
                    }
                });
            }
            return promise;
        } catch (Throwable cause) {
            promise.tryFailure(cause);
        }
    }

    /* access modifiers changed from: private */
    public void notifyConnect(ChannelFuture future, Promise<Channel> promise) {
        if (future.isSuccess()) {
            Channel channel = future.channel();
            if (!promise.trySuccess(channel)) {
                release(channel);
                return;
            }
            return;
        }
        promise.tryFailure(future.cause());
    }

    /* access modifiers changed from: private */
    public void doHealthCheck(final Channel ch, final Promise<Channel> promise) {
        if (ch.eventLoop().inEventLoop()) {
            Future<Boolean> f = this.healthCheck.isHealthy(ch);
            if (f.isDone()) {
                notifyHealthCheck(f, ch, promise);
            } else {
                f.addListener(new FutureListener<Boolean>() {
                    public void operationComplete(Future<Boolean> future) {
                        SimpleChannelPool.this.notifyHealthCheck(future, ch, promise);
                    }
                });
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public void notifyHealthCheck(Future<Boolean> future, Channel ch, Promise<Channel> promise) {
        if (!ch.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (!future.isSuccess()) {
            closeChannel(ch);
            acquireHealthyFromPoolOrNew(promise);
        } else if (future.getNow() == Boolean.TRUE) {
            try {
                ch.attr(POOL_KEY).set(this);
                this.handler.channelAcquired(ch);
                promise.setSuccess(ch);
            } catch (Throwable cause) {
                closeAndFail(ch, cause, promise);
            }
        } else {
            closeChannel(ch);
            acquireHealthyFromPoolOrNew(promise);
        }
    }

    /* access modifiers changed from: protected */
    public ChannelFuture connectChannel(Bootstrap bs) {
        return bs.connect();
    }

    public final Future<Void> release(Channel channel) {
        return release(channel, channel.eventLoop().newPromise());
    }

    public Future<Void> release(final Channel channel, final Promise<Void> promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            EventLoop loop = channel.eventLoop();
            if (loop.inEventLoop()) {
                doReleaseChannel(channel, promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        SimpleChannelPool.this.doReleaseChannel(channel, promise);
                    }
                });
            }
        } catch (Throwable cause) {
            closeAndFail(channel, cause, promise);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void doReleaseChannel(Channel channel, Promise<Void> promise) {
        if (!channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (channel.attr(POOL_KEY).getAndSet(null) != this) {
            closeAndFail(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
        } else {
            try {
                if (this.releaseHealthCheck) {
                    doHealthCheckOnRelease(channel, promise);
                } else {
                    releaseAndOffer(channel, promise);
                }
            } catch (Throwable cause) {
                closeAndFail(channel, cause, promise);
            }
        }
    }

    private void doHealthCheckOnRelease(final Channel channel, final Promise<Void> promise) {
        final Future<Boolean> f = this.healthCheck.isHealthy(channel);
        if (f.isDone()) {
            releaseAndOfferIfHealthy(channel, promise, f);
        } else {
            f.addListener(new FutureListener<Boolean>() {
                public void operationComplete(Future<Boolean> future) {
                    SimpleChannelPool.this.releaseAndOfferIfHealthy(channel, promise, f);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void releaseAndOfferIfHealthy(Channel channel, Promise<Void> promise, Future<Boolean> future) {
        if (future.getNow().booleanValue()) {
            releaseAndOffer(channel, promise);
            return;
        }
        this.handler.channelReleased(channel);
        promise.setSuccess(null);
    }

    private void releaseAndOffer(Channel channel, Promise<Void> promise) {
        if (offerChannel(channel)) {
            this.handler.channelReleased(channel);
            promise.setSuccess(null);
            return;
        }
        closeAndFail(channel, FULL_EXCEPTION, promise);
    }

    private static void closeChannel(Channel channel) {
        channel.attr(POOL_KEY).getAndSet(null);
        channel.close();
    }

    private static void closeAndFail(Channel channel, Throwable cause, Promise<?> promise) {
        closeChannel(channel);
        promise.tryFailure(cause);
    }

    /* access modifiers changed from: protected */
    public Channel pollChannel() {
        return (Channel) (this.lastRecentUsed ? this.deque.pollLast() : this.deque.pollFirst());
    }

    /* access modifiers changed from: protected */
    public boolean offerChannel(Channel channel) {
        return this.deque.offer(channel);
    }

    public void close() {
        while (true) {
            Channel channel = pollChannel();
            if (channel != null) {
                channel.close();
            } else {
                return;
            }
        }
    }
}
