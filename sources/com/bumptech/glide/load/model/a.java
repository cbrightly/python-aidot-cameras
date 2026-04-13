package com.bumptech.glide.load.model;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.data.h;
import com.bumptech.glide.load.data.m;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.InputStream;

/* compiled from: AssetUriLoader */
public class a<Data> implements n<Uri, Data> {
    private static final int a = "file:///android_asset/".length();
    private final AssetManager b;
    private final C0034a<Data> c;

    /* renamed from: com.bumptech.glide.load.model.a$a  reason: collision with other inner class name */
    /* compiled from: AssetUriLoader */
    public interface C0034a<Data> {
        d<Data> a(AssetManager assetManager, String str);
    }

    public a(AssetManager assetManager, C0034a<Data> factory) {
        this.b = assetManager;
        this.c = factory;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull Uri model, int width, int height, @NonNull i options) {
        return new n.a<>(new com.bumptech.glide.signature.d(model), this.c.a(this.b, model.toString().substring(a)));
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri model) {
        if (!"file".equals(model.getScheme()) || model.getPathSegments().isEmpty() || !"android_asset".equals(model.getPathSegments().get(0))) {
            return false;
        }
        return true;
    }

    /* compiled from: AssetUriLoader */
    public static class c implements o<Uri, InputStream>, C0034a<InputStream> {
        private final AssetManager a;

        public c(AssetManager assetManager) {
            this.a = assetManager;
        }

        @NonNull
        public n<Uri, InputStream> b(r multiFactory) {
            return new a(this.a, this);
        }

        public d<InputStream> a(AssetManager assetManager, String assetPath) {
            return new m(assetManager, assetPath);
        }
    }

    /* compiled from: AssetUriLoader */
    public static class b implements o<Uri, ParcelFileDescriptor>, C0034a<ParcelFileDescriptor> {
        private final AssetManager a;

        public b(AssetManager assetManager) {
            this.a = assetManager;
        }

        @NonNull
        public n<Uri, ParcelFileDescriptor> b(r multiFactory) {
            return new a(this.a, this);
        }

        public d<ParcelFileDescriptor> a(AssetManager assetManager, String assetPath) {
            return new h(assetManager, assetPath);
        }
    }
}
