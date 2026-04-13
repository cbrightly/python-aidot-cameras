package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Lazy.kt */
public final class y<T> implements g<T>, Serializable {
    private Object _value = v.a;
    private a<? extends T> initializer;

    public y(@NotNull a<? extends T> initializer2) {
        k.e(initializer2, "initializer");
        this.initializer = initializer2;
    }

    public T getValue() {
        if (this._value == v.a) {
            a<? extends T> aVar = this.initializer;
            k.c(aVar);
            this._value = aVar.invoke();
            this.initializer = null;
        }
        return this._value;
    }

    public boolean isInitialized() {
        return this._value != v.a;
    }

    @NotNull
    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    private final Object writeReplace() {
        return new d(getValue());
    }
}
