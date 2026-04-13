package kotlin.reflect.jvm.internal.impl.storage;

/* compiled from: SingleThreadValue */
public class h<T> {
    private final T a;
    private final Thread b = Thread.currentThread();

    h(T value) {
        this.a = value;
    }

    public boolean b() {
        return this.b == Thread.currentThread();
    }

    public T a() {
        if (b()) {
            return this.a;
        }
        throw new IllegalStateException("No value in this thread (hasValue should be checked before)");
    }
}
