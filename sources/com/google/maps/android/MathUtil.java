package com.google.maps.android;

public class MathUtil {
    static final double EARTH_RADIUS = 6371009.0d;

    MathUtil() {
    }

    static double clamp(double x, double low, double high) {
        if (x < low) {
            return low;
        }
        return x > high ? high : x;
    }

    static double wrap(double n, double min, double max) {
        return (n < min || n >= max) ? mod(n - min, max - min) + min : n;
    }

    static double mod(double x, double m) {
        return ((x % m) + m) % m;
    }

    static double mercator(double lat) {
        return Math.log(Math.tan((0.5d * lat) + 0.7853981633974483d));
    }

    static double inverseMercator(double y) {
        return (Math.atan(Math.exp(y)) * 2.0d) - 1.5707963267948966d;
    }

    static double hav(double x) {
        double sinHalf = Math.sin(0.5d * x);
        return sinHalf * sinHalf;
    }

    static double arcHav(double x) {
        return Math.asin(Math.sqrt(x)) * 2.0d;
    }

    static double sinFromHav(double h) {
        return Math.sqrt((1.0d - h) * h) * 2.0d;
    }

    static double havFromSin(double x) {
        double x2 = x * x;
        return (x2 / (Math.sqrt(1.0d - x2) + 1.0d)) * 0.5d;
    }

    static double sinSumFromHav(double x, double y) {
        double a = Math.sqrt((1.0d - x) * x);
        double b = Math.sqrt((1.0d - y) * y);
        return ((a + b) - (((a * y) + (b * x)) * 2.0d)) * 2.0d;
    }

    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + (hav(dLng) * Math.cos(lat1) * Math.cos(lat2));
    }
}
