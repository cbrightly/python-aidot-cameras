package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.a;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferBitmapDecoder */
public class g implements k<ByteBuffer, Bitmap> {
    private final m a;

    public g(m downsampler) {
        this.a = downsampler;
    }

    /* renamed from: d */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        return this.a.q(source);
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull ByteBuffer source, int width, int height, @NonNull i options) {
        return this.a.f(a.f(source), width, height, options);
    }
}
