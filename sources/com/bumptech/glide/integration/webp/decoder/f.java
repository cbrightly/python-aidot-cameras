package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.io.InputStream;

/* compiled from: StreamBitmapWebpDecoder */
public class f implements k<InputStream, Bitmap> {
    private final j a;
    private final b b;

    public f(j downsampler, b byteArrayPool) {
        this.a = downsampler;
        this.b = byteArrayPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull InputStream source, @NonNull i options) {
        return this.a.l(source, options);
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull InputStream source, int width, int height, @NonNull i options) {
        return this.a.d(source, width, height, options);
    }
}
