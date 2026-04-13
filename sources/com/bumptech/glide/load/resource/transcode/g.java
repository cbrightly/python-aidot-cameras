package com.bumptech.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;

/* compiled from: UnitTranscoder */
public class g<Z> implements e<Z, Z> {
    private static final g<?> a = new g<>();

    public static <Z> e<Z, Z> b() {
        return a;
    }

    @Nullable
    public t<Z> a(@NonNull t<Z> toTranscode, @NonNull i options) {
        return toTranscode;
    }
}
