package com.alibaba.android.arouter.launcher;

import android.app.Application;
import android.content.Context;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.facade.template.b;

/* compiled from: ARouter */
public final class a {
    private static volatile a a = null;
    private static volatile boolean b = false;
    public static b c;

    private a() {
    }

    public static void d(Application application) {
        if (!b) {
            b bVar = b.a;
            c = bVar;
            bVar.c("ARouter::", "ARouter init start.");
            b = b.k(application);
            if (b) {
                b.e();
            }
            b.a.c("ARouter::", "ARouter init over.");
        }
    }

    public static a c() {
        if (b) {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null) {
                        a = new a();
                    }
                }
            }
            return a;
        }
        throw new InitException("ARouter::Init::Invoke init(context) first!");
    }

    public static boolean b() {
        return b.h();
    }

    public void e(Object thiz) {
        b.l(thiz);
    }

    public com.alibaba.android.arouter.facade.a a(String path) {
        return b.j().f(path);
    }

    public <T> T g(Class<? extends T> service) {
        return b.j().n(service);
    }

    public Object f(Context mContext, com.alibaba.android.arouter.facade.a postcard, int requestCode, com.alibaba.android.arouter.facade.callback.b callback) {
        return b.j().m(mContext, postcard, requestCode, callback);
    }
}
