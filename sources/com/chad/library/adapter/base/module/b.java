package com.chad.library.adapter.base.module;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.h;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LoadMoreModule.kt */
public class b {
    /* access modifiers changed from: private */
    public h a;
    /* access modifiers changed from: private */
    public boolean b;
    @NotNull
    private com.chad.library.adapter.base.loadmore.b c;
    private boolean d;
    @NotNull
    private com.chad.library.adapter.base.loadmore.a e;
    private boolean f;
    private boolean g;
    private boolean h;
    private int i;
    private boolean j;
    /* access modifiers changed from: private */
    public final BaseQuickAdapter<?, ?> k;

    @NotNull
    public final com.chad.library.adapter.base.loadmore.b i() {
        return this.c;
    }

    @NotNull
    public final com.chad.library.adapter.base.loadmore.a j() {
        return this.e;
    }

    public final boolean h() {
        return this.f;
    }

    public final int k() {
        if (this.k.hasEmptyView()) {
            return -1;
        }
        BaseQuickAdapter it = this.k;
        return it.getHeaderLayoutCount() + it.getData().size() + it.getFooterLayoutCount();
    }

    public final void r(boolean value) {
        boolean oldHasLoadMore = m();
        this.j = value;
        boolean newHasLoadMore = m();
        if (oldHasLoadMore) {
            if (!newHasLoadMore) {
                this.k.notifyItemRemoved(k());
            }
        } else if (newHasLoadMore) {
            this.c = com.chad.library.adapter.base.loadmore.b.Complete;
            this.k.notifyItemInserted(k());
        }
    }

    /* compiled from: LoadMoreModule.kt */
    public static final class d implements View.OnClickListener {
        final /* synthetic */ b c;

        d(b bVar) {
            this.c = bVar;
        }

        @SensorsDataInstrumented
        public final void onClick(View view) {
            View view2 = view;
            if (this.c.i() == com.chad.library.adapter.base.loadmore.b.Fail) {
                this.c.p();
            } else if (this.c.i() == com.chad.library.adapter.base.loadmore.b.Complete) {
                this.c.p();
            } else if (this.c.h() && this.c.i() == com.chad.library.adapter.base.loadmore.b.End) {
                this.c.p();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public final void s(@NotNull BaseViewHolder viewHolder) {
        k.e(viewHolder, "viewHolder");
        viewHolder.itemView.setOnClickListener(new d(this));
    }

    public final void p() {
        com.chad.library.adapter.base.loadmore.b bVar = this.c;
        com.chad.library.adapter.base.loadmore.b bVar2 = com.chad.library.adapter.base.loadmore.b.Loading;
        if (bVar != bVar2) {
            this.c = bVar2;
            this.k.notifyItemChanged(k());
            n();
        }
    }

    public final boolean m() {
        if (this.a == null || !this.j) {
            return false;
        }
        if (this.c != com.chad.library.adapter.base.loadmore.b.End || !this.d) {
            return !this.k.getData().isEmpty();
        }
        return false;
    }

    public final void f(int position) {
        com.chad.library.adapter.base.loadmore.b bVar;
        if (this.g && m() && position >= this.k.getItemCount() - this.i && (bVar = this.c) == com.chad.library.adapter.base.loadmore.b.Complete && bVar != com.chad.library.adapter.base.loadmore.b.Loading && this.b) {
            n();
        }
    }

    private final void n() {
        this.c = com.chad.library.adapter.base.loadmore.b.Loading;
        RecyclerView it = this.k.k();
        if (it != null) {
            it.post(new c(this));
            return;
        }
        h hVar = this.a;
        if (hVar != null) {
            hVar.onLoadMore();
        }
    }

    /* compiled from: LoadMoreModule.kt */
    public static final class c implements Runnable {
        final /* synthetic */ b c;

        c(b bVar) {
            this.c = bVar;
        }

        public final void run() {
            h b = this.c.a;
            if (b != null) {
                b.onLoadMore();
            }
        }
    }

    public final void g() {
        RecyclerView.LayoutManager layoutManager;
        if (!this.h) {
            this.b = false;
            RecyclerView k2 = this.k.k();
            if (k2 != null && (layoutManager = k2.getLayoutManager()) != null) {
                k.d(layoutManager, "recyclerView.layoutManager ?: return");
                if (layoutManager instanceof LinearLayoutManager) {
                    k2.postDelayed(new a(this, layoutManager), 50);
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    k2.postDelayed(new C0061b(this, layoutManager), 50);
                }
            }
        }
    }

    /* compiled from: LoadMoreModule.kt */
    public static final class a implements Runnable {
        final /* synthetic */ b c;
        final /* synthetic */ RecyclerView.LayoutManager d;

        a(b bVar, RecyclerView.LayoutManager layoutManager) {
            this.c = bVar;
            this.d = layoutManager;
        }

        public final void run() {
            if (this.c.o((LinearLayoutManager) this.d)) {
                this.c.b = true;
            }
        }
    }

    /* renamed from: com.chad.library.adapter.base.module.b$b  reason: collision with other inner class name */
    /* compiled from: LoadMoreModule.kt */
    public static final class C0061b implements Runnable {
        final /* synthetic */ b c;
        final /* synthetic */ RecyclerView.LayoutManager d;

        C0061b(b bVar, RecyclerView.LayoutManager layoutManager) {
            this.c = bVar;
            this.d = layoutManager;
        }

        public final void run() {
            int[] positions = new int[((StaggeredGridLayoutManager) this.d).getSpanCount()];
            ((StaggeredGridLayoutManager) this.d).findLastCompletelyVisibleItemPositions(positions);
            if (this.c.l(positions) + 1 != this.c.k.getItemCount()) {
                this.c.b = true;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean o(LinearLayoutManager llm) {
        if (llm.findLastCompletelyVisibleItemPosition() + 1 == this.k.getItemCount() && llm.findFirstCompletelyVisibleItemPosition() == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final int l(int[] numbers) {
        int tmp = -1;
        if (numbers != null) {
            if (!(numbers.length == 0)) {
                for (int num : numbers) {
                    if (num > tmp) {
                        tmp = num;
                    }
                }
                return tmp;
            }
        }
        return -1;
    }

    public void setOnLoadMoreListener(@Nullable h listener) {
        this.a = listener;
        r(true);
    }

    public final void q() {
        if (this.a != null) {
            r(true);
            this.c = com.chad.library.adapter.base.loadmore.b.Complete;
        }
    }
}
