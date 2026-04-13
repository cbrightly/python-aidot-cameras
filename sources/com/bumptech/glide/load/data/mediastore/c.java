package com.bumptech.glide.load.data.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.d;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ThumbFetcher */
public class c implements d<InputStream> {
    private final Uri c;
    private final e d;
    private InputStream f;

    public static c e(Context context, Uri uri) {
        return c(context, uri, new a(context.getContentResolver()));
    }

    public static c f(Context context, Uri uri) {
        return c(context, uri, new b(context.getContentResolver()));
    }

    private static c c(Context context, Uri uri, d query) {
        return new c(uri, new e(com.bumptech.glide.b.c(context).j().g(), query, com.bumptech.glide.b.c(context).e(), context.getContentResolver()));
    }

    @VisibleForTesting
    c(Uri mediaStoreImageUri, e opener) {
        this.c = mediaStoreImageUri;
        this.d = opener;
    }

    public void d(@NonNull g priority, @NonNull d.a<? super InputStream> callback) {
        try {
            InputStream g = g();
            this.f = g;
            callback.e(g);
        } catch (FileNotFoundException e) {
            if (Log.isLoggable("MediaStoreThumbFetcher", 3)) {
                Log.d("MediaStoreThumbFetcher", "Failed to find thumbnail file", e);
            }
            callback.c(e);
        }
    }

    private InputStream g() {
        InputStream result = this.d.d(this.c);
        int orientation = -1;
        if (result != null) {
            orientation = this.d.a(this.c);
        }
        if (orientation != -1) {
            return new com.bumptech.glide.load.data.g(result, orientation);
        }
        return result;
    }

    public void b() {
        InputStream inputStream = this.f;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public void cancel() {
    }

    @NonNull
    public Class<InputStream> a() {
        return InputStream.class;
    }

    @NonNull
    public com.bumptech.glide.load.a getDataSource() {
        return com.bumptech.glide.load.a.LOCAL;
    }

    /* compiled from: ThumbFetcher */
    public static class b implements d {
        private static final String[] a = {"_data"};
        private final ContentResolver b;

        b(ContentResolver contentResolver) {
            this.b = contentResolver;
        }

        public Cursor a(Uri uri) {
            String videoId = uri.getLastPathSegment();
            return this.b.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, a, "kind = 1 AND video_id = ?", new String[]{videoId}, (String) null);
        }
    }

    /* compiled from: ThumbFetcher */
    public static class a implements d {
        private static final String[] a = {"_data"};
        private final ContentResolver b;

        a(ContentResolver contentResolver) {
            this.b = contentResolver;
        }

        public Cursor a(Uri uri) {
            String imageId = uri.getLastPathSegment();
            return this.b.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, a, "kind = 1 AND image_id = ?", new String[]{imageId}, (String) null);
        }
    }
}
