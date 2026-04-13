package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.core.AutowiredServiceImpl;
import com.alibaba.android.arouter.core.InterceptorServiceImpl;
import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import java.util.Map;

public class ARouter$$Group$$arouter implements e {
    public void loadInto(Map<String, a> atlas) {
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.PROVIDER;
        atlas.put("/arouter/service/autowired", a.a(aVar, AutowiredServiceImpl.class, "/arouter/service/autowired", "arouter", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        atlas.put("/arouter/service/interceptor", a.a(aVar, InterceptorServiceImpl.class, "/arouter/service/interceptor", "arouter", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
