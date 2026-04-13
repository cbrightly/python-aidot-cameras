package org.glassfish.tyrus.container.grizzly.client;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskProcessor {
    private final Condition condition;
    private final Lock taskLock;
    private final Queue<Task> taskQueue;

    public interface Condition {
        boolean isValid();
    }

    public static abstract class Task {
        public abstract void execute();
    }

    public TaskProcessor(Condition condition2) {
        this.taskQueue = new ConcurrentLinkedQueue();
        this.taskLock = new ReentrantLock();
        this.condition = condition2;
    }

    public TaskProcessor() {
        this.taskQueue = new ConcurrentLinkedQueue();
        this.taskLock = new ReentrantLock();
        this.condition = null;
    }

    public void processTask(Task task) {
        this.taskQueue.offer(task);
        processTask();
    }

    public void processTask() {
        if (this.taskLock.tryLock()) {
            while (!this.taskQueue.isEmpty()) {
                Condition condition2 = this.condition;
                if (condition2 == null || condition2.isValid()) {
                    try {
                        Task first = this.taskQueue.poll();
                        if (first != null) {
                            first.execute();
                        }
                    } catch (Throwable th) {
                        this.taskLock.unlock();
                        throw th;
                    }
                } else {
                    this.taskLock.unlock();
                    return;
                }
            }
            this.taskLock.unlock();
            if (!this.taskQueue.isEmpty()) {
                processTask();
            }
        }
    }
}
