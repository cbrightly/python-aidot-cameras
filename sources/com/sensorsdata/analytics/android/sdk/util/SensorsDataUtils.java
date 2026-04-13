package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.R;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackAppViewScreenUrl;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public final class SensorsDataUtils {
    public static final String COMMAND_HARMONYOS_VERSION = "getprop hw_sc.build.platform.version";
    private static final String SHARED_PREF_APP_VERSION = "sensorsdata.app.version";
    private static final String SHARED_PREF_DEVICE_ID_KEY = "sensorsdata.device.id";
    private static final String SHARED_PREF_EDITS_FILE = "sensorsdata";
    private static final String SHARED_PREF_USER_AGENT_KEY = "sensorsdata.user.agent";
    private static final String TAG = "SA.SensorsDataUtils";
    private static String androidID = "";
    private static final Map<String, String> deviceUniqueIdentifiersMap = new HashMap();
    private static boolean isUniApp = false;
    private static final List<String> mInvalidAndroidId = new ArrayList<String>() {
        {
            add("9774d56d682e549c");
            add("0123456789abcdef");
            add("0000000000000000");
        }
    };
    private static final Set<String> mPermissionGrantedSet = new HashSet();
    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final Map<String, String> sCarrierMap = new HashMap<String, String>() {
        {
            put("46000", "中国移动");
            put("46002", "中国移动");
            put("46007", "中国移动");
            put("46008", "中国移动");
            put("46001", "中国联通");
            put("46006", "中国联通");
            put("46009", "中国联通");
            put("46003", "中国电信");
            put("46005", "中国电信");
            put("46011", "中国电信");
            put("46004", "中国卫通");
            put("46020", "中国铁通");
        }
    };

    private static String getJsonFromAssets(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bf = null;
        try {
            BufferedReader bf2 = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            while (true) {
                String readLine = bf2.readLine();
                String line = readLine;
                if (readLine != null) {
                    stringBuilder.append(line);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        SALog.printStackTrace(e);
                    }
                }
            }
            bf2.close();
        } catch (IOException e2) {
            SALog.printStackTrace(e2);
            if (bf != null) {
                bf.close();
            }
        } catch (Throwable th) {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e3) {
                    SALog.printStackTrace(e3);
                }
            }
            throw th;
        }
        return stringBuilder.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        com.sensorsdata.analytics.android.sdk.SALog.i(TAG, r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0060, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0061, code lost:
        com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0055 A[ExcHandler: Error (r0v3 'error' java.lang.Error A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCarrier(android.content.Context r5) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = checkHasPermission(r5, r0)     // Catch:{ Exception -> 0x0060, Error -> 0x0055 }
            if (r0 == 0) goto L_0x0064
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r5.getSystemService(r0)     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            if (r0 == 0) goto L_0x004f
            java.lang.String r1 = r0.getSimOperator()     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            r2 = 0
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            r4 = 28
            if (r3 < r4) goto L_0x002d
            java.lang.CharSequence r3 = r0.getSimCarrierIdName()     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            if (r4 != 0) goto L_0x002d
            java.lang.String r4 = r3.toString()     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            r2 = r4
        L_0x002d:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            if (r3 == 0) goto L_0x0044
            int r3 = r0.getSimState()     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            r4 = 5
            if (r3 != r4) goto L_0x0040
            java.lang.String r3 = r0.getSimOperatorName()     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            r2 = r3
            goto L_0x0044
        L_0x0040:
            java.lang.String r3 = "未知"
            r2 = r3
        L_0x0044:
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            if (r3 != 0) goto L_0x004f
            java.lang.String r3 = operatorToCarrier(r5, r1, r2)     // Catch:{ Exception -> 0x0050, Error -> 0x0055 }
            return r3
        L_0x004f:
            goto L_0x0064
        L_0x0050:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x0060, Error -> 0x0055 }
            goto L_0x0064
        L_0x0055:
            r0 = move-exception
            java.lang.String r1 = r0.toString()
            java.lang.String r2 = "SA.SensorsDataUtils"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r2, (java.lang.String) r1)
            goto L_0x0065
        L_0x0060:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0064:
        L_0x0065:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.getCarrier(android.content.Context):java.lang.String");
    }

    public static String getActivityTitle(Activity activity) {
        PackageManager packageManager;
        if (activity == null) {
            return null;
        }
        String activityTitle = null;
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                String toolbarTitle = getToolbarTitle(activity);
                if (!TextUtils.isEmpty(toolbarTitle)) {
                    activityTitle = toolbarTitle;
                }
            }
            if (TextUtils.isEmpty(activityTitle)) {
                activityTitle = activity.getTitle().toString();
            }
            if (!TextUtils.isEmpty(activityTitle) || (packageManager = activity.getPackageManager()) == null) {
                return activityTitle;
            }
            ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
            if (!TextUtils.isEmpty(activityInfo.loadLabel(packageManager))) {
                return activityInfo.loadLabel(packageManager).toString();
            }
            return activityTitle;
        } catch (Exception e) {
            return null;
        }
    }

    private static String operatorToCarrier(Context context, String operator, String alternativeName) {
        try {
            if (TextUtils.isEmpty(operator)) {
                return alternativeName;
            }
            Map<String, String> map = sCarrierMap;
            if (map.containsKey(operator)) {
                return map.get(operator);
            }
            String carrierJson = getJsonFromAssets("sa_mcc_mnc_mini.json", context);
            if (TextUtils.isEmpty(carrierJson)) {
                map.put(operator, alternativeName);
                return alternativeName;
            }
            String carrier = getCarrierFromJsonObject(new JSONObject(carrierJson), operator);
            if (!TextUtils.isEmpty(carrier)) {
                map.put(operator, carrier);
                return carrier;
            }
            return alternativeName;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private static String getCarrierFromJsonObject(JSONObject jsonObject, String mccMnc) {
        if (jsonObject == null || TextUtils.isEmpty(mccMnc)) {
            return null;
        }
        return jsonObject.optString(mccMnc);
    }

    static String getToolbarTitle(Activity activity) {
        Object supportActionBar;
        CharSequence charSequence;
        try {
            if (!"com.tencent.connect.common.AssistActivity".equals(SnapCache.getInstance().getCanonicalName(activity.getClass()))) {
                ActionBar actionBar = null;
                int i = Build.VERSION.SDK_INT;
                if (i >= 11) {
                    actionBar = activity.getActionBar();
                }
                if (actionBar == null) {
                    try {
                        Class<?> appCompatActivityClass = compatActivity();
                        if (!(appCompatActivityClass == null || !appCompatActivityClass.isInstance(activity) || (supportActionBar = activity.getClass().getMethod("getSupportActionBar", new Class[0]).invoke(activity, new Object[0])) == null || (charSequence = (CharSequence) supportActionBar.getClass().getMethod("getTitle", new Class[0]).invoke(supportActionBar, new Object[0])) == null)) {
                            return charSequence.toString();
                        }
                    } catch (Exception e) {
                    }
                } else if (i >= 11 && !TextUtils.isEmpty(actionBar.getTitle())) {
                    return actionBar.getTitle().toString();
                }
                return null;
            } else if (!TextUtils.isEmpty(activity.getTitle())) {
                return activity.getTitle().toString();
            } else {
                return null;
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }

    private static Class<?> compatActivity() {
        Class<?> appCompatActivityClass = null;
        try {
            appCompatActivityClass = ReflectUtil.getClassByName("androidx.appcompat.app.AppCompatActivity");
        } catch (Exception e) {
        }
        if (appCompatActivityClass != null) {
            return appCompatActivityClass;
        }
        try {
            return ReflectUtil.getClassByName("androidx.appcompat.app.AppCompatActivity");
        } catch (Exception e2) {
            return appCompatActivityClass;
        }
    }

    public static void getScreenNameAndTitleFromActivity(JSONObject properties, Activity activity) {
        PackageManager packageManager;
        if (activity != null && properties != null) {
            try {
                properties.put(AopConstants.SCREEN_NAME, (Object) activity.getClass().getCanonicalName());
                String activityTitle = null;
                if (!TextUtils.isEmpty(activity.getTitle())) {
                    activityTitle = activity.getTitle().toString();
                }
                if (Build.VERSION.SDK_INT >= 11) {
                    String toolbarTitle = getToolbarTitle(activity);
                    if (!TextUtils.isEmpty(toolbarTitle)) {
                        activityTitle = toolbarTitle;
                    }
                }
                if (TextUtils.isEmpty(activityTitle) && (packageManager = activity.getPackageManager()) != null) {
                    activityTitle = packageManager.getActivityInfo(activity.getComponentName(), 0).loadLabel(packageManager).toString();
                }
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(AopConstants.TITLE, (Object) activityTitle);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void mergeJSONObject(JSONObject source, JSONObject dest) {
        try {
            Iterator<String> superPropertiesIterator = source.keys();
            while (superPropertiesIterator.hasNext()) {
                String key = superPropertiesIterator.next();
                Object value = source.get(key);
                if (!(value instanceof Date) || "$time".equals(key)) {
                    dest.put(key, value);
                } else {
                    dest.put(key, (Object) TimeUtils.formatDate((Date) value, Locale.CHINA));
                }
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public static JSONObject mergeSuperJSONObject(JSONObject source, JSONObject dest) {
        if (source == null) {
            source = new JSONObject();
        }
        if (dest == null) {
            return source;
        }
        try {
            Iterator<String> sourceIterator = source.keys();
            while (sourceIterator.hasNext()) {
                String key = sourceIterator.next();
                Iterator<String> destIterator = dest.keys();
                while (destIterator.hasNext()) {
                    String destKey = destIterator.next();
                    if (!TextUtils.isEmpty(key) && key.equalsIgnoreCase(destKey)) {
                        destIterator.remove();
                    }
                }
            }
            mergeJSONObject(source, dest);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
        return dest;
    }

    public static boolean checkHasPermission(Context context, String permission) {
        try {
            if (mPermissionGrantedSet.contains(permission)) {
                return true;
            }
            Class<?> contextCompat = null;
            try {
                contextCompat = Class.forName("androidx.core.content.ContextCompat");
            } catch (Exception e) {
            }
            if (contextCompat == null) {
                try {
                    contextCompat = Class.forName("androidx.core.content.ContextCompat");
                } catch (Exception e2) {
                }
            }
            if (contextCompat == null) {
                mPermissionGrantedSet.add(permission);
                return true;
            }
            if (((Integer) contextCompat.getMethod("checkSelfPermission", new Class[]{Context.class, String.class}).invoke((Object) null, new Object[]{context, permission})).intValue() != 0) {
                SALog.i(TAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"" + permission + "\" />");
                return false;
            }
            mPermissionGrantedSet.add(permission);
            return true;
        } catch (Exception e3) {
            SALog.i(TAG, e3.toString());
            return true;
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context) {
        TelephonyManager tm;
        String imei = "";
        try {
            Map<String, String> map = deviceUniqueIdentifiersMap;
            if (map.containsKey("IMEI")) {
                imei = map.get("IMEI");
            }
            if (TextUtils.isEmpty(imei) && hasReadPhoneStatePermission(context) && (tm = (TelephonyManager) context.getSystemService("phone")) != null) {
                int i = Build.VERSION.SDK_INT;
                if (i > 28) {
                    if (tm.hasCarrierPrivileges()) {
                        imei = tm.getImei();
                    }
                } else if (i >= 26) {
                    imei = tm.getImei();
                } else {
                    imei = tm.getDeviceId();
                }
                map.put("IMEI", imei);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return imei;
    }

    public static String getIMEIOld(Context context) {
        return getDeviceID(context, -1);
    }

    public static String getSlot(Context context, int number) {
        return getDeviceID(context, number);
    }

    public static String getMEID(Context context) {
        return getDeviceID(context, -2);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getDeviceID(Context context, int number) {
        TelephonyManager tm;
        String deviceId = "";
        try {
            String deviceIDKey = "deviceID" + number;
            Map<String, String> map = deviceUniqueIdentifiersMap;
            if (map.containsKey(deviceIDKey)) {
                deviceId = map.get(deviceIDKey);
            }
            if (TextUtils.isEmpty(deviceId) && hasReadPhoneStatePermission(context) && (tm = (TelephonyManager) context.getSystemService("phone")) != null) {
                if (number == -1) {
                    deviceId = tm.getDeviceId();
                } else if (number == -2 && Build.VERSION.SDK_INT >= 26) {
                    deviceId = tm.getMeid();
                } else if (Build.VERSION.SDK_INT >= 23) {
                    deviceId = tm.getDeviceId(number);
                }
                map.put(deviceIDKey, deviceId);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return deviceId;
    }

    private static boolean hasReadPhoneStatePermission(Context context) {
        if (Build.VERSION.SDK_INT > 28) {
            if (checkHasPermission(context, "android.permission.READ_PRECISE_PHONE_STATE")) {
                return true;
            }
            SALog.i(TAG, "Don't have permission android.permission.READ_PRECISE_PHONE_STATE,getDeviceID failed");
            return false;
        } else if (checkHasPermission(context, "android.permission.READ_PHONE_STATE")) {
            return true;
        } else {
            SALog.i(TAG, "Don't have permission android.permission.READ_PHONE_STATE,getDeviceID failed");
            return false;
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        try {
            if (TextUtils.isEmpty(androidID)) {
                String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                androidID = string;
                if (!isValidAndroidId(string)) {
                    androidID = "";
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return androidID;
    }

    private static String getMacAddressByInterface() {
        try {
            for (NetworkInterface nif : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if ("wlan0".equalsIgnoreCase(nif.getName())) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }
                    StringBuilder res1 = new StringBuilder();
                    int length = macBytes.length;
                    for (int i = 0; i < length; i++) {
                        res1.append(String.format("%02X:", new Object[]{Byte.valueOf(macBytes[i])}));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint({"MissingPermission"})
    public static String getMacAddress(Context context) {
        WifiManager wifiMan;
        WifiInfo wifiInfo;
        String macAddress = "";
        try {
            Map<String, String> map = deviceUniqueIdentifiersMap;
            if (map.containsKey("macAddress")) {
                macAddress = map.get("macAddress");
            }
            if (!(!TextUtils.isEmpty(macAddress) || !checkHasPermission(context, "android.permission.ACCESS_WIFI_STATE") || (wifiMan = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null || (wifiInfo = wifiMan.getConnectionInfo()) == null || wifiInfo.getMacAddress() == null)) {
                macAddress = wifiInfo.getMacAddress();
                if (marshmallowMacAddress.equals(macAddress) && (macAddress = getMacAddressByInterface()) == null) {
                    macAddress = marshmallowMacAddress;
                }
                map.put("macAddress", macAddress);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return macAddress;
    }

    public static boolean isValidAndroidId(String androidId) {
        if (TextUtils.isEmpty(androidId)) {
            return false;
        }
        return !mInvalidAndroidId.contains(androidId.toLowerCase(Locale.getDefault()));
    }

    public static boolean checkVersionIsNew(Context context, String currVersion) {
        try {
            String localVersion = SAStoreManager.getInstance().getString(SHARED_PREF_APP_VERSION, "");
            if (TextUtils.isEmpty(currVersion) || currVersion.equals(localVersion)) {
                return false;
            }
            SAStoreManager.getInstance().setString(SHARED_PREF_APP_VERSION, currVersion);
            return true;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return true;
        }
    }

    public static boolean isDoubleClick(View view) {
        if (view == null) {
            return false;
        }
        try {
            long currentOnClickTimestamp = SystemClock.elapsedRealtime();
            int i = R.id.sensors_analytics_tag_view_onclick_timestamp;
            String tag = (String) view.getTag(i);
            if (!TextUtils.isEmpty(tag) && currentOnClickTimestamp - Long.parseLong(tag) < 500) {
                return true;
            }
            view.setTag(i, String.valueOf(currentOnClickTimestamp));
            return false;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static String getScreenUrl(Object object) {
        if (object == null) {
            return null;
        }
        String screenUrl = null;
        try {
            if (object instanceof ScreenAutoTracker) {
                screenUrl = ((ScreenAutoTracker) object).getScreenUrl();
            } else {
                SensorsDataAutoTrackAppViewScreenUrl autoTrackAppViewScreenUrl = (SensorsDataAutoTrackAppViewScreenUrl) object.getClass().getAnnotation(SensorsDataAutoTrackAppViewScreenUrl.class);
                if (autoTrackAppViewScreenUrl != null) {
                    screenUrl = autoTrackAppViewScreenUrl.url();
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (screenUrl == null) {
            return object.getClass().getCanonicalName();
        }
        return screenUrl;
    }

    public static void handleSchemeUrl(Activity activity, Intent intent) {
        SASchemeHelper.handleSchemeUrl(activity, intent);
    }

    public static void initUniAppStatus() {
        try {
            Class.forName("io.dcloud.application.DCloudApplication");
            isUniApp = true;
        } catch (ClassNotFoundException e) {
        }
    }

    public static boolean isUniApp() {
        return isUniApp;
    }
}
