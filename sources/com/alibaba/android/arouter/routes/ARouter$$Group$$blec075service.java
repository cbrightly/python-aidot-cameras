package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import java.util.Map;

public class ARouter$$Group$$blec075service implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/blec075service/blec075", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BleC075ServiceImpl.class, "/blec075service/blec075", "blec075service", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
