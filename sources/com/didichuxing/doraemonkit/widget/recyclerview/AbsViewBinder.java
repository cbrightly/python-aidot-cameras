package com.didichuxing.doraemonkit.widget.recyclerview;

import android.content.Context;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public abstract class AbsViewBinder<T> extends RecyclerView.ViewHolder {
    /* access modifiers changed from: private */
    public T data;
    private View mView;

    public abstract void bind(T t);

    /* access modifiers changed from: protected */
    public abstract void getViews();

    public AbsViewBinder(final View view) {
        super(view);
        this.mView = view;
        getViews();
        view.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                AbsViewBinder absViewBinder = AbsViewBinder.this;
                absViewBinder.onViewClick(view, absViewBinder.data);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    /* access modifiers changed from: protected */
    public final View getView() {
        return this.mView;
    }

    public final <V extends View> V getView(@IdRes int id) {
        return this.mView.findViewById(id);
    }

    public void bind(T t, int position) {
        bind(t);
    }

    /* access modifiers changed from: protected */
    public void onViewClick(View view, T t) {
    }

    /* access modifiers changed from: protected */
    public final void setData(T data2) {
        this.data = data2;
    }

    /* access modifiers changed from: protected */
    public final Context getContext() {
        return this.mView.getContext();
    }
}
