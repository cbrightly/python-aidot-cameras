package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import com.chad.library.adapter.base.entity.b;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.l;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseMultiItemQuickAdapter.kt */
public abstract class BaseMultiItemQuickAdapter<T extends b, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {
    private final g N4;

    public BaseMultiItemQuickAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    private final SparseIntArray getLayouts() {
        return (SparseIntArray) this.N4.getValue();
    }

    public BaseMultiItemQuickAdapter(@Nullable List<T> data) {
        super(0, data);
        this.N4 = i.a(k.NONE, a.INSTANCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseMultiItemQuickAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    /* compiled from: BaseMultiItemQuickAdapter.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<SparseIntArray> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        @NotNull
        public final SparseIntArray invoke() {
            return new SparseIntArray();
        }
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return ((b) getData().get(position)).getItemType();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH s(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.e(parent, "parent");
        int layoutResId = getLayouts().get(viewType);
        if (layoutResId != 0) {
            return h(parent, layoutResId);
        }
        throw new IllegalArgumentException(("ViewType: " + viewType + " found layoutResId，please use addItemType() first!").toString());
    }

    /* access modifiers changed from: protected */
    public final void addItemType(int type, @LayoutRes int layoutResId) {
        getLayouts().put(type, layoutResId);
    }
}
