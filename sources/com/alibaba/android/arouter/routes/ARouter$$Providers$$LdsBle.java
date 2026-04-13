package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.ble.BleServiceImpl;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.business.BleBusinessServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsBle implements d {
    public void loadInto(Map<String, a> providers) {
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.PROVIDER;
        providers.put("com.leedarson.serviceinterface.BleBusinessService", a.a(aVar, BleBusinessServiceImpl.class, "/ble/business", "ble", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        providers.put("com.leedarson.serviceinterface.BleService", a.a(aVar, BleServiceImpl.class, "/bleservice/ble", "bleservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        providers.put("com.leedarson.serviceinterface.BleC075Service", a.a(aVar, BleC075ServiceImpl.class, "/blec075service/blec075", "blec075service", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
