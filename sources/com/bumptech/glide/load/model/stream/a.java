package com.bumptech.glide.load.model.stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.data.j;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.load.model.m;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.r;
import java.io.InputStream;

/* compiled from: HttpGlideUrlLoader */
public class a implements n<g, InputStream> {
    public static final h<Integer> a = h.f("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", 2500);
    @Nullable
    private final m<g, g> b;

    public a(@Nullable m<g, g> modelCache) {
        this.b = modelCache;
    }

    /* renamed from: c */
    public n.a<InputStream> b(@NonNull g model, int width, int height, @NonNull i options) {
        g url = model;
        m<g, g> mVar = this.b;
        if (mVar != null && (url = mVar.a(model, 0, 0)) == null) {
            this.b.b(model, 0, 0, model);
            url = model;
        }
        return new n.a<>(url, new j(url, ((Integer) options.a(a)).intValue()));
    }

    /* renamed from: d */
    public boolean a(@NonNull g model) {
        return true;
    }

    /* renamed from: com.bumptech.glide.load.model.stream.a$a  reason: collision with other inner class name */
    /* compiled from: HttpGlideUrlLoader */
    public static class C0038a implements o<g, InputStream> {
        private final m<g, g> a = new m<>(500);

        @NonNull
        public n<g, InputStream> b(r multiFactory) {
            return new a(this.a);
        }
    }
}
