package com.didichuxing.doraemonkit.widget.bravh;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import com.didichuxing.doraemonkit.widget.bravh.entity.MultiItemEntity;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseMultiItemQuickAdapter.kt */
public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {
    private final g layouts$delegate;

    public BaseMultiItemQuickAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    private final SparseIntArray getLayouts() {
        return (SparseIntArray) this.layouts$delegate.getValue();
    }

    public BaseMultiItemQuickAdapter(@Nullable List<T> data) {
        super(0, data);
        this.layouts$delegate = i.a(k.NONE, BaseMultiItemQuickAdapter$layouts$2.INSTANCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseMultiItemQuickAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return ((MultiItemEntity) getData().get(position)).getItemType();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public VH onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.f(parent, "parent");
        int layoutResId = getLayouts().get(viewType);
        if (layoutResId != 0) {
            return createBaseViewHolder(parent, layoutResId);
        }
        throw new IllegalArgumentException(("ViewType: " + viewType + " found layoutResId，please use addItemType() first!").toString());
    }

    /* access modifiers changed from: protected */
    public final void addItemType(int type, @LayoutRes int layoutResId) {
        getLayouts().put(type, layoutResId);
    }
}
