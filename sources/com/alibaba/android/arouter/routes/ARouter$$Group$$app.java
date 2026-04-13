package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.smarthome.ui.MainActivity;
import java.util.Map;

public class ARouter$$Group$$app implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/app/main/", a.a(com.alibaba.android.arouter.facade.enums.a.ACTIVITY, MainActivity.class, "/app/main/", "app", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
