package com.didichuxing.doraemonkit.widget.bravh.module;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kotlin.l;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/x;", "run", "()V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: LoadMoreModule.kt */
public final class BaseLoadMoreModule$checkDisableLoadMoreIfNotFullPage$2 implements Runnable {
    final /* synthetic */ RecyclerView.LayoutManager $manager;
    final /* synthetic */ BaseLoadMoreModule this$0;

    BaseLoadMoreModule$checkDisableLoadMoreIfNotFullPage$2(BaseLoadMoreModule baseLoadMoreModule, RecyclerView.LayoutManager layoutManager) {
        this.this$0 = baseLoadMoreModule;
        this.$manager = layoutManager;
    }

    public final void run() {
        int[] positions = new int[((StaggeredGridLayoutManager) this.$manager).getSpanCount()];
        ((StaggeredGridLayoutManager) this.$manager).findLastCompletelyVisibleItemPositions(positions);
        if (this.this$0.getTheBiggestNumber(positions) + 1 != this.this$0.baseQuickAdapter.getItemCount()) {
            this.this$0.mNextLoadEnable = true;
        }
    }
}
