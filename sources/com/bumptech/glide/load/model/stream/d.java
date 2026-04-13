package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.load.model.o;
import com.bumptech.glide.load.model.r;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RequiresApi(29)
/* compiled from: QMediaStoreUriLoader */
public final class d<DataT> implements n<Uri, DataT> {
    private final Context a;
    private final n<File, DataT> b;
    private final n<Uri, DataT> c;
    private final Class<DataT> d;

    d(Context context, n<File, DataT> fileDelegate, n<Uri, DataT> uriDelegate, Class<DataT> dataClass) {
        this.a = context.getApplicationContext();
        this.b = fileDelegate;
        this.c = uriDelegate;
        this.d = dataClass;
    }

    /* renamed from: c */
    public n.a<DataT> b(@NonNull Uri uri, int width, int height, @NonNull i options) {
        Uri uri2 = uri;
        return new n.a<>(new com.bumptech.glide.signature.d(uri), new C0039d(this.a, this.b, this.c, uri, width, height, options, this.d));
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri uri) {
        return Build.VERSION.SDK_INT >= 29 && com.bumptech.glide.load.data.mediastore.b.b(uri);
    }

    /* renamed from: com.bumptech.glide.load.model.stream.d$d  reason: collision with other inner class name */
    /* compiled from: QMediaStoreUriLoader */
    public static final class C0039d<DataT> implements com.bumptech.glide.load.data.d<DataT> {
        private static final String[] c = {"_data"};
        private final Class<DataT> a1;
        @Nullable
        private volatile com.bumptech.glide.load.data.d<DataT> a2;
        private final Context d;
        private final n<File, DataT> f;
        private final i p0;
        private volatile boolean p1;
        private final n<Uri, DataT> q;
        private final Uri x;
        private final int y;
        private final int z;

        C0039d(Context context, n<File, DataT> fileDelegate, n<Uri, DataT> uriDelegate, Uri uri, int width, int height, i options, Class<DataT> dataClass) {
            this.d = context.getApplicationContext();
            this.f = fileDelegate;
            this.q = uriDelegate;
            this.x = uri;
            this.y = width;
            this.z = height;
            this.p0 = options;
            this.a1 = dataClass;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super DataT> callback) {
            try {
                DataFetcher<DataT> local = e();
                if (local == null) {
                    callback.c(new IllegalArgumentException("Failed to build fetcher for: " + this.x));
                    return;
                }
                this.a2 = local;
                if (this.p1) {
                    cancel();
                } else {
                    local.d(priority, callback);
                }
            } catch (FileNotFoundException e) {
                callback.c(e);
            }
        }

        @Nullable
        private com.bumptech.glide.load.data.d<DataT> e() {
            ModelLoader.LoadData<DataT> result = c();
            if (result != null) {
                return result.c;
            }
            return null;
        }

        @Nullable
        private n.a<DataT> c() {
            if (Environment.isExternalStorageLegacy()) {
                return this.f.b(g(this.x), this.y, this.z, this.p0);
            }
            return this.q.b(f() ? MediaStore.setRequireOriginal(this.x) : this.x, this.y, this.z, this.p0);
        }

        public void b() {
            DataFetcher<DataT> local = this.a2;
            if (local != null) {
                local.b();
            }
        }

        public void cancel() {
            this.p1 = true;
            DataFetcher<DataT> local = this.a2;
            if (local != null) {
                local.cancel();
            }
        }

        @NonNull
        public Class<DataT> a() {
            return this.a1;
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return com.bumptech.glide.load.a.LOCAL;
        }

        @NonNull
        private File g(Uri uri) {
            Cursor cursor = null;
            try {
                cursor = this.d.getContentResolver().query(uri, c, (String) null, (String[]) null, (String) null);
                if (cursor == null || !cursor.moveToFirst()) {
                    throw new FileNotFoundException("Failed to media store entry for: " + uri);
                }
                String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    cursor.close();
                    return file;
                }
                throw new FileNotFoundException("File path was empty in media store for: " + uri);
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        private boolean f() {
            return this.d.checkSelfPermission("android.permission.ACCESS_MEDIA_LOCATION") == 0;
        }
    }

    @RequiresApi(29)
    /* compiled from: QMediaStoreUriLoader */
    public static final class c extends a<InputStream> {
        public c(Context context) {
            super(context, InputStream.class);
        }
    }

    @RequiresApi(29)
    /* compiled from: QMediaStoreUriLoader */
    public static final class b extends a<ParcelFileDescriptor> {
        public b(Context context) {
            super(context, ParcelFileDescriptor.class);
        }
    }

    /* compiled from: QMediaStoreUriLoader */
    public static abstract class a<DataT> implements o<Uri, DataT> {
        private final Context a;
        private final Class<DataT> b;

        a(Context context, Class<DataT> dataClass) {
            this.a = context;
            this.b = dataClass;
        }

        @NonNull
        public final n<Uri, DataT> b(@NonNull r multiFactory) {
            return new d(this.a, multiFactory.d(File.class, this.b), multiFactory.d(Uri.class, this.b), this.b);
        }
    }
}
