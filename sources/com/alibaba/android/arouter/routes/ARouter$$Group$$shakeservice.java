package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.shake.ShakeServiceImpl;
import java.util.Map;

public class ARouter$$Group$$shakeservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/shakeservice/shake", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, ShakeServiceImpl.class, "/shakeservice/shake", "shakeservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
