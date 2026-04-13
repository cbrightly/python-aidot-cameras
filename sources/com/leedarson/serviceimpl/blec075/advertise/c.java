package com.leedarson.serviceimpl.blec075.advertise;

import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.blec075.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: ServiceDataParser */
public class c extends a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean a(h.a serviceDataUnit) {
        byte[] bArr;
        boolean b = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceDataUnit}, (Object) null, changeQuickRedirect, true, 6467, new Class[]{h.a.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (serviceDataUnit == null || (bArr = serviceDataUnit.c) == null) {
            return false;
        }
        boolean a = w.a(bArr).startsWith("1115");
        byte[] bArr2 = serviceDataUnit.c;
        if (!(bArr2[0] == -10 && bArr2[1] == -1)) {
            b = false;
        }
        return a | b;
    }
}
