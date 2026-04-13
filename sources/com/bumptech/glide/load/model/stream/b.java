package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.mediastore.c;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.r;
import com.bumptech.glide.signature.d;
import java.io.InputStream;

/* compiled from: MediaStoreImageThumbLoader */
public class b implements n<Uri, InputStream> {
    private final Context a;

    public b(Context context) {
        this.a = context.getApplicationContext();
    }

    /* renamed from: c */
    public n.a<InputStream> b(@NonNull Uri model, int width, int height, @NonNull i options) {
        if (com.bumptech.glide.load.data.mediastore.b.d(width, height)) {
            return new n.a<>(new d(model), c.e(this.a, model));
        }
        return null;
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri model) {
        return com.bumptech.glide.load.data.mediastore.b.a(model);
    }

    /* compiled from: MediaStoreImageThumbLoader */
    public static class a implements o<Uri, InputStream> {
        private final Context a;

        public a(Context context) {
            this.a = context;
        }

        @NonNull
        public n<Uri, InputStream> b(r multiFactory) {
            return new b(this.a);
        }
    }
}
