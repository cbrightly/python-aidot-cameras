package com.google.maps.android.geometry;

public class Point {
    public final double x;
    public final double y;

    public Point(double x2, double y2) {
        this.x = x2;
        this.y = y2;
    }

    public String toString() {
        return "Point{x=" + this.x + ", y=" + this.y + '}';
    }
}
