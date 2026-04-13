package com.bumptech.glide.load.resource.bytes;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.util.i;

/* compiled from: BytesResource */
public class b implements t<byte[]> {
    private final byte[] c;

    public b(byte[] bytes) {
        this.c = (byte[]) i.d(bytes);
    }

    @NonNull
    public Class<byte[]> a() {
        return byte[].class;
    }

    @NonNull
    /* renamed from: b */
    public byte[] get() {
        return this.c;
    }

    public int getSize() {
        return this.c.length;
    }

    public void recycle() {
    }
}
