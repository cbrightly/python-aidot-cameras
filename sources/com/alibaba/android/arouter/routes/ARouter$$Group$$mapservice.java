package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.map.MapServiceImpl;
import java.util.Map;

public class ARouter$$Group$$mapservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/mapservice/map", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, MapServiceImpl.class, "/mapservice/map", "mapservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
