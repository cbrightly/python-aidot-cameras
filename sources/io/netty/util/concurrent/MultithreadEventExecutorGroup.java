package io.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
    /* access modifiers changed from: private */
    public final AtomicInteger childIndex = new AtomicInteger();
    /* access modifiers changed from: private */
    public final EventExecutor[] children;
    private final EventExecutorChooser chooser;
    /* access modifiers changed from: private */
    public final AtomicInteger terminatedChildren = new AtomicInteger();
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);

    public interface EventExecutorChooser {
        EventExecutor next();
    }

    /* access modifiers changed from: protected */
    public abstract EventExecutor newChild(ThreadFactory threadFactory, Object... objArr);

    protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        if (nThreads > 0) {
            threadFactory = threadFactory == null ? newDefaultThreadFactory() : threadFactory;
            SingleThreadEventExecutor[] singleThreadEventExecutorArr = new SingleThreadEventExecutor[nThreads];
            this.children = singleThreadEventExecutorArr;
            if (isPowerOfTwo(singleThreadEventExecutorArr.length)) {
                this.chooser = new PowerOfTwoEventExecutorChooser();
            } else {
                this.chooser = new GenericEventExecutorChooser();
            }
            int i = 0;
            while (i < nThreads) {
                try {
                    this.children[i] = newChild(threadFactory, args);
                    if (1 == 0) {
                        for (int j = 0; j < i; j++) {
                            this.children[j].shutdownGracefully();
                        }
                        for (int j2 = 0; j2 < i; j2++) {
                            EventExecutor e = this.children[j2];
                            while (!e.isTerminated()) {
                                try {
                                    e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                                } catch (InterruptedException e2) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    }
                    i++;
                } catch (Exception e3) {
                    throw new IllegalStateException("failed to create a child event loop", e3);
                } catch (Throwable th) {
                    if (0 == 0) {
                        for (int j3 = 0; j3 < i; j3++) {
                            this.children[j3].shutdownGracefully();
                        }
                        for (int j4 = 0; j4 < i; j4++) {
                            EventExecutor e4 = this.children[j4];
                            while (!e4.isTerminated()) {
                                try {
                                    e4.awaitTermination(2147483647L, TimeUnit.SECONDS);
                                } catch (InterruptedException e5) {
                                    Thread.currentThread().interrupt();
                                    throw th;
                                }
                            }
                        }
                    }
                    throw th;
                }
            }
            FutureListener<Object> terminationListener = new FutureListener<Object>() {
                public void operationComplete(Future<Object> future) {
                    if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
                        MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
                    }
                }
            };
            for (EventExecutor e6 : this.children) {
                e6.terminationFuture().addListener(terminationListener);
            }
            return;
        }
        throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[]{Integer.valueOf(nThreads)}));
    }

    /* access modifiers changed from: protected */
    public ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass());
    }

    public EventExecutor next() {
        return this.chooser.next();
    }

    public Iterator<EventExecutor> iterator() {
        return children().iterator();
    }

    public final int executorCount() {
        return this.children.length;
    }

    /* access modifiers changed from: protected */
    public Set<EventExecutor> children() {
        Set<EventExecutor> children2 = Collections.newSetFromMap(new LinkedHashMap());
        Collections.addAll(children2, this.children);
        return children2;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        for (EventExecutor l : this.children) {
            l.shutdownGracefully(quietPeriod, timeout, unit);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        for (EventExecutor l : this.children) {
            l.shutdown();
        }
    }

    public boolean isShuttingDown() {
        for (EventExecutor l : this.children) {
            if (!l.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (EventExecutor l : this.children) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (EventExecutor l : this.children) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        long timeLeft;
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        loop0:
        for (EventExecutor l : this.children) {
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    break loop0;
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return isTerminated();
    }

    private static boolean isPowerOfTwo(int val) {
        return ((-val) & val) == val;
    }

    public final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser {
        private PowerOfTwoEventExecutorChooser() {
        }

        public EventExecutor next() {
            return MultithreadEventExecutorGroup.this.children[MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() & (MultithreadEventExecutorGroup.this.children.length - 1)];
        }
    }

    public final class GenericEventExecutorChooser implements EventExecutorChooser {
        private GenericEventExecutorChooser() {
        }

        public EventExecutor next() {
            return MultithreadEventExecutorGroup.this.children[Math.abs(MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() % MultithreadEventExecutorGroup.this.children.length)];
        }
    }
}
