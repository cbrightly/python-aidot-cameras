package com.google.maps.android.data.kml;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.DataPolygon;
import java.util.ArrayList;
import java.util.List;

public class KmlPolygon implements DataPolygon<ArrayList<ArrayList<LatLng>>> {
    public static final String GEOMETRY_TYPE = "Polygon";
    private final List<List<LatLng>> mInnerBoundaryCoordinates;
    private final List<LatLng> mOuterBoundaryCoordinates;

    public KmlPolygon(List<LatLng> outerBoundaryCoordinates, List<List<LatLng>> innerBoundaryCoordinates) {
        if (outerBoundaryCoordinates != null) {
            this.mOuterBoundaryCoordinates = outerBoundaryCoordinates;
            this.mInnerBoundaryCoordinates = innerBoundaryCoordinates;
            return;
        }
        throw new IllegalArgumentException("Outer boundary coordinates cannot be null");
    }

    public String getGeometryType() {
        return GEOMETRY_TYPE;
    }

    public List<List<LatLng>> getGeometryObject() {
        List<List<LatLng>> coordinates = new ArrayList<>();
        coordinates.add(this.mOuterBoundaryCoordinates);
        List<List<LatLng>> list = this.mInnerBoundaryCoordinates;
        if (list != null) {
            coordinates.addAll(list);
        }
        return coordinates;
    }

    public List<LatLng> getOuterBoundaryCoordinates() {
        return this.mOuterBoundaryCoordinates;
    }

    public List<List<LatLng>> getInnerBoundaryCoordinates() {
        return this.mInnerBoundaryCoordinates;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(GEOMETRY_TYPE).append("{");
        sb.append("\n outer coordinates=");
        sb.append(this.mOuterBoundaryCoordinates);
        sb.append(",\n inner coordinates=");
        sb.append(this.mInnerBoundaryCoordinates);
        sb.append("\n}\n");
        return sb.toString();
    }
}
