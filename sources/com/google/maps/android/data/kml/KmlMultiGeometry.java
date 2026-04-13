package com.google.maps.android.data.kml;

import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.MultiGeometry;
import java.util.ArrayList;

public class KmlMultiGeometry extends MultiGeometry {
    public KmlMultiGeometry(ArrayList<Geometry> geometries) {
        super(geometries);
    }

    public ArrayList<Geometry> getGeometryObject() {
        return new ArrayList<>(super.getGeometryObject());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getGeometryType()).append("{");
        sb.append("\n geometries=");
        sb.append(getGeometryObject());
        sb.append("\n}\n");
        return sb.toString();
    }
}
