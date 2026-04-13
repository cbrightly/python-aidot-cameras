package kotlin.reflect;

import kotlin.jvm.functions.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: KProperty.kt */
public interface i<T, V> extends m<T, V>, k {

    /* compiled from: KProperty.kt */
    public interface a<T, V> extends g<V>, p<T, V, x> {
    }

    @NotNull
    a<T, V> getSetter();
}
