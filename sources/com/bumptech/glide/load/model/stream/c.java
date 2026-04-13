package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.data.mediastore.b;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.r;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.signature.d;
import java.io.InputStream;

/* compiled from: MediaStoreVideoThumbLoader */
public class c implements n<Uri, InputStream> {
    private final Context a;

    public c(Context context) {
        this.a = context.getApplicationContext();
    }

    @Nullable
    /* renamed from: c */
    public n.a<InputStream> b(@NonNull Uri model, int width, int height, @NonNull i options) {
        if (!b.d(width, height) || !e(options)) {
            return null;
        }
        return new n.a<>(new d(model), com.bumptech.glide.load.data.mediastore.c.f(this.a, model));
    }

    private boolean e(i options) {
        Long specifiedFrame = (Long) options.a(VideoDecoder.a);
        return specifiedFrame != null && specifiedFrame.longValue() == -1;
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri model) {
        return b.c(model);
    }

    /* compiled from: MediaStoreVideoThumbLoader */
    public static class a implements o<Uri, InputStream> {
        private final Context a;

        public a(Context context) {
            this.a = context;
        }

        @NonNull
        public n<Uri, InputStream> b(r multiFactory) {
            return new c(this.a);
        }
    }
}
