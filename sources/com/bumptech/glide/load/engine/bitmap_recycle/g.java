package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ByteArrayAdapter */
public final class g implements a<byte[]> {
    public String getTag() {
        return "ByteArrayPool";
    }

    /* renamed from: c */
    public int b(byte[] array) {
        return array.length;
    }

    /* renamed from: d */
    public byte[] newArray(int length) {
        return new byte[length];
    }

    public int a() {
        return 1;
    }
}
