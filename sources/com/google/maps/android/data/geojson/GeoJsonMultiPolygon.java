package com.google.maps.android.data.geojson;

import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.MultiGeometry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeoJsonMultiPolygon extends MultiGeometry {
    public GeoJsonMultiPolygon(List<GeoJsonPolygon> geoJsonPolygons) {
        super(geoJsonPolygons);
        setGeometryType("MultiPolygon");
    }

    public String getType() {
        return getGeometryType();
    }

    public List<GeoJsonPolygon> getPolygons() {
        List<Geometry> geometryList = getGeometryObject();
        ArrayList<GeoJsonPolygon> geoJsonPolygon = new ArrayList<>();
        Iterator<Geometry> it = geometryList.iterator();
        while (it.hasNext()) {
            geoJsonPolygon.add((GeoJsonPolygon) it.next());
        }
        return geoJsonPolygon;
    }
}
