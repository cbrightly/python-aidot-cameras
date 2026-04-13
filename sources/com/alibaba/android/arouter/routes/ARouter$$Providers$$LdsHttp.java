package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.http.HttpServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsHttp implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.HttpService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, HttpServiceImpl.class, "/httpservice/http", "httpservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
