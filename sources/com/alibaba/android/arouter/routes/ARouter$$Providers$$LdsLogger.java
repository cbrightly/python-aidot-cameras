package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.log.LoggerServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsLogger implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.LoggerService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LoggerServiceImpl.class, "/loggerservice/logger", "loggerservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
