package kotlin.coroutines.jvm.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DebugProbes.kt */
public final class h {
    @NotNull
    public static final <T> d<T> a(@NotNull d<? super T> completion) {
        k.e(completion, "completion");
        return completion;
    }

    public static final void b(@NotNull d<?> frame) {
        k.e(frame, TypedValues.AttributesType.S_FRAME);
    }

    public static final void c(@NotNull d<?> frame) {
        k.e(frame, TypedValues.AttributesType.S_FRAME);
    }
}
