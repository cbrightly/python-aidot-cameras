package com.leedarson.view.rangeseekbar;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: SeekBarState */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public float b;
    public float c;
    public boolean d;
    public boolean e;

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11978, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "indicatorText: " + this.a + " ,isMin: " + this.d + " ,isMax: " + this.e;
    }
}
