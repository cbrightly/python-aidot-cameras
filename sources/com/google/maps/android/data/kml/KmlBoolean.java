package com.google.maps.android.data.kml;

public class KmlBoolean {
    public static boolean parseBoolean(String text) {
        return "1".equals(text) || "true".equals(text);
    }
}
