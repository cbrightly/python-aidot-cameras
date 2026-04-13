package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* compiled from: BitmapPool */
public interface e {
    void a(int i);

    void b(Bitmap bitmap);

    @NonNull
    Bitmap c(int i, int i2, Bitmap.Config config);

    void d();

    @NonNull
    Bitmap e(int i, int i2, Bitmap.Config config);
}
