package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.log.LoggerServiceImpl;
import java.util.Map;

public class ARouter$$Group$$loggerservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/loggerservice/logger", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LoggerServiceImpl.class, "/loggerservice/logger", "loggerservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
