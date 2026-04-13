package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;

/* compiled from: LruPoolStrategy */
public interface l {
    String a(Bitmap bitmap);

    void b(Bitmap bitmap);

    @Nullable
    Bitmap c(int i, int i2, Bitmap.Config config);

    String d(int i, int i2, Bitmap.Config config);

    int e(Bitmap bitmap);

    @Nullable
    Bitmap removeLast();
}
