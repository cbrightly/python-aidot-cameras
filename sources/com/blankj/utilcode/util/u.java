package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.List;

/* compiled from: ProcessUtils */
public final class u {
    public static boolean e() {
        return f0.a().getPackageName().equals(a());
    }

    public static String a() {
        String name = c();
        if (!TextUtils.isEmpty(name)) {
            return name;
        }
        String name2 = b();
        if (!TextUtils.isEmpty(name2)) {
            return name2;
        }
        return d();
    }

    private static String c() {
        try {
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/cmdline")));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String b() {
        List<ActivityManager.RunningAppProcessInfo> info;
        String str;
        try {
            ActivityManager am = (ActivityManager) f0.a().getSystemService("activity");
            if (!(am == null || (info = am.getRunningAppProcesses()) == null)) {
                if (info.size() != 0) {
                    int pid = Process.myPid();
                    for (ActivityManager.RunningAppProcessInfo aInfo : info) {
                        if (aInfo.pid == pid && (str = aInfo.processName) != null) {
                            return str;
                        }
                    }
                    return "";
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    private static String d() {
        try {
            Application app = f0.a();
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);
            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);
            return (String) activityThread.getClass().getDeclaredMethod("getProcessName", new Class[0]).invoke(activityThread, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
