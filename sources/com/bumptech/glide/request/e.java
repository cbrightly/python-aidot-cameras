package com.bumptech.glide.request;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.j;

/* compiled from: RequestListener */
public interface e<R> {
    boolean onLoadFailed(@Nullable GlideException glideException, Object obj, j<R> jVar, boolean z);

    boolean onResourceReady(R r, Object obj, j<R> jVar, a aVar, boolean z);
}
