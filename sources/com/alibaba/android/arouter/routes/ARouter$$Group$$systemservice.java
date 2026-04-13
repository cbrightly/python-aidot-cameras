package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.system.AppStoreServiceImpl;
import com.leedarson.serviceimpl.system.SystemServiceImpl;
import java.util.Map;

public class ARouter$$Group$$systemservice implements e {
    public void loadInto(Map<String, a> atlas) {
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.PROVIDER;
        atlas.put("/systemservice/appstore", a.a(aVar, AppStoreServiceImpl.class, "/systemservice/appstore", "systemservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        atlas.put("/systemservice/system", a.a(aVar, SystemServiceImpl.class, "/systemservice/system", "systemservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
