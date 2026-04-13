package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.BleMeshServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsBleMesh implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.BleMeshService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BleMeshServiceImpl.class, "/blemeshservice/blemesh", "blemeshservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
