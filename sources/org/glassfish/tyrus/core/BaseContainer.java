package org.glassfish.tyrus.core;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Endpoint;
import jakarta.websocket.Extension;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseContainer extends ExecutorServiceProvider implements WebSocketContainer {
    private static final Logger LOGGER = Logger.getLogger(BaseContainer.class.getName());
    private final Object EXECUTORS_CLEAN_UP_LOCK = new Object();
    private volatile ExecutorService executorService = null;
    private final ExecutorService managedExecutorService;
    private final ScheduledExecutorService managedScheduledExecutorService;
    private volatile ScheduledExecutorService scheduledExecutorService = null;
    private final ThreadFactory threadFactory;

    public interface ShutDownCondition {
        boolean evaluate();
    }

    public abstract /* synthetic */ Session connectToServer(Endpoint endpoint, ClientEndpointConfig clientEndpointConfig, URI uri);

    public abstract /* synthetic */ Session connectToServer(Class<? extends Endpoint> cls, ClientEndpointConfig clientEndpointConfig, URI uri);

    public abstract /* synthetic */ Session connectToServer(Class<?> cls, URI uri);

    public abstract /* synthetic */ Session connectToServer(Object obj, URI uri);

    public abstract /* synthetic */ long getDefaultAsyncSendTimeout();

    public abstract /* synthetic */ int getDefaultMaxBinaryMessageBufferSize();

    public abstract /* synthetic */ long getDefaultMaxSessionIdleTimeout();

    public abstract /* synthetic */ int getDefaultMaxTextMessageBufferSize();

    public abstract /* synthetic */ Set<Extension> getInstalledExtensions();

    public abstract /* synthetic */ void setAsyncSendTimeout(long j);

    public abstract /* synthetic */ void setDefaultMaxBinaryMessageBufferSize(int i);

    public abstract /* synthetic */ void setDefaultMaxSessionIdleTimeout(long j);

    public abstract /* synthetic */ void setDefaultMaxTextMessageBufferSize(int i);

    public BaseContainer() {
        ExecutorService lookupManagedExecutorService = lookupManagedExecutorService();
        this.managedExecutorService = lookupManagedExecutorService;
        ScheduledExecutorService lookupManagedScheduledExecutorService = lookupManagedScheduledExecutorService();
        this.managedScheduledExecutorService = lookupManagedScheduledExecutorService;
        if (lookupManagedExecutorService == null || lookupManagedScheduledExecutorService == null) {
            this.threadFactory = new DaemonThreadFactory();
        } else {
            this.threadFactory = null;
        }
    }

    public ExecutorService getExecutorService() {
        ExecutorService executorService2 = this.managedExecutorService;
        if (executorService2 != null) {
            return executorService2;
        }
        if (this.executorService == null) {
            synchronized (this.EXECUTORS_CLEAN_UP_LOCK) {
                if (this.executorService == null) {
                    this.executorService = Executors.newCachedThreadPool(this.threadFactory);
                }
            }
        }
        return this.executorService;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        ScheduledExecutorService scheduledExecutorService2 = this.managedScheduledExecutorService;
        if (scheduledExecutorService2 != null) {
            return scheduledExecutorService2;
        }
        if (this.scheduledExecutorService == null) {
            synchronized (this.EXECUTORS_CLEAN_UP_LOCK) {
                if (this.scheduledExecutorService == null) {
                    this.scheduledExecutorService = Executors.newScheduledThreadPool(10, this.threadFactory);
                }
            }
        }
        return this.scheduledExecutorService;
    }

    public void shutdown() {
        if (this.executorService != null) {
            this.executorService.shutdown();
            this.executorService = null;
        }
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdownNow();
            this.scheduledExecutorService = null;
        }
    }

    /* access modifiers changed from: protected */
    public void shutdown(ShutDownCondition shutDownCondition) {
        synchronized (this.EXECUTORS_CLEAN_UP_LOCK) {
            if (shutDownCondition.evaluate()) {
                shutdown();
            }
        }
    }

    private ExecutorService lookupManagedExecutorService() {
        try {
            Class<?> aClass = Class.forName("javax.naming.InitialContext");
            Object o = aClass.newInstance();
            return (ExecutorService) aClass.getMethod("lookup", new Class[]{String.class}).invoke(o, new Object[]{"java:comp/DefaultManagedExecutorService"});
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (!logger.isLoggable(level)) {
                return null;
            }
            logger.log(level, e.getMessage(), e);
            return null;
        } catch (LinkageError e2) {
            return null;
        }
    }

    private ScheduledExecutorService lookupManagedScheduledExecutorService() {
        try {
            Class<?> aClass = Class.forName("javax.naming.InitialContext");
            Object o = aClass.newInstance();
            return (ScheduledExecutorService) aClass.getMethod("lookup", new Class[]{String.class}).invoke(o, new Object[]{"java:comp/DefaultManagedScheduledExecutorService"});
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (!logger.isLoggable(level)) {
                return null;
            }
            logger.log(level, e.getMessage(), e);
            return null;
        } catch (LinkageError e2) {
            return null;
        }
    }

    public static class DaemonThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final String namePrefix = ("tyrus-" + poolNumber.getAndIncrement() + "-thread-");
        final AtomicInteger threadNumber = new AtomicInteger(1);

        DaemonThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread((ThreadGroup) null, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
            if (!t.isDaemon()) {
                t.setDaemon(true);
            }
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }
}
