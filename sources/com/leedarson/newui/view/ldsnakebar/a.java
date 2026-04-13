package com.leedarson.newui.view.ldsnakebar;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: AnimationUtils */
public class a {
    static final Interpolator a = new LinearInterpolator();
    static final Interpolator b = new FastOutSlowInInterpolator();
    static final Interpolator c = new FastOutLinearInInterpolator();
    public static ChangeQuickRedirect changeQuickRedirect;
    static final Interpolator d = new LinearOutSlowInInterpolator();
    static final Interpolator e = new DecelerateInterpolator();
}
