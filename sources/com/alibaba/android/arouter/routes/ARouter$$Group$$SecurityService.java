package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.security.SecurityServiceImpl;
import java.util.Map;

public class ARouter$$Group$$SecurityService implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/SecurityService/security", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, SecurityServiceImpl.class, "/securityservice/security", "securityservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
