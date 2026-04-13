package com.chad.library.adapter.base;

import androidx.annotation.LayoutRes;
import com.chad.library.adapter.base.entity.c;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseSectionQuickAdapter.kt */
public abstract class BaseSectionQuickAdapter<T extends c, VH extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, VH> {
    private final int O4;

    /* access modifiers changed from: protected */
    public abstract void x(@NotNull VH vh, @NotNull T t);

    public BaseSectionQuickAdapter(@LayoutRes int sectionHeadResId, @Nullable List<T> data) {
        super(data);
        this.O4 = sectionHeadResId;
        addItemType(-99, sectionHeadResId);
    }

    public BaseSectionQuickAdapter(@LayoutRes int sectionHeadResId, @LayoutRes int layoutResId, @Nullable List<T> data) {
        this(sectionHeadResId, data);
        setNormalLayout(layoutResId);
    }

    /* access modifiers changed from: protected */
    public void y(@NotNull VH helper, @NotNull T item, @NotNull List<Object> payloads) {
        k.e(helper, "helper");
        k.e(item, "item");
        k.e(payloads, "payloads");
    }

    /* access modifiers changed from: protected */
    public final void setNormalLayout(@LayoutRes int layoutResId) {
        addItemType(-100, layoutResId);
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || type == -99;
    }

    /* renamed from: q */
    public void onBindViewHolder(@NotNull VH holder, int position) {
        k.e(holder, "holder");
        if (holder.getItemViewType() == -99) {
            x(holder, (c) getItem(position - getHeaderLayoutCount()));
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    /* renamed from: r */
    public void onBindViewHolder(@NotNull VH holder, int position, @NotNull List<Object> payloads) {
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (holder.getItemViewType() == -99) {
            y(holder, (c) getItem(position - getHeaderLayoutCount()), payloads);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
}
