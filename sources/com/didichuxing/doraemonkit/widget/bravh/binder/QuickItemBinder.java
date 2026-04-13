package com.didichuxing.doraemonkit.widget.bravh.binder;

import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import com.didichuxing.doraemonkit.widget.bravh.util.AdapterUtilsKt;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: QuickItemBinder.kt */
public abstract class QuickItemBinder<T> extends BaseItemBinder<T, BaseViewHolder> {
    @LayoutRes
    public abstract int getLayoutId();

    @NotNull
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        return new BaseViewHolder(AdapterUtilsKt.getItemView(parent, getLayoutId()));
    }
}
