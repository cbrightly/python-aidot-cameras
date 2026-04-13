package com.google.maps.android.data.geojson;

public interface GeoJsonStyle {
    String[] getGeometryType();

    boolean isVisible();

    void setVisible(boolean z);
}
