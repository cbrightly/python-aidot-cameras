package com.leedarson.module_base.andserver.processor.generator;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.framework.b;
import com.yanzhenjie.andserver.register.a;
import java.util.HashMap;
import java.util.Map;

public final class ResolverRegister implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Map<String, b> mMap;

    public ResolverRegister() {
        HashMap hashMap = new HashMap();
        this.mMap = hashMap;
        hashMap.put("default", new com.leedarson.base.webservice.server.b());
    }

    public void onRegister(Context context, String group, com.yanzhenjie.andserver.register.b register) {
        Class[] clsArr = {Context.class, String.class, com.yanzhenjie.andserver.register.b.class};
        if (!PatchProxy.proxy(new Object[]{context, group, register}, this, changeQuickRedirect, false, 1434, clsArr, Void.TYPE).isSupported) {
            b resolver = this.mMap.get(group);
            if (resolver == null) {
                resolver = this.mMap.get("default");
            }
            if (resolver != null) {
                register.e(resolver);
            }
        }
    }
}
