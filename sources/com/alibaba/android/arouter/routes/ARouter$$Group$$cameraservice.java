package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.camera.CameraServiceImpl;
import java.util.Map;

public class ARouter$$Group$$cameraservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/cameraservice/camera", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, CameraServiceImpl.class, "/cameraservice/camera", "cameraservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
