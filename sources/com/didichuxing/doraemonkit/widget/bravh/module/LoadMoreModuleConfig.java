package com.didichuxing.doraemonkit.widget.bravh.module;

import com.didichuxing.doraemonkit.widget.bravh.loadmore.BaseLoadMoreView;
import com.didichuxing.doraemonkit.widget.bravh.loadmore.SimpleLoadMoreView;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LoadMoreModule.kt */
public final class LoadMoreModuleConfig {
    public static final LoadMoreModuleConfig INSTANCE = new LoadMoreModuleConfig();
    @NotNull
    private static BaseLoadMoreView defLoadMoreView = new SimpleLoadMoreView();

    public static /* synthetic */ void defLoadMoreView$annotations() {
    }

    private LoadMoreModuleConfig() {
    }

    @NotNull
    public static final BaseLoadMoreView getDefLoadMoreView() {
        return defLoadMoreView;
    }

    public static final void setDefLoadMoreView(@NotNull BaseLoadMoreView baseLoadMoreView) {
        k.f(baseLoadMoreView, "<set-?>");
        defLoadMoreView = baseLoadMoreView;
    }
}
