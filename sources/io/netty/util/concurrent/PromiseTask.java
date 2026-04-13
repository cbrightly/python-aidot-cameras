package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

public class PromiseTask<V> extends DefaultPromise<V> implements RunnableFuture<V> {
    protected final Callable<V> task;

    static <T> Callable<T> toCallable(Runnable runnable, T result) {
        return new RunnableAdapter(runnable, result);
    }

    public static final class RunnableAdapter<T> implements Callable<T> {
        final T result;
        final Runnable task;

        RunnableAdapter(Runnable task2, T result2) {
            this.task = task2;
            this.result = result2;
        }

        public T call() {
            this.task.run();
            return this.result;
        }

        public String toString() {
            return "Callable(task: " + this.task + ", result: " + this.result + ')';
        }
    }

    PromiseTask(EventExecutor executor, Runnable runnable, V result) {
        this(executor, toCallable(runnable, result));
    }

    PromiseTask(EventExecutor executor, Callable<V> callable) {
        super(executor);
        this.task = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public void run() {
        try {
            if (setUncancellableInternal()) {
                setSuccessInternal(this.task.call());
            }
        } catch (Throwable e) {
            setFailureInternal(e);
        }
    }

    public final Promise<V> setFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Promise<V> setFailureInternal(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    public final boolean tryFailure(Throwable cause) {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean tryFailureInternal(Throwable cause) {
        return super.tryFailure(cause);
    }

    public final Promise<V> setSuccess(V v) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Promise<V> setSuccessInternal(V result) {
        super.setSuccess(result);
        return this;
    }

    public final boolean trySuccess(V v) {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean trySuccessInternal(V result) {
        return super.trySuccess(result);
    }

    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder buf = super.toStringBuilder();
        buf.setCharAt(buf.length() - 1, StringUtil.COMMA);
        buf.append(" task: ");
        buf.append(this.task);
        buf.append(')');
        return buf;
    }
}
