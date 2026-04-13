package com.alibaba.android.arouter.core;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.facade.template.c;
import com.alibaba.android.arouter.facade.template.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: Warehouse */
public class b {
    static Map<String, Class<? extends e>> a = new HashMap();
    static Map<String, a> b = new HashMap();
    static Map<Class, c> c = new HashMap();
    static Map<String, a> d = new HashMap();
    static Map<Integer, Class<? extends IInterceptor>> e = new com.alibaba.android.arouter.base.a("More than one interceptors use same priority [%s]");
    static List<IInterceptor> f = new ArrayList();
}
