package com.google.maps.android.data;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class LineString implements Geometry<List<LatLng>> {
    private static final String GEOMETRY_TYPE = "LineString";
    private final List<LatLng> mCoordinates;

    public LineString(List<LatLng> coordinates) {
        if (coordinates != null) {
            this.mCoordinates = coordinates;
            return;
        }
        throw new IllegalArgumentException("Coordinates cannot be null");
    }

    public String getGeometryType() {
        return GEOMETRY_TYPE;
    }

    public List<LatLng> getGeometryObject() {
        return this.mCoordinates;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(GEOMETRY_TYPE).append("{");
        sb.append("\n coordinates=");
        sb.append(this.mCoordinates);
        sb.append("\n}\n");
        return sb.toString();
    }
}
