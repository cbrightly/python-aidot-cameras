package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.system.AppStoreServiceImpl;
import com.leedarson.serviceimpl.system.SystemServiceImpl;
import com.leedarson.serviceimpl.system.external.ExternalServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsSystem implements d {
    public void loadInto(Map<String, a> providers) {
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.PROVIDER;
        providers.put("com.leedarson.serviceinterface.ExternalService", a.a(aVar, ExternalServiceImpl.class, "/externalservice/external", "externalservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        providers.put("com.leedarson.serviceinterface.AppStoreService", a.a(aVar, AppStoreServiceImpl.class, "/systemservice/appstore", "systemservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        providers.put("com.leedarson.serviceinterface.SystemService", a.a(aVar, SystemServiceImpl.class, "/systemservice/system", "systemservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
