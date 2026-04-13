package org.glassfish.tyrus.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TyrusFuture<T> implements Future<T> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile T result;
    private volatile Throwable throwable = null;

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return this.latch.getCount() == 0;
    }

    public T get() {
        this.latch.await();
        if (this.throwable == null) {
            return this.result;
        }
        throw new ExecutionException(this.throwable);
    }

    public T get(long timeout, TimeUnit unit) {
        if (!this.latch.await(timeout, unit)) {
            throw new TimeoutException();
        } else if (this.throwable == null) {
            return this.result;
        } else {
            throw new ExecutionException(this.throwable);
        }
    }

    public void setResult(T result2) {
        if (this.latch.getCount() == 1) {
            this.result = result2;
            this.latch.countDown();
        }
    }

    public void setFailure(Throwable throwable2) {
        if (this.latch.getCount() == 1) {
            this.throwable = throwable2;
            this.latch.countDown();
        }
    }
}
