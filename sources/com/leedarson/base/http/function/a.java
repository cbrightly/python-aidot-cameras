package com.leedarson.base.http.function;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.f;
import io.reactivex.l;

/* compiled from: HttpResultFunction */
public class a<String> implements f<Throwable, l<String>> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ Object apply(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 170, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : a((Throwable) obj);
    }

    public l<String> a(Throwable throwable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, Opcodes.RET, new Class[]{Throwable.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        return l.r(com.leedarson.base.http.exception.a.a(throwable));
    }
}
