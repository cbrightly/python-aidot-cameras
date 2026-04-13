package com.leedarson.module_base.andserver.processor.generator;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.framework.config.c;
import com.yanzhenjie.andserver.register.a;
import com.yanzhenjie.andserver.register.b;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigRegister implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Map<String, c> mMap;

    public ConfigRegister() {
        HashMap hashMap = new HashMap();
        this.mMap = hashMap;
        hashMap.put("default", new com.leedarson.base.webservice.server.a());
    }

    public void onRegister(Context context, String group, b register) {
        Class[] clsArr = {Context.class, String.class, b.class};
        if (!PatchProxy.proxy(new Object[]{context, group, register}, this, changeQuickRedirect, false, 1432, clsArr, Void.TYPE).isSupported) {
            c config = this.mMap.get(group);
            if (config == null) {
                config = this.mMap.get("default");
            }
            if (config != null) {
                com.yanzhenjie.andserver.framework.config.a delegate = com.yanzhenjie.andserver.framework.config.a.e();
                config.a(context, delegate);
                List<com.yanzhenjie.andserver.framework.website.c> d = delegate.d();
                if (d != null && !d.isEmpty()) {
                    for (com.yanzhenjie.andserver.framework.website.c website : d) {
                        register.d(website);
                    }
                }
                register.a(delegate.c());
            }
        }
    }
}
