package kotlin.reflect;

import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty.kt */
public interface l<V> extends k<V>, kotlin.jvm.functions.a<V> {

    /* compiled from: KProperty.kt */
    public interface a<V> extends k.a<V>, kotlin.jvm.functions.a<V> {
    }

    @Nullable
    Object getDelegate();

    @NotNull
    a<V> getGetter();
}
