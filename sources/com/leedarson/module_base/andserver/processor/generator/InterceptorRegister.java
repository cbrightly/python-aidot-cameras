package com.leedarson.module_base.andserver.processor.generator;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.c;
import com.yanzhenjie.andserver.register.a;
import com.yanzhenjie.andserver.register.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class InterceptorRegister implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Map<String, List<c>> mMap = new HashMap();

    public InterceptorRegister() {
        List<HandlerInterceptor> defaultList = new ArrayList<>();
        defaultList.add(new com.leedarson.base.webservice.server.c());
        this.mMap.put("default", defaultList);
    }

    public void onRegister(Context context, String group, b register) {
        Class[] clsArr = {Context.class, String.class, b.class};
        if (!PatchProxy.proxy(new Object[]{context, group, register}, this, changeQuickRedirect, false, 1433, clsArr, Void.TYPE).isSupported) {
            List<HandlerInterceptor> list = this.mMap.get(group);
            if (list == null) {
                list = new ArrayList<>();
            }
            List<HandlerInterceptor> defaultList = this.mMap.get("default");
            if (defaultList != null && !defaultList.isEmpty()) {
                list.addAll(defaultList);
            }
            if (!list.isEmpty()) {
                Iterator<HandlerInterceptor> it = list.iterator();
                while (it.hasNext()) {
                    register.c((c) it.next());
                }
            }
        }
    }
}
