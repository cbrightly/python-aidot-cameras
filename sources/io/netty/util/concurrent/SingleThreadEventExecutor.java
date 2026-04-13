package io.netty.util.concurrent;

import com.leedarson.bean.Constants;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class SingleThreadEventExecutor extends AbstractScheduledEventExecutor implements OrderedEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE));
    private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, Constants.ACTION_STATE);
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_STARTED = 2;
    private static final int ST_TERMINATED = 5;
    private static final Runnable WAKEUP_TASK = new Runnable() {
        public void run() {
        }
    };
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SingleThreadEventExecutor.class);
    private final boolean addTaskWakesUp;
    private volatile long gracefulShutdownQuietPeriod;
    /* access modifiers changed from: private */
    public long gracefulShutdownStartTime;
    private volatile long gracefulShutdownTimeout;
    private long lastExecutionTime;
    private final int maxPendingTasks;
    private final EventExecutorGroup parent;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    /* access modifiers changed from: private */
    public final Set<Runnable> shutdownHooks;
    private volatile int state;
    /* access modifiers changed from: private */
    public final Queue<Runnable> taskQueue;
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture;
    private final Thread thread;
    /* access modifiers changed from: private */
    public final Semaphore threadLock;
    private final ThreadProperties threadProperties;

    /* access modifiers changed from: protected */
    public abstract void run();

    static {
        Class<SingleThreadEventExecutor> cls = SingleThreadEventExecutor.class;
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent2, ThreadFactory threadFactory, boolean addTaskWakesUp2) {
        this(parent2, threadFactory, addTaskWakesUp2, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventExecutor(EventExecutorGroup parent2, ThreadFactory threadFactory, boolean addTaskWakesUp2, int maxPendingTasks2, RejectedExecutionHandler rejectedHandler) {
        this.threadLock = new Semaphore(0);
        this.shutdownHooks = new LinkedHashSet();
        this.state = 1;
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        if (threadFactory != null) {
            this.parent = parent2;
            this.addTaskWakesUp = addTaskWakesUp2;
            Thread newThread = threadFactory.newThread(new Runnable() {
                /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
                    jadx.core.utils.exceptions.JadxOverflowException: 
                    	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
                    	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
                    */
                public void run() {
                    /*
                        r15 = this;
                        java.lang.String r0 = ".confirmShutdown() must be called before run() implementation terminates."
                        java.lang.String r1 = " implementation; "
                        java.lang.String r2 = "Buggy "
                        java.lang.String r3 = "An event executor terminated with non-empty task queue ("
                        r4 = 0
                        io.netty.util.concurrent.SingleThreadEventExecutor r5 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r5.updateLastExecutionTime()
                        r5 = 0
                        r7 = 3
                        r8 = 41
                        r9 = 0
                        r10 = 5
                        io.netty.util.concurrent.SingleThreadEventExecutor r11 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x01b2 }
                        r11.run()     // Catch:{ all -> 0x01b2 }
                        r4 = 1
                    L_0x001b:
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r11 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r12 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        int r11 = r11.get(r12)
                        if (r11 >= r7) goto L_0x0035
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r12 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r13 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        boolean r12 = r12.compareAndSet(r13, r11, r7)
                        if (r12 == 0) goto L_0x0034
                        goto L_0x0035
                    L_0x0034:
                        goto L_0x001b
                    L_0x0035:
                        if (r4 == 0) goto L_0x006c
                        io.netty.util.concurrent.SingleThreadEventExecutor r7 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        long r11 = r7.gracefulShutdownStartTime
                        int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                        if (r5 != 0) goto L_0x006c
                        io.netty.util.internal.logging.InternalLogger r5 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        r6.<init>()
                        r6.append(r2)
                        java.lang.Class<io.netty.util.concurrent.EventExecutor> r2 = io.netty.util.concurrent.EventExecutor.class
                        java.lang.String r2 = r2.getSimpleName()
                        r6.append(r2)
                        r6.append(r1)
                        java.lang.Class<io.netty.util.concurrent.SingleThreadEventExecutor> r1 = io.netty.util.concurrent.SingleThreadEventExecutor.class
                        java.lang.String r1 = r1.getSimpleName()
                        r6.append(r1)
                        r6.append(r0)
                        java.lang.String r0 = r6.toString()
                        r5.error(r0)
                    L_0x006c:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0114 }
                        boolean r0 = r0.confirmShutdown()     // Catch:{ all -> 0x0114 }
                        if (r0 == 0) goto L_0x006c
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x00c8 }
                        r0.cleanup()     // Catch:{ all -> 0x00c8 }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r0.set(r1, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r0 = r0.threadLock
                        r0.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r0 = r0.taskQueue
                        boolean r0 = r0.isEmpty()
                        if (r0 != 0) goto L_0x00bb
                        io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        r1.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r2 = r2.taskQueue
                        int r2 = r2.size()
                        r1.append(r2)
                        r1.append(r8)
                        java.lang.String r1 = r1.toString()
                        r0.warn(r1)
                    L_0x00bb:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                        r0.setSuccess(r9)
                    L_0x00c5:
                        goto L_0x0267
                    L_0x00c8:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x010a
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x010a:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x0114:
                        r0 = move-exception
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0166 }
                        r1.cleanup()     // Catch:{ all -> 0x0166 }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x015b
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x015b:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x0166:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x01a8
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x01a8:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x01b2:
                        r11 = move-exception
                        io.netty.util.internal.logging.InternalLogger r12 = io.netty.util.concurrent.SingleThreadEventExecutor.logger     // Catch:{ all -> 0x0352 }
                        java.lang.String r13 = "Unexpected exception from an event executor: "
                        r12.warn((java.lang.String) r13, (java.lang.Throwable) r11)     // Catch:{ all -> 0x0352 }
                    L_0x01bc:
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r11 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r12 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        int r11 = r11.get(r12)
                        if (r11 >= r7) goto L_0x01d6
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r12 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r13 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        boolean r12 = r12.compareAndSet(r13, r11, r7)
                        if (r12 == 0) goto L_0x01d5
                        goto L_0x01d6
                    L_0x01d5:
                        goto L_0x01bc
                    L_0x01d6:
                        if (r4 == 0) goto L_0x020d
                        io.netty.util.concurrent.SingleThreadEventExecutor r7 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        long r11 = r7.gracefulShutdownStartTime
                        int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                        if (r5 != 0) goto L_0x020d
                        io.netty.util.internal.logging.InternalLogger r5 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        r6.<init>()
                        r6.append(r2)
                        java.lang.Class<io.netty.util.concurrent.EventExecutor> r2 = io.netty.util.concurrent.EventExecutor.class
                        java.lang.String r2 = r2.getSimpleName()
                        r6.append(r2)
                        r6.append(r1)
                        java.lang.Class<io.netty.util.concurrent.SingleThreadEventExecutor> r1 = io.netty.util.concurrent.SingleThreadEventExecutor.class
                        java.lang.String r1 = r1.getSimpleName()
                        r6.append(r1)
                        r6.append(r0)
                        java.lang.String r0 = r6.toString()
                        r5.error(r0)
                    L_0x020d:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x02b4 }
                        boolean r0 = r0.confirmShutdown()     // Catch:{ all -> 0x02b4 }
                        if (r0 == 0) goto L_0x020d
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0268 }
                        r0.cleanup()     // Catch:{ all -> 0x0268 }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r0.set(r1, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r0 = r0.threadLock
                        r0.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r0 = r0.taskQueue
                        boolean r0 = r0.isEmpty()
                        if (r0 != 0) goto L_0x025c
                        io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        r1.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r2 = r2.taskQueue
                        int r2 = r2.size()
                        r1.append(r2)
                        r1.append(r8)
                        java.lang.String r1 = r1.toString()
                        r0.warn(r1)
                    L_0x025c:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                        r0.setSuccess(r9)
                        goto L_0x00c5
                    L_0x0267:
                        return
                    L_0x0268:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x02aa
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x02aa:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x02b4:
                        r0 = move-exception
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0306 }
                        r1.cleanup()     // Catch:{ all -> 0x0306 }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x02fb
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x02fb:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x0306:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x0348
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x0348:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x0352:
                        r11 = move-exception
                    L_0x0353:
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r12 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r13 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        int r12 = r12.get(r13)
                        if (r12 >= r7) goto L_0x036d
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r13 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r14 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        boolean r13 = r13.compareAndSet(r14, r12, r7)
                        if (r13 == 0) goto L_0x036c
                        goto L_0x036d
                    L_0x036c:
                        goto L_0x0353
                    L_0x036d:
                        if (r4 == 0) goto L_0x03a4
                        io.netty.util.concurrent.SingleThreadEventExecutor r7 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        long r12 = r7.gracefulShutdownStartTime
                        int r5 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
                        if (r5 != 0) goto L_0x03a4
                        io.netty.util.internal.logging.InternalLogger r5 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        r6.<init>()
                        r6.append(r2)
                        java.lang.Class<io.netty.util.concurrent.EventExecutor> r2 = io.netty.util.concurrent.EventExecutor.class
                        java.lang.String r2 = r2.getSimpleName()
                        r6.append(r2)
                        r6.append(r1)
                        java.lang.Class<io.netty.util.concurrent.SingleThreadEventExecutor> r1 = io.netty.util.concurrent.SingleThreadEventExecutor.class
                        java.lang.String r1 = r1.getSimpleName()
                        r6.append(r1)
                        r6.append(r0)
                        java.lang.String r0 = r6.toString()
                        r5.error(r0)
                    L_0x03a4:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x044b }
                        boolean r0 = r0.confirmShutdown()     // Catch:{ all -> 0x044b }
                        if (r0 == 0) goto L_0x03a4
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x03ff }
                        r0.cleanup()     // Catch:{ all -> 0x03ff }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r0.set(r1, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r0 = r0.threadLock
                        r0.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r0 = r0.taskQueue
                        boolean r0 = r0.isEmpty()
                        if (r0 != 0) goto L_0x03f3
                        io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        r1.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r2 = r2.taskQueue
                        int r2 = r2.size()
                        r1.append(r2)
                        r1.append(r8)
                        java.lang.String r1 = r1.toString()
                        r0.warn(r1)
                    L_0x03f3:
                        io.netty.util.concurrent.SingleThreadEventExecutor r0 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                        r0.setSuccess(r9)
                        throw r11
                    L_0x03ff:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x0441
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x0441:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x044b:
                        r0 = move-exception
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x049d }
                        r1.cleanup()     // Catch:{ all -> 0x049d }
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x0492
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x0492:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    L_0x049d:
                        r0 = move-exception
                        java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                        io.netty.util.concurrent.SingleThreadEventExecutor r2 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        r1.set(r2, r10)
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.concurrent.Semaphore r1 = r1.threadLock
                        r1.release()
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r1 = r1.taskQueue
                        boolean r1 = r1.isEmpty()
                        if (r1 != 0) goto L_0x04df
                        io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.concurrent.SingleThreadEventExecutor.logger
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        r2.append(r3)
                        io.netty.util.concurrent.SingleThreadEventExecutor r3 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        java.util.Queue r3 = r3.taskQueue
                        int r3 = r3.size()
                        r2.append(r3)
                        r2.append(r8)
                        java.lang.String r2 = r2.toString()
                        r1.warn(r2)
                    L_0x04df:
                        io.netty.util.concurrent.SingleThreadEventExecutor r1 = io.netty.util.concurrent.SingleThreadEventExecutor.this
                        io.netty.util.concurrent.Promise r1 = r1.terminationFuture
                        r1.setSuccess(r9)
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.SingleThreadEventExecutor.AnonymousClass2.run():void");
                }
            });
            this.thread = newThread;
            this.threadProperties = new DefaultThreadProperties(newThread);
            this.maxPendingTasks = Math.max(16, maxPendingTasks2);
            this.taskQueue = newTaskQueue();
            this.rejectedExecutionHandler = (RejectedExecutionHandler) ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
            return;
        }
        throw new NullPointerException("threadFactory");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Queue<Runnable> newTaskQueue() {
        return newTaskQueue(this.maxPendingTasks);
    }

    /* access modifiers changed from: protected */
    public Queue<Runnable> newTaskQueue(int maxPendingTasks2) {
        return new LinkedBlockingQueue(maxPendingTasks2);
    }

    public EventExecutorGroup parent() {
        return this.parent;
    }

    /* access modifiers changed from: protected */
    public void interruptThread() {
        this.thread.interrupt();
    }

    /* access modifiers changed from: protected */
    public Runnable pollTask() {
        Runnable task;
        if (inEventLoop()) {
            do {
                task = this.taskQueue.poll();
            } while (task == WAKEUP_TASK);
            return task;
        }
        throw new AssertionError();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Runnable} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Runnable takeTask() {
        /*
            r7 = this;
            boolean r0 = r7.inEventLoop()
            if (r0 == 0) goto L_0x0052
            java.util.Queue<java.lang.Runnable> r0 = r7.taskQueue
            boolean r1 = r0 instanceof java.util.concurrent.BlockingQueue
            if (r1 == 0) goto L_0x004c
            java.util.concurrent.BlockingQueue r0 = (java.util.concurrent.BlockingQueue) r0
        L_0x000e:
            io.netty.util.concurrent.ScheduledFutureTask r1 = r7.peekScheduledTask()
            if (r1 != 0) goto L_0x0024
            r2 = 0
            java.lang.Object r3 = r0.take()     // Catch:{ InterruptedException -> 0x0022 }
            java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ InterruptedException -> 0x0022 }
            r2 = r3
            java.lang.Runnable r3 = WAKEUP_TASK     // Catch:{ InterruptedException -> 0x0022 }
            if (r2 != r3) goto L_0x0021
            r2 = 0
        L_0x0021:
            goto L_0x0023
        L_0x0022:
            r3 = move-exception
        L_0x0023:
            return r2
        L_0x0024:
            long r2 = r1.delayNanos()
            r4 = 0
            r5 = 0
            int r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x003c
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0039 }
            java.lang.Object r5 = r0.poll(r2, r5)     // Catch:{ InterruptedException -> 0x0039 }
            java.lang.Runnable r5 = (java.lang.Runnable) r5     // Catch:{ InterruptedException -> 0x0039 }
            r4 = r5
            goto L_0x003c
        L_0x0039:
            r5 = move-exception
            r6 = 0
            return r6
        L_0x003c:
            if (r4 != 0) goto L_0x0048
            r7.fetchFromScheduledTaskQueue()
            java.lang.Object r5 = r0.poll()
            r4 = r5
            java.lang.Runnable r4 = (java.lang.Runnable) r4
        L_0x0048:
            if (r4 == 0) goto L_0x004b
            return r4
        L_0x004b:
            goto L_0x000e
        L_0x004c:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>()
            throw r0
        L_0x0052:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.SingleThreadEventExecutor.takeTask():java.lang.Runnable");
    }

    private boolean fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable scheduledTask = pollScheduledTask(nanoTime);
        while (scheduledTask != null) {
            if (!this.taskQueue.offer(scheduledTask)) {
                scheduledTaskQueue().add((ScheduledFutureTask) scheduledTask);
                return false;
            }
            scheduledTask = pollScheduledTask(nanoTime);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public Runnable peekTask() {
        if (inEventLoop()) {
            return this.taskQueue.peek();
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public boolean hasTasks() {
        if (inEventLoop()) {
            return !this.taskQueue.isEmpty();
        }
        throw new AssertionError();
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    /* access modifiers changed from: protected */
    public void addTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        } else if (!offerTask(task)) {
            this.rejectedExecutionHandler.rejected(task, this);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean offerTask(Runnable task) {
        if (isShutdown()) {
            reject();
        }
        return this.taskQueue.offer(task);
    }

    /* access modifiers changed from: protected */
    public boolean removeTask(Runnable task) {
        if (task != null) {
            return this.taskQueue.remove(task);
        }
        throw new NullPointerException("task");
    }

    /* access modifiers changed from: protected */
    public boolean runAllTasks() {
        boolean fetchedAll;
        do {
            fetchedAll = fetchFromScheduledTaskQueue();
            Runnable task = pollTask();
            if (task == null) {
                return false;
            }
            do {
                try {
                    task.run();
                } catch (Throwable t) {
                    logger.warn("A task raised an exception.", t);
                }
                task = pollTask();
            } while (task != null);
        } while (!fetchedAll);
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean runAllTasks(long timeoutNanos) {
        long lastExecutionTime2;
        fetchFromScheduledTaskQueue();
        Runnable task = pollTask();
        if (task == null) {
            return false;
        }
        long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
        long runTasks = 0;
        while (true) {
            try {
                task.run();
            } catch (Throwable t) {
                logger.warn("A task raised an exception.", t);
            }
            runTasks++;
            if ((63 & runTasks) == 0) {
                lastExecutionTime2 = ScheduledFutureTask.nanoTime();
                if (lastExecutionTime2 >= deadline) {
                    break;
                }
            }
            task = pollTask();
            if (task == null) {
                lastExecutionTime2 = ScheduledFutureTask.nanoTime();
                break;
            }
        }
        this.lastExecutionTime = lastExecutionTime2;
        return true;
    }

    /* access modifiers changed from: protected */
    public long delayNanos(long currentTimeNanos) {
        ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
        if (scheduledTask == null) {
            return SCHEDULE_PURGE_INTERVAL;
        }
        return scheduledTask.delayNanos(currentTimeNanos);
    }

    /* access modifiers changed from: protected */
    public void updateLastExecutionTime() {
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
    }

    /* access modifiers changed from: protected */
    public void cleanup() {
    }

    /* access modifiers changed from: protected */
    public void wakeup(boolean inEventLoop) {
        if (!inEventLoop || this.state == 3) {
            this.taskQueue.offer(WAKEUP_TASK);
        }
    }

    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    public void addShutdownHook(final Runnable task) {
        if (inEventLoop()) {
            this.shutdownHooks.add(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.add(task);
                }
            });
        }
    }

    public void removeShutdownHook(final Runnable task) {
        if (inEventLoop()) {
            this.shutdownHooks.remove(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.remove(task);
                }
            });
        }
    }

    private boolean runShutdownHooks() {
        boolean ran = false;
        while (!this.shutdownHooks.isEmpty()) {
            List<Runnable> copy = new ArrayList<>(this.shutdownHooks);
            this.shutdownHooks.clear();
            for (Runnable task : copy) {
                try {
                    task.run();
                } catch (Throwable th) {
                    throw th;
                }
                ran = true;
            }
        }
        if (ran) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        return ran;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        int newState;
        if (quietPeriod < 0) {
            throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
        } else if (timeout < quietPeriod) {
            throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
        } else if (unit == null) {
            throw new NullPointerException("unit");
        } else if (isShuttingDown()) {
            return terminationFuture();
        } else {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                boolean wakeup = true;
                int oldState = this.state;
                if (!inEventLoop) {
                    switch (oldState) {
                        case 1:
                        case 2:
                            newState = 3;
                            break;
                        default:
                            newState = oldState;
                            wakeup = false;
                            break;
                    }
                } else {
                    newState = 3;
                }
                if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
                    this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
                    this.gracefulShutdownTimeout = unit.toNanos(timeout);
                    if (oldState == 1) {
                        this.thread.start();
                    }
                    if (wakeup) {
                        wakeup(inEventLoop);
                    }
                    return terminationFuture();
                }
            }
            return terminationFuture();
        }
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        int newState;
        if (!isShutdown()) {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                boolean wakeup = true;
                int oldState = this.state;
                if (!inEventLoop) {
                    switch (oldState) {
                        case 1:
                        case 2:
                        case 3:
                            newState = 4;
                            break;
                        default:
                            newState = oldState;
                            wakeup = false;
                            break;
                    }
                } else {
                    newState = 4;
                }
                if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
                    if (oldState == 1) {
                        this.thread.start();
                    }
                    if (wakeup) {
                        wakeup(inEventLoop);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public boolean isShuttingDown() {
        return this.state >= 3;
    }

    public boolean isShutdown() {
        return this.state >= 4;
    }

    public boolean isTerminated() {
        return this.state == 5;
    }

    /* access modifiers changed from: protected */
    public boolean confirmShutdown() {
        if (!isShuttingDown()) {
            return false;
        }
        if (inEventLoop()) {
            cancelScheduledTasks();
            if (this.gracefulShutdownStartTime == 0) {
                this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
            }
            if (!runAllTasks() && !runShutdownHooks()) {
                long nanoTime = ScheduledFutureTask.nanoTime();
                if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout || nanoTime - this.lastExecutionTime > this.gracefulShutdownQuietPeriod) {
                    return true;
                }
                wakeup(true);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                return false;
            } else if (isShutdown() || this.gracefulShutdownQuietPeriod == 0) {
                return true;
            } else {
                wakeup(true);
                return false;
            }
        } else {
            throw new IllegalStateException("must be invoked from an event loop");
        }
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        } else if (!inEventLoop()) {
            if (this.threadLock.tryAcquire(timeout, unit)) {
                this.threadLock.release();
            }
            return isTerminated();
        } else {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
    }

    public void execute(Runnable task) {
        if (task != null) {
            boolean inEventLoop = inEventLoop();
            if (inEventLoop) {
                addTask(task);
            } else {
                startThread();
                addTask(task);
                if (isShutdown() && removeTask(task)) {
                    reject();
                }
            }
            if (!this.addTaskWakesUp && wakesUpForTask(task)) {
                wakeup(inEventLoop);
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(tasks, timeout, unit);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(tasks, timeout, unit);
    }

    private void throwIfInEventLoop(String method) {
        if (inEventLoop()) {
            throw new RejectedExecutionException("Calling " + method + " from within the EventLoop is not allowed");
        }
    }

    public final ThreadProperties threadProperties() {
        return this.threadProperties;
    }

    /* access modifiers changed from: protected */
    public boolean wakesUpForTask(Runnable task) {
        return true;
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    private void startThread() {
        if (this.state == 1 && STATE_UPDATER.compareAndSet(this, 1, 2)) {
            this.thread.start();
        }
    }

    public static final class DefaultThreadProperties implements ThreadProperties {
        private final Thread t;

        DefaultThreadProperties(Thread t2) {
            this.t = t2;
        }

        public Thread.State state() {
            return this.t.getState();
        }

        public int priority() {
            return this.t.getPriority();
        }

        public boolean isInterrupted() {
            return this.t.isInterrupted();
        }

        public boolean isDaemon() {
            return this.t.isDaemon();
        }

        public String name() {
            return this.t.getName();
        }

        public long id() {
            return this.t.getId();
        }

        public StackTraceElement[] stackTrace() {
            return this.t.getStackTrace();
        }

        public boolean isAlive() {
            return this.t.isAlive();
        }
    }
}
