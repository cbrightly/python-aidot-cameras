package com.leedarson.base.views.photoview;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: Compat */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(View view, Runnable runnable) {
        Class[] clsArr = {View.class, Runnable.class};
        if (!PatchProxy.proxy(new Object[]{view, runnable}, (Object) null, changeQuickRedirect, true, 941, clsArr, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 16) {
                b(view, runnable);
            } else {
                view.postDelayed(runnable, 16);
            }
        }
    }

    @TargetApi(16)
    private static void b(View view, Runnable runnable) {
        Class[] clsArr = {View.class, Runnable.class};
        if (!PatchProxy.proxy(new Object[]{view, runnable}, (Object) null, changeQuickRedirect, true, 942, clsArr, Void.TYPE).isSupported) {
            view.postOnAnimation(runnable);
        }
    }
}
