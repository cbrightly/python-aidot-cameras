package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.business.BusinessServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsBusiness implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.BusinessService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BusinessServiceImpl.class, "/BusinessService/business", "BusinessService", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
