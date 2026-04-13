package com.google.android.datatransport.runtime.dagger.internal;

import com.google.android.datatransport.runtime.dagger.Lazy;
import javax.inject.a;

public final class ProviderOfLazy<T> implements a<Lazy<T>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final a<T> provider;

    private ProviderOfLazy(a<T> provider2) {
        if (provider2 != null) {
            this.provider = provider2;
            return;
        }
        throw new AssertionError();
    }

    public Lazy<T> get() {
        return DoubleCheck.lazy(this.provider);
    }

    public static <T> a<Lazy<T>> create(a<T> provider2) {
        return new ProviderOfLazy((a) Preconditions.checkNotNull(provider2));
    }
}
