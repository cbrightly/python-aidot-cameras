package org.spongycastle.util;

public class Integers {
    public static int a(int i, int distance) {
        return Integer.rotateLeft(i, distance);
    }

    public static Integer b(int value) {
        return Integer.valueOf(value);
    }
}
