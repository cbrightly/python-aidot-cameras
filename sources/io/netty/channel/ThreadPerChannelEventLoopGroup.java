package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import io.netty.util.internal.ThrowableUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    final Set<ThreadPerChannelEventLoop> activeChildren;
    private final Object[] childArgs;
    private final FutureListener<Object> childTerminationListener;
    final Queue<ThreadPerChannelEventLoop> idleChildren;
    private final int maxChannels;
    private volatile boolean shuttingDown;
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture;
    final ThreadFactory threadFactory;
    private final ChannelException tooManyChannels;

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels2) {
        this(maxChannels2, Executors.defaultThreadFactory(), new Object[0]);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels2, ThreadFactory threadFactory2, Object... args) {
        this.activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
        this.idleChildren = new ConcurrentLinkedQueue();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.childTerminationListener = new FutureListener<Object>() {
            public void operationComplete(Future<Object> future) {
                if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
                    ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
                }
            }
        };
        if (maxChannels2 < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", new Object[]{Integer.valueOf(maxChannels2)}));
        } else if (threadFactory2 != null) {
            if (args == null) {
                this.childArgs = EmptyArrays.EMPTY_OBJECTS;
            } else {
                this.childArgs = (Object[]) args.clone();
            }
            this.maxChannels = maxChannels2;
            this.threadFactory = threadFactory2;
            this.tooManyChannels = (ChannelException) ThrowableUtil.unknownStackTrace(new ChannelException("too many channels (max: " + maxChannels2 + ')'), ThreadPerChannelEventLoopGroup.class, "nextChild()");
        } else {
            throw new NullPointerException("threadFactory");
        }
    }

    /* access modifiers changed from: protected */
    public ThreadPerChannelEventLoop newChild(Object... args) {
        return new ThreadPerChannelEventLoop(this);
    }

    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator(this.activeChildren.iterator());
    }

    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        this.shuttingDown = true;
        for (ThreadPerChannelEventLoop l : this.activeChildren) {
            l.shutdownGracefully(quietPeriod, timeout, unit);
        }
        for (EventLoop l2 : this.idleChildren) {
            l2.shutdownGracefully(quietPeriod, timeout, unit);
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        this.shuttingDown = true;
        for (ThreadPerChannelEventLoop l : this.activeChildren) {
            l.shutdown();
        }
        for (EventLoop l2 : this.idleChildren) {
            l2.shutdown();
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
    }

    public boolean isShuttingDown() {
        for (ThreadPerChannelEventLoop l : this.activeChildren) {
            if (!l.isShuttingDown()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (ThreadPerChannelEventLoop l : this.activeChildren) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (ThreadPerChannelEventLoop l : this.activeChildren) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        for (EventLoop l : this.activeChildren) {
            while (true) {
                long timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    return isTerminated();
                }
                if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
                }
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            while (true) {
                long timeLeft2 = deadline - System.nanoTime();
                if (timeLeft2 <= 0) {
                    return isTerminated();
                }
                if (l2.awaitTermination(timeLeft2, TimeUnit.NANOSECONDS)) {
                }
            }
        }
        return isTerminated();
    }

    public ChannelFuture register(Channel channel) {
        if (channel != null) {
            try {
                EventLoop l = nextChild();
                return l.register(channel, new DefaultChannelPromise(channel, l));
            } catch (Throwable t) {
                return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, t);
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        if (channel != null) {
            try {
                return nextChild().register(channel, promise);
            } catch (Throwable t) {
                promise.setFailure(t);
                return promise;
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    private EventLoop nextChild() {
        if (!this.shuttingDown) {
            ThreadPerChannelEventLoop loop = this.idleChildren.poll();
            if (loop == null) {
                if (this.maxChannels <= 0 || this.activeChildren.size() < this.maxChannels) {
                    loop = newChild(this.childArgs);
                    loop.terminationFuture().addListener(this.childTerminationListener);
                } else {
                    throw this.tooManyChannels;
                }
            }
            this.activeChildren.add(loop);
            return loop;
        }
        throw new RejectedExecutionException("shutting down");
    }
}
