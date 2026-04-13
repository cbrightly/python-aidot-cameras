package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import java.util.Map;

public class ARouter$$Root$$LdsPlayer implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put("ipc", ARouter$$Group$$ipc.class);
        routes.put("ipcservice", ARouter$$Group$$ipcservice.class);
    }
}
