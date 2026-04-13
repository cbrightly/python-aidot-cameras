package com.leedarson.scheme;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: PopScheme */
public class d extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    int q;
    int r;
    int s = 0;

    public d() {
        this.b = 150;
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5667, new Class[0], Void.TYPE).isSupported) {
            super.c();
            this.r = -1;
            this.q = 0;
            this.s = 0;
        }
    }
}
