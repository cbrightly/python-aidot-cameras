package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.buglymodule.BuglyEngineHelper;
import java.util.Map;

public class ARouter$$Providers$$LdsBuglyModule implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.LdsCrashEngineReportService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BuglyEngineHelper.class, "/bugly/crashreport/bugly", "bugly", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
