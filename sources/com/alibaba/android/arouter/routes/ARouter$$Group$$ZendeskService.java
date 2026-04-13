package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.zendesk.ZendeskServiceImpl;
import java.util.Map;

public class ARouter$$Group$$ZendeskService implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/ZendeskService/zendesk", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, ZendeskServiceImpl.class, "/zendeskservice/zendesk", "zendeskservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
