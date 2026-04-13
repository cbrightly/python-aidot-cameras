package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.camera.HeadImageClipActivity;
import java.util.Map;

public class ARouter$$Group$$camera implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/camera/headclip", a.a(com.alibaba.android.arouter.facade.enums.a.ACTIVITY, HeadImageClipActivity.class, "/camera/headclip", "camera", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
