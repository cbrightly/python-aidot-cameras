package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.plugin.property.SAPresetPropertyPlugin;
import com.sensorsdata.analytics.android.sdk.plugin.property.SensorsDataPropertyPluginManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class SAContextManager {
    private boolean isAppStartSuccess;
    private String mAndroidId;
    private final Context mContext;
    private Map<String, Object> mDeviceInfo;
    private List<SAEventListener> mEventListenerList;

    public SAContextManager(Context context) {
        this.mContext = context;
    }

    public List<SAEventListener> getEventListenerList() {
        return this.mEventListenerList;
    }

    public void addEventListener(SAEventListener eventListener) {
        try {
            if (this.mEventListenerList == null) {
                this.mEventListenerList = new ArrayList();
            }
            this.mEventListenerList.add(eventListener);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void removeEventListener(SAEventListener eventListener) {
        try {
            List<SAEventListener> list = this.mEventListenerList;
            if (list != null && list.contains(eventListener)) {
                this.mEventListenerList.remove(eventListener);
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void addKeyIfExist(JSONObject jsonObject, String key) {
        try {
            setupDeviceInfo();
            Map<String, Object> map = this.mDeviceInfo;
            if (map != null && map.containsKey(key)) {
                jsonObject.put(key, this.mDeviceInfo.get(key));
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public String getAndroidId() {
        if (TextUtils.isEmpty(this.mAndroidId) && AbstractSensorsDataAPI.getConfigOptions().isDataCollectEnable()) {
            this.mAndroidId = SensorsDataUtils.getAndroidID(this.mContext);
        }
        return this.mAndroidId;
    }

    public JSONObject getPresetProperties() {
        JSONObject properties;
        JSONObject properties2 = new JSONObject();
        try {
            setupDeviceInfo();
            String str = "$app_name";
            String str2 = "$device_id";
            properties = properties2;
            try {
                properties.put("$app_version", this.mDeviceInfo.get("$app_version"));
                properties.put("$lib", (Object) "Android");
                properties.put("$lib_version", this.mDeviceInfo.get("$lib_version"));
                properties.put("$manufacturer", this.mDeviceInfo.get("$manufacturer"));
                properties.put("$model", this.mDeviceInfo.get("$model"));
                properties.put("$brand", this.mDeviceInfo.get("$brand"));
                properties.put("$os", this.mDeviceInfo.get("$os"));
                properties.put("$os_version", this.mDeviceInfo.get("$os_version"));
                properties.put("$screen_height", this.mDeviceInfo.get("$screen_height"));
                properties.put("$screen_width", this.mDeviceInfo.get("$screen_width"));
                String networkType = NetworkUtils.networkType(this.mContext);
                properties.put("$wifi", LDNetUtil.NETWORKTYPE_WIFI.equals(networkType));
                properties.put("$network_type", (Object) networkType);
                properties.put("$carrier", this.mDeviceInfo.get("$carrier"));
                properties.put("$app_id", this.mDeviceInfo.get("$app_id"));
                properties.put("$timezone_offset", this.mDeviceInfo.get("$timezone_offset"));
                if (this.mDeviceInfo.containsKey("$anonymization_id")) {
                    properties.put("$anonymization_id", this.mDeviceInfo.get("$anonymization_id"));
                }
                String str3 = str2;
                if (this.mDeviceInfo.containsKey(str3)) {
                    properties.put(str3, this.mDeviceInfo.get(str3));
                }
                String str4 = str;
                properties.put(str4, this.mDeviceInfo.get(str4));
            } catch (Exception e) {
                e = e;
                SALog.printStackTrace(e);
                return properties;
            }
        } catch (Exception e2) {
            e = e2;
            properties = properties2;
            SALog.printStackTrace(e);
            return properties;
        }
        return properties;
    }

    private void setupDeviceInfo() {
        Map<String, Object> map = this.mDeviceInfo;
        if (map == null || map.isEmpty()) {
            this.mDeviceInfo = Collections.unmodifiableMap(SensorsDataPropertyPluginManager.getInstance().getPropertiesByPlugin(SAPresetPropertyPlugin.class));
        }
    }

    public boolean isAppStartSuccess() {
        return this.isAppStartSuccess;
    }

    public void setAppStartSuccess(boolean appStartSuccess) {
        this.isAppStartSuccess = appStartSuccess;
    }
}
