package com.leedarson.newui.view.ldsnakebar;

import android.content.Context;
import android.content.res.TypedArray;
import com.leedarson.R$attr;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: ThemeUtils */
public class d {
    private static final int[] a = {R$attr.colorPrimary};
    public static ChangeQuickRedirect changeQuickRedirect;

    static void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 5393, new Class[]{Context.class}, Void.TYPE).isSupported) {
            TypedArray a2 = context.obtainStyledAttributes(a);
            boolean failed = true ^ a2.hasValue(0);
            a2.recycle();
            if (failed) {
                throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
            }
        }
    }
}
