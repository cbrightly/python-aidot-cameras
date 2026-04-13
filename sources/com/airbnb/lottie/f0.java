package com.airbnb.lottie;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* compiled from: LottieImageAsset */
public class f0 {
    private final int a;
    private final int b;
    private final String c;
    private final String d;
    private final String e;
    @Nullable
    private Bitmap f;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public f0(int width, int height, String id, String fileName, String dirName) {
        this.a = width;
        this.b = height;
        this.c = id;
        this.d = fileName;
        this.e = dirName;
    }

    public int e() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public String d() {
        return this.c;
    }

    public String b() {
        return this.d;
    }

    @Nullable
    public Bitmap a() {
        return this.f;
    }

    public void f(@Nullable Bitmap bitmap) {
        this.f = bitmap;
    }
}
