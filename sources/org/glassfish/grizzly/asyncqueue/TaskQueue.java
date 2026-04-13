package org.glassfish.grizzly.asyncqueue;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.asyncqueue.AsyncQueueRecord;

public final class TaskQueue<E extends AsyncQueueRecord> {
    private static final AtomicReferenceFieldUpdater<TaskQueue, AsyncQueueRecord> currentElementUpdater;
    private static final AtomicIntegerFieldUpdater<TaskQueue> spaceInBytesUpdater;
    private static final AtomicIntegerFieldUpdater<TaskQueue> writeHandlersCounterUpdater;
    private volatile E currentElement;
    private volatile boolean isClosed;
    private final MutableMaxQueueSize maxQueueSizeHolder;
    private final Queue<E> queue;
    private volatile int spaceInBytes;
    private volatile int writeHandlersCounter;
    protected final Queue<WriteHandler> writeHandlersQueue = new ConcurrentLinkedQueue();

    public interface MutableMaxQueueSize {
        int getMaxQueueSize();
    }

    static {
        Class<TaskQueue> cls = TaskQueue.class;
        currentElementUpdater = AtomicReferenceFieldUpdater.newUpdater(cls, AsyncQueueRecord.class, "currentElement");
        spaceInBytesUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, "spaceInBytes");
        writeHandlersCounterUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, "writeHandlersCounter");
    }

    protected TaskQueue(MutableMaxQueueSize maxQueueSizeHolder2) {
        this.maxQueueSizeHolder = maxQueueSizeHolder2;
        this.queue = new ConcurrentLinkedQueue();
    }

    public static <E extends AsyncQueueRecord> TaskQueue<E> createTaskQueue(MutableMaxQueueSize maxQueueSizeHolder2) {
        return new TaskQueue<>(maxQueueSizeHolder2);
    }

    public int size() {
        return this.spaceInBytes;
    }

    public E poll() {
        E current = (AsyncQueueRecord) currentElementUpdater.getAndSet(this, (Object) null);
        return current == null ? (AsyncQueueRecord) this.queue.poll() : current;
    }

    public E peek() {
        E current = this.currentElement;
        if (current == null && (current = (AsyncQueueRecord) this.queue.poll()) != null) {
            this.currentElement = current;
        }
        if (current == null || !this.isClosed || !currentElementUpdater.compareAndSet(this, current, (Object) null)) {
            return current;
        }
        current.notifyFailure(new IOException("Connection closed"));
        return null;
    }

    public int reserveSpace(int amount) {
        return spaceInBytesUpdater.addAndGet(this, amount);
    }

    public int releaseSpace(int amount) {
        return spaceInBytesUpdater.addAndGet(this, -amount);
    }

    public int releaseSpaceAndNotify(int amount) {
        int space = releaseSpace(amount);
        doNotify();
        return space;
    }

    public int spaceInBytes() {
        return this.spaceInBytes;
    }

    public Queue<E> getQueue() {
        return this.queue;
    }

    public void notifyWritePossible(WriteHandler writeHandler) {
        notifyWritePossible(writeHandler, this.maxQueueSizeHolder.getMaxQueueSize());
    }

    public void notifyWritePossible(WriteHandler writeHandler, int maxQueueSize) {
        if (writeHandler != null) {
            if (this.isClosed) {
                writeHandler.onError(new IOException("Connection is closed"));
            } else if (maxQueueSize < 0 || spaceInBytes() < maxQueueSize) {
                try {
                    writeHandler.onWritePossible();
                } catch (Throwable e) {
                    writeHandler.onError(e);
                }
            } else {
                offerWriteHandler(writeHandler);
                if (spaceInBytes() >= maxQueueSize || !removeWriteHandler(writeHandler)) {
                    checkWriteHandlerOnClose(writeHandler);
                    return;
                }
                try {
                    writeHandler.onWritePossible();
                } catch (Throwable e2) {
                    writeHandler.onError(e2);
                }
            }
        }
    }

    public boolean forgetWritePossible(WriteHandler writeHandler) {
        return removeWriteHandler(writeHandler);
    }

    private void checkWriteHandlerOnClose(WriteHandler writeHandler) {
        if (this.isClosed && removeWriteHandler(writeHandler)) {
            writeHandler.onError(new IOException("Connection is closed"));
        }
    }

    public void doNotify() {
        WriteHandler writeHandler;
        if (this.maxQueueSizeHolder != null && this.writeHandlersCounter != 0) {
            int maxQueueSize = this.maxQueueSizeHolder.getMaxQueueSize();
            while (spaceInBytes() < maxQueueSize && (writeHandler = pollWriteHandler()) != null) {
                try {
                    writeHandler.onWritePossible();
                } catch (Throwable e) {
                    writeHandler.onError(e);
                }
            }
        }
    }

    public void setCurrentElement(E task) {
        this.currentElement = task;
        if (task != null && this.isClosed && currentElementUpdater.compareAndSet(this, task, (Object) null)) {
            task.notifyFailure(new IOException("Connection closed"));
        }
    }

    public boolean compareAndSetCurrentElement(E expected, E newValue) {
        AtomicReferenceFieldUpdater<TaskQueue, AsyncQueueRecord> atomicReferenceFieldUpdater = currentElementUpdater;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, expected, newValue)) {
            return false;
        }
        if (newValue == null || !this.isClosed || !atomicReferenceFieldUpdater.compareAndSet(this, newValue, (Object) null)) {
            return true;
        }
        newValue.notifyFailure(new IOException("Connection closed"));
        return false;
    }

    public boolean remove(E task) {
        return this.queue.remove(task);
    }

    public void offer(E task) {
        this.queue.offer(task);
        if (this.isClosed && this.queue.remove(task)) {
            task.notifyFailure(new IOException("Connection closed"));
        }
    }

    public boolean isEmpty() {
        return this.spaceInBytes == 0;
    }

    public void onClose() {
        onClose((Throwable) null);
    }

    public void onClose(Throwable cause) {
        this.isClosed = true;
        IOException error = null;
        if (!isEmpty()) {
            if (0 == 0) {
                error = new IOException("Connection closed", cause);
            }
            while (true) {
                AsyncQueueRecord poll = poll();
                AsyncQueueRecord record = poll;
                if (poll == null) {
                    break;
                }
                record.notifyFailure(error);
            }
        }
        while (true) {
            WriteHandler pollWriteHandler = pollWriteHandler();
            WriteHandler writeHandler = pollWriteHandler;
            if (pollWriteHandler != null) {
                if (error == null) {
                    error = new IOException("Connection closed", cause);
                }
                writeHandler.onError(error);
            } else {
                return;
            }
        }
    }

    private void offerWriteHandler(WriteHandler writeHandler) {
        writeHandlersCounterUpdater.incrementAndGet(this);
        this.writeHandlersQueue.offer(writeHandler);
    }

    private boolean removeWriteHandler(WriteHandler writeHandler) {
        if (!this.writeHandlersQueue.remove(writeHandler)) {
            return false;
        }
        writeHandlersCounterUpdater.decrementAndGet(this);
        return true;
    }

    private WriteHandler pollWriteHandler() {
        WriteHandler record = this.writeHandlersQueue.poll();
        if (record == null) {
            return null;
        }
        writeHandlersCounterUpdater.decrementAndGet(this);
        return record;
    }
}
