package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.gifdecoder.a;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;

/* compiled from: GifFrameResourceDecoder */
public final class g implements k<a, Bitmap> {
    private final e a;

    public g(e bitmapPool) {
        this.a = bitmapPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull a source, @NonNull i options) {
        return true;
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull a source, int width, int height, @NonNull i options) {
        return com.bumptech.glide.load.resource.bitmap.e.c(source.getNextFrame(), this.a);
    }
}
