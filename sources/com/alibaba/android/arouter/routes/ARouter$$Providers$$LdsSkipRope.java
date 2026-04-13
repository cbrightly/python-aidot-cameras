package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.SkipRopeServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsSkipRope implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.SkipRopeService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, SkipRopeServiceImpl.class, "/skipropeservice/skiprope", "skipropeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
