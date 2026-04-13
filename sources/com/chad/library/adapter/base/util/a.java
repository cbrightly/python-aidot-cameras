package com.chad.library.adapter.base.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterUtils.kt */
public final class a {
    @NotNull
    public static final View a(@NotNull ViewGroup $this$getItemView, @LayoutRes int layoutResId) {
        k.e($this$getItemView, "$this$getItemView");
        View inflate = LayoutInflater.from($this$getItemView.getContext()).inflate(layoutResId, $this$getItemView, false);
        k.d(inflate, "LayoutInflater.from(this…layoutResId, this, false)");
        return inflate;
    }
}
