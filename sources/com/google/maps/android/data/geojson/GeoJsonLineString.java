package com.google.maps.android.data.geojson;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.LineString;
import java.util.List;

public class GeoJsonLineString extends LineString {
    private final List<Double> mAltitudes;

    public GeoJsonLineString(List<LatLng> coordinates) {
        this(coordinates, (List<Double>) null);
    }

    public GeoJsonLineString(List<LatLng> coordinates, List<Double> altitudes) {
        super(coordinates);
        this.mAltitudes = altitudes;
    }

    public String getType() {
        return getGeometryType();
    }

    public List<LatLng> getCoordinates() {
        return getGeometryObject();
    }

    public List<Double> getAltitudes() {
        return this.mAltitudes;
    }
}
