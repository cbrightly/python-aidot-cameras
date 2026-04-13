package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AdaptScreenUtils */
public final class b {
    private static List<Field> a;

    private static void b(Resources resources, float newXdpi) {
        resources.getDisplayMetrics().xdpi = newXdpi;
        f0.a().getResources().getDisplayMetrics().xdpi = newXdpi;
        d(resources, newXdpi);
    }

    /* compiled from: AdaptScreenUtils */
    public static final class a implements Runnable {
        a() {
        }

        public void run() {
            b.g();
        }
    }

    static Runnable f() {
        return new a();
    }

    /* access modifiers changed from: private */
    public static void g() {
        b(Resources.getSystem(), Resources.getSystem().getDisplayMetrics().xdpi);
    }

    private static void d(Resources resources, float newXdpi) {
        if (a == null) {
            a = new ArrayList();
            Class resCls = resources.getClass();
            Field[] declaredFields = resCls.getDeclaredFields();
            while (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
                        field.setAccessible(true);
                        DisplayMetrics tmpDm = e(resources, field);
                        if (tmpDm != null) {
                            a.add(field);
                            tmpDm.xdpi = newXdpi;
                        }
                    }
                }
                resCls = resCls.getSuperclass();
                if (resCls != null) {
                    declaredFields = resCls.getDeclaredFields();
                } else {
                    return;
                }
            }
            return;
        }
        c(resources, newXdpi);
    }

    private static void c(Resources resources, float newXdpi) {
        for (Field metricsField : a) {
            try {
                DisplayMetrics dm = (DisplayMetrics) metricsField.get(resources);
                if (dm != null) {
                    dm.xdpi = newXdpi;
                }
            } catch (Exception e) {
                Log.e("AdaptScreenUtils", "applyMetricsFields: " + e);
            }
        }
    }

    private static DisplayMetrics e(Resources resources, Field field) {
        try {
            return (DisplayMetrics) field.get(resources);
        } catch (Exception e) {
            return null;
        }
    }
}
