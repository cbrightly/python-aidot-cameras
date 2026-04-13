package com.didichuxing.doraemonkit.widget.bravh.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.listener.LoadMoreListenerImp;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnLoadMoreListener;
import com.didichuxing.doraemonkit.widget.bravh.loadmore.BaseLoadMoreView;
import com.didichuxing.doraemonkit.widget.bravh.loadmore.LoadMoreStatus;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LoadMoreModule.kt */
public class BaseLoadMoreModule implements LoadMoreListenerImp {
    /* access modifiers changed from: private */
    public final BaseQuickAdapter<?, ?> baseQuickAdapter;
    private boolean enableLoadMoreEndClick;
    private boolean isAutoLoadMore = true;
    private boolean isEnableLoadMore;
    private boolean isEnableLoadMoreIfNotFullPage = true;
    private boolean isLoadEndMoreGone;
    /* access modifiers changed from: private */
    @NotNull
    public LoadMoreStatus loadMoreStatus = LoadMoreStatus.Complete;
    @NotNull
    private BaseLoadMoreView loadMoreView = LoadMoreModuleConfig.getDefLoadMoreView();
    /* access modifiers changed from: private */
    public OnLoadMoreListener mLoadMoreListener;
    /* access modifiers changed from: private */
    public boolean mNextLoadEnable = true;
    private int preLoadNumber = 1;

    public final void loadMoreEnd() {
        loadMoreEnd$default(this, false, 1, (Object) null);
    }

