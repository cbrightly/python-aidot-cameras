package com.google.maps.android.data.geojson;

import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.MultiGeometry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeoJsonMultiLineString extends MultiGeometry {
    public GeoJsonMultiLineString(List<GeoJsonLineString> geoJsonLineStrings) {
        super(geoJsonLineStrings);
        setGeometryType("MultiLineString");
    }

    public String getType() {
        return getGeometryType();
    }

    public List<GeoJsonLineString> getLineStrings() {
        List<Geometry> geometryList = getGeometryObject();
        ArrayList<GeoJsonLineString> geoJsonLineStrings = new ArrayList<>();
        Iterator<Geometry> it = geometryList.iterator();
        while (it.hasNext()) {
            geoJsonLineStrings.add((GeoJsonLineString) it.next());
        }
        return geoJsonLineStrings;
    }
}
