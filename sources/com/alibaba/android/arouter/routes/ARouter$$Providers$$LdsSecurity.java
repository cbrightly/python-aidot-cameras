package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.security.SecurityServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsSecurity implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.SecurityService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, SecurityServiceImpl.class, "/SecurityService/security", "SecurityService", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
