package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alibaba.android.arouter.launcher.a;

/* compiled from: PackageUtils */
public class d {
    private static String a;
    private static int b;

    public static boolean b(Context context) {
        PackageInfo packageInfo = a(context);
        if (packageInfo == null) {
            return true;
        }
        String versionName = packageInfo.versionName;
        int versionCode = packageInfo.versionCode;
        SharedPreferences sp = context.getSharedPreferences("SP_AROUTER_CACHE", 0);
        if (versionName.equals(sp.getString("LAST_VERSION_NAME", (String) null)) && versionCode == sp.getInt("LAST_VERSION_CODE", -1)) {
            return false;
        }
        a = versionName;
        b = versionCode;
        return true;
    }

    public static void c(Context context) {
        if (!TextUtils.isEmpty(a) && b != 0) {
            context.getSharedPreferences("SP_AROUTER_CACHE", 0).edit().putString("LAST_VERSION_NAME", a).putInt("LAST_VERSION_CODE", b).apply();
        }
    }

    private static PackageInfo a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            a.c.d("ARouter::", "Get package info error.");
            return null;
        }
    }
}
