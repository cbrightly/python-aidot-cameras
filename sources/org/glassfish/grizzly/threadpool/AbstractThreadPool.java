package org.glassfish.grizzly.threadpool;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.memory.ThreadLocalPoolProvider;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;

public abstract class AbstractThreadPool extends AbstractExecutorService implements Thread.UncaughtExceptionHandler, MonitoringAware<ThreadPoolProbe> {
    public static final int DEFAULT_IDLE_THREAD_KEEPALIVE_TIMEOUT = 30000;
    public static final int DEFAULT_MAX_TASKS_QUEUED = -1;
    public static final int DEFAULT_MAX_THREAD_COUNT = Integer.MAX_VALUE;
    public static final int DEFAULT_MIN_THREAD_COUNT;
    /* access modifiers changed from: private */
    public static final Long NEVER_TIMEOUT = Long.valueOf(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    private static final Logger logger = Grizzly.logger(AbstractThreadPool.class);
    protected static final Runnable poison = new Runnable() {
        public void run() {
        }
    };
    private static final DelayedExecutor.Resolver<Worker> transactionResolver = new DelayedExecutor.Resolver<Worker>() {
        public boolean removeTimeout(Worker element) {
            element.transactionExpirationTime = -1;
            return true;
        }

        public long getTimeoutMillis(Worker element) {
            return element.transactionExpirationTime;
        }

        public void setTimeoutMillis(Worker element, long timeoutMillis) {
            element.transactionExpirationTime = timeoutMillis;
        }
    };
    protected final ThreadPoolConfig config;
    protected final DelayedExecutor.DelayQueue<Worker> delayedQueue;
    protected final DefaultMonitoringConfig<ThreadPoolProbe> monitoringConfig;
    protected volatile boolean running = true;
    protected final Object stateLock = new Object();
    protected final long transactionTimeoutMillis;
    protected final Map<Worker, Long> workers = new HashMap();

    static {
        int processorsBasedThreadCount = Runtime.getRuntime().availableProcessors();
        int i = 5;
        if (processorsBasedThreadCount > 5) {
            i = processorsBasedThreadCount;
        }
        DEFAULT_MIN_THREAD_COUNT = i;
    }

    public AbstractThreadPool(ThreadPoolConfig config2) {
        AnonymousClass3 r1 = new DefaultMonitoringConfig<ThreadPoolProbe>(ThreadPoolProbe.class) {
            public Object createManagementObject() {
                return AbstractThreadPool.this.createJmxManagementObject();
            }
        };
        this.monitoringConfig = r1;
        if (config2.getMaxPoolSize() >= 1) {
            this.config = config2;
            if (config2.getInitialMonitoringConfig().hasProbes()) {
                r1.addProbes(config2.getInitialMonitoringConfig().getProbes());
            }
            if (config2.getThreadFactory() == null) {
                config2.setThreadFactory(getDefaultThreadFactory());
            }
            long transactionTimeout = config2.getTransactionTimeout(TimeUnit.MILLISECONDS);
            this.transactionTimeoutMillis = transactionTimeout;
            DelayedExecutor transactionMonitor = transactionTimeout > 0 ? config2.getTransactionMonitor() : null;
            if (transactionMonitor != null) {
                this.delayedQueue = transactionMonitor.createDelayQueue(new DelayedExecutor.Worker<Worker>() {
                    public boolean doWork(Worker worker) {
                        worker.t.interrupt();
                        AbstractThreadPool.this.delayedQueue.add(worker, AbstractThreadPool.NEVER_TIMEOUT.longValue(), TimeUnit.MILLISECONDS);
                        return true;
                    }
                }, transactionResolver);
            } else {
                this.delayedQueue = null;
            }
        } else {
            throw new IllegalArgumentException("poolsize < 1");
        }
    }

    /* access modifiers changed from: protected */
    public void startWorker(Worker worker) {
        worker.t = this.config.getThreadFactory().newThread(worker);
        this.workers.put(worker, Long.valueOf(System.currentTimeMillis()));
        worker.t.start();
    }

    public ThreadPoolConfig getConfig() {
        return this.config;
    }

    public Queue<Runnable> getQueue() {
        return this.config.getQueue();
    }

    public final int getSize() {
        int size;
        synchronized (this.stateLock) {
            size = this.workers.size();
        }
        return size;
    }

    public List<Runnable> shutdownNow() {
        List<Runnable> drained;
        synchronized (this.stateLock) {
            drained = new ArrayList<>();
            if (this.running) {
                this.running = false;
                drain(getQueue(), drained);
                for (Runnable task : drained) {
                    onTaskDequeued(task);
                    onTaskCancelled(task);
                }
                poisonAll();
                for (Worker w : this.workers.keySet()) {
                    w.t.interrupt();
                }
                ProbeNotifier.notifyThreadPoolStopped(this);
            }
        }
        return drained;
    }

    public void shutdown() {
        synchronized (this.stateLock) {
            if (this.running) {
                this.running = false;
                poisonAll();
                this.stateLock.notifyAll();
                ProbeNotifier.notifyThreadPoolStopped(this);
            }
        }
    }

    public boolean isShutdown() {
        return !this.running;
    }

    public boolean isTerminated() {
        boolean z;
        synchronized (this.stateLock) {
            z = !this.running && this.workers.isEmpty();
        }
        return z;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        long millis = unit.toMillis(timeout);
        long timeEnd = System.currentTimeMillis() + millis;
        synchronized (this.stateLock) {
            if (isTerminated()) {
                return true;
            }
            while (millis >= 20) {
                this.stateLock.wait(millis);
                if (isTerminated()) {
                    return true;
                }
                millis = timeEnd - System.currentTimeMillis();
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void poisonAll() {
        int size = (Math.max(this.config.getMaxPoolSize(), this.workers.size()) * 4) / 3;
        Queue<Runnable> q = getQueue();
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                q.offer(poison);
                size = size2;
            } else {
                return;
            }
        }
    }

    protected static void drain(Queue<Runnable> from, Collection<Runnable> to) {
        boolean cont = true;
        while (cont) {
            Runnable r = from.poll();
            boolean z = r != null;
            cont = z;
            if (z && r != poison) {
                to.add(r);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void validateNewPoolSize(int corePoolsize, int maxPoolSize) {
        if (maxPoolSize < 1) {
            throw new IllegalArgumentException("maxPoolsize < 1 :" + maxPoolSize);
        } else if (corePoolsize < 1) {
            throw new IllegalArgumentException("corePoolsize < 1 :" + corePoolsize);
        } else if (corePoolsize > maxPoolSize) {
            throw new IllegalArgumentException("corePoolsize > maxPoolSize: " + corePoolsize + " > " + maxPoolSize);
        }
    }

    /* access modifiers changed from: protected */
    public void beforeExecute(Worker worker, Thread t, Runnable r) {
        if (this.delayedQueue != null) {
            worker.transactionExpirationTime = System.currentTimeMillis() + this.transactionTimeoutMillis;
        }
        ClassLoader initial = this.config.getInitialClassLoader();
        if (initial != null) {
            t.setContextClassLoader(initial);
        }
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Worker worker, Thread thread, Runnable r, Throwable t) {
        if (this.delayedQueue != null) {
            worker.transactionExpirationTime = NEVER_TIMEOUT.longValue();
        }
    }

    /* access modifiers changed from: protected */
    public void onTaskCompletedEvent(Runnable task) {
        ProbeNotifier.notifyTaskCompleted(this, task);
    }

    /* access modifiers changed from: protected */
    public void onWorkerStarted(Worker worker) {
        DelayedExecutor.DelayQueue<Worker> delayQueue = this.delayedQueue;
        if (delayQueue != null) {
            delayQueue.add(worker, NEVER_TIMEOUT.longValue(), TimeUnit.MILLISECONDS);
        }
        ProbeNotifier.notifyThreadAllocated(this, worker.t);
    }

    /* access modifiers changed from: protected */
    public void onWorkerExit(Worker worker) {
        synchronized (this.stateLock) {
            this.workers.remove(worker);
            DelayedExecutor.DelayQueue<Worker> delayQueue = this.delayedQueue;
            if (delayQueue != null) {
                delayQueue.remove(worker);
            }
            if (this.workers.isEmpty()) {
                this.stateLock.notifyAll();
            }
        }
        ProbeNotifier.notifyThreadReleased(this, worker.t);
    }

    /* access modifiers changed from: protected */
    public void onMaxNumberOfThreadsReached() {
        ProbeNotifier.notifyMaxNumberOfThreads(this, this.config.getMaxPoolSize());
    }

    /* access modifiers changed from: protected */
    public void onTaskQueued(Runnable task) {
        ProbeNotifier.notifyTaskQueued(this, task);
    }

    /* access modifiers changed from: protected */
    public void onTaskDequeued(Runnable task) {
        ProbeNotifier.notifyTaskDequeued(this, task);
    }

    /* access modifiers changed from: protected */
    public void onTaskCancelled(Runnable task) {
        ProbeNotifier.notifyTaskCancelled(this, task);
    }

    /* access modifiers changed from: protected */
    public void onTaskQueueOverflow() {
        ProbeNotifier.notifyTaskQueueOverflow(this);
        throw new RejectedExecutionException("The thread pool's task queue is full, limit: " + this.config.getQueueLimit());
    }

    public MonitoringConfig<ThreadPoolProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        logger.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_THREADPOOL_UNCAUGHT_EXCEPTION(thread), throwable);
    }

    /* access modifiers changed from: package-private */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.threadpool.jmx.ThreadPool", this, AbstractThreadPool.class);
    }

    /* access modifiers changed from: protected */
    public final ThreadFactory getDefaultThreadFactory() {
        final AtomicInteger counter = new AtomicInteger();
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                ThreadLocalPoolProvider threadLocalPoolProvider;
                MemoryManager mm = AbstractThreadPool.this.config.getMemoryManager();
                if (mm instanceof ThreadLocalPoolProvider) {
                    threadLocalPoolProvider = (ThreadLocalPoolProvider) mm;
                } else {
                    threadLocalPoolProvider = null;
                }
                AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
                DefaultWorkerThread thread = new DefaultWorkerThread(attributeBuilder, AbstractThreadPool.this.config.getPoolName() + '(' + counter.incrementAndGet() + ')', threadLocalPoolProvider != null ? threadLocalPoolProvider.createThreadLocalPool() : null, r);
                thread.setUncaughtExceptionHandler(AbstractThreadPool.this);
                thread.setPriority(AbstractThreadPool.this.config.getPriority());
                thread.setDaemon(AbstractThreadPool.this.config.isDaemon());
                ClassLoader initial = AbstractThreadPool.this.config.getInitialClassLoader();
                if (initial != null) {
                    thread.setContextClassLoader(initial);
                }
                return thread;
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(getClass().getSimpleName());
        sb.append(" config: [");
        sb.append(this.config.toString());
        sb.append("]\r\n");
        sb.append(", is-shutdown=");
        sb.append(isShutdown());
        return sb.toString();
    }

    public abstract class Worker implements Runnable {
        protected Thread t;
        protected volatile long transactionExpirationTime;

        /* access modifiers changed from: protected */
        public abstract Runnable getTask();

        public Worker() {
        }

        public void run() {
            try {
                AbstractThreadPool.this.onWorkerStarted(this);
                doWork();
            } finally {
                AbstractThreadPool.this.onWorkerExit(this);
            }
        }

        /* access modifiers changed from: protected */
        public void doWork() {
            Runnable r;
            Throwable error;
            AbstractThreadPool abstractThreadPool;
            Thread thread = this.t;
            while (true) {
                try {
                    Thread.interrupted();
                    r = getTask();
                    if (r == AbstractThreadPool.poison) {
                        return;
                    }
                    if (r != null) {
                        AbstractThreadPool.this.onTaskDequeued(r);
                        error = null;
                        AbstractThreadPool.this.beforeExecute(this, thread, r);
                        r.run();
                        AbstractThreadPool.this.onTaskCompletedEvent(r);
                        abstractThreadPool = AbstractThreadPool.this;
                        abstractThreadPool.afterExecute(this, thread, r, error);
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    error = e;
                    abstractThreadPool = AbstractThreadPool.this;
                } catch (Exception e2) {
                } catch (Throwable th) {
                    AbstractThreadPool.this.afterExecute(this, thread, r, (Throwable) null);
                    throw th;
                }
            }
        }
    }
}
