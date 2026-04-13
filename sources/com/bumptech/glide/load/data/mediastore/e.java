package com.bumptech.glide.load.data.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: ThumbnailStreamOpener */
public class e {
    private static final a a = new a();
    private final a b;
    private final d c;
    private final b d;
    private final ContentResolver e;
    private final List<ImageHeaderParser> f;

    e(List<ImageHeaderParser> parsers, d query, b byteArrayPool, ContentResolver contentResolver) {
        this(parsers, a, query, byteArrayPool, contentResolver);
    }

    e(List<ImageHeaderParser> parsers, a service, d query, b byteArrayPool, ContentResolver contentResolver) {
        this.b = service;
        this.c = query;
        this.d = byteArrayPool;
        this.e = contentResolver;
        this.f = parsers;
    }

    /* access modifiers changed from: package-private */
    public int a(Uri uri) {
        InputStream is = null;
        try {
            InputStream is2 = this.e.openInputStream(uri);
            int b2 = com.bumptech.glide.load.e.b(this.f, is2, this.d);
            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e2) {
                }
            }
            return b2;
        } catch (IOException | NullPointerException e3) {
            if (Log.isLoggable("ThumbStreamOpener", 3)) {
                Log.d("ThumbStreamOpener", "Failed to open uri: " + uri, e3);
            }
            if (is == null) {
                return -1;
            }
            try {
                is.close();
                return -1;
            } catch (IOException e4) {
                return -1;
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
    }

    public InputStream d(Uri uri) {
        String path = b(uri);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = this.b.b(path);
        if (!c(file)) {
            return null;
        }
        Uri thumbnailUri = Uri.fromFile(file);
        try {
            return this.e.openInputStream(thumbnailUri);
        } catch (NullPointerException e2) {
            throw ((FileNotFoundException) new FileNotFoundException("NPE opening uri: " + uri + " -> " + thumbnailUri).initCause(e2));
        }
    }

    @Nullable
    private String b(@NonNull Uri uri) {
        Cursor cursor = null;
        try {
            Cursor cursor2 = this.c.a(uri);
            if (cursor2 == null || !cursor2.moveToFirst()) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return null;
            }
            String string = cursor2.getString(0);
            cursor2.close();
            return string;
        } catch (SecurityException e2) {
            if (Log.isLoggable("ThumbStreamOpener", 3)) {
                Log.d("ThumbStreamOpener", "Failed to query for thumbnail for Uri: " + uri, e2);
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private boolean c(File file) {
        return this.b.a(file) && 0 < this.b.c(file);
    }
}
