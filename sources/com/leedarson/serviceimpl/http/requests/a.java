package com.leedarson.serviceimpl.http.requests;

import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.e;
import okhttp3.e0;

/* compiled from: DownloadRequest */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    b<com.trello.rxlifecycle3.android.a> a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;

    public a(b<com.trello.rxlifecycle3.android.a> lifecycle) {
        this.a = lifecycle;
    }

    public io.reactivex.disposables.b a(e<e0> next, e<Throwable> error) {
        Class<e> cls = e.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect2, false, 8009, new Class[]{cls, cls}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        return ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(this.b).h(this.a.h0()).b0(l.a).J(l.a).Y(next, error);
    }
}
