package com.didichuxing.doraemonkit.widget.bravh.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterUtils.kt */
public final class AdapterUtilsKt {
    @NotNull
    public static final View getItemView(@NotNull ViewGroup $this$getItemView, @LayoutRes int layoutResId) {
        k.f($this$getItemView, "$this$getItemView");
        View inflate = LayoutInflater.from($this$getItemView.getContext()).inflate(layoutResId, $this$getItemView, false);
        k.b(inflate, "LayoutInflater.from(this…layoutResId, this, false)");
        return inflate;
    }
}
