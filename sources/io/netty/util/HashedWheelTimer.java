package io.netty.util;

import com.alibaba.fastjson.asm.Opcodes;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.amazonaws.kinesisvideo.producer.Time;
import com.leedarson.bean.Constants;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

public class HashedWheelTimer implements Timer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
    private static final int INSTANCE_COUNT_LIMIT = 64;
    private static final AtomicBoolean WARNED_TOO_MANY_INSTANCES = new AtomicBoolean();
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    public static final int WORKER_STATE_STARTED = 1;
    /* access modifiers changed from: private */
    public static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
    private static final ResourceLeakDetector<HashedWheelTimer> leakDetector;
    static final InternalLogger logger;
    /* access modifiers changed from: private */
    public final Queue<HashedWheelTimeout> cancelledTimeouts;
    private final ResourceLeakTracker<HashedWheelTimer> leak;
    /* access modifiers changed from: private */
    public final int mask;
    private final long maxPendingTimeouts;
    /* access modifiers changed from: private */
    public final AtomicLong pendingTimeouts;
    /* access modifiers changed from: private */
    public volatile long startTime;
    /* access modifiers changed from: private */
    public final CountDownLatch startTimeInitialized;
    /* access modifiers changed from: private */
    public final long tickDuration;
    /* access modifiers changed from: private */
    public final Queue<HashedWheelTimeout> timeouts;
    /* access modifiers changed from: private */
    public final HashedWheelBucket[] wheel;
    private final Worker worker;
    private volatile int workerState;
    private final Thread workerThread;

    static {
        Class<HashedWheelTimer> cls = HashedWheelTimer.class;
        logger = InternalLoggerFactory.getInstance((Class<?>) cls);
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(cls, 1);
        WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "workerState");
    }

    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }

    public HashedWheelTimer(long tickDuration2, TimeUnit unit) {
        this(Executors.defaultThreadFactory(), tickDuration2, unit);
    }

    public HashedWheelTimer(long tickDuration2, TimeUnit unit, int ticksPerWheel) {
        this(Executors.defaultThreadFactory(), tickDuration2, unit, ticksPerWheel);
    }

    public HashedWheelTimer(ThreadFactory threadFactory) {
        this(threadFactory, 100, TimeUnit.MILLISECONDS);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration2, TimeUnit unit) {
        this(threadFactory, tickDuration2, unit, 512);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration2, TimeUnit unit, int ticksPerWheel) {
        this(threadFactory, tickDuration2, unit, ticksPerWheel, true);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration2, TimeUnit unit, int ticksPerWheel, boolean leakDetection) {
        this(threadFactory, tickDuration2, unit, ticksPerWheel, leakDetection, -1);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration2, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts2) {
        ThreadFactory threadFactory2 = threadFactory;
        long j = tickDuration2;
        TimeUnit timeUnit = unit;
        int i = ticksPerWheel;
        ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker = null;
        Worker worker2 = new Worker();
        this.worker = worker2;
        this.startTimeInitialized = new CountDownLatch(1);
        this.timeouts = PlatformDependent.newMpscQueue();
        this.cancelledTimeouts = PlatformDependent.newMpscQueue();
        this.pendingTimeouts = new AtomicLong(0);
        if (threadFactory2 == null) {
            long j2 = maxPendingTimeouts2;
            throw new NullPointerException("threadFactory");
        } else if (timeUnit == null) {
            long j3 = maxPendingTimeouts2;
            throw new NullPointerException("unit");
        } else if (j <= 0) {
            long j4 = maxPendingTimeouts2;
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + j);
        } else if (i > 0) {
            HashedWheelBucket[] createWheel = createWheel(ticksPerWheel);
            this.wheel = createWheel;
            this.mask = createWheel.length - 1;
            long nanos = timeUnit.toNanos(j);
            this.tickDuration = nanos;
            if (nanos < DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE / ((long) createWheel.length)) {
                Thread newThread = threadFactory2.newThread(worker2);
                this.workerThread = newThread;
                this.leak = (leakDetection || !newThread.isDaemon()) ? leakDetector.track(this) : resourceLeakTracker;
                this.maxPendingTimeouts = maxPendingTimeouts2;
                if (INSTANCE_COUNTER.incrementAndGet() > 64 && WARNED_TOO_MANY_INSTANCES.compareAndSet(false, true)) {
                    reportTooManyInstances();
                    return;
                }
                return;
            }
            long j5 = maxPendingTimeouts2;
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[]{Long.valueOf(tickDuration2), Long.valueOf(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE / ((long) createWheel.length))}));
        } else {
            long j6 = maxPendingTimeouts2;
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + i);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            super.finalize();
        } finally {
            if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                INSTANCE_COUNTER.decrementAndGet();
            }
        }
    }

    private static HashedWheelBucket[] createWheel(int ticksPerWheel) {
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        } else if (ticksPerWheel <= 1073741824) {
            HashedWheelBucket[] wheel2 = new HashedWheelBucket[normalizeTicksPerWheel(ticksPerWheel)];
            for (int i = 0; i < wheel2.length; i++) {
                wheel2[i] = new HashedWheelBucket();
            }
            return wheel2;
        } else {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
        }
    }

    private static int normalizeTicksPerWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        return normalizedTicksPerWheel;
    }

    public void start() {
        AtomicIntegerFieldUpdater<HashedWheelTimer> atomicIntegerFieldUpdater = WORKER_STATE_UPDATER;
        switch (atomicIntegerFieldUpdater.get(this)) {
            case 0:
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    this.workerThread.start();
                    break;
                }
                break;
            case 1:
                break;
            case 2:
                throw new IllegalStateException("cannot be started once stopped");
            default:
                throw new Error("Invalid WorkerState");
        }
        while (this.startTime == 0) {
            try {
                this.startTimeInitialized.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public Set<Timeout> stop() {
        if (Thread.currentThread() != this.workerThread) {
            AtomicIntegerFieldUpdater<HashedWheelTimer> atomicIntegerFieldUpdater = WORKER_STATE_UPDATER;
            if (!atomicIntegerFieldUpdater.compareAndSet(this, 1, 2)) {
                if (atomicIntegerFieldUpdater.getAndSet(this, 2) != 2) {
                    INSTANCE_COUNTER.decrementAndGet();
                    ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker = this.leak;
                    if (resourceLeakTracker != null && !resourceLeakTracker.close(this)) {
                        throw new AssertionError();
                    }
                }
                return Collections.emptySet();
            }
            boolean interrupted = false;
            while (this.workerThread.isAlive()) {
                try {
                    this.workerThread.interrupt();
                    try {
                        this.workerThread.join(100);
                    } catch (InterruptedException e) {
                        interrupted = true;
                    }
                } catch (Throwable th) {
                    INSTANCE_COUNTER.decrementAndGet();
                    ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker2 = this.leak;
                    if (resourceLeakTracker2 == null || resourceLeakTracker2.close(this)) {
                        throw th;
                    }
                    throw new AssertionError();
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            INSTANCE_COUNTER.decrementAndGet();
            ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker3 = this.leak;
            if (resourceLeakTracker3 == null || resourceLeakTracker3.close(this)) {
                return this.worker.unprocessedTimeouts();
            }
            throw new AssertionError();
        }
        throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
    }

    public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
        if (task == null) {
            throw new NullPointerException("task");
        } else if (unit != null) {
            long pendingTimeoutsCount = this.pendingTimeouts.incrementAndGet();
            long j = this.maxPendingTimeouts;
            if (j <= 0 || pendingTimeoutsCount <= j) {
                start();
                HashedWheelTimeout timeout = new HashedWheelTimeout(this, task, (System.nanoTime() + unit.toNanos(delay)) - this.startTime);
                this.timeouts.add(timeout);
                return timeout;
            }
            this.pendingTimeouts.decrementAndGet();
            throw new RejectedExecutionException("Number of pending timeouts (" + pendingTimeoutsCount + ") is greater than or equal to maximum allowed pending timeouts (" + this.maxPendingTimeouts + ")");
        } else {
            throw new NullPointerException("unit");
        }
    }

    public long pendingTimeouts() {
        return this.pendingTimeouts.get();
    }

    private static void reportTooManyInstances() {
        String resourceType = StringUtil.simpleClassName((Class<?>) HashedWheelTimer.class);
        InternalLogger internalLogger = logger;
        internalLogger.error("You are creating too many " + resourceType + " instances. " + resourceType + " is a shared resource that must be reused across the JVM,so that only a few instances are created.");
    }

    public final class Worker implements Runnable {
        private long tick;
        private final Set<Timeout> unprocessedTimeouts;

        private Worker() {
            this.unprocessedTimeouts = new HashSet();
        }

        public void run() {
            long unused = HashedWheelTimer.this.startTime = System.nanoTime();
            if (HashedWheelTimer.this.startTime == 0) {
                long unused2 = HashedWheelTimer.this.startTime = 1;
            }
            HashedWheelTimer.this.startTimeInitialized.countDown();
            do {
                long deadline = waitForNextTick();
                if (deadline > 0) {
                    processCancelledTasks();
                    HashedWheelBucket bucket = HashedWheelTimer.this.wheel[(int) (this.tick & ((long) HashedWheelTimer.this.mask))];
                    transferTimeoutsToBuckets();
                    bucket.expireTimeouts(deadline);
                    this.tick++;
                }
            } while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
            for (HashedWheelBucket bucket2 : HashedWheelTimer.this.wheel) {
                bucket2.clearTimeouts(this.unprocessedTimeouts);
            }
            while (true) {
                HashedWheelTimeout timeout = (HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll();
                if (timeout == null) {
                    processCancelledTasks();
                    return;
                } else if (!timeout.isCancelled()) {
                    this.unprocessedTimeouts.add(timeout);
                }
            }
        }

        private void transferTimeoutsToBuckets() {
            HashedWheelTimeout timeout;
            for (int i = 0; i < 100000 && (timeout = (HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll()) != null; i++) {
                if (timeout.state() != 1) {
                    long calculated = timeout.deadline / HashedWheelTimer.this.tickDuration;
                    timeout.remainingRounds = (calculated - this.tick) / ((long) HashedWheelTimer.this.wheel.length);
                    long ticks = Math.max(calculated, this.tick);
                    HashedWheelTimer.this.wheel[(int) (((long) HashedWheelTimer.this.mask) & ticks)].addTimeout(timeout);
                }
            }
        }

        private void processCancelledTasks() {
            while (true) {
                HashedWheelTimeout timeout = (HashedWheelTimeout) HashedWheelTimer.this.cancelledTimeouts.poll();
                if (timeout != null) {
                    try {
                        timeout.remove();
                    } catch (Throwable t) {
                        if (HashedWheelTimer.logger.isWarnEnabled()) {
                            HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", t);
                        }
                    }
                } else {
                    return;
                }
            }
        }

        private long waitForNextTick() {
            long deadline = HashedWheelTimer.this.tickDuration * (this.tick + 1);
            while (true) {
                long currentTime = System.nanoTime() - HashedWheelTimer.this.startTime;
                long sleepTimeMs = ((deadline - currentTime) + 999999) / Time.NANOS_IN_A_MILLISECOND;
                if (sleepTimeMs > 0) {
                    if (PlatformDependent.isWindows()) {
                        sleepTimeMs = (sleepTimeMs / 10) * 10;
                    }
                    try {
                        Thread.sleep(sleepTimeMs);
                    } catch (InterruptedException e) {
                        if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
                            return Long.MIN_VALUE;
                        }
                    }
                } else if (currentTime == Long.MIN_VALUE) {
                    return -9223372036854775807L;
                } else {
                    return currentTime;
                }
            }
        }

        public Set<Timeout> unprocessedTimeouts() {
            return Collections.unmodifiableSet(this.unprocessedTimeouts);
        }
    }

    public static final class HashedWheelTimeout implements Timeout {
        private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, Constants.ACTION_STATE);
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private static final int ST_INIT = 0;
        HashedWheelBucket bucket;
        /* access modifiers changed from: private */
        public final long deadline;
        HashedWheelTimeout next;
        HashedWheelTimeout prev;
        long remainingRounds;
        private volatile int state = 0;
        private final TimerTask task;
        /* access modifiers changed from: private */
        public final HashedWheelTimer timer;

        HashedWheelTimeout(HashedWheelTimer timer2, TimerTask task2, long deadline2) {
            this.timer = timer2;
            this.task = task2;
            this.deadline = deadline2;
        }

        public Timer timer() {
            return this.timer;
        }

        public TimerTask task() {
            return this.task;
        }

        public boolean cancel() {
            if (!compareAndSetState(0, 1)) {
                return false;
            }
            this.timer.cancelledTimeouts.add(this);
            return true;
        }

        /* access modifiers changed from: package-private */
        public void remove() {
            HashedWheelBucket bucket2 = this.bucket;
            if (bucket2 != null) {
                bucket2.remove(this);
            } else {
                this.timer.pendingTimeouts.decrementAndGet();
            }
        }

        public boolean compareAndSetState(int expected, int state2) {
            return STATE_UPDATER.compareAndSet(this, expected, state2);
        }

        public int state() {
            return this.state;
        }

        public boolean isCancelled() {
            return state() == 1;
        }

        public boolean isExpired() {
            return state() == 2;
        }

        public void expire() {
            if (compareAndSetState(0, 2)) {
                try {
                    this.task.run(this);
                } catch (Throwable t) {
                    InternalLogger internalLogger = HashedWheelTimer.logger;
                    if (internalLogger.isWarnEnabled()) {
                        internalLogger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
                    }
                }
            }
        }

        public String toString() {
            long remaining = (this.deadline - System.nanoTime()) + this.timer.startTime;
            StringBuilder sb = new StringBuilder(Opcodes.CHECKCAST);
            sb.append(StringUtil.simpleClassName((Object) this));
            sb.append('(');
            StringBuilder buf = sb.append("deadline: ");
            if (remaining > 0) {
                buf.append(remaining);
                buf.append(" ns later");
            } else if (remaining < 0) {
                buf.append(-remaining);
                buf.append(" ns ago");
            } else {
                buf.append("now");
            }
            if (isCancelled()) {
                buf.append(", cancelled");
            }
            buf.append(", task: ");
            buf.append(task());
            buf.append(')');
            return buf.toString();
        }
    }

    public static final class HashedWheelBucket {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;

        private HashedWheelBucket() {
        }

        public void addTimeout(HashedWheelTimeout timeout) {
            if (timeout.bucket == null) {
                timeout.bucket = this;
                if (this.head == null) {
                    this.tail = timeout;
                    this.head = timeout;
                    return;
                }
                HashedWheelTimeout hashedWheelTimeout = this.tail;
                hashedWheelTimeout.next = timeout;
                timeout.prev = hashedWheelTimeout;
                this.tail = timeout;
                return;
            }
            throw new AssertionError();
        }

        public void expireTimeouts(long deadline) {
            HashedWheelTimeout timeout = this.head;
            while (timeout != null) {
                HashedWheelTimeout next = timeout.next;
                if (timeout.remainingRounds <= 0) {
                    next = remove(timeout);
                    if (timeout.deadline <= deadline) {
                        timeout.expire();
                    } else {
                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", new Object[]{Long.valueOf(timeout.deadline), Long.valueOf(deadline)}));
                    }
                } else if (timeout.isCancelled()) {
                    next = remove(timeout);
                } else {
                    timeout.remainingRounds--;
                }
                timeout = next;
            }
        }

        public HashedWheelTimeout remove(HashedWheelTimeout timeout) {
            HashedWheelTimeout next = timeout.next;
            HashedWheelTimeout hashedWheelTimeout = timeout.prev;
            if (hashedWheelTimeout != null) {
                hashedWheelTimeout.next = next;
            }
            HashedWheelTimeout hashedWheelTimeout2 = timeout.next;
            if (hashedWheelTimeout2 != null) {
                hashedWheelTimeout2.prev = hashedWheelTimeout;
            }
            if (timeout == this.head) {
                if (timeout == this.tail) {
                    this.tail = null;
                    this.head = null;
                } else {
                    this.head = next;
                }
            } else if (timeout == this.tail) {
                this.tail = timeout.prev;
            }
            timeout.prev = null;
            timeout.next = null;
            timeout.bucket = null;
            timeout.timer.pendingTimeouts.decrementAndGet();
            return next;
        }

        public void clearTimeouts(Set<Timeout> set) {
            while (true) {
                HashedWheelTimeout timeout = pollTimeout();
                if (timeout != null) {
                    if (!timeout.isExpired() && !timeout.isCancelled()) {
                        set.add(timeout);
                    }
                } else {
                    return;
                }
            }
        }

        private HashedWheelTimeout pollTimeout() {
            HashedWheelTimeout head2 = this.head;
            if (head2 == null) {
                return null;
            }
            HashedWheelTimeout next = head2.next;
            if (next == null) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = next;
                next.prev = null;
            }
            head2.next = null;
            head2.prev = null;
            head2.bucket = null;
            return head2;
        }
    }
}
