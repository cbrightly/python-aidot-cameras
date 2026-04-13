package com.didichuxing.doraemonkit.widget.bravh;

import android.view.ViewGroup;
import com.didichuxing.doraemonkit.widget.bravh.delegate.BaseMultiTypeDelegate;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseDelegateMultiAdapter.kt */
public abstract class BaseDelegateMultiAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {
    private BaseMultiTypeDelegate<T> mMultiTypeDelegate;

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

    public final void setMultiTypeDelegate(@NotNull BaseMultiTypeDelegate<T> multiTypeDelegate) {
        k.f(multiTypeDelegate, "multiTypeDelegate");
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    @Nullable
    public final BaseMultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        BaseMultiTypeDelegate delegate = getMultiTypeDelegate();
        if (delegate != null) {
            return createBaseViewHolder(parent, delegate.getLayoutId(viewType));
        }
        throw new IllegalStateException("Please use setMultiTypeDelegate first!".toString());
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        BaseMultiTypeDelegate delegate = getMultiTypeDelegate();
        if (delegate != null) {
            return delegate.getItemType(getData(), position);
        }
        throw new IllegalStateException("Please use setMultiTypeDelegate first!".toString());
    }
}
