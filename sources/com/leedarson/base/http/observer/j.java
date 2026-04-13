package com.leedarson.base.http.observer;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.functions.f;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.a;

/* compiled from: LDSAutoRetryFlowable */
public class j implements f<e<Throwable>, a<?>> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = 2;
    private int d = 2;
    private int f = 0;

    public /* bridge */ /* synthetic */ Object apply(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 191, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : a((e) obj);
    }

    public j(int retryMaxTimes, int retryAfterDelayMillis) {
        this.c = retryMaxTimes;
        this.d = retryAfterDelayMillis;
    }

    public a<?> a(e<Throwable> throwableFlowable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{throwableFlowable}, this, changeQuickRedirect, false, 190, new Class[]{e.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        return throwableFlowable.o(new a(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ a c(Throwable throwable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, Opcodes.CHECKCAST, new Class[]{Throwable.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        int i = this.f + 1;
        this.f = i;
        if (i > this.c) {
            return e.m(throwable);
        }
        timber.log.a.i("flowable-->retry    retryCount=" + this.f + "    retryMaxTimes=" + this.c, new Object[0]);
        return e.R((long) this.d, TimeUnit.MILLISECONDS);
    }
}
