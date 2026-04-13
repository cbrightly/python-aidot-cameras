package com.google.android.datatransport.runtime.dagger.internal;

import javax.inject.a;

public final class DelegateFactory<T> implements Factory<T> {
    private a<T> delegate;

    public T get() {
        a<T> aVar = this.delegate;
        if (aVar != null) {
            return aVar.get();
        }
        throw new IllegalStateException();
    }

    @Deprecated
    public void setDelegatedProvider(a<T> delegate2) {
        setDelegate(this, delegate2);
    }

    public static <T> void setDelegate(a<T> delegateFactory, a<T> delegate2) {
        Preconditions.checkNotNull(delegate2);
        DelegateFactory<T> asDelegateFactory = (DelegateFactory) delegateFactory;
        if (asDelegateFactory.delegate == null) {
            asDelegateFactory.delegate = delegate2;
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: package-private */
    public a<T> getDelegate() {
        return (a) Preconditions.checkNotNull(this.delegate);
    }
}
