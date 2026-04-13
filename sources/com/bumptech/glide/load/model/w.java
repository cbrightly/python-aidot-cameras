package com.bumptech.glide.load.model;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: UriLoader */
public class w<Data> implements n<Uri, Data> {
    private static final Set<String> a = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"file", "android.resource", FirebaseAnalytics.Param.CONTENT})));
    private final c<Data> b;

    /* compiled from: UriLoader */
    public interface c<Data> {
        com.bumptech.glide.load.data.d<Data> a(Uri uri);
    }

    public w(c<Data> factory) {
        this.b = factory;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull Uri model, int width, int height, @NonNull i options) {
        return new n.a<>(new com.bumptech.glide.signature.d(model), this.b.a(model));
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri model) {
        return a.contains(model.getScheme());
    }

    /* compiled from: UriLoader */
    public static class d implements o<Uri, InputStream>, c<InputStream> {
        private final ContentResolver a;

        public d(ContentResolver contentResolver) {
            this.a = contentResolver;
        }

        public com.bumptech.glide.load.data.d<InputStream> a(Uri uri) {
            return new com.bumptech.glide.load.data.n(this.a, uri);
        }

        @NonNull
        public n<Uri, InputStream> b(r multiFactory) {
            return new w(this);
        }
    }

    /* compiled from: UriLoader */
    public static class b implements o<Uri, ParcelFileDescriptor>, c<ParcelFileDescriptor> {
        private final ContentResolver a;

        public b(ContentResolver contentResolver) {
            this.a = contentResolver;
        }

        public com.bumptech.glide.load.data.d<ParcelFileDescriptor> a(Uri uri) {
            return new com.bumptech.glide.load.data.i(this.a, uri);
        }

        @NonNull
        public n<Uri, ParcelFileDescriptor> b(r multiFactory) {
            return new w(this);
        }
    }

    /* compiled from: UriLoader */
    public static final class a implements o<Uri, AssetFileDescriptor>, c<AssetFileDescriptor> {
        private final ContentResolver a;

        public a(ContentResolver contentResolver) {
            this.a = contentResolver;
        }

        public n<Uri, AssetFileDescriptor> b(r multiFactory) {
            return new w(this);
        }

        public com.bumptech.glide.load.data.d<AssetFileDescriptor> a(Uri uri) {
            return new com.bumptech.glide.load.data.a(this.a, uri);
        }
    }
}
