package com.leedarson.newui.door_phone.repos;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.disposables.a;
import io.reactivex.disposables.b;

/* compiled from: LDSAbRepos */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;
    public a a = new a();

    public void a(b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 4159, new Class[]{b.class}, Void.TYPE).isSupported) {
            this.a.b(disposable);
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4160, new Class[0], Void.TYPE).isSupported) {
            this.a.dispose();
        }
    }
}
