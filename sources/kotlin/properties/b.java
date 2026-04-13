package kotlin.properties;

import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ObservableProperty.kt */
public abstract class b<V> implements c<Object, V> {
    private V a;

    public b(V initialValue) {
        this.a = initialValue;
    }

    /* access modifiers changed from: protected */
    public boolean d(@NotNull k<?> property, V oldValue, V newValue) {
        kotlin.jvm.internal.k.e(property, "property");
        return true;
    }

    /* access modifiers changed from: protected */
    public void c(@NotNull k<?> property, V oldValue, V newValue) {
        kotlin.jvm.internal.k.e(property, "property");
    }

    public V b(@Nullable Object thisRef, @NotNull k<?> property) {
        kotlin.jvm.internal.k.e(property, "property");
        return this.a;
    }

    public void a(@Nullable Object thisRef, @NotNull k<?> property, V value) {
        kotlin.jvm.internal.k.e(property, "property");
        Object oldValue = this.a;
        if (d(property, oldValue, value)) {
            this.a = value;
            c(property, oldValue, value);
        }
    }
}
