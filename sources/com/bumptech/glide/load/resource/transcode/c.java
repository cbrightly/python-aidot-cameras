package com.bumptech.glide.load.resource.transcode;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.resource.gif.GifDrawable;

/* compiled from: DrawableBytesTranscoder */
public final class c implements e<Drawable, byte[]> {
    private final e a;
    private final e<Bitmap, byte[]> b;
    private final e<GifDrawable, byte[]> c;

    public c(@NonNull e bitmapPool, @NonNull e<Bitmap, byte[]> bitmapBytesTranscoder, @NonNull e<GifDrawable, byte[]> gifDrawableBytesTranscoder) {
        this.a = bitmapPool;
        this.b = bitmapBytesTranscoder;
        this.c = gifDrawableBytesTranscoder;
    }

    @Nullable
    public t<byte[]> a(@NonNull t<Drawable> toTranscode, @NonNull i options) {
        Drawable drawable = toTranscode.get();
        if (drawable instanceof BitmapDrawable) {
            return this.b.a(com.bumptech.glide.load.resource.bitmap.e.c(((BitmapDrawable) drawable).getBitmap(), this.a), options);
        }
        if (drawable instanceof GifDrawable) {
            return this.c.a(b(toTranscode), options);
        }
        return null;
    }

    @NonNull
    private static t<GifDrawable> b(@NonNull t<Drawable> resource) {
        return resource;
    }
}
