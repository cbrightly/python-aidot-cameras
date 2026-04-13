package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.g;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.data.d;
import java.io.IOException;

/* compiled from: AssetPathFetcher */
public abstract class b<T> implements d<T> {
    private final String c;
    private final AssetManager d;
    private T f;

    /* access modifiers changed from: protected */
    public abstract void c(T t);

    /* access modifiers changed from: protected */
    public abstract T e(AssetManager assetManager, String str);

    public b(AssetManager assetManager, String assetPath) {
        this.d = assetManager;
        this.c = assetPath;
    }

    public void d(@NonNull g priority, @NonNull d.a<? super T> callback) {
        try {
            T e = e(this.d, this.c);
            this.f = e;
            callback.e(e);
        } catch (IOException e2) {
            if (Log.isLoggable("AssetPathFetcher", 3)) {
                Log.d("AssetPathFetcher", "Failed to load data from asset manager", e2);
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
