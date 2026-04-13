package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import java.util.Map;

public class ARouter$$Root$$LdsSystem implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put("externalservice", ARouter$$Group$$externalservice.class);
        routes.put("systemservice", ARouter$$Group$$systemservice.class);
    }
}
