package com.didichuxing.doraemonkit.widget.bravh.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: QuickDataBindingItemBinder.kt */
public abstract class QuickDataBindingItemBinder<T, DB extends ViewDataBinding> extends BaseItemBinder<T, BinderDataBindingHolder<DB>> {
    @NotNull
    public abstract DB onCreateDataBinding(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup viewGroup, int i);

    /* compiled from: QuickDataBindingItemBinder.kt */
    public static final class BinderDataBindingHolder<DB extends ViewDataBinding> extends BaseViewHolder {
        @NotNull
        private final DB dataBinding;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BinderDataBindingHolder(@org.jetbrains.annotations.NotNull DB r3) {
            /*
                r2 = this;
                java.lang.String r0 = "dataBinding"
                kotlin.jvm.internal.k.f(r3, r0)
                android.view.View r0 = r3.getRoot()
                java.lang.String r1 = "dataBinding.root"
                kotlin.jvm.internal.k.b(r0, r1)
                r2.<init>(r0)
                r2.dataBinding = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.widget.bravh.binder.QuickDataBindingItemBinder.BinderDataBindingHolder.<init>(androidx.databinding.ViewDataBinding):void");
        }

        @NotNull
        public final DB getDataBinding() {
            return this.dataBinding;
        }
    }

    @NotNull
    public BinderDataBindingHolder<DB> onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        k.b(from, "LayoutInflater.from(parent.context)");
        return new BinderDataBindingHolder<>(onCreateDataBinding(from, parent, viewType));
    }
}
