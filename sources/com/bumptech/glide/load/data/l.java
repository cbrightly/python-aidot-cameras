package com.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.data.d;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: LocalUriFetcher */
public abstract class l<T> implements d<T> {
    private final Uri c;
    private final ContentResolver d;
    private T f;

    /* access modifiers changed from: protected */
    public abstract void c(T t);

    /* access modifiers changed from: protected */
    public abstract T e(Uri uri, ContentResolver contentResolver);

    public l(ContentResolver contentResolver, Uri uri) {
        this.d = contentResolver;
        this.c = uri;
    }

    public final void d(@NonNull g priority, @NonNull d.a<? super T> callback) {
        try {
            T e = e(this.c, this.d);
            this.f = e;
            callback.e(e);
        } catch (FileNotFoundException e2) {
            if (Log.isLoggable("LocalUriFetcher", 3)) {
                Log.d("LocalUriFetcher", "Failed to open Uri", e2);
            }
            callback.c(e2);
        }
    }

    public void b() {
        T t = this.f;
        if (t != null) {
            try {
                c(t);
            } catch (IOException e) {
            }
        }
    }

    public void cancel() {
    }

    @NonNull
    public a getDataSource() {
        return a.LOCAL;
    }
}
