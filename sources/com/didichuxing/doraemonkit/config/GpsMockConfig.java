package com.didichuxing.doraemonkit.config;

import com.didichuxing.doraemonkit.constant.CachesKey;
import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.model.LatLng;
import com.didichuxing.doraemonkit.util.CacheUtils;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;
import java.io.Serializable;

public class GpsMockConfig {
    public static boolean isGPSMockOpen() {
        return SharedPrefsUtil.getBoolean(SharedPrefsKey.GPS_MOCK_OPEN, false);
    }

    public static void setGPSMockOpen(boolean open) {
        SharedPrefsUtil.putBoolean(SharedPrefsKey.GPS_MOCK_OPEN, open);
    }

    public static LatLng getMockLocation() {
        return (LatLng) CacheUtils.readObject(CachesKey.MOCK_LOCATION);
    }

    public static void saveMockLocation(LatLng latLng) {
        CacheUtils.saveObject(CachesKey.MOCK_LOCATION, (Serializable) latLng);
    }
}
