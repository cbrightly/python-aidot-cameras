package com.didichuxing.doraemonkit.widget.bravh.module;

import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnUpFetchListener;
import com.didichuxing.doraemonkit.widget.bravh.listener.UpFetchListenerImp;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UpFetchModule.kt */
public class BaseUpFetchModule implements UpFetchListenerImp {
    private final BaseQuickAdapter<?, ?> baseQuickAdapter;
    private boolean isUpFetchEnable;
    private boolean isUpFetching;
    private OnUpFetchListener mUpFetchListener;
    private int startUpFetchPosition = 1;

    public BaseUpFetchModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter2) {
        k.f(baseQuickAdapter2, "baseQuickAdapter");
        this.baseQuickAdapter = baseQuickAdapter2;
    }

    public final boolean isUpFetchEnable() {
        return this.isUpFetchEnable;
    }

    public final void setUpFetchEnable(boolean z) {
        this.isUpFetchEnable = z;
    }

    public final boolean isUpFetching() {
        return this.isUpFetching;
    }

    public final void setUpFetching(boolean z) {
        this.isUpFetching = z;
    }

    public final int getStartUpFetchPosition() {
        return this.startUpFetchPosition;
    }

    public final void setStartUpFetchPosition(int i) {
        this.startUpFetchPosition = i;
    }

    public final void autoUpFetch$doraemonkit_release(int position) {
        OnUpFetchListener onUpFetchListener;
        if (this.isUpFetchEnable && !this.isUpFetching && position <= this.startUpFetchPosition && (onUpFetchListener = this.mUpFetchListener) != null) {
            onUpFetchListener.onUpFetch();
        }
    }

    public void setOnUpFetchListener(@Nullable OnUpFetchListener listener) {
        this.mUpFetchListener = listener;
    }
}
