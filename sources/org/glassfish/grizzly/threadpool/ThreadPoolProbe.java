package org.glassfish.grizzly.threadpool;

public interface ThreadPoolProbe {
    void onMaxNumberOfThreadsEvent(AbstractThreadPool abstractThreadPool, int i);

    void onTaskCancelEvent(AbstractThreadPool abstractThreadPool, Runnable runnable);

    void onTaskCompleteEvent(AbstractThreadPool abstractThreadPool, Runnable runnable);

    void onTaskDequeueEvent(AbstractThreadPool abstractThreadPool, Runnable runnable);

    void onTaskQueueEvent(AbstractThreadPool abstractThreadPool, Runnable runnable);

    void onTaskQueueOverflowEvent(AbstractThreadPool abstractThreadPool);

    void onThreadAllocateEvent(AbstractThreadPool abstractThreadPool, Thread thread);

    void onThreadPoolStartEvent(AbstractThreadPool abstractThreadPool);

    void onThreadPoolStopEvent(AbstractThreadPool abstractThreadPool);

    void onThreadReleaseEvent(AbstractThreadPool abstractThreadPool, Thread thread);

    public static class Adapter implements ThreadPoolProbe {
        public void onThreadPoolStartEvent(AbstractThreadPool threadPool) {
        }

        public void onThreadPoolStopEvent(AbstractThreadPool threadPool) {
        }

        public void onThreadAllocateEvent(AbstractThreadPool threadPool, Thread thread) {
        }

        public void onThreadReleaseEvent(AbstractThreadPool threadPool, Thread thread) {
        }

        public void onMaxNumberOfThreadsEvent(AbstractThreadPool threadPool, int maxNumberOfThreads) {
        }

        public void onTaskQueueEvent(AbstractThreadPool threadPool, Runnable task) {
        }

        public void onTaskDequeueEvent(AbstractThreadPool threadPool, Runnable task) {
        }

        public void onTaskCancelEvent(AbstractThreadPool threadPool, Runnable task) {
        }

        public void onTaskCompleteEvent(AbstractThreadPool threadPool, Runnable task) {
        }

        public void onTaskQueueOverflowEvent(AbstractThreadPool threadPool) {
        }
    }
}
