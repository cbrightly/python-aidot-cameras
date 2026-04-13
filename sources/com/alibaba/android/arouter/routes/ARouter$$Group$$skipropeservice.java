package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.SkipRopeServiceImpl;
import java.util.Map;

public class ARouter$$Group$$skipropeservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/skipropeservice/skiprope", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, SkipRopeServiceImpl.class, "/skipropeservice/skiprope", "skipropeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
