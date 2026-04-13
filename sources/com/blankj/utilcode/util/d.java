package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.f0;
import java.util.List;

/* compiled from: AppUtils */
public final class d {
    public static void registerAppStatusChangedListener(@NonNull f0.b listener) {
        if (listener != null) {
            h0.addOnAppStatusChangedListener(listener);
            return;
        }
        throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void unregisterAppStatusChangedListener(@NonNull f0.b listener) {
        if (listener != null) {
            h0.removeOnAppStatusChangedListener(listener);
            return;
        }
        throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean p() {
        List<ActivityManager.RunningAppProcessInfo> info;
        ActivityManager am = (ActivityManager) f0.a().getSystemService("activity");
        if (am == null || (info = am.getRunningAppProcesses()) == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == 100 && aInfo.processName.equals(f0.a().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void q() {
        r(false);
    }

    public static void r(boolean isKillProcess) {
        Intent intent = h0.s(f0.a().getPackageName());
        if (intent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        intent.addFlags(335577088);
        f0.a().startActivity(intent);
        if (isKillProcess) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    public static String c() {
        return f0.a().getPackageName();
    }

    public static String a() {
        return b(f0.a().getPackageName());
    }

    public static String b(String packageName) {
        if (h0.E(packageName)) {
            return "";
        }
        try {
            PackageManager pm = f0.a().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (pi == null) {
                return null;
            }
            return pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String n() {
        return o(f0.a().getPackageName());
    }

    public static String o(String packageName) {
        if (h0.E(packageName)) {
            return "";
        }
        try {
            PackageInfo pi = f0.a().getPackageManager().getPackageInfo(packageName, 0);
            if (pi == null) {
                return null;
            }
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int l() {
        return m(f0.a().getPackageName());
    }

    public static int m(String packageName) {
        if (h0.E(packageName)) {
            return -1;
        }
        try {
            PackageInfo pi = f0.a().getPackageManager().getPackageInfo(packageName, 0);
            if (pi == null) {
                return -1;
            }
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Signature[] d(String packageName) {
        if (h0.E(packageName)) {
            return null;
        }
        try {
            PackageInfo pi = f0.a().getPackageManager().getPackageInfo(packageName, 64);
            if (pi == null) {
                return null;
            }
            return pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String h() {
        return i(f0.a().getPackageName());
    }

    public static String i(String packageName) {
        return e(packageName, "SHA1");
    }

    public static String j() {
        return k(f0.a().getPackageName());
    }

    public static String k(String packageName) {
        return e(packageName, "SHA256");
    }

    public static String f() {
        return g(f0.a().getPackageName());
    }

    public static String g(String packageName) {
        return e(packageName, "MD5");
    }

    private static String e(String packageName, String algorithm) {
        Signature[] signature;
        if (!h0.E(packageName) && (signature = d(packageName)) != null && signature.length > 0) {
            return h0.c(h0.x(signature[0].toByteArray(), algorithm)).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
        }
        return "";
    }
}
