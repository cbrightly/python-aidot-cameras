package com.google.maps.android.data.geojson;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Point;

public class GeoJsonPoint extends Point {
    private final Double mAltitude;

    public GeoJsonPoint(LatLng coordinates) {
        this(coordinates, (Double) null);
    }

    public GeoJsonPoint(LatLng coordinates, Double altitude) {
        super(coordinates);
        this.mAltitude = altitude;
    }

    public String getType() {
        return getGeometryType();
    }

    public LatLng getCoordinates() {
        return getGeometryObject();
    }

    public Double getAltitude() {
        return this.mAltitude;
    }
}
