package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceimpl.map.PathViewMapActivity;
import java.util.Map;

public class ARouter$$Group$$location implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/location/radar", a.a(com.alibaba.android.arouter.facade.enums.a.ACTIVITY, PathViewMapActivity.class, "/location/radar", FirebaseAnalytics.Param.LOCATION, (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
