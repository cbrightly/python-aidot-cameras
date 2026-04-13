package com.chad.library.adapter.base;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.listener.a;
import kotlin.jvm.internal.k;

/* compiled from: BaseQuickAdapter.kt */
public final class BaseQuickAdapter$onAttachedToRecyclerView$1 extends GridLayoutManager.SpanSizeLookup {
    final /* synthetic */ BaseQuickAdapter a;
    final /* synthetic */ RecyclerView.LayoutManager b;
    final /* synthetic */ GridLayoutManager.SpanSizeLookup c;

    BaseQuickAdapter$onAttachedToRecyclerView$1(BaseQuickAdapter this$0, RecyclerView.LayoutManager $captured_local_variable$1, GridLayoutManager.SpanSizeLookup $captured_local_variable$2) {
        this.a = this$0;
        this.b = $captured_local_variable$1;
        this.c = $captured_local_variable$2;
    }

    public int getSpanSize(int position) {
        int type = this.a.getItemViewType(position);
        if (type == 268435729 && this.a.getHeaderViewAsFlow()) {
            return 1;
        }
        if (type == 268436275 && this.a.getFooterViewAsFlow()) {
            return 1;
        }
        if (this.a.A4 == null) {
            return this.a.isFixedViewType(type) ? ((GridLayoutManager) this.b).getSpanCount() : this.c.getSpanSize(position);
        }
        if (this.a.isFixedViewType(type)) {
            return ((GridLayoutManager) this.b).getSpanCount();
        }
        a a2 = this.a.A4;
        k.c(a2);
        return a2.getSpanSize((GridLayoutManager) this.b, type, position - this.a.getHeaderLayoutCount());
    }
}
