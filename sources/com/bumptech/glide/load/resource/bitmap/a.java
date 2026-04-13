package com.bumptech.glide.load.resource.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.i;

/* compiled from: BitmapDrawableDecoder */
public class a<DataType> implements k<DataType, BitmapDrawable> {
    private final k<DataType, Bitmap> a;
    private final Resources b;

    public a(@NonNull Resources resources, @NonNull k<DataType, Bitmap> decoder) {
        this.b = (Resources) i.d(resources);
        this.a = (k) i.d(decoder);
    }

    public boolean a(@NonNull DataType source, @NonNull com.bumptech.glide.load.i options) {
        return this.a.a(source, options);
    }

    public t<BitmapDrawable> b(@NonNull DataType source, int width, int height, @NonNull com.bumptech.glide.load.i options) {
        return u.c(this.b, this.a.b(source, width, height, options));
    }
}
