package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.mqtt.LDSBaseMqttServiceImpl;
import java.util.Map;

public class ARouter$$Group$$channel implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/channel/ldsmqtt", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LDSBaseMqttServiceImpl.class, "/channel/ldsmqtt", "channel", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
