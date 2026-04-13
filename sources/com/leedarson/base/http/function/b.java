package com.leedarson.base.http.function;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.f;

/* compiled from: ServerResultFunction */
public class b implements f<String, String> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ Object apply(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 171, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : a((String) obj);
    }

    public String a(String response) {
        return response;
    }
}
