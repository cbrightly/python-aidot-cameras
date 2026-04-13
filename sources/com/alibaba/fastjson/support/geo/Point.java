package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "Point")
public class Point extends Geometry {
    private double latitude;
    private double longitude;

    public Point() {
        super("Point");
    }

    public double[] getCoordinates() {
        return new double[]{this.longitude, this.latitude};
    }

    public void setCoordinates(double[] coordinates) {
        if (coordinates == null || coordinates.length == 0) {
            this.longitude = 0.0d;
            this.latitude = 0.0d;
        } else if (coordinates.length == 1) {
            this.longitude = coordinates[0];
        } else {
            this.longitude = coordinates[0];
            this.latitude = coordinates[1];
        }
    }

    @JSONField(serialize = false)
    public double getLongitude() {
        return this.longitude;
    }

    @JSONField(serialize = false)
    public double getLatitude() {
        return this.latitude;
    }

    @JSONField(deserialize = false)
    public void setLongitude(double longitude2) {
        this.longitude = longitude2;
    }

    @JSONField(deserialize = false)
    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }
}
