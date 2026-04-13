package com.didichuxing.doraemonkit.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public class SystemUtil {
    public static final String PHONE_HONOR = "HONOR";
    public static final String PHONE_HTC = "htc";
    public static final String PHONE_HUAWEI = "HUAWEI";
    public static final String PHONE_LENOVO = "lenovo";
    public static final String PHONE_LG = "lg";
    public static final String PHONE_LeMobile = "LeMobile";
    public static final String PHONE_MEIZU = "Meizu";
    public static final String PHONE_NOVA = "nova";
    public static final String PHONE_OPPO = "oppo";
    public static final String PHONE_SAMSUNG = "samsung";
    public static final String PHONE_SONY = "sony";
    public static final String PHONE_VIVO = "vivo";
    public static final String PHONE_XIAOMI = "xiaomi";
    private static final String TAG = "SystemUtil";
    private static final Pattern VERSION_NAME_PATTERN = Pattern.compile("(\\d+\\.\\d+\\.\\d+)-*.*");
    private static String sAppName;
    private static String sAppVersion;
    private static int sAppVersionCode = -1;
    private static String sPackageName;

    public static String getVersionName(Context context) {
        if (!TextUtils.isEmpty(sAppVersion)) {
            return sAppVersion;
        }
        try {
            String appVersion = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName;
            if (appVersion == null || appVersion.length() <= 0) {
                return appVersion;
            }
            Matcher matcher = VERSION_NAME_PATTERN.matcher(appVersion);
            if (!matcher.matches()) {
                return appVersion;
            }
            String appVersion2 = matcher.group(1);
            sAppVersion = appVersion2;
            return appVersion2;
        } catch (Throwable t) {
            LogHelper.e(TAG, t.toString());
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        int i = sAppVersionCode;
        if (i != -1) {
            return i;
        }
        try {
            int versionCode = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionCode;
            if (versionCode != -1) {
                sAppVersionCode = versionCode;
            }
        } catch (Throwable t) {
            LogHelper.e(TAG, t.toString());
        }
        return sAppVersionCode;
    }

    public static String getPackageName(Context context) {
        if (TextUtils.isEmpty(sPackageName)) {
            sPackageName = context.getPackageName();
        }
        return sPackageName;
    }

    public static String getAppName(Context context) {
        if (!TextUtils.isEmpty(sAppName)) {
            return sAppName;
        }
        ApplicationInfo applicationInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogHelper.e(TAG, e.toString());
        }
        String str = (String) packageManager.getApplicationLabel(applicationInfo);
        sAppName = str;
        return str;
    }

    public static String obtainProcessName(Context context) {
        int pid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> listTaskInfo = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (listTaskInfo == null || listTaskInfo.isEmpty()) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo info : listTaskInfo) {
            if (info != null && info.pid == pid) {
                return info.processName;
            }
        }
        return null;
    }

    public static boolean isMainLaunchActivity(Activity activity) {
        Intent intent = activity.getApplication().getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        if (intent == null) {
            return false;
        }
        ComponentName launchComponentName = intent.getComponent();
        ComponentName componentName = activity.getComponentName();
        if (launchComponentName == null || !componentName.toString().equals(launchComponentName.toString())) {
            return false;
        }
        return true;
    }

    public static boolean isOnlyFirstLaunchActivity(Activity activity) {
        boolean isMainActivity = isMainLaunchActivity(activity);
        ActivityLifecycleInfo activityLifecycleInfo = DokitConstant.ACTIVITY_LIFECYCLE_INFOS.get(activity.getClass().getCanonicalName());
        return activityLifecycleInfo != null && isMainActivity && !activityLifecycleInfo.isInvokeStopMethod();
    }

    public static void startDevelopmentActivity(Context context) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent2 = new Intent();
                intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                intent2.setComponent(componentName);
                intent2.setAction("android.intent.action.View");
                context.startActivity(intent2);
            } catch (Exception e2) {
                try {
                    Intent intent3 = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    intent3.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    context.startActivity(intent3);
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
        }
    }

    public static void startLocalActivity(Context context) {
        try {
            Intent intent = new Intent("android.settings.LOCALE_SETTINGS");
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServiceRunningActivity(Context context) {
        ComponentName componentName;
        try {
            if (getBrand().equalsIgnoreCase(PHONE_VIVO)) {
                componentName = new ComponentName("com.android.settings", "com.vivo.settings.VivoSubSettingsForImmersiveBar");
            } else {
                componentName = new ComponentName("com.android.settings", "com.android.settings.CleanSubSettings");
            }
            Intent intent = new Intent();
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.View");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBrand() {
        return Build.BRAND;
    }
}
