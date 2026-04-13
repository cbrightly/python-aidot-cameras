package dagger.internal;

import javax.inject.Provider;
import javax.inject.a;

/* compiled from: SingleCheck */
public final class c<T> implements a<T> {
    private static final Object a = new Object();
    private volatile a<T> b;
    private volatile Object c = a;

    private c(a<T> provider) {
        if (provider != null) {
            this.b = provider;
            return;
        }
        throw new AssertionError();
    }

    public T get() {
        Object local = this.c;
        if (local != a) {
            return local;
        }
        Provider<T> providerReference = this.b;
        if (providerReference == null) {
            return this.c;
        }
        Object local2 = providerReference.get();
        this.c = local2;
        this.b = null;
        return local2;
    }

    public static <P extends a<T>, T> a<T> a(P provider) {
        if ((provider instanceof c) || (provider instanceof a)) {
            return provider;
        }
        return new c((a) b.b(provider));
    }
}
