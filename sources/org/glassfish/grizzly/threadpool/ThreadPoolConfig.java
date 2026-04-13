package org.glassfish.grizzly.threadpool;

import androidx.work.WorkRequest;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.utils.DelayedExecutor;

public final class ThreadPoolConfig {
    private static final ThreadPoolConfig DEFAULT = new ThreadPoolConfig("Grizzly", AbstractThreadPool.DEFAULT_MIN_THREAD_COUNT, AbstractThreadPool.DEFAULT_MAX_THREAD_COUNT, (Queue<Runnable>) null, -1, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS, (ThreadFactory) null, 5, true, (MemoryManager) null, (DelayedExecutor) null, -1, (ClassLoader) null);
    protected int corePoolSize;
    protected ClassLoader initialClassLoader;
    protected boolean isDaemon;
    protected long keepAliveTimeMillis;
    protected int maxPoolSize;
    protected MemoryManager mm;
    protected String poolName;
    protected int priority = 10;
    protected Queue<Runnable> queue;
    protected int queueLimit = -1;
    protected ThreadFactory threadFactory;
    protected final DefaultMonitoringConfig<ThreadPoolProbe> threadPoolMonitoringConfig;
    protected DelayedExecutor transactionMonitor;
    protected long transactionTimeoutMillis;

    public static ThreadPoolConfig defaultConfig() {
        return DEFAULT.copy();
    }

    private ThreadPoolConfig(String poolName2, int corePoolSize2, int maxPoolSize2, Queue<Runnable> queue2, int queueLimit2, long keepAliveTime, TimeUnit timeUnit, ThreadFactory threadFactory2, int priority2, boolean isDaemon2, MemoryManager mm2, DelayedExecutor transactionMonitor2, long transactionTimeoutMillis2, ClassLoader initialClassLoader2) {
        long j = keepAliveTime;
        this.poolName = poolName2;
        this.corePoolSize = corePoolSize2;
        this.maxPoolSize = maxPoolSize2;
        this.queue = queue2;
        this.queueLimit = queueLimit2;
        if (j > 0) {
            this.keepAliveTimeMillis = TimeUnit.MILLISECONDS.convert(j, timeUnit);
        } else {
            TimeUnit timeUnit2 = timeUnit;
            this.keepAliveTimeMillis = j;
        }
        this.threadFactory = threadFactory2;
        this.priority = priority2;
        this.isDaemon = isDaemon2;
        this.mm = mm2;
        this.transactionMonitor = transactionMonitor2;
        this.transactionTimeoutMillis = transactionTimeoutMillis2;
        this.initialClassLoader = initialClassLoader2;
        this.threadPoolMonitoringConfig = new DefaultMonitoringConfig<>(ThreadPoolProbe.class);
    }

    private ThreadPoolConfig(ThreadPoolConfig cfg) {
        this.queue = cfg.queue;
        this.threadFactory = cfg.threadFactory;
        this.poolName = cfg.poolName;
        this.priority = cfg.priority;
        this.isDaemon = cfg.isDaemon;
        this.maxPoolSize = cfg.maxPoolSize;
        this.queueLimit = cfg.queueLimit;
        this.corePoolSize = cfg.corePoolSize;
        this.keepAliveTimeMillis = cfg.keepAliveTimeMillis;
        this.mm = cfg.mm;
        this.initialClassLoader = cfg.initialClassLoader;
        DefaultMonitoringConfig<ThreadPoolProbe> defaultMonitoringConfig = new DefaultMonitoringConfig<>(ThreadPoolProbe.class);
        this.threadPoolMonitoringConfig = defaultMonitoringConfig;
        ThreadPoolProbe[] srcProbes = (ThreadPoolProbe[]) cfg.threadPoolMonitoringConfig.getProbesUnsafe();
        if (srcProbes != null) {
            defaultMonitoringConfig.addProbes(srcProbes);
        }
        this.transactionMonitor = cfg.transactionMonitor;
        this.transactionTimeoutMillis = cfg.transactionTimeoutMillis;
    }

    public ThreadPoolConfig copy() {
        return new ThreadPoolConfig(this);
    }

    public Queue<Runnable> getQueue() {
        return this.queue;
    }

