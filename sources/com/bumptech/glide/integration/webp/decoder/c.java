package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.a;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferBitmapWebpDecoder */
public class c implements k<ByteBuffer, Bitmap> {
    private final j a;

    public c(j downsampler) {
        this.a = downsampler;
    }

    /* renamed from: d */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        return this.a.m(source, options);
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull ByteBuffer source, int width, int height, @NonNull i options) {
        return this.a.d(a.f(source), width, height, options);
    }
}
