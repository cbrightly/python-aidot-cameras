package kotlin.reflect;

import kotlin.jvm.functions.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: KProperty.kt */
public interface h<V> extends l<V>, k {

    /* compiled from: KProperty.kt */
    public interface a<V> extends g<V>, l<V, x> {
    }

    @NotNull
    a<V> getSetter();
}
