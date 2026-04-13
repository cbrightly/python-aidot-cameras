package com.alibaba.android.arouter.core;

import android.content.Context;
import android.util.LruCache;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.template.g;
import java.util.ArrayList;
import java.util.List;

public class AutowiredServiceImpl implements AutowiredService {
    private LruCache<String, g> a;
    private List<String> b;

    public void init(Context context) {
        this.a = new LruCache<>(50);
        this.b = new ArrayList();
    }

    public void c(Object instance) {
        a(instance, (Class<?>) null);
    }

    private void a(Object instance, Class<?> parent) {
        Class<?> clazz = parent == null ? instance.getClass() : parent;
        g syringe = h(clazz);
        if (syringe != null) {
            syringe.inject(instance);
        }
        Class<? super Object> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.getName().startsWith("android")) {
            a(instance, superclass);
        }
    }

    private g h(Class<?> clazz) {
        String className = clazz.getName();
        try {
            if (this.b.contains(className)) {
                return null;
            }
            g syringeHelper = this.a.get(className);
            if (syringeHelper == null) {
                syringeHelper = (g) Class.forName(clazz.getName() + "$$ARouter$$Autowired").getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            this.a.put(className, syringeHelper);
            return syringeHelper;
        } catch (Exception e) {
            this.b.add(className);
            return null;
        }
    }
}
