package com.sensorsdata.analytics.android.sdk.autotrack.utils;

public class AutoTrackUtils {
    private static String sLastScreenUrl = "";

    public static String getLastScreenUrl() {
        return sLastScreenUrl;
    }

    public static void setLastScreenUrl(String lastScreenUrl) {
        sLastScreenUrl = lastScreenUrl;
    }
}