    public ThreadPoolConfig setQueue(Queue<Runnable> queue2) {
        this.queue = queue2;
        return this;
    }

    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }

    public ThreadPoolConfig setThreadFactory(ThreadFactory threadFactory2) {
        this.threadFactory = threadFactory2;
        return this;
    }

    public String getPoolName() {
        return this.poolName;
    }

    public ThreadPoolConfig setPoolName(String poolname) {
        this.poolName = poolname;
        return this;
    }

    public int getPriority() {
        return this.priority;
    }

    public ThreadPoolConfig setPriority(int priority2) {
        this.priority = priority2;
        return this;
    }

    public boolean isDaemon() {
        return this.isDaemon;
    }

    public ThreadPoolConfig setDaemon(boolean isDaemon2) {
        this.isDaemon = isDaemon2;
        return this;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public ThreadPoolConfig setMaxPoolSize(int maxPoolSize2) {
        this.maxPoolSize = maxPoolSize2;
        return this;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public ThreadPoolConfig setCorePoolSize(int corePoolSize2) {
        this.corePoolSize = corePoolSize2;
        return this;
    }

    public int getQueueLimit() {
        return this.queueLimit;
    }

    public ThreadPoolConfig setQueueLimit(int queueLimit2) {
        this.queueLimit = queueLimit2;
        return this;
    }

    public ThreadPoolConfig setKeepAliveTime(long time, TimeUnit unit) {
        if (time < 0) {
            this.keepAliveTimeMillis = -1;
        } else {
            this.keepAliveTimeMillis = TimeUnit.MILLISECONDS.convert(time, unit);
        }
        return this;
    }

    public long getKeepAliveTime(TimeUnit timeUnit) {
        long j = this.keepAliveTimeMillis;
        if (j == -1) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public MemoryManager getMemoryManager() {
        return this.mm;
    }

    public ThreadPoolConfig setMemoryManager(MemoryManager mm2) {
        this.mm = mm2;
        return this;
    }

    public DefaultMonitoringConfig<ThreadPoolProbe> getInitialMonitoringConfig() {
        return this.threadPoolMonitoringConfig;
    }

    public DelayedExecutor getTransactionMonitor() {
        return this.transactionMonitor;
    }

    public ThreadPoolConfig setTransactionMonitor(DelayedExecutor transactionMonitor2) {
        this.transactionMonitor = transactionMonitor2;
        return this;
    }

    public long getTransactionTimeout(TimeUnit timeUnit) {
        long j = this.transactionTimeoutMillis;
        if (j > 0) {
            return timeUnit.convert(j, TimeUnit.MILLISECONDS);
        }
        return 0;
    }

    public ThreadPoolConfig setTransactionTimeout(long transactionTimeout, TimeUnit timeunit) {
        if (transactionTimeout > 0) {
            this.transactionTimeoutMillis = TimeUnit.MILLISECONDS.convert(transactionTimeout, timeunit);
        } else {
            this.transactionTimeoutMillis = 0;
        }
        return this;
    }

    public ThreadPoolConfig setTransactionTimeout(DelayedExecutor transactionMonitor2, long transactionTimeout, TimeUnit timeunit) {
        this.transactionMonitor = transactionMonitor2;
        return setTransactionTimeout(transactionTimeout, timeunit);
    }

    public ClassLoader getInitialClassLoader() {
        return this.initialClassLoader;
    }

    public ThreadPoolConfig setInitialClassLoader(ClassLoader initialClassLoader2) {
        this.initialClassLoader = initialClassLoader2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ThreadPoolConfig.class.getSimpleName());
        sb.append(" :\r\n  poolName: ");
        sb.append(this.poolName);
        sb.append("\r\n  corePoolSize: ");
        sb.append(this.corePoolSize);
        sb.append("\r\n  maxPoolSize: ");
        sb.append(this.maxPoolSize);
        sb.append("\r\n  queue: ");
        Object obj = this.queue;
        sb.append(obj != null ? obj.getClass() : "undefined");
        sb.append("\r\n  queueLimit: ");
        sb.append(this.queueLimit);
        sb.append("\r\n  keepAliveTime (millis): ");
        sb.append(this.keepAliveTimeMillis);
        sb.append("\r\n  threadFactory: ");
        sb.append(this.threadFactory);
        sb.append("\r\n  transactionMonitor: ");
        sb.append(this.transactionMonitor);
        sb.append("\r\n  transactionTimeoutMillis: ");
        sb.append(this.transactionTimeoutMillis);
        sb.append("\r\n  priority: ");
        sb.append(this.priority);
        sb.append("\r\n  isDaemon: ");
        sb.append(this.isDaemon);
        sb.append("\r\n  initialClassLoader: ");
        sb.append(this.initialClassLoader);
        return sb.toString();
    }
}
