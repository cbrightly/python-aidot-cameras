package com.google.maps.android.data.geojson;

import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.data.Style;
import com.google.maps.android.data.kml.KmlPolygon;
import java.util.Arrays;
import java.util.List;

public class GeoJsonPolygonStyle extends Style implements GeoJsonStyle {
    private static final String[] GEOMETRY_TYPE = {KmlPolygon.GEOMETRY_TYPE, "MultiPolygon", "GeometryCollection"};

    public GeoJsonPolygonStyle() {
        PolygonOptions polygonOptions = new PolygonOptions();
        this.mPolygonOptions = polygonOptions;
        polygonOptions.clickable(true);
    }

    public String[] getGeometryType() {
        return GEOMETRY_TYPE;
    }

    public int getFillColor() {
        return this.mPolygonOptions.getFillColor();
    }

    public void setFillColor(int fillColor) {
        setPolygonFillColor(fillColor);
        styleChanged();
    }

    public boolean isGeodesic() {
        return this.mPolygonOptions.isGeodesic();
    }

    public void setGeodesic(boolean geodesic) {
        this.mPolygonOptions.geodesic(geodesic);
        styleChanged();
    }

    public int getStrokeColor() {
        return this.mPolygonOptions.getStrokeColor();
    }

    public void setStrokeColor(int strokeColor) {
        this.mPolygonOptions.strokeColor(strokeColor);
        styleChanged();
    }

    public int getStrokeJointType() {
        return this.mPolygonOptions.getStrokeJointType();
    }

    public void setStrokeJointType(int strokeJointType) {
        this.mPolygonOptions.strokeJointType(strokeJointType);
        styleChanged();
    }

    public List<PatternItem> getStrokePattern() {
        return this.mPolygonOptions.getStrokePattern();
    }

    public void setStrokePattern(List<PatternItem> strokePattern) {
        this.mPolygonOptions.strokePattern(strokePattern);
        styleChanged();
    }

    public float getStrokeWidth() {
        return this.mPolygonOptions.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        setPolygonStrokeWidth(strokeWidth);
        styleChanged();
    }

    public float getZIndex() {
        return this.mPolygonOptions.getZIndex();
    }

    public void setZIndex(float zIndex) {
        this.mPolygonOptions.zIndex(zIndex);
        styleChanged();
    }

    public boolean isVisible() {
        return this.mPolygonOptions.isVisible();
    }

    public void setVisible(boolean visible) {
        this.mPolygonOptions.visible(visible);
        styleChanged();
    }

    private void styleChanged() {
        setChanged();
        notifyObservers();
    }

    public PolygonOptions toPolygonOptions() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.fillColor(this.mPolygonOptions.getFillColor());
        polygonOptions.geodesic(this.mPolygonOptions.isGeodesic());
        polygonOptions.strokeColor(this.mPolygonOptions.getStrokeColor());
        polygonOptions.strokeJointType(this.mPolygonOptions.getStrokeJointType());
        polygonOptions.strokePattern(this.mPolygonOptions.getStrokePattern());
        polygonOptions.strokeWidth(this.mPolygonOptions.getStrokeWidth());
        polygonOptions.visible(this.mPolygonOptions.isVisible());
        polygonOptions.zIndex(this.mPolygonOptions.getZIndex());
        polygonOptions.clickable(this.mPolygonOptions.isClickable());
        return polygonOptions;
    }

    public String toString() {
        return "PolygonStyle{" + "\n geometry type=" + Arrays.toString(GEOMETRY_TYPE) + ",\n fill color=" + getFillColor() + ",\n geodesic=" + isGeodesic() + ",\n stroke color=" + getStrokeColor() + ",\n stroke joint type=" + getStrokeJointType() + ",\n stroke pattern=" + getStrokePattern() + ",\n stroke width=" + getStrokeWidth() + ",\n visible=" + isVisible() + ",\n z index=" + getZIndex() + ",\n clickable=" + isClickable() + "\n}\n";
    }

    public void setClickable(boolean clickable) {
        this.mPolygonOptions.clickable(clickable);
        styleChanged();
    }

    public boolean isClickable() {
        return this.mPolygonOptions.isClickable();
    }
}
