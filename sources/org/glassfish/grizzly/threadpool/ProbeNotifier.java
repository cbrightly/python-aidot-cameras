package org.glassfish.grizzly.threadpool;

public final class ProbeNotifier {
    ProbeNotifier() {
    }

    static void notifyThreadPoolStarted(AbstractThreadPool threadPool) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onThreadPoolStartEvent(threadPool);
            }
        }
    }

    static void notifyThreadPoolStopped(AbstractThreadPool threadPool) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onThreadPoolStopEvent(threadPool);
            }
        }
    }

    static void notifyThreadAllocated(AbstractThreadPool threadPool, Thread thread) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onThreadAllocateEvent(threadPool, thread);
            }
        }
    }

    static void notifyThreadReleased(AbstractThreadPool threadPool, Thread thread) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onThreadReleaseEvent(threadPool, thread);
            }
        }
    }

    static void notifyMaxNumberOfThreads(AbstractThreadPool threadPool, int maxNumberOfThreads) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onMaxNumberOfThreadsEvent(threadPool, maxNumberOfThreads);
            }
        }
    }

    static void notifyTaskQueued(AbstractThreadPool threadPool, Runnable task) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onTaskQueueEvent(threadPool, task);
            }
        }
    }

    static void notifyTaskDequeued(AbstractThreadPool threadPool, Runnable task) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onTaskDequeueEvent(threadPool, task);
            }
        }
    }

    static void notifyTaskCancelled(AbstractThreadPool threadPool, Runnable task) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onTaskCancelEvent(threadPool, task);
            }
        }
    }

    static void notifyTaskCompleted(AbstractThreadPool threadPool, Runnable task) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onTaskCompleteEvent(threadPool, task);
            }
        }
    }

    static void notifyTaskQueueOverflow(AbstractThreadPool threadPool) {
        ThreadPoolProbe[] probes = (ThreadPoolProbe[]) threadPool.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ThreadPoolProbe probe : probes) {
                probe.onTaskQueueOverflowEvent(threadPool);
            }
        }
    }
}
