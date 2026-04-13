package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* compiled from: BitmapPoolAdapter */
public class f implements e {
    public void b(Bitmap bitmap) {
        bitmap.recycle();
    }

    @NonNull
    public Bitmap c(int width, int height, Bitmap.Config config) {
        return Bitmap.createBitmap(width, height, config);
    }

    @NonNull
    public Bitmap e(int width, int height, Bitmap.Config config) {
        return c(width, height, config);
    }

    public void d() {
    }

    public void a(int level) {
    }
}
