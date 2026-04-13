package com.yanzhenjie.andserver;

import android.content.Context;
import com.yanzhenjie.andserver.register.a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ComponentRegister */
public class b {
    private static final List<String> a;
    private Context b;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add("AdapterRegister");
        arrayList.add("ConfigRegister");
        arrayList.add("ConverterRegister");
        arrayList.add("InterceptorRegister");
        arrayList.add("ResolverRegister");
    }

    public b(Context context) {
        this.b = context;
    }

    public void a(com.yanzhenjie.andserver.register.b register, String group) {
        String[] pathList = null;
        try {
            pathList = this.b.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pathList != null && pathList.length != 0) {
            for (String path : pathList) {
                if (path.endsWith(".andserver")) {
                    String packageName = path.substring(0, path.lastIndexOf(".andserver"));
                    for (String clazz : a) {
                        b(register, group, String.format("%s%s%s", new Object[]{packageName, ".andserver.processor.generator.", clazz}));
                    }
                }
            }
        }
    }

    private void b(com.yanzhenjie.andserver.register.b register, String group, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (a.class.isAssignableFrom(clazz)) {
                ((a) clazz.newInstance()).onRegister(this.b, group, register);
            }
        } catch (ClassNotFoundException e) {
        }
    }
}
