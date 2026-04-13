package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import java.io.File;

/* compiled from: BitmapDrawableEncoder */
public class b implements l<BitmapDrawable> {
    private final e a;
    private final l<Bitmap> b;

    public b(e bitmapPool, l<Bitmap> encoder) {
        this.a = bitmapPool;
        this.b = encoder;
    }

    /* renamed from: c */
    public boolean a(@NonNull t<BitmapDrawable> data, @NonNull File file, @NonNull i options) {
        return this.b.a(new e(data.get().getBitmap(), this.a), file, options);
    }

    @NonNull
    public c b(@NonNull i options) {
        return this.b.b(options);
    }
}
