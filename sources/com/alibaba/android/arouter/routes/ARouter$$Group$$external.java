package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import java.util.HashMap;
import java.util.Map;
import smarthome.ui.GuidePagerActivity;

public class ARouter$$Group$$external implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/external/guidepageractivity", a.a(com.alibaba.android.arouter.facade.enums.a.ACTIVITY, GuidePagerActivity.class, "/external/guidepageractivity", "external", new HashMap<String, Integer>() {
            {
                put("data", 8);
            }
        }, -1, Integer.MIN_VALUE));
    }
}
