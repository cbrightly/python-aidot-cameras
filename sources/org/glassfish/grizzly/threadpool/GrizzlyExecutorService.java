package org.glassfish.grizzly.threadpool;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;

public class GrizzlyExecutorService extends AbstractExecutorService implements MonitoringAware<ThreadPoolProbe> {
    protected volatile ThreadPoolConfig config;
    private volatile AbstractThreadPool pool;
    private final Object statelock = new Object();

    public static GrizzlyExecutorService createInstance() {
        return createInstance(ThreadPoolConfig.defaultConfig());
    }

    public static GrizzlyExecutorService createInstance(ThreadPoolConfig cfg) {
        return new GrizzlyExecutorService(cfg);
    }

    protected GrizzlyExecutorService(ThreadPoolConfig config2) {
        setImpl(config2);
    }

    /* access modifiers changed from: protected */
    public final void setImpl(ThreadPoolConfig cfg) {
        if (cfg != null) {
            ThreadPoolConfig cfg2 = cfg.copy();
            if (cfg2.getMemoryManager() == null) {
                cfg2.setMemoryManager(MemoryManager.DEFAULT_MEMORY_MANAGER);
            }
            Queue<Runnable> queue = cfg2.getQueue();
            if ((queue == null || (queue instanceof BlockingQueue)) && (cfg2.getCorePoolSize() < 0 || cfg2.getCorePoolSize() == cfg2.getMaxPoolSize())) {
                this.pool = cfg2.getQueueLimit() < 0 ? new FixedThreadPool(cfg2) : new QueueLimitedThreadPool(cfg2);
            } else {
                this.pool = new SyncThreadPool(cfg2);
            }
            this.config = cfg2;
            return;
        }
        throw new IllegalArgumentException("config is null");
    }

    public GrizzlyExecutorService reconfigure(ThreadPoolConfig config2) {
        synchronized (this.statelock) {
            AbstractThreadPool oldpool = this.pool;
            if (config2.getQueue() == oldpool.getQueue()) {
                config2.setQueue((Queue<Runnable>) null);
            }
            setImpl(config2);
            AbstractThreadPool.drain(oldpool.getQueue(), this.pool.getQueue());
            oldpool.shutdown();
        }
        return this;
    }

    public ThreadPoolConfig getConfiguration() {
        return this.config.copy();
    }

    public void shutdown() {
        this.pool.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return this.pool.shutdownNow();
    }

    public boolean isShutdown() {
        return this.pool.isShutdown();
    }

    public boolean isTerminated() {
        return this.pool.isTerminated();
    }

    public void execute(Runnable r) {
        this.pool.execute(r);
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return this.pool.awaitTermination(timeout, unit);
    }

    public MonitoringConfig<ThreadPoolProbe> getMonitoringConfig() {
        return this.pool.getMonitoringConfig();
    }
}
