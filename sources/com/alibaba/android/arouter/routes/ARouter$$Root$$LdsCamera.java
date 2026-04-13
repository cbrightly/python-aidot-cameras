package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import java.util.Map;

public class ARouter$$Root$$LdsCamera implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put("camera", ARouter$$Group$$camera.class);
        routes.put("cameraservice", ARouter$$Group$$cameraservice.class);
    }
}
