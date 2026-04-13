package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.http.HttpServiceImpl;
import java.util.Map;

public class ARouter$$Group$$httpservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/httpservice/http", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, HttpServiceImpl.class, "/httpservice/http", "httpservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
