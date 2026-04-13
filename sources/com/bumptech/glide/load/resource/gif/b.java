package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.a;
import com.bumptech.glide.load.engine.bitmap_recycle.e;

/* compiled from: GifBitmapProvider */
public final class b implements a.C0022a {
    private final e a;
    @Nullable
    private final com.bumptech.glide.load.engine.bitmap_recycle.b b;

    public b(e bitmapPool, @Nullable com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool) {
        this.a = bitmapPool;
        this.b = arrayPool;
    }

    @NonNull
    public Bitmap c(int width, int height, @NonNull Bitmap.Config config) {
        return this.a.e(width, height, config);
    }

    public void a(@NonNull Bitmap bitmap) {
        this.a.b(bitmap);
    }

    @NonNull
    public byte[] b(int size) {
        com.bumptech.glide.load.engine.bitmap_recycle.b bVar = this.b;
        if (bVar == null) {
            return new byte[size];
        }
        return (byte[]) bVar.e(size, byte[].class);
    }

    public void e(@NonNull byte[] bytes) {
        com.bumptech.glide.load.engine.bitmap_recycle.b bVar = this.b;
        if (bVar != null) {
            bVar.put(bytes);
        }
    }

    @NonNull
    public int[] d(int size) {
        com.bumptech.glide.load.engine.bitmap_recycle.b bVar = this.b;
        if (bVar == null) {
            return new int[size];
        }
        return (int[]) bVar.e(size, int[].class);
    }

    public void f(@NonNull int[] array) {
        com.bumptech.glide.load.engine.bitmap_recycle.b bVar = this.b;
        if (bVar != null) {
            bVar.put(array);
        }
    }
}
