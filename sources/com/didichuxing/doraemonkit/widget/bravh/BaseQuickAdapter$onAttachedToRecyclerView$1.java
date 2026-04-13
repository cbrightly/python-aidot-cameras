package com.didichuxing.doraemonkit.widget.bravh;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.widget.bravh.listener.GridSpanSizeLookup;
import kotlin.jvm.internal.k;

/* compiled from: BaseQuickAdapter.kt */
public final class BaseQuickAdapter$onAttachedToRecyclerView$1 extends GridLayoutManager.SpanSizeLookup {
    final /* synthetic */ GridLayoutManager.SpanSizeLookup $defSpanSizeLookup;
    final /* synthetic */ RecyclerView.LayoutManager $manager;
    final /* synthetic */ BaseQuickAdapter this$0;

    BaseQuickAdapter$onAttachedToRecyclerView$1(BaseQuickAdapter $outer, RecyclerView.LayoutManager $captured_local_variable$1, GridLayoutManager.SpanSizeLookup $captured_local_variable$2) {
        this.this$0 = $outer;
        this.$manager = $captured_local_variable$1;
        this.$defSpanSizeLookup = $captured_local_variable$2;
    }

    public int getSpanSize(int position) {
        int type = this.this$0.getItemViewType(position);
        if (type == 268435729 && this.this$0.getHeaderViewAsFlow()) {
            return 1;
        }
        if (type == 268436275 && this.this$0.getFooterViewAsFlow()) {
            return 1;
        }
        if (this.this$0.mSpanSizeLookup == null) {
            return this.this$0.isFixedViewType(type) ? ((GridLayoutManager) this.$manager).getSpanCount() : this.$defSpanSizeLookup.getSpanSize(position);
        }
        if (this.this$0.isFixedViewType(type)) {
            return ((GridLayoutManager) this.$manager).getSpanCount();
        }
        GridSpanSizeLookup access$getMSpanSizeLookup$p = this.this$0.mSpanSizeLookup;
        if (access$getMSpanSizeLookup$p == null) {
            k.n();
        }
        return access$getMSpanSizeLookup$p.getSpanSize((GridLayoutManager) this.$manager, type, position - this.this$0.getHeaderLayoutCount());
    }
}
