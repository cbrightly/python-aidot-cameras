package com.didichuxing.doraemonkit.widget.bravh.loadmore;

import android.view.View;
import android.view.ViewGroup;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseLoadMoreView.kt */
public abstract class BaseLoadMoreView {

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 4, 0})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoadMoreStatus.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[LoadMoreStatus.Complete.ordinal()] = 1;
            iArr[LoadMoreStatus.Loading.ordinal()] = 2;
            iArr[LoadMoreStatus.Fail.ordinal()] = 3;
            iArr[LoadMoreStatus.End.ordinal()] = 4;
        }
    }

    @NotNull
    public abstract View getLoadComplete(@NotNull BaseViewHolder baseViewHolder);

    @NotNull
    public abstract View getLoadEndView(@NotNull BaseViewHolder baseViewHolder);

    @NotNull
    public abstract View getLoadFailView(@NotNull BaseViewHolder baseViewHolder);

    @NotNull
    public abstract View getLoadingView(@NotNull BaseViewHolder baseViewHolder);

    @NotNull
    public abstract View getRootView(@NotNull ViewGroup viewGroup);

    public void convert(@NotNull BaseViewHolder holder, int position, @NotNull LoadMoreStatus loadMoreStatus) {
        k.f(holder, "holder");
        k.f(loadMoreStatus, "loadMoreStatus");
        switch (WhenMappings.$EnumSwitchMapping$0[loadMoreStatus.ordinal()]) {
            case 1:
                isVisible(getLoadingView(holder), false);
                isVisible(getLoadComplete(holder), true);
                isVisible(getLoadFailView(holder), false);
                isVisible(getLoadEndView(holder), false);
                return;
            case 2:
                isVisible(getLoadingView(holder), true);
                isVisible(getLoadComplete(holder), false);
                isVisible(getLoadFailView(holder), false);
                isVisible(getLoadEndView(holder), false);
                return;
            case 3:
                isVisible(getLoadingView(holder), false);
                isVisible(getLoadComplete(holder), false);
                isVisible(getLoadFailView(holder), true);
                isVisible(getLoadEndView(holder), false);
                return;
            case 4:
                isVisible(getLoadingView(holder), false);
                isVisible(getLoadComplete(holder), false);
                isVisible(getLoadFailView(holder), false);
                isVisible(getLoadEndView(holder), true);
                return;
            default:
                return;
        }
    }

    private final void isVisible(@NotNull View $this$isVisible, boolean visible) {
        int i;
        if (visible) {
            i = 0;
        } else {
            i = 8;
        }
        $this$isVisible.setVisibility(i);
    }
}
