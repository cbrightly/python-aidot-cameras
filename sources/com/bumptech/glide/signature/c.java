package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import java.security.MessageDigest;

/* compiled from: EmptySignature */
public final class c implements f {
    private static final c b = new c();

    @NonNull
    public static c a() {
        return b;
    }

    private c() {
    }

    public String toString() {
        return "EmptySignature";
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
