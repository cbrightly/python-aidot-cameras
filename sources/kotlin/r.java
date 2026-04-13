package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJVM.kt */
public final class r<T> implements g<T>, Serializable {
    private volatile Object _value;
    private a<? extends T> initializer;
    private final Object lock;

    public r(@NotNull a<? extends T> initializer2, @Nullable Object lock2) {
        k.e(initializer2, "initializer");
        this.initializer = initializer2;
        this._value = v.a;
        this.lock = lock2 != null ? lock2 : this;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ r(a aVar, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(aVar, (i & 2) != 0 ? null : obj);
    }

    public T getValue() {
        Object _v2;
        Object _v1 = this._value;
        Object obj = v.a;
        if (_v1 != obj) {
            return _v1;
        }
        synchronized (this.lock) {
            _v2 = this._value;
            if (_v2 == obj) {
                a aVar = this.initializer;
                k.c(aVar);
                Object typedValue = aVar.invoke();
                this._value = typedValue;
                this.initializer = null;
                _v2 = typedValue;
            }
        }
        return _v2;
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
