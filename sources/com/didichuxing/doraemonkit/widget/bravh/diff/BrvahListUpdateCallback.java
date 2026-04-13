package com.didichuxing.doraemonkit.widget.bravh.diff;

import androidx.recyclerview.widget.ListUpdateCallback;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrvahListUpdateCallback.kt */
public final class BrvahListUpdateCallback implements ListUpdateCallback {
    private final BaseQuickAdapter<?, ?> mAdapter;

    public BrvahListUpdateCallback(@NotNull BaseQuickAdapter<?, ?> mAdapter2) {
        k.f(mAdapter2, "mAdapter");
        this.mAdapter = mAdapter2;
    }

    public void onInserted(int position, int count) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeInserted(baseQuickAdapter.getHeaderLayoutCount() + position, count);
    }

    public void onRemoved(int position, int count) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeRemoved(baseQuickAdapter.getHeaderLayoutCount() + position, count);
    }

    public void onMoved(int fromPosition, int toPosition) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemMoved(baseQuickAdapter.getHeaderLayoutCount() + fromPosition, this.mAdapter.getHeaderLayoutCount() + toPosition);
    }

    public void onChanged(int position, int count, @Nullable Object payload) {
        BaseQuickAdapter<?, ?> baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeChanged(baseQuickAdapter.getHeaderLayoutCount() + position, count, payload);
    }
}
