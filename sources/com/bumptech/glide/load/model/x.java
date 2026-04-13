package com.bumptech.glide.load.model;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.l;

/* compiled from: UrlUriLoader */
public class x<Data> implements n<Uri, Data> {
    private static final Set<String> a = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{l.DEFAULT_SCHEME_NAME, "https"})));
    private final n<g, Data> b;

    public x(n<g, Data> urlLoader) {
        this.b = urlLoader;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull Uri uri, int width, int height, @NonNull i options) {
        return this.b.b(new g(uri.toString()), width, height, options);
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri uri) {
        return a.contains(uri.getScheme());
    }

    /* compiled from: UrlUriLoader */
    public static class a implements o<Uri, InputStream> {
        @NonNull
        public n<Uri, InputStream> b(r multiFactory) {
            return new x(multiFactory.d(g.class, InputStream.class));
        }
    }
}
