package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.wifi.WiFiServiceImpl;
import java.util.Map;

public class ARouter$$Group$$WiFiService implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/WiFiService/wifi", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, WiFiServiceImpl.class, "/wifiservice/wifi", "wifiservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
