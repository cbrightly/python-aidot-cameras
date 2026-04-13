package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.database.DatabaseServiceImpl;
import java.util.Map;

public class ARouter$$Group$$databaseservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/databaseservice/database", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, DatabaseServiceImpl.class, "/databaseservice/database", "databaseservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
