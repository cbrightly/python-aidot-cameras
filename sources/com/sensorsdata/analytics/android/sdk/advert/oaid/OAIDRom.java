package com.sensorsdata.analytics.android.sdk.advert.oaid;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.util.SystemUtil;
import com.sensorsdata.analytics.android.sdk.SALog;

public final class OAIDRom {
    private static final String TAG = "SA.OAIDRom";

    private OAIDRom() {
    }

    public static String sysProperty(String key, String defValue) {
        Class<String> cls = String.class;
        String res = null;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            res = (String) clazz.getMethod("get", new Class[]{cls, cls}).invoke(clazz, new Object[]{key, defValue});
        } catch (Throwable th) {
            SALog.i(TAG, th);
        }
        if (res == null) {
            return "";
        }
        return res;
    }

    public static boolean isHuawei() {
        if (!Build.MANUFACTURER.equalsIgnoreCase(SystemUtil.PHONE_HUAWEI)) {
            String str = Build.BRAND;
            return str.equalsIgnoreCase(SystemUtil.PHONE_HUAWEI) || str.equalsIgnoreCase(SystemUtil.PHONE_HONOR);
        }
    }

    public static boolean isEmui() {
        return !TextUtils.isEmpty(sysProperty("ro.build.version.emui", ""));
    }

    public static boolean isOppo() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
            String str = Build.BRAND;
            return str.equalsIgnoreCase("OPPO") || str.equalsIgnoreCase("REALME") || !TextUtils.isEmpty(sysProperty("ro.build.version.opporom", ""));
        }
    }

    public static boolean isVivo() {
        return Build.MANUFACTURER.equalsIgnoreCase("VIVO") || Build.BRAND.equalsIgnoreCase("VIVO") || !TextUtils.isEmpty(sysProperty("ro.vivo.os.version", ""));
    }

    public static boolean isXiaomi() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("XIAOMI")) {
            String str = Build.BRAND;
            return str.equalsIgnoreCase("XIAOMI") || str.equalsIgnoreCase("REDMI");
        }
    }

    public static boolean isMiui() {
        return !TextUtils.isEmpty(sysProperty("ro.miui.ui.version.name", ""));
    }

    public static boolean isBlackShark() {
        return Build.MANUFACTURER.equalsIgnoreCase("BLACKSHARK") || Build.BRAND.equalsIgnoreCase("BLACKSHARK");
    }

    public static boolean isOnePlus() {
        return Build.MANUFACTURER.equalsIgnoreCase("ONEPLUS") || Build.BRAND.equalsIgnoreCase("ONEPLUS");
    }

    public static boolean isSamsung() {
        return Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG") || Build.BRAND.equalsIgnoreCase("SAMSUNG");
    }

    public static boolean isMeizu() {
        return Build.MANUFACTURER.equalsIgnoreCase("MEIZU") || Build.BRAND.equalsIgnoreCase("MEIZU") || Build.DISPLAY.toUpperCase().contains("FLYME");
    }

    public static boolean isLenovo() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("LENOVO")) {
            String str = Build.BRAND;
            return str.equalsIgnoreCase("LENOVO") || str.equalsIgnoreCase("ZUK");
        }
    }

    public static boolean isNubia() {
        return Build.MANUFACTURER.equalsIgnoreCase("NUBIA") || Build.BRAND.equalsIgnoreCase("NUBIA");
    }

    public static boolean isASUS() {
        return Build.MANUFACTURER.equalsIgnoreCase("ASUS") || Build.BRAND.equalsIgnoreCase("ASUS");
    }

    public static boolean isZTE() {
        return Build.MANUFACTURER.equalsIgnoreCase("ZTE") || Build.BRAND.equalsIgnoreCase("ZTE");
    }

    public static boolean isMotolora() {
        return Build.MANUFACTURER.equalsIgnoreCase("MOTOLORA") || Build.BRAND.equalsIgnoreCase("MOTOLORA");
    }

    public static boolean isCoolpad(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
