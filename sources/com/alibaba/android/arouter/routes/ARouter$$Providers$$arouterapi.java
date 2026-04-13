package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.core.AutowiredServiceImpl;
import com.alibaba.android.arouter.core.InterceptorServiceImpl;
import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import java.util.Map;

public class ARouter$$Providers$$arouterapi implements d {
    public void loadInto(Map<String, a> providers) {
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.PROVIDER;
        providers.put("com.alibaba.android.arouter.facade.service.AutowiredService", a.a(aVar, AutowiredServiceImpl.class, "/arouter/service/autowired", "arouter", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        providers.put("com.alibaba.android.arouter.facade.service.InterceptorService", a.a(aVar, InterceptorServiceImpl.class, "/arouter/service/interceptor", "arouter", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
