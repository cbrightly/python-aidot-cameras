package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public final class ImmediateEventExecutor extends AbstractEventExecutor {
    private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new FastThreadLocal<Queue<Runnable>>() {
        /* access modifiers changed from: protected */
        public Queue<Runnable> initialValue() {
            return new ArrayDeque();
        }
    };
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Boolean> RUNNING = new FastThreadLocal<Boolean>() {
        /* access modifiers changed from: protected */
        public Boolean initialValue() {
            return false;
        }
    };
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ImmediateEventExecutor.class);
    private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());

    private ImmediateEventExecutor() {
    }

    public EventExecutorGroup parent() {
        return null;
    }

    public boolean inEventLoop() {
        return true;
    }

    public boolean inEventLoop(Thread thread) {
        return true;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return false;
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void execute(java.lang.Runnable r8) {
        /*
            r7 = this;
            java.lang.String r0 = "Throwable caught while executing Runnable {}"
            if (r8 == 0) goto L_0x0099
            io.netty.util.concurrent.FastThreadLocal<java.lang.Boolean> r1 = RUNNING
            java.lang.Object r2 = r1.get()
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x008d
            r2 = 1
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r1.set(r2)
            r1 = 0
            r8.run()     // Catch:{ all -> 0x0044 }
            io.netty.util.concurrent.FastThreadLocal<java.util.Queue<java.lang.Runnable>> r2 = DELAYED_RUNNABLES
            java.lang.Object r2 = r2.get()
            java.util.Queue r2 = (java.util.Queue) r2
        L_0x0026:
            java.lang.Object r3 = r2.poll()
            java.lang.Runnable r3 = (java.lang.Runnable) r3
            r4 = r3
            if (r3 == 0) goto L_0x003a
            r4.run()     // Catch:{ all -> 0x0033 }
        L_0x0032:
            goto L_0x0026
        L_0x0033:
            r3 = move-exception
            io.netty.util.internal.logging.InternalLogger r5 = logger
            r5.info(r0, r4, r3)
            goto L_0x0032
        L_0x003a:
            io.netty.util.concurrent.FastThreadLocal<java.lang.Boolean> r0 = RUNNING
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r0.set(r1)
            goto L_0x0098
        L_0x0044:
            r2 = move-exception
            io.netty.util.internal.logging.InternalLogger r3 = logger     // Catch:{ all -> 0x0066 }
            r3.info(r0, r8, r2)     // Catch:{ all -> 0x0066 }
            io.netty.util.concurrent.FastThreadLocal<java.util.Queue<java.lang.Runnable>> r2 = DELAYED_RUNNABLES
            java.lang.Object r2 = r2.get()
            java.util.Queue r2 = (java.util.Queue) r2
        L_0x0052:
            java.lang.Object r3 = r2.poll()
            java.lang.Runnable r3 = (java.lang.Runnable) r3
            r4 = r3
            if (r3 == 0) goto L_0x003a
            r4.run()     // Catch:{ all -> 0x005f }
        L_0x005e:
            goto L_0x0052
        L_0x005f:
            r3 = move-exception
            io.netty.util.internal.logging.InternalLogger r5 = logger
            r5.info(r0, r4, r3)
            goto L_0x005e
        L_0x0066:
            r2 = move-exception
            io.netty.util.concurrent.FastThreadLocal<java.util.Queue<java.lang.Runnable>> r3 = DELAYED_RUNNABLES
            java.lang.Object r3 = r3.get()
            java.util.Queue r3 = (java.util.Queue) r3
        L_0x006f:
            java.lang.Object r4 = r3.poll()
            java.lang.Runnable r4 = (java.lang.Runnable) r4
            r5 = r4
            if (r4 == 0) goto L_0x0083
            r5.run()     // Catch:{ all -> 0x007c }
        L_0x007b:
            goto L_0x006f
        L_0x007c:
            r4 = move-exception
            io.netty.util.internal.logging.InternalLogger r6 = logger
            r6.info(r0, r5, r4)
            goto L_0x007b
        L_0x0083:
            io.netty.util.concurrent.FastThreadLocal<java.lang.Boolean> r0 = RUNNING
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r0.set(r1)
            throw r2
        L_0x008d:
            io.netty.util.concurrent.FastThreadLocal<java.util.Queue<java.lang.Runnable>> r0 = DELAYED_RUNNABLES
            java.lang.Object r0 = r0.get()
            java.util.Queue r0 = (java.util.Queue) r0
            r0.add(r8)
        L_0x0098:
            return
        L_0x0099:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "command"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.ImmediateEventExecutor.execute(java.lang.Runnable):void");
    }

    public <V> Promise<V> newPromise() {
        return new ImmediatePromise(this);
    }

    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }

    public static class ImmediatePromise<V> extends DefaultPromise<V> {
        ImmediatePromise(EventExecutor executor) {
            super(executor);
        }

        /* access modifiers changed from: protected */
        public void checkDeadLock() {
        }
    }

    public static class ImmediateProgressivePromise<V> extends DefaultProgressivePromise<V> {
        ImmediateProgressivePromise(EventExecutor executor) {
            super(executor);
        }

        /* access modifiers changed from: protected */
        public void checkDeadLock() {
        }
    }
}
