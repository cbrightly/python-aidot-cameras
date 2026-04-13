package kotlin;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyJVM.kt */
public final class q<T> implements g<T>, Serializable {
    @NotNull
    public static final a Companion = new a((DefaultConstructorMarker) null);
    private static final AtomicReferenceFieldUpdater<q<?>, Object> c = AtomicReferenceFieldUpdater.newUpdater(q.class, Object.class, "_value");
    private volatile Object _value;

    /* renamed from: final  reason: not valid java name */
    private final Object f30final;
    private volatile kotlin.jvm.functions.a<? extends T> initializer;

    public q(@NotNull kotlin.jvm.functions.a<? extends T> initializer2) {
        k.e(initializer2, "initializer");
        this.initializer = initializer2;
        v vVar = v.a;
        this._value = vVar;
        this.f30final = vVar;
    }

    public T getValue() {
        Object value = this._value;
        Object obj = v.a;
        if (value != obj) {
            return value;
        }
        kotlin.jvm.functions.a initializerValue = this.initializer;
        if (initializerValue != null) {
            Object newValue = initializerValue.invoke();
            if (c.compareAndSet(this, obj, newValue)) {
                this.initializer = null;
                return newValue;
            }
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

    /* compiled from: LazyJVM.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
