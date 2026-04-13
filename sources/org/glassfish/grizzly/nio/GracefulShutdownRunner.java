package org.glassfish.grizzly.nio;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ShutdownContext;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.localization.LogMessages;

public class GracefulShutdownRunner implements Runnable {
    private static final Logger LOGGER = Grizzly.logger(GracefulShutdownRunner.class);
    private final long gracePeriod;
    /* access modifiers changed from: private */
    public final Set<GracefulShutdownListener> shutdownListeners;
    private final ExecutorService shutdownService;
    private final TimeUnit timeUnit;
    /* access modifiers changed from: private */
    public final NIOTransport transport;

    GracefulShutdownRunner(NIOTransport transport2, Set<GracefulShutdownListener> shutdownListeners2, ExecutorService shutdownService2, long gracePeriod2, TimeUnit timeUnit2) {
        this.transport = transport2;
        this.shutdownListeners = shutdownListeners2;
        this.shutdownService = shutdownService2;
        this.gracePeriod = gracePeriod2;
        this.timeUnit = timeUnit2;
    }

    public void run() {
        Lock lock;
        int listenerCount = this.shutdownListeners.size();
        final CountDownLatch shutdownLatch = new CountDownLatch(listenerCount);
        final Map<ShutdownContext, GracefulShutdownListener> contexts = new HashMap<>(listenerCount);
        if (this.gracePeriod <= 0) {
            for (GracefulShutdownListener l : this.shutdownListeners) {
                l.shutdownRequested(createContext(contexts, l, shutdownLatch));
            }
        } else {
            this.shutdownService.execute(new Runnable() {
                public void run() {
                    for (GracefulShutdownListener l : GracefulShutdownRunner.this.shutdownListeners) {
                        l.shutdownRequested(GracefulShutdownRunner.this.createContext(contexts, l, shutdownLatch));
                    }
                }
            });
        }
        try {
            if (this.gracePeriod <= 0) {
                shutdownLatch.await();
            } else {
                Logger logger = LOGGER;
                Level level = Level.WARNING;
                if (logger.isLoggable(level)) {
                    logger.log(level, LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_MSG(this.transport.getName() + '[' + Integer.toHexString(hashCode()) + ']', Long.valueOf(this.gracePeriod), this.timeUnit));
                }
                if (!shutdownLatch.await(this.gracePeriod, this.timeUnit)) {
                    if (logger.isLoggable(level)) {
                        logger.log(level, LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_EXCEEDED(this.transport.getName() + '[' + Integer.toHexString(hashCode()) + ']'));
                    }
                    if (!contexts.isEmpty()) {
                        for (GracefulShutdownListener l2 : contexts.values()) {
                            l2.shutdownForced();
                        }
                    }
                }
            }
            Lock lock2 = this.transport.getState().getStateLocker().writeLock();
            lock2.lock();
            try {
                NIOTransport nIOTransport = this.transport;
                if (nIOTransport.shutdownService == this.shutdownService) {
                    nIOTransport.finalizeShutdown();
                }
                lock2.unlock();
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } catch (InterruptedException e) {
            try {
                Logger logger2 = LOGGER;
                if (logger2.isLoggable(Level.WARNING)) {
                    logger2.warning(LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_INTERRUPTED());
                }
                if (!contexts.isEmpty()) {
                    for (GracefulShutdownListener l3 : contexts.values()) {
                        l3.shutdownForced();
                    }
                }
                lock = this.transport.getState().getStateLocker().writeLock();
                lock.lock();
                NIOTransport nIOTransport2 = this.transport;
                if (nIOTransport2.shutdownService == this.shutdownService) {
                    nIOTransport2.finalizeShutdown();
                }
            } catch (Throwable th2) {
                lock = this.transport.getState().getStateLocker().writeLock();
                lock.lock();
                NIOTransport nIOTransport3 = this.transport;
                if (nIOTransport3.shutdownService == this.shutdownService) {
                    nIOTransport3.finalizeShutdown();
                }
                throw th2;
            } finally {
                lock.unlock();
            }
        } catch (Throwable th3) {
            throw th3;
        }
    }

    /* access modifiers changed from: private */
    public ShutdownContext createContext(final Map<ShutdownContext, GracefulShutdownListener> contexts, GracefulShutdownListener listener, final CountDownLatch shutdownLatch) {
        ShutdownContext ctx = new ShutdownContext() {
            boolean isNotified;

            public Transport getTransport() {
                return GracefulShutdownRunner.this.transport;
            }

            public synchronized void ready() {
                if (!this.isNotified) {
                    this.isNotified = true;
                    contexts.remove(this);
                    shutdownLatch.countDown();
                }
            }
        };
        contexts.put(ctx, listener);
        return ctx;
    }
}
