package com.leedarson.serviceimpl.system.notch;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

/* compiled from: NotchHelper */
public class b {
    private static final String a = b.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8933, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (!(context instanceof Activity) || !c((Activity) context)) {
            if (resourceId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        } else if (a.b(context)) {
            statusBarHeight = a.a(context)[1];
        } else if (e.b(context)) {
            statusBarHeight = e.a(context);
        } else if (d((Activity) context)) {
            statusBarHeight = a((Activity) context);
        } else {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight < 48) {
            return 48;
        }
        if (statusBarHeight > 200) {
            return 200;
        }
        a.b g = a.g("CZB");
        g.a("getStatusBarHeight:" + statusBarHeight, new Object[0]);
        return statusBarHeight;
    }

    public static boolean c(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 8934, new Class[]{Activity.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return d(activity) | a.b(activity) | e.b(activity) | d.b(activity) | c.a(activity);
    }

    public static int a(Activity activity) {
        View decorView;
        WindowInsets windowInsets;
        DisplayCutout displayCutout;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 8935, new Class[]{Activity.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (Build.VERSION.SDK_INT < 28 || (decorView = activity.getWindow().getDecorView()) == null || (windowInsets = decorView.getRootWindowInsets()) == null || (displayCutout = windowInsets.getDisplayCutout()) == null) {
            return 0;
        }
        return displayCutout.getSafeInsetTop();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0035, code lost:
        r2 = (r1 = r9.getWindow().getDecorView()).getRootWindowInsets();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean d(android.app.Activity r9) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.app.Activity> r2 = android.app.Activity.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r2 = 0
            r4 = 1
            r5 = 8936(0x22e8, float:1.2522E-41)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0025
            java.lang.Object r9 = r1.result
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            return r9
        L_0x0025:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 28
            if (r1 < r2) goto L_0x0042
            android.view.Window r1 = r9.getWindow()
            android.view.View r1 = r1.getDecorView()
            if (r1 == 0) goto L_0x0042
            android.view.WindowInsets r2 = r1.getRootWindowInsets()
            if (r2 == 0) goto L_0x0042
            android.view.DisplayCutout r3 = r2.getDisplayCutout()
            if (r3 == 0) goto L_0x0042
            return r0
        L_0x0042:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.system.notch.b.d(android.app.Activity):boolean");
    }
}
