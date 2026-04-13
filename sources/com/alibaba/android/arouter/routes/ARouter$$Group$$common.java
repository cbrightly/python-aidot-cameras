package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import java.util.Map;
import smarthome.service.HttpReportServiceImpl;

public class ARouter$$Group$$common implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/common/httpreport", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, HttpReportServiceImpl.class, "/common/httpreport", "common", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
