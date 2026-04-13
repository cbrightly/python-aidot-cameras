package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.File;
import java.io.InputStream;

/* compiled from: StringLoader */
public class u<Data> implements n<String, Data> {
    private final n<Uri, Data> a;

    public u(n<Uri, Data> uriLoader) {
        this.a = uriLoader;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull String model, int width, int height, @NonNull i options) {
        Uri uri = e(model);
        if (uri == null || !this.a.a(uri)) {
            return null;
        }
        return this.a.b(uri, width, height, options);
    }

    /* renamed from: d */
    public boolean a(@NonNull String model) {
        return true;
    }

    @Nullable
    private static Uri e(String model) {
        if (TextUtils.isEmpty(model)) {
            return null;
        }
        if (model.charAt(0) == '/') {
            return f(model);
        }
        Uri uri = Uri.parse(model);
        if (uri.getScheme() == null) {
            return f(model);
        }
        return uri;
    }

    private static Uri f(String path) {
        return Uri.fromFile(new File(path));
    }

    /* compiled from: StringLoader */
    public static class c implements o<String, InputStream> {
        @NonNull
        public n<String, InputStream> b(@NonNull r multiFactory) {
            return new u(multiFactory.d(Uri.class, InputStream.class));
        }
    }

    /* compiled from: StringLoader */
    public static class b implements o<String, ParcelFileDescriptor> {
        @NonNull
        public n<String, ParcelFileDescriptor> b(@NonNull r multiFactory) {
            return new u(multiFactory.d(Uri.class, ParcelFileDescriptor.class));
        }
    }

    /* compiled from: StringLoader */
    public static final class a implements o<String, AssetFileDescriptor> {
        public n<String, AssetFileDescriptor> b(@NonNull r multiFactory) {
            return new u(multiFactory.d(Uri.class, AssetFileDescriptor.class));
        }
    }
}
