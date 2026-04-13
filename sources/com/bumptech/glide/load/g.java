package com.bumptech.glide.load;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: MultiTransformation */
public class g<T> implements m<T> {
    private final Collection<? extends m<T>> b;

    @SafeVarargs
    public g(@NonNull m<T>... transformations) {
        if (transformations.length != 0) {
            this.b = Arrays.asList(transformations);
            return;
        }
        throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
    }

    @NonNull
    public t<T> transform(@NonNull Context context, @NonNull t<T> resource, int outWidth, int outHeight) {
        t<T> tVar = resource;
        Iterator<? extends m<T>> it = this.b.iterator();
        while (it.hasNext()) {
            t<T> transform = ((m) it.next()).transform(context, tVar, outWidth, outHeight);
            if (tVar != null && !tVar.equals(resource) && !tVar.equals(transform)) {
                tVar.recycle();
            }
            tVar = transform;
        }
        return tVar;
    }

    public boolean equals(Object o) {
        if (o instanceof g) {
            return this.b.equals(((g) o).b);
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        Iterator<? extends m<T>> it = this.b.iterator();
        while (it.hasNext()) {
            ((m) it.next()).updateDiskCacheKey(messageDigest);
        }
    }
}
