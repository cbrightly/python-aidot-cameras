package com.google.maps.android.geometry;

public class Bounds {
    public final double maxX;
    public final double maxY;
    public final double midX;
    public final double midY;
    public final double minX;
    public final double minY;

    public Bounds(double minX2, double maxX2, double minY2, double maxY2) {
        this.minX = minX2;
        this.minY = minY2;
        this.maxX = maxX2;
        this.maxY = maxY2;
        this.midX = (minX2 + maxX2) / 2.0d;
        this.midY = (minY2 + maxY2) / 2.0d;
    }

    public boolean contains(double x, double y) {
        return this.minX <= x && x <= this.maxX && this.minY <= y && y <= this.maxY;
    }

    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }

    public boolean intersects(double minX2, double maxX2, double minY2, double maxY2) {
        return minX2 < this.maxX && this.minX < maxX2 && minY2 < this.maxY && this.minY < maxY2;
    }

    public boolean intersects(Bounds bounds) {
        return intersects(bounds.minX, bounds.maxX, bounds.minY, bounds.maxY);
    }

    public boolean contains(Bounds bounds) {
        return bounds.minX >= this.minX && bounds.maxX <= this.maxX && bounds.minY >= this.minY && bounds.maxY <= this.maxY;
    }
}
