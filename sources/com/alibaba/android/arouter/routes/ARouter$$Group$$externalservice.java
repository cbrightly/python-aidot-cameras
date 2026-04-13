package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.system.external.ExternalServiceImpl;
import java.util.Map;

public class ARouter$$Group$$externalservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/externalservice/external", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, ExternalServiceImpl.class, "/externalservice/external", "externalservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
