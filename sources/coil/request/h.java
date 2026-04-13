package coil.request;

import coil.request.i;
import coil.transform.a;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Gifs.kt */
public final class h {
    @NotNull
    public static final i.a d(@NotNull i.a $this$repeatCount, int repeatCount) {
        k.e($this$repeatCount, "<this>");
        if (repeatCount >= -1) {
            return i.a.r($this$repeatCount, "coil#repeat_count", Integer.valueOf(repeatCount), (String) null, 4, (Object) null);
        }
        throw new IllegalArgumentException(k.l("Invalid repeatCount: ", Integer.valueOf(repeatCount)).toString());
    }

    @Nullable
    public static final Integer e(@NotNull l $this$repeatCount) {
        k.e($this$repeatCount, "<this>");
        return (Integer) $this$repeatCount.f("coil#repeat_count");
    }

    @Nullable
    public static final a a(@NotNull l $this$animatedTransformation) {
        k.e($this$animatedTransformation, "<this>");
        return (a) $this$animatedTransformation.f("coil#animated_transformation");
    }

    @Nullable
    public static final kotlin.jvm.functions.a<x> c(@NotNull l $this$animationStartCallback) {
        k.e($this$animationStartCallback, "<this>");
        return (kotlin.jvm.functions.a) e0.e($this$animationStartCallback.f("coil#animation_start_callback"), 0);
    }

    @Nullable
    public static final kotlin.jvm.functions.a<x> b(@NotNull l $this$animationEndCallback) {
        k.e($this$animationEndCallback, "<this>");
        return (kotlin.jvm.functions.a) e0.e($this$animationEndCallback.f("coil#animation_end_callback"), 0);
    }
}
