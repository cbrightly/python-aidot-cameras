package com.leedarson.base.http.observer;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.r;

/* compiled from: HttpRxObservable */
public class h<T> {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* compiled from: HttpRxObservable */
    public static class b {
        /* access modifiers changed from: private */
        public static h a = new h();
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    private h() {
    }

    public static h a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, Opcodes.RETURN, new Class[0], h.class);
        return proxy.isSupported ? (h) proxy.result : b.a;
    }

    public l<String> b(l<String> apiObservable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiObservable}, this, changeQuickRedirect, false, Opcodes.GETSTATIC, new Class[]{l.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        return apiObservable.R(new k(2, 1500)).G(new com.leedarson.base.http.function.b()).M(new com.leedarson.base.http.function.a()).b0(l.f).J(io.reactivex.android.schedulers.a.a());
    }

    public l<String> f(l<String> apiObservable, r scheduler) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiObservable, scheduler}, this, changeQuickRedirect2, false, 179, new Class[]{l.class, r.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        return apiObservable.R(new k(2, 1500)).G(new com.leedarson.base.http.function.b()).M(new com.leedarson.base.http.function.a()).b0(scheduler).J(io.reactivex.android.schedulers.a.a());
    }

    public l<String> d(l<String> apiObservable, com.trello.rxlifecycle3.b lifecycle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiObservable, lifecycle}, this, changeQuickRedirect2, false, 180, new Class[]{l.class, com.trello.rxlifecycle3.b.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (lifecycle == null) {
            return apiObservable.G(new com.leedarson.base.http.function.b()).M(new com.leedarson.base.http.function.a()).b0(l.f).J(io.reactivex.android.schedulers.a.a());
        }
        return apiObservable.G(new com.leedarson.base.http.function.b()).h(lifecycle.h0()).M(new com.leedarson.base.http.function.a()).b0(l.f).J(io.reactivex.android.schedulers.a.a());
    }

    public l<String> c(l<String> apiObservable, com.trello.rxlifecycle3.b lifecycle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiObservable, lifecycle}, this, changeQuickRedirect2, false, Opcodes.PUTFIELD, new Class[]{l.class, com.trello.rxlifecycle3.b.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (lifecycle != null) {
            return apiObservable.R(new k(2, 1500)).G(new com.leedarson.base.http.function.b()).h(lifecycle.h0()).M(new com.leedarson.base.http.function.a()).b0(l.f).J(io.reactivex.android.schedulers.a.a());
        }
        return b(apiObservable);
    }

    public l<String> e(l<String> apiObservable, com.trello.rxlifecycle3.b lifecycle, r scheduler) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiObservable, lifecycle, scheduler}, this, changeQuickRedirect2, false, Opcodes.INVOKEVIRTUAL, new Class[]{l.class, com.trello.rxlifecycle3.b.class, r.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (lifecycle != null) {
            return apiObservable.R(new k(2, 1500)).G(new com.leedarson.base.http.function.b()).h(lifecycle.h0()).M(new com.leedarson.base.http.function.a()).b0(scheduler).J(io.reactivex.android.schedulers.a.a());
        }
        return f(apiObservable, scheduler);
    }
}
