package com.bumptech.glide.load.model.stream;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.r;
import java.io.InputStream;
import java.net.URL;

/* compiled from: UrlLoader */
public class e implements n<URL, InputStream> {
    private final n<g, InputStream> a;

    public e(n<g, InputStream> glideUrlLoader) {
        this.a = glideUrlLoader;
    }

    /* renamed from: c */
    public n.a<InputStream> b(@NonNull URL model, int width, int height, @NonNull i options) {
        return this.a.b(new g(model), width, height, options);
    }

    /* renamed from: d */
    public boolean a(@NonNull URL model) {
        return true;
    }

    /* compiled from: UrlLoader */
    public static class a implements o<URL, InputStream> {
        @NonNull
        public n<URL, InputStream> b(r multiFactory) {
            return new e(multiFactory.d(g.class, InputStream.class));
        }
    }
}
