package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.MatterServiceImpl;
import java.util.Map;

public class ARouter$$Group$$matterservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/matterservice/matter", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, MatterServiceImpl.class, "/matterservice/matter", "matterservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
