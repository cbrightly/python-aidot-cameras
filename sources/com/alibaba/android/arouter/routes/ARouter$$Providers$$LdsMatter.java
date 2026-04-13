package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.MatterServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsMatter implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.MatterService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, MatterServiceImpl.class, "/matterservice/matter", "matterservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
