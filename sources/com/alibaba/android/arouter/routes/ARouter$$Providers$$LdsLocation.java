package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.map.MapServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsLocation implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.MapService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, MapServiceImpl.class, "/mapservice/map", "mapservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
