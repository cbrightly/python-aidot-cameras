package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinType.kt */
public final class d0 {
    public static final boolean a(@NotNull b0 $this$isError) {
        k.f($this$isError, "$this$isError");
        g1 unwrapped = $this$isError.L0();
        return (unwrapped instanceof t) || ((unwrapped instanceof v) && (((v) unwrapped).P0() instanceof t));
    }
}
