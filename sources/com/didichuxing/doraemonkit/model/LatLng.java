package com.didichuxing.doraemonkit.model;

import java.io.Serializable;

public class LatLng implements Serializable {
    public double latitude;
    public double longitude;

    public LatLng(double latitude2, double longitude2) {
        this.latitude = latitude2;
        this.longitude = longitude2;
    }
}
