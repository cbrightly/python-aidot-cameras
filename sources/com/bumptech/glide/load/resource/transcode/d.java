package com.bumptech.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.resource.bytes.b;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.a;

/* compiled from: GifDrawableBytesTranscoder */
public class d implements e<GifDrawable, byte[]> {
    @Nullable
    public t<byte[]> a(@NonNull t<GifDrawable> toTranscode, @NonNull i options) {
        return new b(a.d(toTranscode.get().c()));
    }
}
