package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.database.DatabaseServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsStorage implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.DatabaseService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, DatabaseServiceImpl.class, "/databaseservice/database", "databaseservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
