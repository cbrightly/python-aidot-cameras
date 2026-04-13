package com.sensorsdata.analytics.android.sdk.advert.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.encrypt.AESSecretManager;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ChannelUtils {
    private static final Map<String, String> LATEST_UTM_MAP = new HashMap<String, String>() {
        {
            put(ChannelUtils.UTM_SOURCE_KEY, "$latest_utm_source");
            put(ChannelUtils.UTM_MEDIUM_KEY, "$latest_utm_medium");
            put(ChannelUtils.UTM_TERM_KEY, "$latest_utm_term");
            put(ChannelUtils.UTM_CONTENT_KEY, "$latest_utm_content");
            put(ChannelUtils.UTM_CAMPAIGN_KEY, "$latest_utm_campaign");
        }
    };
    private static final String SHARED_PREF_CORRECT_TRACK_INSTALLATION = "sensorsdata.correct.track.installation";
    private static final String SHARED_PREF_UTM_FILE = "sensorsdata.utm";
    private static final String UTM_CAMPAIGN_KEY = "SENSORS_ANALYTICS_UTM_CAMPAIGN";
    private static final String UTM_CONTENT_KEY = "SENSORS_ANALYTICS_UTM_CONTENT";
    private static final HashMap<String, String> UTM_LINK_MAP = new HashMap<String, String>() {
        {
            put(ChannelUtils.UTM_SOURCE_KEY, "utm_source");
            put(ChannelUtils.UTM_MEDIUM_KEY, "utm_medium");
            put(ChannelUtils.UTM_TERM_KEY, "utm_term");
            put(ChannelUtils.UTM_CONTENT_KEY, "utm_content");
            put(ChannelUtils.UTM_CAMPAIGN_KEY, "utm_campaign");
        }
    };
    private static final HashMap<String, String> UTM_MAP = new HashMap<String, String>() {
        {
            put(ChannelUtils.UTM_SOURCE_KEY, "$utm_source");
            put(ChannelUtils.UTM_MEDIUM_KEY, "$utm_medium");
            put(ChannelUtils.UTM_TERM_KEY, "$utm_term");
            put(ChannelUtils.UTM_CONTENT_KEY, "$utm_content");
            put(ChannelUtils.UTM_CAMPAIGN_KEY, "$utm_campaign");
        }
    };
    private static final String UTM_MEDIUM_KEY = "SENSORS_ANALYTICS_UTM_MEDIUM";
    private static final String UTM_SOURCE_KEY = "SENSORS_ANALYTICS_UTM_SOURCE";
    private static final String UTM_TERM_KEY = "SENSORS_ANALYTICS_UTM_TERM";
    private static final List<String> mDeepLinkBlackList = new ArrayList() {
        {
            add("io.dcloud.PandoraEntryActivity");
        }
    };
    private static HashSet<String> sChannelSourceKeySet = new HashSet<>();
    private static Map<String, String> sLatestUtmProperties = new HashMap();
    private static Map<String, String> sUtmProperties = new HashMap();

    public static JSONObject getUtmProperties() {
        if (sUtmProperties.size() > 0) {
            return new JSONObject((Map<?, ?>) sUtmProperties);
        }
        return new JSONObject();
    }

    public static JSONObject getLatestUtmProperties() {
        if (sLatestUtmProperties.size() > 0) {
            return new JSONObject((Map<?, ?>) sLatestUtmProperties);
        }
        return new JSONObject();
    }

    public static void mergeUtmToEndData(JSONObject source, JSONObject dest) {
        if (source != null && dest != null) {
            try {
                Iterator<String> keys = source.keys();
                while (keys.hasNext()) {
                    String latestKey = keys.next();
                    if (latestKey.startsWith("$latest") || latestKey.startsWith("_latest")) {
                        dest.put(latestKey, (Object) source.getString(latestKey));
                    }
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public static boolean hasUtmProperties(JSONObject properties) {
        if (properties == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : UTM_MAP.entrySet()) {
            if (entry != null && properties.has(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLinkUtmProperties(Set<String> parameterNames) {
        if (parameterNames == null || parameterNames.isEmpty()) {
            return false;
        }
        for (Map.Entry<String, String> entry : UTM_LINK_MAP.entrySet()) {
            if (entry != null && parameterNames.contains(entry.getValue())) {
                return true;
            }
        }
        Iterator<String> it = sChannelSourceKeySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (!TextUtils.isEmpty(key) && sChannelSourceKeySet.contains(key)) {
                return true;
            }
        }
        return false;
    }

    public static String getDeviceInfo(Context mContext, String androidId, String oaid) {
        return String.format("android_id=%s##imei=%s##imei_old=%s##imei_slot1=%s##imei_slot2=%s##imei_meid=%s##mac=%s##oaid=%s", new Object[]{androidId, SensorsDataUtils.getIMEI(mContext), SensorsDataUtils.getIMEIOld(mContext), SensorsDataUtils.getSlot(mContext, 0), SensorsDataUtils.getSlot(mContext, 1), SensorsDataUtils.getMEID(mContext), SensorsDataUtils.getMacAddress(mContext), oaid});
    }

    public static void mergeUtmByMetaData(Context context, JSONObject properties) {
        if (properties != null) {
            for (Map.Entry<String, String> entry : UTM_MAP.entrySet()) {
                if (entry != null) {
                    String utmValue = getApplicationMetaData(context, entry.getKey());
                    if (!TextUtils.isEmpty(utmValue)) {
                        properties.put(entry.getValue(), (Object) utmValue);
                    }
                }
            }
        }
    }

    public static void setSourceChannelKeys(String... sourceChannelKeys) {
        sChannelSourceKeySet.clear();
        if (sourceChannelKeys != null && sourceChannelKeys.length > 0) {
            for (String key : sourceChannelKeys) {
                if (!TextUtils.isEmpty(key)) {
                    sChannelSourceKeySet.add(key);
                }
            }
        }
    }

    public static void parseParams(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : UTM_LINK_MAP.entrySet()) {
                String value = params.get(entry.getValue());
                if (!TextUtils.isEmpty(value)) {
                    sUtmProperties.put(UTM_MAP.get(entry.getKey()), value);
                    sLatestUtmProperties.put(LATEST_UTM_MAP.get(entry.getKey()), value);
                }
            }
            Iterator<String> it = sChannelSourceKeySet.iterator();
            while (it.hasNext()) {
                String sourceKey = it.next();
                try {
                    if (SADataHelper.assertPropertyKey(sourceKey)) {
                        String value2 = params.get(sourceKey);
                        if (!TextUtils.isEmpty(value2)) {
                            sUtmProperties.put(sourceKey, value2);
                            Map<String, String> map = sLatestUtmProperties;
                            map.put("_latest_" + sourceKey, value2);
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void loadUtmByLocal(Context context) {
        try {
            sLatestUtmProperties.clear();
            String channelJson = SAStoreManager.getInstance().getString(SHARED_PREF_UTM_FILE, "");
            if (!TextUtils.isEmpty(channelJson)) {
                JSONObject jsonObject = new JSONObject(channelJson);
                for (Map.Entry<String, String> entry : LATEST_UTM_MAP.entrySet()) {
                    String utmKey = entry.getValue();
                    if (jsonObject.has(utmKey)) {
                        sLatestUtmProperties.put(utmKey, jsonObject.optString(utmKey));
                    }
                }
                Iterator<String> it = sChannelSourceKeySet.iterator();
                while (it.hasNext()) {
                    String latestSourceKey = "_latest_" + it.next();
                    if (jsonObject.has(latestSourceKey)) {
                        sLatestUtmProperties.put(latestSourceKey, jsonObject.optString(latestSourceKey));
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void clearLocalUtm(Context context) {
        try {
            SAStoreManager.getInstance().remove(SHARED_PREF_UTM_FILE);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void clearMemoryUtm() {
        sUtmProperties.clear();
        sLatestUtmProperties.clear();
    }

    public static void clearUtm(Context context) {
        clearMemoryUtm();
        clearLocalUtm(context);
    }

    public static void removeDeepLinkInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String latestKey = keys.next();
                    if (latestKey.startsWith("$latest") || latestKey.startsWith("_latest")) {
                        keys.remove();
                    }
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public static void saveDeepLinkInfo(Context context) {
        try {
            if (sLatestUtmProperties.size() > 0) {
                SAStoreManager.getInstance().setString(SHARED_PREF_UTM_FILE, sLatestUtmProperties.toString());
            } else {
                clearLocalUtm(context);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private static String getApplicationMetaData(Context mContext, String metaKey) {
        try {
            ApplicationInfo appInfo = mContext.getApplicationContext().getPackageManager().getApplicationInfo(mContext.getApplicationContext().getPackageName(), 128);
            String value = appInfo.metaData.getString(metaKey);
            int iValue = -1;
            if (value == null) {
                iValue = appInfo.metaData.getInt(metaKey, -1);
            }
            if (iValue != -1) {
                return String.valueOf(iValue);
            }
            return value;
        } catch (Exception e) {
            return "";
        }
    }

    public static JSONObject checkOrSetChannelCallbackEvent(boolean isAutoAddChannelCallbackEvent, String eventName, JSONObject properties, Context context) {
        if (isAutoAddChannelCallbackEvent) {
            if (properties == null) {
                properties = new JSONObject();
            }
            try {
                properties.put("$is_channel_callback_event", isFirstChannelEvent(eventName));
                if (context != null && !hasUtmProperties(properties)) {
                    mergeUtmByMetaData(context, properties);
                }
                properties.put("$channel_device_info", (Object) "1");
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
        return properties;
    }

    public static boolean isFirstChannelEvent(String eventName) {
        String secretEventName = AbstractSensorsDataAPI.getConfigOptions().getStorePlugins() == null || AbstractSensorsDataAPI.getConfigOptions().getStorePlugins().isEmpty() ? eventName : AESSecretManager.getInstance().encryptAES(eventName);
        boolean isFirst = DbAdapter.getInstance().isFirstChannelEvent(new String[]{secretEventName, eventName});
        if (isFirst) {
            DbAdapter.getInstance().addChannelEvent(secretEventName);
        }
        return isFirst;
    }

    public static boolean hasUtmByMetaData(Context context) {
        if (context == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : UTM_MAP.entrySet()) {
            if (entry != null && !TextUtils.isEmpty(getApplicationMetaData(context, entry.getKey()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGetDeviceInfo(Context context, String androidId, String oaid) {
        try {
            return !TextUtils.isEmpty(androidId) || !TextUtils.isEmpty(oaid) || !TextUtils.isEmpty(SensorsDataUtils.getIMEI(context)) || !TextUtils.isEmpty(SensorsDataUtils.getIMEIOld(context)) || !TextUtils.isEmpty(SensorsDataUtils.getSlot(context, 0)) || !TextUtils.isEmpty(SensorsDataUtils.getSlot(context, 1)) || !TextUtils.isEmpty(SensorsDataUtils.getMEID(context));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static boolean isTrackInstallation(Context context) {
        try {
            return SAStoreManager.getInstance().isExists(SHARED_PREF_CORRECT_TRACK_INSTALLATION);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static boolean isCorrectTrackInstallation(Context context) {
        try {
            return SAStoreManager.getInstance().getBool(SHARED_PREF_CORRECT_TRACK_INSTALLATION, false);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static void saveCorrectTrackInstallation(Context context, boolean isCorrectTrackInstallation) {
        try {
            SAStoreManager.getInstance().setBool(SHARED_PREF_CORRECT_TRACK_INSTALLATION, isCorrectTrackInstallation);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static boolean checkDeviceInfo(Context context, String deviceInfo) {
        if (context == null || TextUtils.isEmpty(deviceInfo)) {
            return false;
        }
        String[] codes = deviceInfo.split("##");
        Map<String, String> deviceMaps = new HashMap<>();
        if (codes.length == 0) {
            return false;
        }
        for (String code : codes) {
            String[] keyValue = code.trim().split("=");
            if (keyValue.length == 2) {
                deviceMaps.put(keyValue[0], keyValue[1]);
            }
        }
        if (deviceMaps.isEmpty()) {
            return false;
        }
        if ((!deviceMaps.containsKey("oaid") || !TextUtils.equals(deviceMaps.get("oaid"), OaidHelper.getOAID(context))) && ((!deviceMaps.containsKey("imei") || !TextUtils.equals(deviceMaps.get("imei"), SensorsDataUtils.getIMEI(context))) && ((!deviceMaps.containsKey("imei_old") || !TextUtils.equals(deviceMaps.get("imei_old"), SensorsDataUtils.getIMEIOld(context))) && ((!deviceMaps.containsKey("imei_slot1") || !TextUtils.equals(deviceMaps.get("imei_slot1"), SensorsDataUtils.getSlot(context, 0))) && ((!deviceMaps.containsKey("imei_slot2") || !TextUtils.equals(deviceMaps.get("imei_slot2"), SensorsDataUtils.getSlot(context, 1))) && ((!deviceMaps.containsKey("imei_meid") || !TextUtils.equals(deviceMaps.get("imei_meid"), SensorsDataUtils.getMEID(context))) && ((!deviceMaps.containsKey("android_id") || !TextUtils.equals(deviceMaps.get("android_id"), SensorsDataUtils.getAndroidID(context))) && (!deviceMaps.containsKey("mac") || !TextUtils.equals(deviceMaps.get("mac"), SensorsDataUtils.getMacAddress(context)))))))))) {
            return false;
        }
        return true;
    }

    public static boolean isDeepLinkBlackList(Activity activity) {
        if (activity == null) {
            return false;
        }
        for (String activityName : mDeepLinkBlackList) {
            try {
                if (Class.forName(activityName).isAssignableFrom(activity.getClass())) {
                    return true;
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
        return false;
    }
}
