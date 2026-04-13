package com.google.maps.android.data.kml;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.HashMap;

public class KmlTrack extends KmlLineString {
    private final HashMap<String, String> mProperties;
    private final ArrayList<Long> mTimestamps;

    public KmlTrack(ArrayList<LatLng> coordinates, ArrayList<Double> altitudes, ArrayList<Long> timestamps, HashMap<String, String> properties) {
        super(coordinates, altitudes);
        this.mTimestamps = timestamps;
        this.mProperties = properties;
    }

    public ArrayList<Long> getTimestamps() {
        return this.mTimestamps;
    }

    public HashMap<String, String> getProperties() {
        return this.mProperties;
    }
}
