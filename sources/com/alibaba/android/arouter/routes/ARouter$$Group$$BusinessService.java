package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.business.BusinessServiceImpl;
import java.util.Map;

public class ARouter$$Group$$BusinessService implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/BusinessService/business", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, BusinessServiceImpl.class, "/businessservice/business", "businessservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
