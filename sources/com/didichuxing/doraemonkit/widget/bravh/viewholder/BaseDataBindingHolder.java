package com.didichuxing.doraemonkit.widget.bravh.viewholder;

import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseDataBindingHolder.kt */
public class BaseDataBindingHolder<BD extends ViewDataBinding> extends BaseViewHolder {
    @Nullable
    private final BD dataBinding;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseDataBindingHolder(@NotNull View view) {
        super(view);
        k.f(view, "view");
        this.dataBinding = DataBindingUtil.bind(view);
    }

    @Nullable
    public final BD getDataBinding() {
        return this.dataBinding;
    }
}
