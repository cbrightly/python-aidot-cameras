package com.google.maps.android.data.geojson;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.Style;
import java.util.Arrays;

public class GeoJsonPointStyle extends Style implements GeoJsonStyle {
    private static final String[] GEOMETRY_TYPE = {"Point", "MultiPoint", "GeometryCollection"};

    public GeoJsonPointStyle() {
        this.mMarkerOptions = new MarkerOptions();
    }

    public String[] getGeometryType() {
        return GEOMETRY_TYPE;
    }

    public float getAlpha() {
        return this.mMarkerOptions.getAlpha();
    }

    public void setAlpha(float alpha) {
        this.mMarkerOptions.alpha(alpha);
        styleChanged();
    }

    public float getAnchorU() {
        return this.mMarkerOptions.getAnchorU();
    }

    public float getAnchorV() {
        return this.mMarkerOptions.getAnchorV();
    }

    public void setAnchor(float anchorU, float anchorV) {
        setMarkerHotSpot(anchorU, anchorV, Progress.FRACTION, Progress.FRACTION);
        styleChanged();
    }

    public boolean isDraggable() {
        return this.mMarkerOptions.isDraggable();
    }

    public void setDraggable(boolean draggable) {
        this.mMarkerOptions.draggable(draggable);
        styleChanged();
    }

    public boolean isFlat() {
        return this.mMarkerOptions.isFlat();
    }

    public void setFlat(boolean flat) {
        this.mMarkerOptions.flat(flat);
        styleChanged();
    }

    public BitmapDescriptor getIcon() {
        return this.mMarkerOptions.getIcon();
    }

    public void setIcon(BitmapDescriptor bitmap) {
        this.mMarkerOptions.icon(bitmap);
        styleChanged();
    }

    public float getInfoWindowAnchorU() {
        return this.mMarkerOptions.getInfoWindowAnchorU();
    }

    public float getInfoWindowAnchorV() {
        return this.mMarkerOptions.getInfoWindowAnchorV();
    }

    public void setInfoWindowAnchor(float infoWindowAnchorU, float infoWindowAnchorV) {
        this.mMarkerOptions.infoWindowAnchor(infoWindowAnchorU, infoWindowAnchorV);
        styleChanged();
    }

    public float getRotation() {
        return this.mMarkerOptions.getRotation();
    }

    public void setRotation(float rotation) {
        setMarkerRotation(rotation);
        styleChanged();
    }

    public String getSnippet() {
        return this.mMarkerOptions.getSnippet();
    }

    public void setSnippet(String snippet) {
        this.mMarkerOptions.snippet(snippet);
        styleChanged();
    }

    public String getTitle() {
        return this.mMarkerOptions.getTitle();
    }

    public void setTitle(String title) {
        this.mMarkerOptions.title(title);
        styleChanged();
    }

    public boolean isVisible() {
        return this.mMarkerOptions.isVisible();
    }

    public void setVisible(boolean visible) {
        this.mMarkerOptions.visible(visible);
        styleChanged();
    }

    private void styleChanged() {
        setChanged();
        notifyObservers();
    }

    public MarkerOptions toMarkerOptions() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.alpha(this.mMarkerOptions.getAlpha());
        markerOptions.anchor(this.mMarkerOptions.getAnchorU(), this.mMarkerOptions.getAnchorV());
        markerOptions.draggable(this.mMarkerOptions.isDraggable());
        markerOptions.flat(this.mMarkerOptions.isFlat());
        markerOptions.icon(this.mMarkerOptions.getIcon());
        markerOptions.infoWindowAnchor(this.mMarkerOptions.getInfoWindowAnchorU(), this.mMarkerOptions.getInfoWindowAnchorV());
        markerOptions.rotation(this.mMarkerOptions.getRotation());
        markerOptions.snippet(this.mMarkerOptions.getSnippet());
        markerOptions.title(this.mMarkerOptions.getTitle());
        markerOptions.visible(this.mMarkerOptions.isVisible());
        markerOptions.zIndex(this.mMarkerOptions.getZIndex());
        return markerOptions;
    }

    public String toString() {
        return "PointStyle{" + "\n geometry type=" + Arrays.toString(GEOMETRY_TYPE) + ",\n alpha=" + getAlpha() + ",\n anchor U=" + getAnchorU() + ",\n anchor V=" + getAnchorV() + ",\n draggable=" + isDraggable() + ",\n flat=" + isFlat() + ",\n info window anchor U=" + getInfoWindowAnchorU() + ",\n info window anchor V=" + getInfoWindowAnchorV() + ",\n rotation=" + getRotation() + ",\n snippet=" + getSnippet() + ",\n title=" + getTitle() + ",\n visible=" + isVisible() + ",\n z index=" + getZIndex() + "\n}\n";
    }

    public float getZIndex() {
        return this.mMarkerOptions.getZIndex();
    }

    public void setZIndex(float zIndex) {
        this.mMarkerOptions.zIndex(zIndex);
        styleChanged();
    }
}
