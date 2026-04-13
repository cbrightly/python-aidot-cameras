package org.glassfish.tyrus.client;

import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolConfig {
    private static final ThreadPoolConfig DEFAULT;
    private static final int DEFAULT_CORE_POOL_SIZE = 1;
    private static final int DEFAULT_IDLE_THREAD_KEEP_ALIVE_TIMEOUT = 10;
    private static final int DEFAULT_MAX_POOL_SIZE;
    private static final int DEFAULT_MAX_QUEUE_SIZE = -1;
    private int corePoolSize;
    private ClassLoader initialClassLoader;
    private boolean isDaemon;
    private long keepAliveTimeMillis;
    private int maxPoolSize;
    private String poolName;
    private int priority = 10;
    private Queue<Runnable> queue;
    private int queueLimit = -1;
    private ThreadFactory threadFactory;

    static {
        int max = Math.max(Runtime.getRuntime().availableProcessors(), 20);
        DEFAULT_MAX_POOL_SIZE = max;
        DEFAULT = new ThreadPoolConfig("Tyrus-client", 1, max, (Queue<Runnable>) null, -1, 10, TimeUnit.SECONDS, (ThreadFactory) null, 5, true, (ClassLoader) null);
    }

    public static ThreadPoolConfig defaultConfig() {
        return DEFAULT.copy();
    }

    private ThreadPoolConfig(String poolName2, int corePoolSize2, int maxPoolSize2, Queue<Runnable> queue2, int queueLimit2, long keepAliveTime, TimeUnit timeUnit, ThreadFactory threadFactory2, int priority2, boolean isDaemon2, ClassLoader initialClassLoader2) {
        this.poolName = poolName2;
        this.corePoolSize = corePoolSize2;
        this.maxPoolSize = maxPoolSize2;
        this.queue = queue2;
        this.queueLimit = queueLimit2;
        if (keepAliveTime > 0) {
            this.keepAliveTimeMillis = TimeUnit.MILLISECONDS.convert(keepAliveTime, timeUnit);
        } else {
            this.keepAliveTimeMillis = keepAliveTime;
        }
        this.threadFactory = threadFactory2;
        this.priority = priority2;
        this.isDaemon = isDaemon2;
        this.initialClassLoader = initialClassLoader2;
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
        this.initialClassLoader = cfg.initialClassLoader;
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

    public ThreadPoolConfig setPoolName(String poolName2) {
        this.poolName = poolName2;
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
        if (maxPoolSize2 >= 3) {
            this.maxPoolSize = maxPoolSize2;
            return this;
        }
        throw new IllegalArgumentException("Max thread pool size cannot be smaller than 3");
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public ThreadPoolConfig setCorePoolSize(int corePoolSize2) {
        if (corePoolSize2 >= 0) {
            this.corePoolSize = corePoolSize2;
            return this;
        }
        throw new IllegalArgumentException("Core thread pool size cannot be smaller than 0");
    }

    public int getQueueLimit() {
        return this.queueLimit;
    }

    public ThreadPoolConfig setQueueLimit(int queueLimit2) {
        if (queueLimit2 < 0) {
            this.queueLimit = -1;
        } else {
            this.queueLimit = queueLimit2;
        }
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
        sb.append("\r\n  priority: ");
        sb.append(this.priority);
        sb.append("\r\n  isDaemon: ");
        sb.append(this.isDaemon);
        sb.append("\r\n  initialClassLoader: ");
        sb.append(this.initialClassLoader);
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThreadPoolConfig that = (ThreadPoolConfig) o;
        if (this.corePoolSize != that.corePoolSize || this.isDaemon != that.isDaemon || this.keepAliveTimeMillis != that.keepAliveTimeMillis || this.maxPoolSize != that.maxPoolSize || this.priority != that.priority || this.queueLimit != that.queueLimit) {
            return false;
        }
        ClassLoader classLoader = this.initialClassLoader;
        if (classLoader == null ? that.initialClassLoader != null : !classLoader.equals(that.initialClassLoader)) {
            return false;
        }
        String str = this.poolName;
        if (str == null ? that.poolName != null : !str.equals(that.poolName)) {
            return false;
        }
        Queue<Runnable> queue2 = this.queue;
        if (queue2 == null ? that.queue != null : !queue2.equals(that.queue)) {
            return false;
        }
        ThreadFactory threadFactory2 = this.threadFactory;
        if (threadFactory2 == null ? that.threadFactory == null : threadFactory2.equals(that.threadFactory)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.poolName;
        int i = 0;
        int result = (((((str != null ? str.hashCode() : 0) * 31) + this.corePoolSize) * 31) + this.maxPoolSize) * 31;
        Queue<Runnable> queue2 = this.queue;
        int hashCode = queue2 != null ? queue2.hashCode() : 0;
        long j = this.keepAliveTimeMillis;
        int result2 = (((((result + hashCode) * 31) + this.queueLimit) * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        ThreadFactory threadFactory2 = this.threadFactory;
        int result3 = (((((result2 + (threadFactory2 != null ? threadFactory2.hashCode() : 0)) * 31) + this.priority) * 31) + (this.isDaemon ? 1 : 0)) * 31;
        ClassLoader classLoader = this.initialClassLoader;
        if (classLoader != null) {
            i = classLoader.hashCode();
        }
        return result3 + i;
    }
}
