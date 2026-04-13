package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Copyable;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.impl.UnsafeFutureImpl;

public class Futures {
    public static <R> FutureImpl<R> createSafeFuture() {
        return SafeFutureImpl.create();
    }

    public static <R> FutureImpl<R> createUnsafeFuture() {
        return UnsafeFutureImpl.create();
    }

    public static <R> GrizzlyFuture<R> createReadyFuture(R result) {
        return ReadyFutureImpl.create(result);
    }

    public static <R> GrizzlyFuture<R> createReadyFuture(Throwable error) {
        return ReadyFutureImpl.create(error);
    }

    public static <R> void notifyResult(FutureImpl<R> future, CompletionHandler<R> completionHandler, R result) {
        if (completionHandler != null) {
            completionHandler.completed(result);
        }
        if (future != null) {
            future.result(result);
        }
    }

    public static <R> void notifyFailure(FutureImpl<R> future, CompletionHandler completionHandler, Throwable error) {
        if (completionHandler != null) {
            completionHandler.failed(error);
        }
        if (future != null) {
            future.failure(error);
        }
    }

    public static <R> void notifyCancel(FutureImpl<R> future, CompletionHandler completionHandler) {
        if (completionHandler != null) {
            completionHandler.cancelled();
        }
        if (future != null) {
            future.cancel(false);
        }
    }

    public static <R> CompletionHandler<R> toCompletionHandler(FutureImpl<R> future) {
        return new FutureToCompletionHandler(future);
    }

    public static <R> CompletionHandler<R> toCompletionHandler(FutureImpl<R> future, CompletionHandler<R> completionHandler) {
        return new CompletionHandlerAdapter(future, completionHandler);
    }

    public static <A, B> CompletionHandler<B> toAdaptedCompletionHandler(FutureImpl<A> future, GenericAdapter<B, A> adapter) {
        return toAdaptedCompletionHandler(future, (CompletionHandler) null, adapter);
    }

    public static <A, B> CompletionHandler<B> toAdaptedCompletionHandler(FutureImpl<A> future, CompletionHandler<A> completionHandler, GenericAdapter<B, A> adapter) {
        return new CompletionHandlerAdapter(future, completionHandler, adapter);
    }

    public static final class FutureToCompletionHandler<E> extends EmptyCompletionHandler<E> {
        private final FutureImpl<E> future;

        public FutureToCompletionHandler(FutureImpl<E> future2) {
            this.future = future2;
        }

        public void cancelled() {
            this.future.cancel(false);
        }

        public void completed(E result) {
            if (result instanceof Copyable) {
                result = ((Copyable) result).copy();
            }
            this.future.result(result);
        }

        public void failed(Throwable throwable) {
            this.future.failure(throwable);
        }
    }
}
