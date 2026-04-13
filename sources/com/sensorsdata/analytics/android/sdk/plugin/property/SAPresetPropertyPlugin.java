package com.sensorsdata.analytics.android.sdk.plugin.property;

import android.content.Context;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import com.sensorsdata.analytics.android.sdk.util.DeviceUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.util.Map;
import java.util.Set;

public final class SAPresetPropertyPlugin extends SAPropertyPlugin {
    private final Context mContext;
    private final boolean mDisableAndroidId;
    private final boolean mDisableTrackDeviceId;

    public SAPresetPropertyPlugin(Context context, boolean disableTrackDeviceId, boolean disableAndroidId) {
        this.mContext = context;
        this.mDisableTrackDeviceId = disableTrackDeviceId;
        this.mDisableAndroidId = disableAndroidId;
    }

    public void appendProperties(Map<String, Object> properties) {
        if (AbstractSensorsDataAPI.getConfigOptions().isDataCollectEnable()) {
            String osVersion = DeviceUtils.getHarmonyOSVersion();
            if (!TextUtils.isEmpty(osVersion)) {
                properties.put("$os", "HarmonyOS");
                properties.put("$os_version", osVersion);
            } else {
                properties.put("$os", "Android");
                properties.put("$os_version", DeviceUtils.getOS());
            }
            properties.put("$lib", "Android");
            properties.put("$lib_version", SensorsDataAPI.sharedInstance().getSDKVersion());
            properties.put("$manufacturer", DeviceUtils.getManufacturer());
            properties.put("$model", DeviceUtils.getModel());
            properties.put("$brand", DeviceUtils.getBrand());
            properties.put("$app_version", AppInfoUtils.getAppVersionName(this.mContext));
            int[] size = DeviceUtils.getDeviceSize(this.mContext);
            properties.put("$screen_width", Integer.valueOf(size[0]));
            properties.put("$screen_height", Integer.valueOf(size[1]));
            String carrier = SensorsDataUtils.getCarrier(this.mContext);
            if (!TextUtils.isEmpty(carrier)) {
                properties.put("$carrier", carrier);
            }
            Integer zone_offset = TimeUtils.getZoneOffset();
            if (zone_offset != null) {
                properties.put("$timezone_offset", zone_offset);
            }
            properties.put("$app_id", AppInfoUtils.getProcessName(this.mContext));
            properties.put("$app_name", AppInfoUtils.getAppName(this.mContext));
            String mAndroidId = SensorsDataUtils.getAndroidID(this.mContext);
            if (!this.mDisableTrackDeviceId && !TextUtils.isEmpty(mAndroidId)) {
                if (this.mDisableAndroidId) {
                    properties.put("$anonymization_id", Base64Coder.encodeString(mAndroidId));
                } else {
                    properties.put("$device_id", mAndroidId);
                }
            }
        }
    }

    public void appendDynamicProperties(Map<String, Object> map) {
    }

    public void eventNameFilter(Set<String> set) {
    }

    public void eventTypeFilter(Set<EventType> eventTypeFilter) {
        eventTypeFilter.add(EventType.TRACK);
        eventTypeFilter.add(EventType.TRACK_SIGNUP);
        eventTypeFilter.add(EventType.TRACK_ID_BIND);
        eventTypeFilter.add(EventType.TRACK_ID_UNBIND);
    }

    public void propertyKeyFilter(Set<String> set) {
    }

    public SAPropertyPluginPriority priority() {
        return SAPropertyPluginPriority.LOW;
    }
}
