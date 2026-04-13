package org.glassfish.grizzly.nio.tmpselectors;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.Selectors;

public class TemporarySelectorPool {
    public static final int DEFAULT_SELECTORS_COUNT = 32;
    private static final Logger LOGGER = Grizzly.logger(TemporarySelectorPool.class);
    private static final int MISS_THRESHOLD = 10000;
    private final AtomicBoolean isClosed;
    private volatile int maxPoolSize;
    private final AtomicInteger missesCounter;
    private final AtomicInteger poolSize;
    private final SelectorProvider selectorProvider;
    private final Queue<Selector> selectors;

    public TemporarySelectorPool(SelectorProvider selectorProvider2) {
        this(selectorProvider2, 32);
    }

    public TemporarySelectorPool(SelectorProvider selectorProvider2, int selectorsCount) {
        this.selectorProvider = selectorProvider2;
        this.maxPoolSize = selectorsCount;
        this.isClosed = new AtomicBoolean();
        this.selectors = new ConcurrentLinkedQueue();
        this.poolSize = new AtomicInteger();
        this.missesCounter = new AtomicInteger();
    }

    public synchronized int size() {
        return this.maxPoolSize;
    }

    public synchronized void setSize(int size) {
        if (!this.isClosed.get()) {
            this.missesCounter.set(0);
            this.maxPoolSize = size;
        }
    }

    public SelectorProvider getSelectorProvider() {
        return this.selectorProvider;
    }

    public Selector poll() {
        Selector selector = this.selectors.poll();
        if (selector != null) {
            this.poolSize.decrementAndGet();
        } else {
            try {
                selector = Selectors.newSelector(this.selectorProvider);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION(), e);
            }
            int missesCount = this.missesCounter.incrementAndGet();
            if (missesCount % 10000 == 0) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_MISSES_EXCEPTION(Integer.valueOf(missesCount), Integer.valueOf(this.maxPoolSize)));
            }
        }
        return selector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void offer(java.nio.channels.Selector r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.concurrent.atomic.AtomicInteger r0 = r2.poolSize
            int r0 = r0.getAndIncrement()
            int r1 = r2.maxPoolSize
            if (r0 >= r1) goto L_0x001b
            java.nio.channels.Selector r0 = r2.checkSelector(r3)
            r3 = r0
            if (r0 == 0) goto L_0x001b
            java.util.Queue<java.nio.channels.Selector> r0 = r2.selectors
            r0.offer(r3)
            r0 = 1
            goto L_0x0024
        L_0x001b:
            java.util.concurrent.atomic.AtomicInteger r0 = r2.poolSize
            r0.decrementAndGet()
            if (r3 != 0) goto L_0x0023
            return
        L_0x0023:
            r0 = 0
        L_0x0024:
            java.util.concurrent.atomic.AtomicBoolean r1 = r2.isClosed
            boolean r1 = r1.get()
            if (r1 == 0) goto L_0x0038
            java.util.Queue<java.nio.channels.Selector> r1 = r2.selectors
            boolean r1 = r1.remove(r3)
            if (r1 == 0) goto L_0x003d
            r2.closeSelector(r3)
            goto L_0x003d
        L_0x0038:
            if (r0 != 0) goto L_0x003d
            r2.closeSelector(r3)
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorPool.offer(java.nio.channels.Selector):void");
    }

    public synchronized void close() {
        if (!this.isClosed.getAndSet(true)) {
            while (true) {
                Selector poll = this.selectors.poll();
                Selector selector = poll;
                if (poll == null) {
                    break;
                }
                closeSelector(selector);
            }
        }
    }

    private void closeSelector(Selector selector) {
        try {
            selector.close();
        } catch (IOException e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "TemporarySelectorFactory: error occurred when trying to close the Selector", e);
            }
        }
    }

    private Selector checkSelector(Selector selector) {
        try {
            selector.selectNow();
            return selector;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_SELECTOR_FAILURE_EXCEPTION(), e);
            try {
                return Selectors.newSelector(this.selectorProvider);
            } catch (IOException ee) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION(), ee);
                return null;
            }
        }
    }
}
