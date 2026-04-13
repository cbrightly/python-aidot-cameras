package com.leedarson.view.easypopup;

import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: EasyPopup */
public class b extends a<b> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a N4;

    /* compiled from: EasyPopup */
    public interface a {
        void a(View view, b bVar);
    }

    public /* bridge */ /* synthetic */ void G(View view, a aVar) {
        Class[] clsArr = {View.class, a.class};
        if (!PatchProxy.proxy(new Object[]{view, aVar}, this, changeQuickRedirect, false, 11906, clsArr, Void.TYPE).isSupported) {
            V(view, (b) aVar);
        }
    }

    public static b U() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11903, new Class[0], b.class);
        return proxy.isSupported ? (b) proxy.result : new b();
    }

    public void D() {
    }

    public void V(View view, b popup) {
        a aVar;
        Class[] clsArr = {View.class, b.class};
        if (!PatchProxy.proxy(new Object[]{view, popup}, this, changeQuickRedirect, false, 11905, clsArr, Void.TYPE).isSupported && (aVar = this.N4) != null) {
            aVar.a(view, popup);
        }
    }

    public b W(a listener) {
        this.N4 = listener;
        return this;
    }
}
