package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.JsbridgeServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsJsBridge implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.JsbridgeService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, JsbridgeServiceImpl.class, "/jsbridgeservice/jsbridge", "jsbridgeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
