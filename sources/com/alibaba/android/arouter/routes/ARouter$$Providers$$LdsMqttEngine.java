package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.mqtt.LDSBaseMqttServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsMqttEngine implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.LDSBaseMqttService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LDSBaseMqttServiceImpl.class, "/channel/ldsmqtt", "channel", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
