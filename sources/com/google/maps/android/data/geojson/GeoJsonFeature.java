package com.google.maps.android.data.geojson;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class GeoJsonFeature extends Feature implements Observer {
    private final LatLngBounds mBoundingBox;
    private GeoJsonLineStringStyle mLineStringStyle;
    private GeoJsonPointStyle mPointStyle;
    private GeoJsonPolygonStyle mPolygonStyle;

    public GeoJsonFeature(Geometry geometry, String id, HashMap<String, String> properties, LatLngBounds boundingBox) {
        super(geometry, id, properties);
        this.mId = id;
        this.mBoundingBox = boundingBox;
    }

    public String setProperty(String property, String propertyValue) {
        return super.setProperty(property, propertyValue);
    }

    public String removeProperty(String property) {
        return super.removeProperty(property);
    }

    public GeoJsonPointStyle getPointStyle() {
        return this.mPointStyle;
    }

    public void setPointStyle(GeoJsonPointStyle pointStyle) {
        if (pointStyle != null) {
            GeoJsonPointStyle geoJsonPointStyle = this.mPointStyle;
            if (geoJsonPointStyle != null) {
                geoJsonPointStyle.deleteObserver(this);
            }
            this.mPointStyle = pointStyle;
            pointStyle.addObserver(this);
            checkRedrawFeature(this.mPointStyle);
            return;
        }
        throw new IllegalArgumentException("Point style cannot be null");
    }

    public GeoJsonLineStringStyle getLineStringStyle() {
        return this.mLineStringStyle;
    }

    public void setLineStringStyle(GeoJsonLineStringStyle lineStringStyle) {
        if (lineStringStyle != null) {
            GeoJsonLineStringStyle geoJsonLineStringStyle = this.mLineStringStyle;
            if (geoJsonLineStringStyle != null) {
                geoJsonLineStringStyle.deleteObserver(this);
            }
            this.mLineStringStyle = lineStringStyle;
            lineStringStyle.addObserver(this);
            checkRedrawFeature(this.mLineStringStyle);
            return;
        }
        throw new IllegalArgumentException("Line string style cannot be null");
    }

    public GeoJsonPolygonStyle getPolygonStyle() {
        return this.mPolygonStyle;
    }

    public void setPolygonStyle(GeoJsonPolygonStyle polygonStyle) {
        if (polygonStyle != null) {
            GeoJsonPolygonStyle geoJsonPolygonStyle = this.mPolygonStyle;
            if (geoJsonPolygonStyle != null) {
                geoJsonPolygonStyle.deleteObserver(this);
            }
            this.mPolygonStyle = polygonStyle;
            polygonStyle.addObserver(this);
            checkRedrawFeature(this.mPolygonStyle);
            return;
        }
        throw new IllegalArgumentException("Polygon style cannot be null");
    }

    public PolygonOptions getPolygonOptions() {
        return this.mPolygonStyle.toPolygonOptions();
    }

    public MarkerOptions getMarkerOptions() {
        return this.mPointStyle.toMarkerOptions();
    }

    public PolylineOptions getPolylineOptions() {
        return this.mLineStringStyle.toPolylineOptions();
    }

    private void checkRedrawFeature(GeoJsonStyle style) {
        if (hasGeometry() && Arrays.asList(style.getGeometryType()).contains(getGeometry().getGeometryType())) {
            setChanged();
            notifyObservers();
        }
    }

    public void setGeometry(Geometry geometry) {
        super.setGeometry(geometry);
        setChanged();
        notifyObservers();
    }

    public LatLngBounds getBoundingBox() {
        return this.mBoundingBox;
    }

    public String toString() {
        return "Feature{" + "\n bounding box=" + this.mBoundingBox + ",\n geometry=" + getGeometry() + ",\n point style=" + this.mPointStyle + ",\n line string style=" + this.mLineStringStyle + ",\n polygon style=" + this.mPolygonStyle + ",\n id=" + this.mId + ",\n properties=" + getProperties() + "\n}\n";
    }

    public void update(Observable observable, Object data) {
        if (observable instanceof GeoJsonStyle) {
            checkRedrawFeature((GeoJsonStyle) observable);
        }
    }
}
