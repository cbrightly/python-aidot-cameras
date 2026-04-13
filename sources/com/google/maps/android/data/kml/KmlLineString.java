package com.google.maps.android.data.kml;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.LineString;
import java.util.ArrayList;

public class KmlLineString extends LineString {
    private final ArrayList<Double> mAltitudes;

    public KmlLineString(ArrayList<LatLng> coordinates) {
        this(coordinates, (ArrayList<Double>) null);
    }

    public KmlLineString(ArrayList<LatLng> coordinates, ArrayList<Double> altitudes) {
        super(coordinates);
        this.mAltitudes = altitudes;
    }

    public ArrayList<Double> getAltitudes() {
        return this.mAltitudes;
    }

    public ArrayList<LatLng> getGeometryObject() {
        return new ArrayList<>(super.getGeometryObject());
    }
}
