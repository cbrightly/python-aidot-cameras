package com.google.maps.android.data.geojson;

import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Style;
import java.util.Arrays;
import java.util.List;

public class GeoJsonLineStringStyle extends Style implements GeoJsonStyle {
    private static final String[] GEOMETRY_TYPE = {"LineString", "MultiLineString", "GeometryCollection"};

    public GeoJsonLineStringStyle() {
        PolylineOptions polylineOptions = new PolylineOptions();
        this.mPolylineOptions = polylineOptions;
        polylineOptions.clickable(true);
    }

    public String[] getGeometryType() {
        return GEOMETRY_TYPE;
    }

    public int getColor() {
        return this.mPolylineOptions.getColor();
    }

    public void setColor(int color) {
        this.mPolylineOptions.color(color);
        styleChanged();
    }

    public boolean isClickable() {
        return this.mPolylineOptions.isClickable();
    }

    public void setClickable(boolean clickable) {
        this.mPolylineOptions.clickable(clickable);
        styleChanged();
    }

    public boolean isGeodesic() {
        return this.mPolylineOptions.isGeodesic();
    }

    public void setGeodesic(boolean geodesic) {
        this.mPolylineOptions.geodesic(geodesic);
        styleChanged();
    }

    public float getWidth() {
        return this.mPolylineOptions.getWidth();
    }

    public void setWidth(float width) {
        setLineStringWidth(width);
        styleChanged();
    }

    public float getZIndex() {
        return this.mPolylineOptions.getZIndex();
    }

    public void setZIndex(float zIndex) {
        this.mPolylineOptions.zIndex(zIndex);
        styleChanged();
    }

    public boolean isVisible() {
        return this.mPolylineOptions.isVisible();
    }

    public void setVisible(boolean visible) {
        this.mPolylineOptions.visible(visible);
        styleChanged();
    }

    private void styleChanged() {
        setChanged();
        notifyObservers();
    }

    public PolylineOptions toPolylineOptions() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(this.mPolylineOptions.getColor());
        polylineOptions.clickable(this.mPolylineOptions.isClickable());
        polylineOptions.geodesic(this.mPolylineOptions.isGeodesic());
        polylineOptions.visible(this.mPolylineOptions.isVisible());
        polylineOptions.width(this.mPolylineOptions.getWidth());
        polylineOptions.zIndex(this.mPolylineOptions.getZIndex());
        polylineOptions.pattern(getPattern());
        return polylineOptions;
    }

    public String toString() {
        return "LineStringStyle{" + "\n geometry type=" + Arrays.toString(GEOMETRY_TYPE) + ",\n color=" + getColor() + ",\n clickable=" + isClickable() + ",\n geodesic=" + isGeodesic() + ",\n visible=" + isVisible() + ",\n width=" + getWidth() + ",\n z index=" + getZIndex() + ",\n pattern=" + getPattern() + "\n}\n";
    }

    public List<PatternItem> getPattern() {
        return this.mPolylineOptions.getPattern();
    }

    public void setPattern(List<PatternItem> pattern) {
        this.mPolylineOptions.pattern(pattern);
        styleChanged();
    }
}
