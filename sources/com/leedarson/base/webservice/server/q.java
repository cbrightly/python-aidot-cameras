package com.leedarson.base.webservice.server;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.yanzhenjie.andserver.framework.e;
import com.yanzhenjie.andserver.framework.handler.c;
import com.yanzhenjie.andserver.framework.mapping.a;
import com.yanzhenjie.andserver.framework.mapping.b;
import com.yanzhenjie.andserver.http.d;
import com.yanzhenjie.andserver.http.f;
import java.util.Map;

/* compiled from: PageControllerSceneHandler */
public final class q extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Object l;

    public q(Object host, b mapping, a addition, com.yanzhenjie.andserver.framework.cross.a crossOrigin) {
        super(host, mapping, addition, crossOrigin);
        this.l = host;
    }

    public com.yanzhenjie.andserver.framework.view.c g(com.yanzhenjie.andserver.http.c request, d dVar) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{request, dVar}, this, changeQuickRedirect, false, 981, new Class[]{com.yanzhenjie.andserver.http.c.class, d.class}, com.yanzhenjie.andserver.framework.view.c.class);
        if (proxy.isSupported) {
            return (com.yanzhenjie.andserver.framework.view.c) proxy.result;
        }
        Context context = (Context) request.getAttribute("android.context");
        String httpPath = request.getPath();
        com.yanzhenjie.andserver.http.b httpMethod = request.getMethod();
        Object converterObj = request.getAttribute("http.message.converter");
        if (converterObj != null && (converterObj instanceof e)) {
            e converter = (e) converterObj;
        }
        if (request instanceof com.yanzhenjie.andserver.http.multipart.c) {
            com.yanzhenjie.andserver.http.multipart.c multiRequest = (com.yanzhenjie.andserver.http.multipart.c) request;
        }
        if (httpMethod.allowBody()) {
            f requestBody = request.z();
        }
        Map<String, String> d = d(httpPath);
        return new com.yanzhenjie.andserver.framework.view.b(false, ((h) this.l).h());
    }
}
