package com.leedarson.base.webservice.server;

import com.meituan.robust.ChangeQuickRedirect;
import com.yanzhenjie.andserver.framework.handler.b;
import com.yanzhenjie.andserver.framework.mapping.a;
import com.yanzhenjie.andserver.framework.mapping.c;
import java.util.LinkedHashMap;
import java.util.Map;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: FileControllerAdapter */
public final class f extends b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private e h = new e();
    private Map<com.yanzhenjie.andserver.framework.mapping.b, com.yanzhenjie.andserver.framework.handler.f> i = new LinkedHashMap();

    public f() {
        com.yanzhenjie.andserver.framework.mapping.b mapping = new com.yanzhenjie.andserver.framework.mapping.b();
        com.yanzhenjie.andserver.framework.mapping.f path = new com.yanzhenjie.andserver.framework.mapping.f();
        path.a("/deviceLogUpLoad/545687/");
        path.a("/deviceLogUpLoad/545687");
        mapping.h(path);
        c method = new c();
        method.a(Constants.POST);
        mapping.g(method);
        this.i.put(mapping, new g(this.h, mapping, new a(), (com.yanzhenjie.andserver.framework.cross.a) null));
    }

    public Map<com.yanzhenjie.andserver.framework.mapping.b, com.yanzhenjie.andserver.framework.handler.f> g() {
        return this.i;
    }
}
