package com.leedarson.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RotateCircleView extends ImageView {
    public static ChangeQuickRedirect changeQuickRedirect;

    public RotateCircleView(Context context) {
        super(context);
    }

    public RotateCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void onFinishInflate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 798, new Class[0], Void.TYPE).isSupported) {
            super.onFinishInflate();
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(-1);
            startAnimation(rotateAnimation);
        }
    }
}
