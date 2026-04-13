package com.google.maps.android.data;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.Observable;

public abstract class Style extends Observable {
    protected MarkerOptions mMarkerOptions = new MarkerOptions();
    protected PolygonOptions mPolygonOptions;
    protected PolylineOptions mPolylineOptions;

    public Style() {
        PolylineOptions polylineOptions = new PolylineOptions();
        this.mPolylineOptions = polylineOptions;
        polylineOptions.clickable(true);
        PolygonOptions polygonOptions = new PolygonOptions();
        this.mPolygonOptions = polygonOptions;
        polygonOptions.clickable(true);
    }

    public float getRotation() {
        return this.mMarkerOptions.getRotation();
    }

    public void setMarkerRotation(float rotation) {
        this.mMarkerOptions.rotation(rotation);
    }

    public void setMarkerHotSpot(float x, float y, String xUnits, String yUnits) {
        float xAnchor = 0.5f;
        float yAnchor = 1.0f;
        if (xUnits.equals(Progress.FRACTION)) {
            xAnchor = x;
        }
        if (yUnits.equals(Progress.FRACTION)) {
            yAnchor = y;
        }
        this.mMarkerOptions.anchor(xAnchor, yAnchor);
    }

    public void setLineStringWidth(float width) {
        this.mPolylineOptions.width(width);
    }

    public void setPolygonStrokeWidth(float strokeWidth) {
        this.mPolygonOptions.strokeWidth(strokeWidth);
    }

    public void setPolygonFillColor(int fillColor) {
        this.mPolygonOptions.fillColor(fillColor);
    }
}
