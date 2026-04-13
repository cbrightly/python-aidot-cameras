package com.bumptech.glide.load.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.signature.d;
import java.io.File;
import java.io.FileNotFoundException;

/* compiled from: MediaStoreFileLoader */
public final class k implements n<Uri, File> {
    private final Context a;

    public k(Context context) {
        this.a = context;
    }

    /* renamed from: c */
    public n.a<File> b(@NonNull Uri uri, int width, int height, @NonNull i options) {
        return new n.a<>(new d(uri), new b(this.a, uri));
    }

    /* renamed from: d */
    public boolean a(@NonNull Uri uri) {
        return com.bumptech.glide.load.data.mediastore.b.b(uri);
    }

    /* compiled from: MediaStoreFileLoader */
    public static class b implements com.bumptech.glide.load.data.d<File> {
        private static final String[] c = {"_data"};
        private final Context d;
        private final Uri f;

        b(Context context, Uri uri) {
            this.d = context;
            this.f = uri;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super File> callback) {
            Cursor cursor = this.d.getContentResolver().query(this.f, c, (String) null, (String[]) null, (String) null);
            String filePath = null;
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (TextUtils.isEmpty(filePath)) {
                callback.c(new FileNotFoundException("Failed to find file path for: " + this.f));
                return;
            }
            callback.e(new File(filePath));
        }

        public void b() {
        }

        public void cancel() {
        }

        @NonNull
        public Class<File> a() {
            return File.class;
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return com.bumptech.glide.load.a.LOCAL;
        }
    }

    /* compiled from: MediaStoreFileLoader */
    public static final class a implements o<Uri, File> {
        private final Context a;

        public a(Context context) {
            this.a = context;
        }

        @NonNull
        public n<Uri, File> b(r multiFactory) {
            return new k(this.a);
        }
    }
}
