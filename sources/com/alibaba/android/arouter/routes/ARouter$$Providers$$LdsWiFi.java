package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.wifi.WiFiServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsWiFi implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.WiFiService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, WiFiServiceImpl.class, "/WiFiService/wifi", "WiFiService", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
