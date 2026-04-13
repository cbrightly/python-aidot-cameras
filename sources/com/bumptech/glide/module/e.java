package com.bumptech.glide.module;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* compiled from: ManifestParser */
public final class e {
    private final Context a;

    public e(Context context) {
        this.a = context;
    }

    public List<c> a() {
        if (Log.isLoggable("ManifestParser", 3)) {
            Log.d("ManifestParser", "Loading Glide modules");
        }
        List<GlideModule> modules = new ArrayList<>();
        try {
            ApplicationInfo appInfo = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128);
            if (appInfo.metaData == null) {
                if (Log.isLoggable("ManifestParser", 3)) {
                    Log.d("ManifestParser", "Got null app info metadata");
                }
                return modules;
            }
            if (Log.isLoggable("ManifestParser", 2)) {
                Log.v("ManifestParser", "Got app info metadata: " + appInfo.metaData);
            }
            for (String key : appInfo.metaData.keySet()) {
                if ("GlideModule".equals(appInfo.metaData.get(key))) {
                    modules.add(b(key));
                    if (Log.isLoggable("ManifestParser", 3)) {
                        Log.d("ManifestParser", "Loaded Glide module: " + key);
                    }
                }
            }
            if (Log.isLoggable("ManifestParser", 3)) {
                Log.d("ManifestParser", "Finished loading Glide modules");
            }
            return modules;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse GlideModules", e);
        }
    }

    private static c b(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object module = null;
            try {
                module = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (InstantiationException e) {
                c(clazz, e);
            } catch (IllegalAccessException e2) {
                c(clazz, e2);
            } catch (NoSuchMethodException e3) {
                c(clazz, e3);
            } catch (InvocationTargetException e4) {
                c(clazz, e4);
            }
            if (module instanceof c) {
                return (c) module;
            }
            throw new RuntimeException("Expected instanceof GlideModule, but found: " + module);
        } catch (ClassNotFoundException e5) {
            throw new IllegalArgumentException("Unable to find GlideModule implementation", e5);
        }
    }

    private static void c(Class<?> clazz, Exception e) {
        throw new RuntimeException("Unable to instantiate GlideModule implementation for " + clazz, e);
    }
}
