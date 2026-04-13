package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.BleMeshServiceImpl;
import java.util.Map;

public class ARouter$$Group$$blemeshservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/blemeshservice/blemesh", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BleMeshServiceImpl.class, "/blemeshservice/blemesh", "blemeshservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
