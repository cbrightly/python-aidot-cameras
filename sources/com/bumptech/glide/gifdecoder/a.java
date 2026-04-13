package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;

/* compiled from: GifDecoder */
public interface a {

    /* renamed from: com.bumptech.glide.gifdecoder.a$a  reason: collision with other inner class name */
    /* compiled from: GifDecoder */
    public interface C0022a {
        void a(@NonNull Bitmap bitmap);

        @NonNull
        byte[] b(int i);

        @NonNull
        Bitmap c(int i, int i2, @NonNull Bitmap.Config config);

        @NonNull
        int[] d(int i);

        void e(@NonNull byte[] bArr);

        void f(@NonNull int[] iArr);
    }

    void a();

    int b();

    void c(@NonNull Bitmap.Config config);

    void clear();

    int d();

    void e();

    int f();

    int g();

    @NonNull
    ByteBuffer getData();

    @Nullable
    Bitmap getNextFrame();
}
