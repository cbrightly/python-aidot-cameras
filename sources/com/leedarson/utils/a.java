package com.leedarson.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: AnimationUtil */
public class a {
    private static a a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static a b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11450, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public void c(View v, long Duration) {
        if (!PatchProxy.proxy(new Object[]{v, new Long(Duration)}, this, changeQuickRedirect, false, 11451, new Class[]{View.class, Long.TYPE}, Void.TYPE).isSupported) {
            if (v.getVisibility() == 0) {
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
                translateAnimation.setDuration(Duration);
                v.clearAnimation();
                v.setAnimation(translateAnimation);
                v.setVisibility(8);
            }
        }
    }

    public void a(View view, long j) {
        Object[] objArr = {view, new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11452, new Class[]{View.class, Long.TYPE}, Void.TYPE).isSupported) {
            long Duration = j;
            View v = view;
            if (v.getVisibility() != 0) {
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
                translateAnimation.setDuration(Duration);
                v.clearAnimation();
                v.setAnimation(translateAnimation);
                v.setVisibility(0);
            }
        }
    }

    public void d(View v, long Duration) {
        if (!PatchProxy.proxy(new Object[]{v, new Long(Duration)}, this, changeQuickRedirect, false, 11453, new Class[]{View.class, Long.TYPE}, Void.TYPE).isSupported) {
            if (v.getVisibility() == 0) {
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
                translateAnimation.setDuration(Duration);
                v.clearAnimation();
                v.setAnimation(translateAnimation);
                v.setVisibility(8);
            }
        }
    }

    public void e(View view, long j) {
        Object[] objArr = {view, new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11454, new Class[]{View.class, Long.TYPE}, Void.TYPE).isSupported) {
            long Duration = j;
            View v = view;
            if (v.getVisibility() != 0) {
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
                translateAnimation.setDuration(Duration);
                v.clearAnimation();
                v.setAnimation(translateAnimation);
                v.setVisibility(0);
            }
        }
    }
}
