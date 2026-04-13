package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.zendesk.ZendeskServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsZendesk implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.ZendeskService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, ZendeskServiceImpl.class, "/ZendeskService/zendesk", "ZendeskService", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
