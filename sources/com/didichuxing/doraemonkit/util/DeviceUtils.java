package com.didichuxing.doraemonkit.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import com.blankj.utilcode.util.g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DeviceUtils {
    private static final String BRAND_HONGMI = "hongmi";
    private static final String BRAND_HONOR = "honor";
    private static final String BRAND_HUAWEI = "huawei";
    private static final String BRAND_SAMSUNG = "samsung";
    private static final String BRAND_XIAOMI = "xiaomi";
    private static String IMEI = "";
    private static String IMSI = "";
    private static final String KEY_UUID = "key_uuid";
    private static String PHONE_NUMBER = null;
    private static final String PREF_DEVICE = "com.kuaidi.daijia.driver.device_pref";
    private static Boolean ROOTED = null;
    private static final String TAG = "DeviceUtils";

    public static int getCoreNum() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            Log.e(TAG, "getCoreNum", e);
            return 1;
        }
    }

    public static long getTotalMemory(Context context) {
        long initial_memory = 0;
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String str2 = localBufferedReader.readLine();
            if (!TextUtils.isEmpty(str2)) {
                initial_memory = (long) (Integer.valueOf(str2.split("\\s+")[1]).intValue() / 1024);
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory;
    }

    public static long getAvailMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(mi);
        return (mi.availMem / 1024) / 1024;
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionName;
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionCode;
        }
        return 0;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PackageInfo getPackageInfoForPermission(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getWifiSSID(Context context) {
        WifiManager mWifi = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (mWifi.isWifiEnabled()) {
            return mWifi.getConnectionInfo().getSSID();
        }
        return "";
    }

    public static String getWifiBSSID(Context context) {
        WifiManager mWifi = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (mWifi.isWifiEnabled()) {
            return mWifi.getConnectionInfo().getBSSID();
        }
        return "";
    }

    public static boolean checkPermission(Context context, String permission, boolean defalutValue) {
        try {
            return context.checkCallingOrSelfPermission(permission) == 0;
        } catch (Exception e) {
            return defalutValue;
        }
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        return wm != null && (wm.getWifiState() == 3 || wm.getWifiState() == 2);
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isConnected() || activeNetInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static boolean hasJellyBeanMr2() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean hasJellyBeanMr1() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean isProduceByBrand(String brand) {
        String str;
        boolean result = false;
        String str2 = Build.MANUFACTURER;
        if (str2 != null && str2.toLowerCase().contains(brand)) {
            result = true;
        }
        if (result || (str = Build.BRAND) == null || !str.toLowerCase().contains(brand)) {
            return result;
        }
        return true;
    }

    public static boolean isProduceByXiaomi() {
        return isProductInBrands("xiaomi", BRAND_HONGMI);
    }

    public static boolean isProduceByHuaWei() {
        return isProductInBrands(BRAND_HUAWEI, BRAND_HONOR);
    }

    public static boolean isProduceBySamsung() {
        return isProduceByBrand("samsung");
    }

    public static boolean isProductInBrands(String... brands) {
        for (String brand : brands) {
            if (isProduceByBrand(brand)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSimReady(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService("phone");
            Log.d(TAG, "[isSimReady]" + mgr.getSimState());
            if (5 == mgr.getSimState() || 4 == mgr.getSimState()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "[isSimReady]" + e);
            return false;
        }
    }

    public static boolean isRoot(Context context) {
        Boolean bool = ROOTED;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf(g.r());
            ROOTED = valueOf;
            if (valueOf.booleanValue()) {
                Log.w(TAG, "Device rooted.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Check root failed.", e);
            ROOTED = false;
        }
        return ROOTED.booleanValue();
    }

    public static boolean hasFrontCamera() {
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (1 == info.facing) {
                return true;
            }
        }
        return false;
    }

    public static long getExternalAvailableSpaceInBytes() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String getSDTotalSize(Context context) {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return Formatter.formatFileSize(context, ((long) stat.getBlockSize()) * ((long) stat.getBlockCount()));
    }

    private static String getSDAvailableSize(Context context) {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return Formatter.formatFileSize(context, ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks()));
    }

    private static String getRomTotalSize(Context context) {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(context, ((long) stat.getBlockSize()) * ((long) stat.getBlockCount()));
    }

    private static String getRomAvailableSize(Context context) {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(context, ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks()));
    }

    public static String getSDCardSpace(Context context) {
        try {
            String free = getSDAvailableSize(context);
            String total = getSDTotalSize(context);
            return free + "/" + total;
        } catch (Exception e) {
            return "-/-";
        }
    }

    public static String getRomSpace(Context context) {
        try {
            String free = getRomAvailableSize(context);
            String total = getRomTotalSize(context);
            return free + "/" + total;
        } catch (Exception e) {
            return "-/-";
        }
    }
}
