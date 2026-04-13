package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.buglymodule.BuglyEngineHelper;
import java.util.Map;

public class ARouter$$Group$$bugly implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/bugly/crashreport/bugly", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BuglyEngineHelper.class, "/bugly/crashreport/bugly", "bugly", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
