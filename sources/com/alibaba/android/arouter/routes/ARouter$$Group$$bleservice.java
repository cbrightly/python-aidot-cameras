package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.ble.BleServiceImpl;
import java.util.Map;

public class ARouter$$Group$$bleservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/bleservice/ble", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BleServiceImpl.class, "/bleservice/ble", "bleservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
