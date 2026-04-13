package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.business.BleBusinessServiceImpl;
import java.util.Map;

public class ARouter$$Group$$ble implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/ble/business", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BleBusinessServiceImpl.class, "/ble/business", "ble", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
