package io.netty.handler.traffic;

import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GlobalChannelTrafficCounter extends TrafficCounter {
    public GlobalChannelTrafficCounter(GlobalChannelTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
        super(trafficShapingHandler, executor, name, checkInterval);
        if (executor == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
    }

    public static class MixedTrafficMonitoringTask implements Runnable {
        private final TrafficCounter counter;
        private final GlobalChannelTrafficShapingHandler trafficShapingHandler1;

        MixedTrafficMonitoringTask(GlobalChannelTrafficShapingHandler trafficShapingHandler, TrafficCounter counter2) {
            this.trafficShapingHandler1 = trafficShapingHandler;
            this.counter = counter2;
        }

        public void run() {
            if (this.counter.monitorActive) {
                long newLastTime = TrafficCounter.milliSecondFromNano();
                this.counter.resetAccounting(newLastTime);
                for (GlobalChannelTrafficShapingHandler.PerChannel perChannel : this.trafficShapingHandler1.channelQueues.values()) {
                    perChannel.channelTrafficCounter.resetAccounting(newLastTime);
                }
                this.trafficShapingHandler1.doAccounting(this.counter);
                TrafficCounter trafficCounter = this.counter;
                trafficCounter.scheduledFuture = trafficCounter.executor.schedule(this, trafficCounter.checkInterval.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.monitorActive     // Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r5)
            return
        L_0x0007:
            java.util.concurrent.atomic.AtomicLong r0 = r5.lastTime     // Catch:{ all -> 0x0036 }
            long r1 = io.netty.handler.traffic.TrafficCounter.milliSecondFromNano()     // Catch:{ all -> 0x0036 }
            r0.set(r1)     // Catch:{ all -> 0x0036 }
            java.util.concurrent.atomic.AtomicLong r0 = r5.checkInterval     // Catch:{ all -> 0x0036 }
            long r0 = r0.get()     // Catch:{ all -> 0x0036 }
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0034
            r2 = 1
            r5.monitorActive = r2     // Catch:{ all -> 0x0036 }
            io.netty.handler.traffic.GlobalChannelTrafficCounter$MixedTrafficMonitoringTask r2 = new io.netty.handler.traffic.GlobalChannelTrafficCounter$MixedTrafficMonitoringTask     // Catch:{ all -> 0x0036 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r3 = r5.trafficShapingHandler     // Catch:{ all -> 0x0036 }
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler r3 = (io.netty.handler.traffic.GlobalChannelTrafficShapingHandler) r3     // Catch:{ all -> 0x0036 }
            r2.<init>(r3, r5)     // Catch:{ all -> 0x0036 }
            r5.monitor = r2     // Catch:{ all -> 0x0036 }
            java.util.concurrent.ScheduledExecutorService r3 = r5.executor     // Catch:{ all -> 0x0036 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0036 }
            java.util.concurrent.ScheduledFuture r2 = r3.schedule(r2, r0, r4)     // Catch:{ all -> 0x0036 }
            r5.scheduledFuture = r2     // Catch:{ all -> 0x0036 }
        L_0x0034:
            monitor-exit(r5)
            return
        L_0x0036:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalChannelTrafficCounter.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.monitorActive     // Catch:{ all -> 0x0022 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 0
            r2.monitorActive = r0     // Catch:{ all -> 0x0022 }
            long r0 = io.netty.handler.traffic.TrafficCounter.milliSecondFromNano()     // Catch:{ all -> 0x0022 }
            r2.resetAccounting(r0)     // Catch:{ all -> 0x0022 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r0 = r2.trafficShapingHandler     // Catch:{ all -> 0x0022 }
            r0.doAccounting(r2)     // Catch:{ all -> 0x0022 }
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0020
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0022 }
            r1 = 1
            r0.cancel(r1)     // Catch:{ all -> 0x0022 }
        L_0x0020:
            monitor-exit(r2)
            return
        L_0x0022:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalChannelTrafficCounter.stop():void");
    }

    public void resetCumulativeTime() {
        for (GlobalChannelTrafficShapingHandler.PerChannel perChannel : ((GlobalChannelTrafficShapingHandler) this.trafficShapingHandler).channelQueues.values()) {
            perChannel.channelTrafficCounter.resetCumulativeTime();
        }
        super.resetCumulativeTime();
    }
}
