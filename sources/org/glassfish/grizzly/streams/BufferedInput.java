package org.glassfish.grizzly.streams;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.utils.conditions.Condition;

public abstract class BufferedInput implements Input {
    protected CompletionHandler<Integer> completionHandler;
    protected final CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer();
    protected Condition condition;
    protected FutureImpl<Integer> future;
    private volatile boolean isClosed;
    protected boolean isCompletionHandlerRegistered;
    protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    protected Exception registrationStackTrace;

    /* access modifiers changed from: protected */
    public abstract void onCloseInputSource();

    /* access modifiers changed from: protected */
    public abstract void onOpenInputSource();

    /* JADX INFO: finally extract failed */
    public boolean append(Buffer buffer) {
        if (buffer == null) {
            return false;
        }
        this.lock.writeLock().lock();
        try {
            if (this.isClosed) {
                buffer.dispose();
            } else {
                if (buffer.remaining() > 0) {
                    this.compositeBuffer.append(buffer);
                }
                notifyUpdate();
            }
            this.lock.writeLock().unlock();
            return true;
        } catch (Throwable th) {
            this.lock.writeLock().unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean prepend(Buffer buffer) {
        if (buffer == null) {
            return false;
        }
        this.lock.writeLock().lock();
        try {
            if (this.isClosed) {
                buffer.dispose();
            } else {
                if (buffer.remaining() > 0) {
                    this.compositeBuffer.prepend(buffer);
                }
                notifyUpdate();
            }
            this.lock.writeLock().unlock();
            return true;
        } catch (Throwable th) {
            this.lock.writeLock().unlock();
            throw th;
        }
    }

    public byte read() {
        byte result = this.compositeBuffer.get();
        this.compositeBuffer.shrink();
        return result;
    }

    public void skip(int length) {
        if (length <= size()) {
            CompositeBuffer compositeBuffer2 = this.compositeBuffer;
            compositeBuffer2.position(compositeBuffer2.position() + length);
            this.compositeBuffer.shrink();
            return;
        }
        throw new IllegalStateException("Can not skip more bytes than available");
    }

    public final boolean isBuffered() {
        return true;
    }

    public Buffer getBuffer() {
        return this.compositeBuffer;
    }

    public Buffer takeBuffer() {
        Buffer duplicate = this.compositeBuffer.duplicate();
        this.compositeBuffer.removeAll();
        return duplicate;
    }

    public int size() {
        return this.compositeBuffer.remaining();
    }

    public void close() {
        this.lock.writeLock().lock();
        try {
            if (!this.isClosed) {
                this.isClosed = true;
                this.compositeBuffer.dispose();
                CompletionHandler<Integer> localCompletionHandler = this.completionHandler;
                if (localCompletionHandler != null) {
                    this.completionHandler = null;
                    this.isCompletionHandlerRegistered = false;
                    notifyFailure(localCompletionHandler, new EOFException("Input is closed"));
                }
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public GrizzlyFuture<Integer> notifyCondition(Condition condition2, CompletionHandler<Integer> completionHandler2) {
        this.lock.writeLock().lock();
        try {
            if (this.isCompletionHandlerRegistered) {
                throw new IllegalStateException("Only one notificator could be registered. Previous registration came from: ", this.registrationStackTrace);
            } else if (condition2.check()) {
                notifyCompleted(completionHandler2);
                return ReadyFutureImpl.create(Integer.valueOf(this.compositeBuffer.remaining()));
            } else {
                this.registrationStackTrace = new Exception();
                this.isCompletionHandlerRegistered = true;
                this.completionHandler = completionHandler2;
                FutureImpl<Integer> localFuture = SafeFutureImpl.create();
                this.future = localFuture;
                this.condition = condition2;
                onOpenInputSource();
                return localFuture;
            }
        } catch (IOException e) {
            notifyFailure(completionHandler2, e);
            return ReadyFutureImpl.create((Throwable) e);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private void notifyUpdate() {
        Condition condition2 = this.condition;
        if (condition2 != null && condition2.check()) {
            this.condition = null;
            CompletionHandler<Integer> localCompletionHandler = this.completionHandler;
            this.completionHandler = null;
            FutureImpl<Integer> localFuture = this.future;
            this.future = null;
            this.isCompletionHandlerRegistered = false;
            try {
                onCloseInputSource();
                notifyCompleted(localCompletionHandler);
                localFuture.result(Integer.valueOf(this.compositeBuffer.remaining()));
            } catch (IOException e) {
                notifyFailure(localCompletionHandler, e);
                localFuture.failure(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyCompleted(CompletionHandler<Integer> completionHandler2) {
        if (completionHandler2 != null) {
            completionHandler2.completed(Integer.valueOf(this.compositeBuffer.remaining()));
        }
    }

    /* access modifiers changed from: protected */
    public void notifyFailure(CompletionHandler<Integer> completionHandler2, Throwable failure) {
        if (completionHandler2 != null) {
            completionHandler2.failed(failure);
        }
    }
}
