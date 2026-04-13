package com.chad.library.adapter.base;

import android.view.ViewGroup;
import com.chad.library.adapter.base.delegate.a;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseDelegateMultiAdapter.kt */
public abstract class BaseDelegateMultiAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {
    private a<T> N4;

    public BaseDelegateMultiAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    public BaseDelegateMultiAdapter(@Nullable List<T> data) {
        super(0, data);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseDelegateMultiAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    @Nullable
    public final a<T> x() {
        return this.N4;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH s(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        a delegate = x();
        if (delegate != null) {
            return h(parent, delegate.b(viewType));
        }
        throw new IllegalStateException("Please use setMultiTypeDelegate first!".toString());
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        a delegate = x();
        if (delegate != null) {
            return delegate.a(getData(), position);
        }
        throw new IllegalStateException("Please use setMultiTypeDelegate first!".toString());
    }
}
