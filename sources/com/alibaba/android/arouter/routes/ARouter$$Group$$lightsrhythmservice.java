package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.LightsRhythmServiceImpl;
import java.util.Map;

public class ARouter$$Group$$lightsrhythmservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/lightsrhythmservice/lightsrhythm", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, LightsRhythmServiceImpl.class, "/lightsrhythmservice/lightsrhythm", "lightsrhythmservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
