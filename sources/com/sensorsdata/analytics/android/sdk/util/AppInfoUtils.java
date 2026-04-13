package com.sensorsdata.analytics.android.sdk.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ThreadNameConstants;
import java.lang.reflect.Method;

public class AppInfoUtils {
    private static String mAppVersionName;
    private static Bundle mConfigBundle;

    public static CharSequence getAppName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationInfo(context.getPackageName(), 128).loadLabel(packageManager);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static String getProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getApplicationInfo().processName;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }

    public static String getAppVersionName(Context context) {
        if (context == null) {
            return "";
        }
        if (!TextUtils.isEmpty(mAppVersionName)) {
            return mAppVersionName;
        }
        try {
            mAppVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return mAppVersionName;
    }

    public static String getMainProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getApplicationContext().getApplicationInfo().processName;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }

    public static boolean isMainProcess(Context context, Bundle bundle) {
        if (context == null) {
            return false;
        }
        String mainProcessName = getMainProcessName(context);
        if (TextUtils.isEmpty(mainProcessName) && bundle != null) {
            mainProcessName = bundle.getString("com.sensorsdata.analytics.android.MainProcessName");
        }
        if (TextUtils.isEmpty(mainProcessName)) {
            return true;
        }
        String currentProcess = getCurrentProcessName();
        if (TextUtils.isEmpty(currentProcess) || mainProcessName.equals(currentProcess)) {
            return true;
        }
        return false;
    }

    public static boolean isTaskExecuteThread() {
        return TextUtils.equals(ThreadNameConstants.THREAD_TASK_EXECUTE, Thread.currentThread().getName());
    }

    public static Bundle getAppInfoBundle(Context context) {
        if (mConfigBundle == null) {
            try {
                ApplicationInfo appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (appInfo != null) {
                    mConfigBundle = appInfo.metaData;
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
        Bundle bundle = mConfigBundle;
        if (bundle == null) {
            return new Bundle();
        }
        return bundle;
    }

    private static String getCurrentProcessName() {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                return Application.getProcessName();
            }
            String currentProcess = getCurrentProcessNameByCmd();
            if (TextUtils.isEmpty(currentProcess)) {
                return getCurrentProcessNameByAT();
            }
            return currentProcess;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private static String getCurrentProcessNameByAT() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            Object processInvoke = declaredMethod.invoke((Object) null, new Object[0]);
            if (processInvoke instanceof String) {
                return (String) processInvoke;
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0034 A[SYNTHETIC, Splitter:B:15:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0021 A[Catch:{ all -> 0x003d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getCurrentProcessNameByCmd() {
        /*
            r0 = 0
            java.lang.String r1 = "/proc/self/cmdline"
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x003d }
            r2.<init>(r1)     // Catch:{ all -> 0x003d }
            r0 = r2
            r2 = 256(0x100, float:3.59E-43)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x003d }
            r3 = 0
        L_0x000e:
            int r4 = r0.read()     // Catch:{ all -> 0x003d }
            r5 = r4
            if (r4 <= 0) goto L_0x001f
            int r4 = r2.length     // Catch:{ all -> 0x003d }
            if (r3 >= r4) goto L_0x001f
            int r4 = r3 + 1
            byte r6 = (byte) r5     // Catch:{ all -> 0x003d }
            r2[r3] = r6     // Catch:{ all -> 0x003d }
            r3 = r4
            goto L_0x000e
        L_0x001f:
            if (r3 <= 0) goto L_0x0033
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x003d }
            r6 = 0
            java.lang.String r7 = "UTF-8"
            r4.<init>(r2, r6, r3, r7)     // Catch:{ all -> 0x003d }
            r0.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x0032
        L_0x002e:
            r6 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r6)
        L_0x0032:
            return r4
        L_0x0033:
            r0.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0037:
            goto L_0x0044
        L_0x0038:
            r1 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r1)
            goto L_0x0037
        L_0x003d:
            r1 = move-exception
            if (r0 == 0) goto L_0x0044
            r0.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x0037
        L_0x0044:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.AppInfoUtils.getCurrentProcessNameByCmd():java.lang.String");
    }
}
