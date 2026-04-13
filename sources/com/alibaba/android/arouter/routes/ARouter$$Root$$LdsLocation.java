package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.e;
import com.alibaba.android.arouter.facade.template.f;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;

public class ARouter$$Root$$LdsLocation implements f {
    public void loadInto(Map<String, Class<? extends e>> routes) {
        routes.put(FirebaseAnalytics.Param.LOCATION, ARouter$$Group$$location.class);
        routes.put("mapservice", ARouter$$Group$$mapservice.class);
    }
}
