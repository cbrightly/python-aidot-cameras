package kotlin.reflect;

import kotlin.jvm.functions.l;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty.kt */
public interface m<T, V> extends k<V>, l<T, V> {

    /* compiled from: KProperty.kt */
    public interface a<T, V> extends k.a<V>, l<T, V> {
    }

    @Nullable
    Object getDelegate(T t);

    @NotNull
    a<T, V> getGetter();
}
