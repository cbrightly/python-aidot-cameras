package com.bumptech.glide.load.resource.transcode;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.resource.bitmap.u;
import com.bumptech.glide.util.i;

/* compiled from: BitmapDrawableTranscoder */
public class b implements e<Bitmap, BitmapDrawable> {
    private final Resources a;

    public b(@NonNull Resources resources) {
        this.a = (Resources) i.d(resources);
    }

    @Nullable
    public t<BitmapDrawable> a(@NonNull t<Bitmap> toTranscode, @NonNull com.bumptech.glide.load.i options) {
        return u.c(this.a, toTranscode);
    }
}
