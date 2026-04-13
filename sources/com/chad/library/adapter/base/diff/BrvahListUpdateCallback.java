package com.chad.library.adapter.base.diff;

import androidx.recyclerview.widget.ListUpdateCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.b;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrvahListUpdateCallback.kt */
public final class BrvahListUpdateCallback implements ListUpdateCallback {
    private final BaseQuickAdapter<?, ?> c;

    public BrvahListUpdateCallback(@NotNull BaseQuickAdapter<?, ?> mAdapter) {
        k.e(mAdapter, "mAdapter");
        this.c = mAdapter;
    }

    public void onInserted(int position, int count) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.c;
        baseQuickAdapter.notifyItemRangeInserted(baseQuickAdapter.getHeaderLayoutCount() + position, count);
    }

    public void onRemoved(int position, int count) {
        b j = this.c.j();
        if (j != null && j.m() && this.c.getItemCount() == 0) {
            BaseQuickAdapter<?, ?> baseQuickAdapter = this.c;
            baseQuickAdapter.notifyItemRangeRemoved(baseQuickAdapter.getHeaderLayoutCount() + position, count + 1);
            return;
        }
        BaseQuickAdapter<?, ?> baseQuickAdapter2 = this.c;
        baseQuickAdapter2.notifyItemRangeRemoved(baseQuickAdapter2.getHeaderLayoutCount() + position, count);
    }

    public void onMoved(int fromPosition, int toPosition) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.c;
        baseQuickAdapter.notifyItemMoved(baseQuickAdapter.getHeaderLayoutCount() + fromPosition, this.c.getHeaderLayoutCount() + toPosition);
    }

    public void onChanged(int position, int count, @Nullable Object payload) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.c;
        baseQuickAdapter.notifyItemRangeChanged(baseQuickAdapter.getHeaderLayoutCount() + position, count, payload);
    }
}
