package io.netty.handler.traffic;

import com.alibaba.fastjson.asm.Opcodes;
import com.amazonaws.kinesisvideo.producer.Time;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TrafficCounter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) TrafficCounter.class);
    final AtomicLong checkInterval = new AtomicLong(1000);
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private final AtomicLong currentReadBytes = new AtomicLong();
    private final AtomicLong currentWrittenBytes = new AtomicLong();
    final ScheduledExecutorService executor;
    private long lastCumulativeTime;
    private volatile long lastReadBytes;
    private long lastReadThroughput;
    private volatile long lastReadingTime;
    final AtomicLong lastTime = new AtomicLong();
    private long lastWriteThroughput;
    private volatile long lastWritingTime;
    private volatile long lastWrittenBytes;
    Runnable monitor;
    volatile boolean monitorActive;
    final String name;
    private long readingTime;
    private long realWriteThroughput;
    private final AtomicLong realWrittenBytes = new AtomicLong();
    volatile ScheduledFuture<?> scheduledFuture;
    final AbstractTrafficShapingHandler trafficShapingHandler;
    private long writingTime;

    public static long milliSecondFromNano() {
        return System.nanoTime() / Time.NANOS_IN_A_MILLISECOND;
    }

    public final class TrafficMonitoringTask implements Runnable {
        private TrafficMonitoringTask() {
        }

        public void run() {
            if (TrafficCounter.this.monitorActive) {
                TrafficCounter.this.resetAccounting(TrafficCounter.milliSecondFromNano());
                TrafficCounter trafficCounter = TrafficCounter.this;
                AbstractTrafficShapingHandler abstractTrafficShapingHandler = trafficCounter.trafficShapingHandler;
                if (abstractTrafficShapingHandler != null) {
                    abstractTrafficShapingHandler.doAccounting(trafficCounter);
                }
                TrafficCounter trafficCounter2 = TrafficCounter.this;
                trafficCounter2.scheduledFuture = trafficCounter2.executor.schedule(this, trafficCounter2.checkInterval.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.monitorActive     // Catch:{ all -> 0x0037 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r5)
            return
        L_0x0007:
            java.util.concurrent.atomic.AtomicLong r0 = r5.lastTime     // Catch:{ all -> 0x0037 }
            long r1 = milliSecondFromNano()     // Catch:{ all -> 0x0037 }
            r0.set(r1)     // Catch:{ all -> 0x0037 }
            java.util.concurrent.atomic.AtomicLong r0 = r5.checkInterval     // Catch:{ all -> 0x0037 }
            long r0 = r0.get()     // Catch:{ all -> 0x0037 }
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0035
            java.util.concurrent.ScheduledExecutorService r2 = r5.executor     // Catch:{ all -> 0x0037 }
            if (r2 == 0) goto L_0x0035
            r2 = 1
            r5.monitorActive = r2     // Catch:{ all -> 0x0037 }
            io.netty.handler.traffic.TrafficCounter$TrafficMonitoringTask r2 = new io.netty.handler.traffic.TrafficCounter$TrafficMonitoringTask     // Catch:{ all -> 0x0037 }
            r3 = 0
            r2.<init>()     // Catch:{ all -> 0x0037 }
            r5.monitor = r2     // Catch:{ all -> 0x0037 }
            java.util.concurrent.ScheduledExecutorService r3 = r5.executor     // Catch:{ all -> 0x0037 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0037 }
            java.util.concurrent.ScheduledFuture r2 = r3.schedule(r2, r0, r4)     // Catch:{ all -> 0x0037 }
            r5.scheduledFuture = r2     // Catch:{ all -> 0x0037 }
        L_0x0035:
            monitor-exit(r5)
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.TrafficCounter.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.monitorActive     // Catch:{ all -> 0x0024 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 0
            r2.monitorActive = r0     // Catch:{ all -> 0x0024 }
            long r0 = milliSecondFromNano()     // Catch:{ all -> 0x0024 }
            r2.resetAccounting(r0)     // Catch:{ all -> 0x0024 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r0 = r2.trafficShapingHandler     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0018
            r0.doAccounting(r2)     // Catch:{ all -> 0x0024 }
        L_0x0018:
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0022
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0024 }
            r1 = 1
            r0.cancel(r1)     // Catch:{ all -> 0x0024 }
        L_0x0022:
            monitor-exit(r2)
            return
        L_0x0024:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.TrafficCounter.stop():void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void resetAccounting(long newLastTime) {
        long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
        if (interval != 0) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled() && interval > (checkInterval() << 1)) {
                internalLogger.debug("Acct schedule not ok: " + interval + " > 2*" + checkInterval() + " from " + this.name);
            }
            this.lastReadBytes = this.currentReadBytes.getAndSet(0);
            this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0);
            this.lastReadThroughput = (this.lastReadBytes * 1000) / interval;
            this.lastWriteThroughput = (this.lastWrittenBytes * 1000) / interval;
            this.realWriteThroughput = (this.realWrittenBytes.getAndSet(0) * 1000) / interval;
            this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
            this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
        }
    }

    public TrafficCounter(ScheduledExecutorService executor2, String name2, long checkInterval2) {
        if (name2 != null) {
            this.trafficShapingHandler = null;
            this.executor = executor2;
            this.name = name2;
            init(checkInterval2);
            return;
        }
        throw new NullPointerException("name");
    }

    public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler2, ScheduledExecutorService executor2, String name2, long checkInterval2) {
        if (trafficShapingHandler2 == null) {
            throw new IllegalArgumentException("trafficShapingHandler");
        } else if (name2 != null) {
            this.trafficShapingHandler = trafficShapingHandler2;
            this.executor = executor2;
            this.name = name2;
            init(checkInterval2);
        } else {
            throw new NullPointerException("name");
        }
    }

    private void init(long checkInterval2) {
        this.lastCumulativeTime = System.currentTimeMillis();
        long milliSecondFromNano = milliSecondFromNano();
        this.writingTime = milliSecondFromNano;
        this.readingTime = milliSecondFromNano;
        this.lastWritingTime = milliSecondFromNano;
        this.lastReadingTime = this.writingTime;
        configure(checkInterval2);
    }

    public void configure(long newCheckInterval) {
        long newInterval = (newCheckInterval / 10) * 10;
        if (this.checkInterval.getAndSet(newInterval) == newInterval) {
            return;
        }
        if (newInterval <= 0) {
            stop();
            this.lastTime.set(milliSecondFromNano());
            return;
        }
        start();
    }

    /* access modifiers changed from: package-private */
    public void bytesRecvFlowControl(long recv) {
        this.currentReadBytes.addAndGet(recv);
        this.cumulativeReadBytes.addAndGet(recv);
    }

    /* access modifiers changed from: package-private */
    public void bytesWriteFlowControl(long write) {
        this.currentWrittenBytes.addAndGet(write);
        this.cumulativeWrittenBytes.addAndGet(write);
    }

    /* access modifiers changed from: package-private */
    public void bytesRealWriteFlowControl(long write) {
        this.realWrittenBytes.addAndGet(write);
    }

    public long checkInterval() {
        return this.checkInterval.get();
    }

    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }

    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }

    public long lastReadBytes() {
        return this.lastReadBytes;
    }

    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }

    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }

    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }

    public long lastTime() {
        return this.lastTime.get();
    }

    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }

    public AtomicLong getRealWrittenBytes() {
        return this.realWrittenBytes;
    }

    public long getRealWriteThroughput() {
        return this.realWriteThroughput;
    }

    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0);
        this.cumulativeWrittenBytes.set(0);
    }

    public String name() {
        return this.name;
    }

    @Deprecated
    public long readTimeToWait(long size, long limitTraffic, long maxTime) {
        return readTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
    }

    public long readTimeToWait(long size, long limitTraffic, long maxTime, long now) {
        long j = now;
        bytesRecvFlowControl(size);
        if (size == 0 || limitTraffic == 0) {
            return 0;
        }
        long lastTimeCheck = this.lastTime.get();
        long sum = this.currentReadBytes.get();
        long localReadingTime = this.readingTime;
        long interval = j - lastTimeCheck;
        long lastRB = this.lastReadBytes;
        long pastDelay = Math.max(this.lastReadingTime - lastTimeCheck, 0);
        if (interval > 10) {
            long j2 = lastTimeCheck;
            long time = (((1000 * sum) / limitTraffic) - interval) + pastDelay;
            if (time > 10) {
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    long j3 = lastRB;
                    internalLogger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
                }
                if (time > maxTime && (j + time) - localReadingTime > maxTime) {
                    time = maxTime;
                }
                long j4 = pastDelay;
                this.readingTime = Math.max(localReadingTime, j + time);
                return time;
            }
            long lastRB2 = pastDelay;
            this.readingTime = Math.max(localReadingTime, j);
            return 0;
        }
        long lastRB3 = lastRB;
        long pastDelay2 = pastDelay;
        long pastDelay3 = sum + lastRB3;
        long lastinterval = this.checkInterval.get() + interval;
        long j5 = sum;
        long time2 = (((1000 * pastDelay3) / limitTraffic) - lastinterval) + pastDelay2;
        if (time2 > 10) {
            InternalLogger internalLogger2 = logger;
            if (internalLogger2.isDebugEnabled()) {
                long j6 = interval;
                internalLogger2.debug("Time: " + time2 + ':' + pastDelay3 + ':' + lastinterval + ':' + pastDelay2);
            } else {
                long j7 = pastDelay2;
            }
            if (time2 > maxTime && (j + time2) - localReadingTime > maxTime) {
                time2 = maxTime;
            }
            long j8 = lastinterval;
            this.readingTime = Math.max(localReadingTime, j + time2);
            return time2;
        }
        this.readingTime = Math.max(localReadingTime, j);
        return 0;
    }

    @Deprecated
    public long writeTimeToWait(long size, long limitTraffic, long maxTime) {
        return writeTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
    }

    public long writeTimeToWait(long size, long limitTraffic, long maxTime, long now) {
        long j = now;
        bytesWriteFlowControl(size);
        if (size == 0) {
            return 0;
        }
        if (limitTraffic == 0) {
            return 0;
        }
        long lastTimeCheck = this.lastTime.get();
        long sum = this.currentWrittenBytes.get();
        long lastWB = this.lastWrittenBytes;
        long localWritingTime = this.writingTime;
        long pastDelay = Math.max(this.lastWritingTime - lastTimeCheck, 0);
        long interval = j - lastTimeCheck;
        long j2 = lastTimeCheck;
        if (interval > 10) {
            long time = (((1000 * sum) / limitTraffic) - interval) + pastDelay;
            if (time > 10) {
                long j3 = lastWB;
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
                }
                if (time > maxTime && (j + time) - localWritingTime > maxTime) {
                    time = maxTime;
                }
                this.writingTime = Math.max(localWritingTime, j + time);
                return time;
            }
            this.writingTime = Math.max(localWritingTime, j);
            return 0;
        }
        long lastWB2 = sum + lastWB;
        long lastinterval = this.checkInterval.get() + interval;
        long j4 = interval;
        long time2 = (((1000 * lastWB2) / limitTraffic) - lastinterval) + pastDelay;
        if (time2 > 10) {
            long j5 = sum;
            InternalLogger internalLogger2 = logger;
            if (internalLogger2.isDebugEnabled()) {
                internalLogger2.debug("Time: " + time2 + ':' + lastWB2 + ':' + lastinterval + ':' + pastDelay);
            }
            if (time2 > maxTime && (j + time2) - localWritingTime > maxTime) {
                time2 = maxTime;
            }
            this.writingTime = Math.max(localWritingTime, j + time2);
            return time2;
        }
        this.writingTime = Math.max(localWritingTime, j);
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(Opcodes.IF_ACMPEQ);
        sb.append("Monitor ");
        sb.append(this.name);
        sb.append(" Current Speed Read: ");
        sb.append(this.lastReadThroughput >> 10);
        sb.append(" KB/s, ");
        sb.append("Asked Write: ");
        sb.append(this.lastWriteThroughput >> 10);
        sb.append(" KB/s, ");
        sb.append("Real Write: ");
        sb.append(this.realWriteThroughput >> 10);
        sb.append(" KB/s, ");
        sb.append("Current Read: ");
        sb.append(this.currentReadBytes.get() >> 10);
        sb.append(" KB, ");
        sb.append("Current asked Write: ");
        sb.append(this.currentWrittenBytes.get() >> 10);
        sb.append(" KB, ");
        sb.append("Current real Write: ");
        sb.append(this.realWrittenBytes.get() >> 10);
        sb.append(" KB");
        return sb.toString();
    }
}
