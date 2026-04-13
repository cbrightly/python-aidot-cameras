package com.google.android.material.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

public class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
    ViewGroupOverlayApi14(Context context, ViewGroup hostView, View requestingView) {
        super(context, hostView, requestingView);
    }

    static ViewGroupOverlayApi14 createFrom(ViewGroup viewGroup) {
        return (ViewGroupOverlayApi14) ViewOverlayApi14.createFrom(viewGroup);
    }

    public void add(@NonNull View view) {
        this.overlayViewGroup.add(view);
    }

    public void remove(@NonNull View view) {
        this.overlayViewGroup.remove(view);
    }
}
