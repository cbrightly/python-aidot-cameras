package com.didichuxing.doraemonkit.widget.bottomview;

import android.view.View;

public abstract class AssociationView {
    private OnStateChangeListener onStateChangeListener;

    public interface OnStateChangeListener {
        void onStateChanged();
    }

    public abstract void cancel();

    public abstract View getView();

    public abstract boolean isCanSubmit();

    public abstract void onHide();

    public abstract void onShow();

    public abstract Object submit();

    /* access modifiers changed from: package-private */
    public final void setOnStateChangeListener(OnStateChangeListener listener) {
        this.onStateChangeListener = listener;
    }

    /* access modifiers changed from: package-private */
    public final OnStateChangeListener getOnStateChangeListener() {
        return this.onStateChangeListener;
    }
}
