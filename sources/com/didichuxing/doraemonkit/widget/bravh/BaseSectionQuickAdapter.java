package com.didichuxing.doraemonkit.widget.bravh;

import androidx.annotation.LayoutRes;
import com.didichuxing.doraemonkit.widget.bravh.entity.SectionEntity;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseSectionQuickAdapter.kt */
public abstract class BaseSectionQuickAdapter<T extends SectionEntity, VH extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, VH> {
    private final int sectionHeadResId;

    public BaseSectionQuickAdapter(@LayoutRes int i) {
        this(i, (List) null, 2, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    public abstract void convertHeader(@NotNull VH vh, @NotNull T t);

    public BaseSectionQuickAdapter(@LayoutRes int sectionHeadResId2, @Nullable List<T> data) {
        super(data);
        this.sectionHeadResId = sectionHeadResId2;
        addItemType(-99, sectionHeadResId2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseSectionQuickAdapter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list);
    }

    public BaseSectionQuickAdapter(@LayoutRes int sectionHeadResId2, @LayoutRes int layoutResId, @Nullable List<T> data) {
        this(sectionHeadResId2, data);
        setNormalLayout(layoutResId);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseSectionQuickAdapter(int i, int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? null : list);
    }

    /* access modifiers changed from: protected */
    public void convertHeader(@NotNull VH helper, @NotNull T item, @NotNull List<Object> payloads) {
        k.f(helper, "helper");
        k.f(item, "item");
        k.f(payloads, "payloads");
    }

    /* access modifiers changed from: protected */
    public final void setNormalLayout(@LayoutRes int layoutResId) {
        addItemType(-100, layoutResId);
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || type == -99;
    }

    public void onBindViewHolder(@NotNull VH holder, int position) {
        k.f(holder, "holder");
        if (holder.getItemViewType() == -99) {
            convertHeader(holder, (SectionEntity) getItem(position - getHeaderLayoutCount()));
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void onBindViewHolder(@NotNull VH holder, int position, @NotNull List<Object> payloads) {
        k.f(holder, "holder");
        k.f(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (holder.getItemViewType() == -99) {
            convertHeader(holder, (SectionEntity) getItem(position - getHeaderLayoutCount()), payloads);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
}
