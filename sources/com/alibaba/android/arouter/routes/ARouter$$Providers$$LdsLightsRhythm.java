package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.LightsRhythmServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsLightsRhythm implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.LightsRhythmService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LightsRhythmServiceImpl.class, "/lightsrhythmservice/lightsrhythm", "lightsrhythmservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
