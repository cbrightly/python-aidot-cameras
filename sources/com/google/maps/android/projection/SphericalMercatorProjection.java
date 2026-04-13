package com.google.maps.android.projection;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geometry.Point;

public class SphericalMercatorProjection {
    final double mWorldWidth;

    public SphericalMercatorProjection(double worldWidth) {
        this.mWorldWidth = worldWidth;
    }

    public Point toPoint(LatLng latLng) {
        double siny = Math.sin(Math.toRadians(latLng.latitude));
        double d = this.mWorldWidth;
        return new Point(((latLng.longitude / 360.0d) + 0.5d) * d, d * (((Math.log((siny + 1.0d) / (1.0d - siny)) * 0.5d) / -6.283185307179586d) + 0.5d));
    }

    public LatLng toLatLng(Point point) {
        double d = point.x;
        double d2 = this.mWorldWidth;
        return new LatLng(90.0d - Math.toDegrees(Math.atan(Math.exp(((-(0.5d - (point.y / d2))) * 2.0d) * 3.141592653589793d)) * 2.0d), 360.0d * ((d / d2) - 0.5d));
    }
}
