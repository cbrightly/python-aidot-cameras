package dagger.internal;

/* compiled from: DoubleCheck */
public final class a<T> implements javax.inject.a<T> {
    private static final Object a = new Object();
    private volatile javax.inject.a<T> b;
    private volatile Object c = a;

    private a(javax.inject.a<T> provider) {
        if (provider != null) {
            this.b = provider;
            return;
        }
        throw new AssertionError();
    }

    public T get() {
        Object result = this.c;
        Object obj = a;
        if (result == obj) {
            synchronized (this) {
                result = this.c;
                if (result == obj) {
                    result = this.b.get();
                    this.c = b(this.c, result);
                    this.b = null;
                }
            }
        }
        return result;
    }

    private static Object b(Object currentInstance, Object newInstance) {
        if (!(currentInstance != a) || currentInstance == newInstance) {
            return newInstance;
        }
        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + currentInstance + " & " + newInstance + ". This is likely due to a circular dependency.");
    }

    public static <P extends javax.inject.a<T>, T> javax.inject.a<T> a(P delegate) {
        b.b(delegate);
        if (delegate instanceof a) {
            return delegate;
        }
        return new a(delegate);
    }
}
