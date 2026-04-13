package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import java.util.Map;

public class ARouter$$Root$$LdsBase implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put("common", ARouter$$Group$$common.class);
        routes.put("external", ARouter$$Group$$external.class);
    }
}
