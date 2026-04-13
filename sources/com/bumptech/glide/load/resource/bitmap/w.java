package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.resource.drawable.d;

/* compiled from: ResourceBitmapDecoder */
public class w implements k<Uri, Bitmap> {
    private final d a;
    private final e b;

    public w(d drawableDecoder, e bitmapPool) {
        this.a = drawableDecoder;
        this.b = bitmapPool;
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri source, @NonNull i options) {
        return "android.resource".equals(source.getScheme());
    }

    @Nullable
    /* renamed from: c */
    public t<Bitmap> b(@NonNull Uri source, int width, int height, @NonNull i options) {
        Resource<Drawable> drawableResource = this.a.b(source, width, height, options);
        if (drawableResource == null) {
            return null;
        }
        return n.a(this.b, drawableResource.get(), width, height);
    }
}
