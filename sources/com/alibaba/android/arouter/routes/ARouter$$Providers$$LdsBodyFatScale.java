package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.BodyFatScaleServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsBodyFatScale implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.BodyFatScaleService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BodyFatScaleServiceImpl.class, "/bodyfatscaleservice/bodyfatscale", "bodyfatscaleservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
