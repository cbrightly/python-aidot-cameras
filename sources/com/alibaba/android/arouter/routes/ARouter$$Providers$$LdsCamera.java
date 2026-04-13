package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.camera.CameraServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsCamera implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.CameraService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, CameraServiceImpl.class, "/cameraservice/camera", "cameraservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
