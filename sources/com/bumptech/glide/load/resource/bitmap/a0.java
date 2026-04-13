package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import com.bumptech.glide.util.j;

/* compiled from: UnitBitmapDecoder */
public final class a0 implements k<Bitmap, Bitmap> {
    /* renamed from: d */
    public boolean a(@NonNull Bitmap source, @NonNull i options) {
        return true;
    }

    /* renamed from: c */
    public t<Bitmap> b(@NonNull Bitmap source, int width, int height, @NonNull i options) {
        return new a(source);
    }

    /* compiled from: UnitBitmapDecoder */
    public static final class a implements t<Bitmap> {
        private final Bitmap c;

        a(@NonNull Bitmap bitmap) {
            this.c = bitmap;
        }

        @NonNull
        public Class<Bitmap> a() {
            return Bitmap.class;
        }

        @NonNull
        /* renamed from: b */
        public Bitmap get() {
            return this.c;
        }

        public int getSize() {
            return j.g(this.c);
        }

        public void recycle() {
        }
    }
}
