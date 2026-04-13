package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import java.io.InputStream;

/* compiled from: ResourceLoader */
public class s<Data> implements n<Integer, Data> {
    private final n<Uri, Data> a;
    private final Resources b;

    public s(Resources resources, n<Uri, Data> uriLoader) {
        this.b = resources;
        this.a = uriLoader;
    }

    /* renamed from: c */
    public n.a<Data> b(@NonNull Integer model, int width, int height, @NonNull i options) {
        Uri uri = d(model);
        if (uri == null) {
            return null;
        }
        return this.a.b(uri, width, height, options);
    }

    @Nullable
    private Uri d(Integer model) {
        try {
            return Uri.parse("android.resource://" + this.b.getResourcePackageName(model.intValue()) + '/' + this.b.getResourceTypeName(model.intValue()) + '/' + this.b.getResourceEntryName(model.intValue()));
        } catch (Resources.NotFoundException e) {
            if (!Log.isLoggable("ResourceLoader", 5)) {
                return null;
            }
            Log.w("ResourceLoader", "Received invalid resource id: " + model, e);
            return null;
        }
    }

    /* renamed from: e */
    public boolean a(@NonNull Integer model) {
        return true;
    }

    /* compiled from: ResourceLoader */
    public static class c implements o<Integer, InputStream> {
        private final Resources a;

        public c(Resources resources) {
            this.a = resources;
        }

        @NonNull
        public n<Integer, InputStream> b(r multiFactory) {
            return new s(this.a, multiFactory.d(Uri.class, InputStream.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static class b implements o<Integer, ParcelFileDescriptor> {
        private final Resources a;

        public b(Resources resources) {
            this.a = resources;
        }

        @NonNull
        public n<Integer, ParcelFileDescriptor> b(r multiFactory) {
            return new s(this.a, multiFactory.d(Uri.class, ParcelFileDescriptor.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static final class a implements o<Integer, AssetFileDescriptor> {
        private final Resources a;

        public a(Resources resources) {
            this.a = resources;
        }

        public n<Integer, AssetFileDescriptor> b(r multiFactory) {
            return new s(this.a, multiFactory.d(Uri.class, AssetFileDescriptor.class));
        }
    }

    /* compiled from: ResourceLoader */
    public static class d implements o<Integer, Uri> {
        private final Resources a;

        public d(Resources resources) {
            this.a = resources;
        }

        @NonNull
        public n<Integer, Uri> b(r multiFactory) {
            return new s(this.a, v.c());
        }
    }
}
