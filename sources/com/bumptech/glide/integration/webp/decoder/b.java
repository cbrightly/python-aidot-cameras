package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.nio.ByteBuffer;

/* compiled from: ByteBufferAnimatedBitmapDecoder */
public class b implements k<ByteBuffer, Bitmap> {
    private final a a;

    public b(a bitmapDecoder) {
        this.a = bitmapDecoder;
    }

    /* renamed from: d */
    public boolean a(@NonNull ByteBuffer source, @NonNull i options) {
        return this.a.d(source, options);
    }

    @Nullable
    /* renamed from: c */
    public t<Bitmap> b(@NonNull ByteBuffer source, int width, int height, @NonNull i options) {
        return this.a.b(source, width, height, options);
    }
}
