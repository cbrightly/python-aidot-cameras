package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.io.InputStream;

/* compiled from: StreamAnimatedBitmapDecoder */
public class e implements k<InputStream, Bitmap> {
    private final a a;

    public e(a bitmapDecoder) {
        this.a = bitmapDecoder;
    }

    /* renamed from: d */
    public boolean a(@NonNull InputStream source, @NonNull i options) {
        return this.a.c(source, options);
    }

    @Nullable
    /* renamed from: c */
    public t<Bitmap> b(@NonNull InputStream source, int width, int height, @NonNull i options) {
        return this.a.a(source, width, height, options);
    }
}
