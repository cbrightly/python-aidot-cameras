package org.glassfish.grizzly.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;
import org.glassfish.grizzly.threadpool.AbstractThreadPool;

public class FixedThreadPool extends AbstractThreadPool {
    protected final BlockingQueue<Runnable> workQueue;

    public FixedThreadPool(ThreadPoolConfig config) {
        super(config);
        BlockingQueue<Runnable> blockingQueue;
        if (config.getQueue() != null) {
            blockingQueue = (BlockingQueue) config.getQueue();
        } else {
            blockingQueue = (BlockingQueue) config.setQueue(new LinkedTransferQueue()).getQueue();
        }
        this.workQueue = blockingQueue;
        int poolSize = config.getMaxPoolSize();
        synchronized (this.stateLock) {
            while (true) {
                int poolSize2 = poolSize - 1;
                if (poolSize > 0) {
                    doStartWorker();
                    poolSize = poolSize2;
                }
            }
            while (true) {
            }
        }
        ProbeNotifier.notifyThreadPoolStarted(this);
        super.onMaxNumberOfThreadsReached();
    }

    private void doStartWorker() {
        startWorker(new BasicWorker());
    }

    public void execute(Runnable command) {
        if (!this.running) {
            throw new RejectedExecutionException("ThreadPool is not running");
        } else if (!this.workQueue.offer(command)) {
            onTaskQueueOverflow();
        } else if (this.running || !this.workQueue.remove(command)) {
            onTaskQueued(command);
        } else {
            throw new RejectedExecutionException("ThreadPool is not running");
        }
    }

    public final class BasicWorker extends AbstractThreadPool.Worker {
        private BasicWorker() {
            super();
        }

        /* access modifiers changed from: protected */
        public Runnable getTask() {
            return FixedThreadPool.this.workQueue.take();
        }
    }
}
