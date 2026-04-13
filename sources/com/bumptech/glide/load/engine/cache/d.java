package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.engine.cache.a;
import java.io.File;

/* compiled from: DiskLruCacheFactory */
public class d implements a.C0027a {
    private final long a;
    private final a b;

    /* compiled from: DiskLruCacheFactory */
    public interface a {
        File a();
    }

    public d(a cacheDirectoryGetter, long diskCacheSize) {
        this.a = diskCacheSize;
        this.b = cacheDirectoryGetter;
    }

    public a build() {
        File cacheDir = this.b.a();
        if (cacheDir == null) {
            return null;
        }
        if (cacheDir.isDirectory() || cacheDir.mkdirs()) {
            return e.c(cacheDir, this.a);
        }
        return null;
    }
}
