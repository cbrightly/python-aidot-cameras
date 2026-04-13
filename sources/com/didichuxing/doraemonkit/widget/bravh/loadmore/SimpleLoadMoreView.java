package com.didichuxing.doraemonkit.widget.bravh.loadmore;

import android.view.View;
import android.view.ViewGroup;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bravh.util.AdapterUtilsKt;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SimpleLoadMoreView.kt */
public final class SimpleLoadMoreView extends BaseLoadMoreView {
    @NotNull
    public View getRootView(@NotNull ViewGroup parent) {
        k.f(parent, "parent");
        return AdapterUtilsKt.getItemView(parent, R.layout.dk_brvah_quick_view_load_more);
    }

    @NotNull
    public View getLoadingView(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        return holder.getView(R.id.load_more_loading_view);
    }

    @NotNull
    public View getLoadComplete(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        return holder.getView(R.id.load_more_load_complete_view);
    }

    @NotNull
    public View getLoadEndView(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        return holder.getView(R.id.load_more_load_end_view);
    }

    @NotNull
    public View getLoadFailView(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        return holder.getView(R.id.load_more_load_fail_view);
    }
}
