package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.shake.ShakeServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsDebug implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.ShakeService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, ShakeServiceImpl.class, "/shakeservice/shake", "shakeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
