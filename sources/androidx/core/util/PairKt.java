package androidx.core.util;

import android.annotation.SuppressLint;
import android.util.Pair;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pair.kt */
public final class PairKt {
    @SuppressLint({"UnknownNullness"})
    public static final <F, S> F component1(@NotNull Pair<F, S> $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.first;
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> S component2(@NotNull Pair<F, S> $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.second;
    }

    @NotNull
    public static final <F, S> n<F, S> toKotlinPair(@NotNull Pair<F, S> $this$toKotlinPair) {
        k.e($this$toKotlinPair, "<this>");
        return new n<>($this$toKotlinPair.first, $this$toKotlinPair.second);
    }

    @NotNull
    public static final <F, S> Pair<F, S> toAndroidXPair(@NotNull n<? extends F, ? extends S> $this$toAndroidXPair) {
        k.e($this$toAndroidXPair, "<this>");
        return new Pair<>($this$toAndroidXPair.getFirst(), $this$toAndroidXPair.getSecond());
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> F component1(@NotNull Pair<F, S> $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.first;
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> S component2(@NotNull Pair<F, S> $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.second;
    }

    @NotNull
    public static final <F, S> n<F, S> toKotlinPair(@NotNull Pair<F, S> $this$toKotlinPair) {
        k.e($this$toKotlinPair, "<this>");
        return new n<>($this$toKotlinPair.first, $this$toKotlinPair.second);
    }

    @NotNull
    public static final <F, S> Pair<F, S> toAndroidPair(@NotNull n<? extends F, ? extends S> $this$toAndroidPair) {
        k.e($this$toAndroidPair, "<this>");
        return new Pair<>($this$toAndroidPair.getFirst(), $this$toAndroidPair.getSecond());
    }
}
