package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceUtils {
    public static String getOS() {
        String str = Build.VERSION.RELEASE;
        return str == null ? LDNetUtil.NETWORKTYPE_INVALID : str;
    }

    public static String getManufacturer() {
        try {
            String manufacturer = Build.MANUFACTURER;
            if (manufacturer != null) {
                return manufacturer.trim().toUpperCase();
            }
            return LDNetUtil.NETWORKTYPE_INVALID;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public static String getBrand() {
        try {
            String brand = Build.BRAND;
            if (brand != null) {
                return brand.trim().toUpperCase();
            }
            return LDNetUtil.NETWORKTYPE_INVALID;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public static String getModel() {
        String str = Build.MODEL;
        return TextUtils.isEmpty(str) ? LDNetUtil.NETWORKTYPE_INVALID : str.trim();
    }

    public static int[] getDeviceSize(Context context) {
        int screenHeight;
        int screenWidth;
        int[] size = new int[2];
        try {
            Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            int rotation = display.getRotation();
            Point point = new Point();
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                display.getRealSize(point);
                screenWidth = point.x;
                screenHeight = point.y;
            } else if (i >= 13) {
                display.getSize(point);
                screenWidth = point.x;
                screenHeight = point.y;
            } else {
                screenWidth = display.getWidth();
                screenHeight = display.getHeight();
            }
            size[0] = getNaturalWidth(rotation, screenWidth, screenHeight);
            size[1] = getNaturalHeight(rotation, screenWidth, screenHeight);
        } catch (Exception e) {
            if (context.getResources() != null) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                size[0] = displayMetrics.widthPixels;
                size[1] = displayMetrics.heightPixels;
            }
        }
        return size;
    }

    private static int getNaturalWidth(int rotation, int width, int height) {
        return (rotation == 0 || rotation == 2) ? width : height;
    }

    private static int getNaturalHeight(int rotation, int width, int height) {
        return (rotation == 0 || rotation == 2) ? height : width;
    }

    public static String getHarmonyOSVersion() {
        if (!isHarmonyOs()) {
            return null;
        }
        String version = getProp("hw_sc.build.platform.version", "");
        if (TextUtils.isEmpty(version)) {
            return exec(SensorsDataUtils.COMMAND_HARMONYOS_VERSION);
        }
        return version;
    }

    private static boolean isHarmonyOs() {
        try {
            Class<?> buildExClass = Class.forName("com.huawei.system.BuildEx");
            Object osBrand = buildExClass.getMethod("getOsBrand", new Class[0]).invoke(buildExClass, new Object[0]);
            if (osBrand == null) {
                return false;
            }
            return "harmony".equalsIgnoreCase(osBrand.toString());
        } catch (Throwable e) {
            SALog.i("SA.HasHarmonyOS", e.getMessage());
            return false;
        }
    }

    private static String getProp(String property, String defaultValue) {
        try {
            Class spClz = Class.forName("android.os.SystemProperties");
            String value = (String) spClz.getDeclaredMethod("get", new Class[]{String.class}).invoke(spClz, new Object[]{property});
            if (TextUtils.isEmpty(value)) {
                return defaultValue;
            }
            return value;
        } catch (Throwable throwable) {
            SALog.i("SA.SystemProperties", throwable.getMessage());
            return defaultValue;
        }
    }

    public static String exec(String command) {
        InputStreamReader ir = null;
        BufferedReader input = null;
        try {
            InputStreamReader ir2 = new InputStreamReader(Runtime.getRuntime().exec(command).getInputStream());
            BufferedReader input2 = new BufferedReader(ir2);
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = input2.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(line);
            }
            String sb = stringBuilder.toString();
            try {
                input2.close();
            } catch (Throwable e) {
                SALog.i("SA.Exec", e.getMessage());
            }
            try {
                ir2.close();
            } catch (IOException e2) {
                SALog.i("SA.Exec", e2.getMessage());
            }
            return sb;
        } catch (Throwable e3) {
            SALog.i("SA.Exec", e3.getMessage());
        }
        if (ir == null) {
            return null;
        }
        try {
            ir.close();
            return null;
        } catch (IOException e4) {
            SALog.i("SA.Exec", e4.getMessage());
            return null;
        }
    }
}
