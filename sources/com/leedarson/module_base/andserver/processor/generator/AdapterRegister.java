package com.leedarson.module_base.andserver.processor.generator;

import android.content.Context;
import com.leedarson.base.webservice.server.f;
import com.leedarson.base.webservice.server.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.framework.handler.HandlerAdapter;
import com.yanzhenjie.andserver.register.a;
import com.yanzhenjie.andserver.register.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AdapterRegister implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Map<String, List<com.yanzhenjie.andserver.framework.handler.a>> mMap = new HashMap();

    public AdapterRegister() {
        List<HandlerAdapter> defaultList = new ArrayList<>();
        defaultList.add(new f());
        defaultList.add(new i());
        this.mMap.put("default", defaultList);
    }

    public void onRegister(Context context, String group, b register) {
        Class[] clsArr = {Context.class, String.class, b.class};
        if (!PatchProxy.proxy(new Object[]{context, group, register}, this, changeQuickRedirect, false, 1431, clsArr, Void.TYPE).isSupported) {
            List<HandlerAdapter> list = this.mMap.get(group);
            if (list == null) {
                list = new ArrayList<>();
            }
            List<HandlerAdapter> defaultList = this.mMap.get("default");
            if (defaultList != null && !defaultList.isEmpty()) {
                list.addAll(defaultList);
            }
            if (!list.isEmpty()) {
                Iterator<HandlerAdapter> it = list.iterator();
                while (it.hasNext()) {
                    register.d((com.yanzhenjie.andserver.framework.handler.a) it.next());
                }
            }
        }
    }
}
