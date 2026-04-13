package org.glassfish.tyrus.container.grizzly.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class GrizzlyTransportTimeoutFilter extends BaseFilter {
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(GrizzlyTransportTimeoutFilter.class.getName());
    /* access modifiers changed from: private */
    public static volatile boolean closed;
    private static final AtomicInteger connectionCounter = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("tyrus-grizzly-container-idle-timeout");
            thread.setDaemon(true);
            return thread;
        }
    });
    /* access modifiers changed from: private */
    public static volatile long lastAccessed;
    /* access modifiers changed from: private */
    public static volatile ScheduledFuture<?> timeoutTask;
    /* access modifiers changed from: private */
    public final int timeout;

    public GrizzlyTransportTimeoutFilter(int timeout2) {
        this.timeout = timeout2;
        closed = false;
    }

    public static void touch() {
        lastAccessed = System.currentTimeMillis();
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        connectionCounter.incrementAndGet();
        touch();
        return super.handleConnect(ctx);
    }

    public NextAction handleClose(FilterChainContext ctx) {
        AtomicInteger atomicInteger = connectionCounter;
        int connectionCount = atomicInteger.decrementAndGet();
        touch();
        if (connectionCount == 0 && timeoutTask == null) {
            Logger logger = LOGGER;
            Level level = Level.FINER;
            logger.log(level, "Scheduling IdleTimeoutTransportTask: " + this.timeout + " seconds.");
            timeoutTask = executorService.schedule(new IdleTimeoutTransportTask(atomicInteger), (long) this.timeout, TimeUnit.SECONDS);
        }
        return super.handleClose(ctx);
    }

    public class IdleTimeoutTransportTask implements Runnable {
        private final AtomicInteger connectionCounter;

        private IdleTimeoutTransportTask(AtomicInteger connectionCounter2) {
            this.connectionCounter = connectionCounter2;
        }

        public void run() {
            if (this.connectionCounter.get() == 0 && !GrizzlyTransportTimeoutFilter.closed) {
                long currentTime = System.currentTimeMillis();
                if (GrizzlyTransportTimeoutFilter.lastAccessed + ((long) (GrizzlyTransportTimeoutFilter.this.timeout * 1000)) < currentTime) {
                    boolean unused = GrizzlyTransportTimeoutFilter.closed = true;
                    ScheduledFuture unused2 = GrizzlyTransportTimeoutFilter.timeoutTask = null;
                    GrizzlyClientSocket.closeSharedTransport();
                    return;
                }
                long delay = (GrizzlyTransportTimeoutFilter.lastAccessed + ((long) (GrizzlyTransportTimeoutFilter.this.timeout * 1000))) - currentTime;
                Logger access$500 = GrizzlyTransportTimeoutFilter.LOGGER;
                Level level = Level.FINER;
                access$500.log(level, "Scheduling IdleTimeoutTransportTask: " + (delay / 1000) + " seconds.");
                ScheduledFuture unused3 = GrizzlyTransportTimeoutFilter.timeoutTask = GrizzlyTransportTimeoutFilter.executorService.schedule(new IdleTimeoutTransportTask(this.connectionCounter), delay, TimeUnit.MILLISECONDS);
            }
        }
    }
}
