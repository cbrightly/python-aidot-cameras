package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.impl.FutureImpl;

public class CompletionHandlerAdapter<A, B> implements CompletionHandler<B> {
    private static final GenericAdapter DIRECT_ADAPTER = new GenericAdapter() {
        public Object adapt(Object result) {
            return result;
        }
    };
    private final GenericAdapter<B, A> adapter;
    private final CompletionHandler<A> completionHandler;
    private final FutureImpl<A> future;

    public CompletionHandlerAdapter(FutureImpl<A> future2) {
        this(future2, (CompletionHandler) null);
    }

    public CompletionHandlerAdapter(FutureImpl<A> future2, CompletionHandler<A> completionHandler2) {
        this(future2, completionHandler2, (GenericAdapter) null);
    }

    public CompletionHandlerAdapter(FutureImpl<A> future2, CompletionHandler<A> completionHandler2, GenericAdapter<B, A> adapter2) {
        this.future = future2;
        this.completionHandler = completionHandler2;
        if (adapter2 != null) {
            this.adapter = adapter2;
        } else {
            this.adapter = getDirectAdapter();
        }
    }

    public void cancelled() {
        CompletionHandler<A> completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.cancelled();
        }
        FutureImpl<A> futureImpl = this.future;
        if (futureImpl != null) {
            futureImpl.cancel(false);
        }
    }

    public void failed(Throwable throwable) {
        CompletionHandler<A> completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.failed(throwable);
        }
        FutureImpl<A> futureImpl = this.future;
        if (futureImpl != null) {
            futureImpl.failure(throwable);
        }
    }

    public void completed(B result) {
        A adaptedResult = adapt(result);
        CompletionHandler<A> completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.completed(adaptedResult);
        }
        FutureImpl<A> futureImpl = this.future;
        if (futureImpl != null) {
            futureImpl.result(adaptedResult);
        }
    }

    public void updated(B result) {
        A adaptedResult = adapt(result);
        CompletionHandler<A> completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.updated(adaptedResult);
        }
    }

    /* access modifiers changed from: protected */
    public A adapt(B result) {
        return this.adapter.adapt(result);
    }

    private static <K, V> GenericAdapter<K, V> getDirectAdapter() {
        return DIRECT_ADAPTER;
    }
}
