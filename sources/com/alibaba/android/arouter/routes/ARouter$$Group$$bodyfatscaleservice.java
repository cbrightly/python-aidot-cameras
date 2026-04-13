package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.BodyFatScaleServiceImpl;
import java.util.Map;

public class ARouter$$Group$$bodyfatscaleservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/bodyfatscaleservice/bodyfatscale", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BodyFatScaleServiceImpl.class, "/bodyfatscaleservice/bodyfatscale", "bodyfatscaleservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
