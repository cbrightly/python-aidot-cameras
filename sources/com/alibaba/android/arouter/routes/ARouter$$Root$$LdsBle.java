package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import java.util.Map;

public class ARouter$$Root$$LdsBle implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put("ble", ARouter$$Group$$ble.class);
        routes.put("blec075service", ARouter$$Group$$blec075service.class);
        routes.put("bleservice", ARouter$$Group$$bleservice.class);
    }
}
