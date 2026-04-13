package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: IntegerArrayAdapter */
public final class i implements a<int[]> {
    public String getTag() {
        return "IntegerArrayPool";
    }

    /* renamed from: c */
    public int b(int[] array) {
        return array.length;
    }

    /* renamed from: d */
    public int[] newArray(int length) {
        return new int[length];
    }

    public int a() {
        return 4;
    }
}
