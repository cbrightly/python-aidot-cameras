package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.JsbridgeServiceImpl;
import java.util.Map;

public class ARouter$$Group$$jsbridgeservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/jsbridgeservice/jsbridge", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, JsbridgeServiceImpl.class, "/jsbridgeservice/jsbridge", "jsbridgeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
