package com.sensorsdata.analytics.android.sdk;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorsDataGPSLocation {
    private String coordinate;
    private long latitude;
    private long longitude;

    public long getLatitude() {
        return this.latitude;
    }

    public void setLatitude(long latitude2) {
        this.latitude = latitude2;
    }

    public long getLongitude() {
        return this.longitude;
    }

    public void setLongitude(long longitude2) {
        this.longitude = longitude2;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(String coordinate2) {
        this.coordinate = coordinate2;
    }

    public void toJSON(JSONObject jsonObject) {
        try {
            jsonObject.put("$latitude", this.latitude);
            jsonObject.put("$longitude", this.longitude);
            jsonObject.put("$geo_coordinate_system", (Object) this.coordinate);
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public final class CoordinateType {
        public static final String BD09 = "BD09";
        public static final String GCJ02 = "GCJ02";
        public static final String WGS84 = "WGS84";

        public CoordinateType() {
        }
    }
}
