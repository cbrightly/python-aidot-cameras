package com.scwang.smart.refresh.layout.api;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.listener.i;

/* compiled from: RefreshContent */
public interface b {
    void a(i iVar);

    void b(MotionEvent motionEvent);

    void c(e eVar, View view, View view2);

    void d(boolean z);

    ValueAnimator.AnimatorUpdateListener e(int i);

    @NonNull
    View f();

    boolean g();

    @NonNull
    View getView();

    void h(int i, int i2, int i3);

    boolean i();
}