    public BaseLoadMoreModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter2) {
        k.f(baseQuickAdapter2, "baseQuickAdapter");
        this.baseQuickAdapter = baseQuickAdapter2;
    }

    @NotNull
    public final LoadMoreStatus getLoadMoreStatus() {
        return this.loadMoreStatus;
    }

    public final boolean isLoadEndMoreGone() {
        return this.isLoadEndMoreGone;
    }

    @NotNull
    public final BaseLoadMoreView getLoadMoreView() {
        return this.loadMoreView;
    }

    public final void setLoadMoreView(@NotNull BaseLoadMoreView baseLoadMoreView) {
        k.f(baseLoadMoreView, "<set-?>");
        this.loadMoreView = baseLoadMoreView;
    }

    public final boolean getEnableLoadMoreEndClick() {
        return this.enableLoadMoreEndClick;
    }

    public final void setEnableLoadMoreEndClick(boolean z) {
        this.enableLoadMoreEndClick = z;
    }

    public final boolean isAutoLoadMore() {
        return this.isAutoLoadMore;
    }

    public final void setAutoLoadMore(boolean z) {
        this.isAutoLoadMore = z;
    }

    public final boolean isEnableLoadMoreIfNotFullPage() {
        return this.isEnableLoadMoreIfNotFullPage;
    }

    public final void setEnableLoadMoreIfNotFullPage(boolean z) {
        this.isEnableLoadMoreIfNotFullPage = z;
    }

    public final int getPreLoadNumber() {
        return this.preLoadNumber;
    }

    public final void setPreLoadNumber(int value) {
        if (value > 1) {
            this.preLoadNumber = value;
        }
    }

    public final boolean isLoading() {
        return this.loadMoreStatus == LoadMoreStatus.Loading;
    }

    public final int getLoadMoreViewPosition() {
        if (this.baseQuickAdapter.hasEmptyView()) {
            return -1;
        }
        BaseQuickAdapter it = this.baseQuickAdapter;
        return it.getHeaderLayoutCount() + it.getData().size() + it.getFooterLayoutCount();
    }

    public final boolean isEnableLoadMore() {
        return this.isEnableLoadMore;
    }

    public final void setEnableLoadMore(boolean value) {
        boolean oldHasLoadMore = hasLoadMoreView();
        this.isEnableLoadMore = value;
        boolean newHasLoadMore = hasLoadMoreView();
        if (oldHasLoadMore) {
            if (!newHasLoadMore) {
                this.baseQuickAdapter.notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (newHasLoadMore) {
            this.loadMoreStatus = LoadMoreStatus.Complete;
            this.baseQuickAdapter.notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public final void setupViewHolder$doraemonkit_release(@NotNull BaseViewHolder viewHolder) {
        k.f(viewHolder, "viewHolder");
        viewHolder.itemView.setOnClickListener(new BaseLoadMoreModule$setupViewHolder$1(this));
    }

    public final void loadMoreToLoading() {
        LoadMoreStatus loadMoreStatus2 = this.loadMoreStatus;
        LoadMoreStatus loadMoreStatus3 = LoadMoreStatus.Loading;
        if (loadMoreStatus2 != loadMoreStatus3) {
            this.loadMoreStatus = loadMoreStatus3;
            this.baseQuickAdapter.notifyItemChanged(getLoadMoreViewPosition());
            invokeLoadMoreListener();
        }
    }

    public final boolean hasLoadMoreView() {
        if (this.mLoadMoreListener == null || !this.isEnableLoadMore) {
            return false;
        }
        if (this.loadMoreStatus != LoadMoreStatus.End || !this.isLoadEndMoreGone) {
            return !this.baseQuickAdapter.getData().isEmpty();
        }
        return false;
    }

    public final void autoLoadMore$doraemonkit_release(int position) {
        LoadMoreStatus loadMoreStatus2;
        if (this.isAutoLoadMore && hasLoadMoreView() && position >= this.baseQuickAdapter.getItemCount() - this.preLoadNumber && (loadMoreStatus2 = this.loadMoreStatus) == LoadMoreStatus.Complete && loadMoreStatus2 != LoadMoreStatus.Loading && this.mNextLoadEnable) {
            invokeLoadMoreListener();
        }
    }

    private final void invokeLoadMoreListener() {
        this.loadMoreStatus = LoadMoreStatus.Loading;
        RecyclerView it = (RecyclerView) this.baseQuickAdapter.getWeakRecyclerView().get();
        if (it != null) {
            it.post(new BaseLoadMoreModule$invokeLoadMoreListener$$inlined$let$lambda$1(this));
            return;
        }
        OnLoadMoreListener onLoadMoreListener = this.mLoadMoreListener;
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    public final void checkDisableLoadMoreIfNotFullPage() {
        if (!this.isEnableLoadMoreIfNotFullPage) {
            this.mNextLoadEnable = false;
            RecyclerView recyclerView = (RecyclerView) this.baseQuickAdapter.getWeakRecyclerView().get();
            if (recyclerView != null) {
                k.b(recyclerView, "baseQuickAdapter.weakRecyclerView.get() ?: return");
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager != null) {
                    k.b(manager, "recyclerView.layoutManager ?: return");
                    if (manager instanceof LinearLayoutManager) {
                        recyclerView.postDelayed(new BaseLoadMoreModule$checkDisableLoadMoreIfNotFullPage$1(this, manager), 50);
                    } else if (manager instanceof StaggeredGridLayoutManager) {
                        recyclerView.postDelayed(new BaseLoadMoreModule$checkDisableLoadMoreIfNotFullPage$2(this, manager), 50);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean isFullScreen(LinearLayoutManager llm) {
        if (llm.findLastCompletelyVisibleItemPosition() + 1 == this.baseQuickAdapter.getItemCount() && llm.findFirstCompletelyVisibleItemPosition() == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final int getTheBiggestNumber(int[] numbers) {
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

    public static /* synthetic */ void loadMoreEnd$default(BaseLoadMoreModule baseLoadMoreModule, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            baseLoadMoreModule.loadMoreEnd(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: loadMoreEnd");
    }

    public final void loadMoreEnd(boolean gone) {
        if (hasLoadMoreView()) {
            this.isLoadEndMoreGone = gone;
            this.loadMoreStatus = LoadMoreStatus.End;
            if (gone) {
                this.baseQuickAdapter.notifyItemRemoved(getLoadMoreViewPosition());
            } else {
                this.baseQuickAdapter.notifyItemChanged(getLoadMoreViewPosition());
            }
        }
    }

    public final void loadMoreComplete() {
        if (hasLoadMoreView()) {
            this.loadMoreStatus = LoadMoreStatus.Complete;
            this.baseQuickAdapter.notifyItemChanged(getLoadMoreViewPosition());
            checkDisableLoadMoreIfNotFullPage();
        }
    }

    public final void loadMoreFail() {
        if (hasLoadMoreView()) {
            this.loadMoreStatus = LoadMoreStatus.Fail;
            this.baseQuickAdapter.notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void setOnLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
        setEnableLoadMore(true);
    }

    public final void reset$doraemonkit_release() {
        if (this.mLoadMoreListener != null) {
            setEnableLoadMore(true);
            this.loadMoreStatus = LoadMoreStatus.Complete;
        }
    }
}
