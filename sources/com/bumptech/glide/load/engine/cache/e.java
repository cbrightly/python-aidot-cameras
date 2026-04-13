package com.bumptech.glide.load.engine.cache;

import android.util.Log;
import com.bumptech.glide.disklrucache.a;
import com.bumptech.glide.load.engine.cache.a;
import com.bumptech.glide.load.f;
import java.io.File;
import java.io.IOException;

/* compiled from: DiskLruCacheWrapper */
public class e implements a {
    private final j a;
    private final File b;
    private final long c;
    private final c d = new c();
    private a e;

    public static a c(File directory, long maxSize) {
        return new e(directory, maxSize);
    }

    @Deprecated
    protected e(File directory, long maxSize) {
        this.b = directory;
        this.c = maxSize;
        this.a = new j();
    }

    private synchronized a d() {
        if (this.e == null) {
            this.e = a.J(this.b, 1, 1, this.c);
        }
        return this.e;
    }

    public File b(f key) {
        String safeKey = this.a.b(key);
        if (Log.isLoggable("DiskLruCacheWrapper", 2)) {
            Log.v("DiskLruCacheWrapper", "Get: Obtained: " + safeKey + " for for Key: " + key);
        }
        try {
            a.e value = d().E(safeKey);
            if (value != null) {
                return value.a(0);
            }
            return null;
        } catch (IOException e2) {
            if (!Log.isLoggable("DiskLruCacheWrapper", 5)) {
                return null;
            }
            Log.w("DiskLruCacheWrapper", "Unable to get from disk cache", e2);
            return null;
        }
    }

    public void a(f key, a.b writer) {
        a.c editor;
        String safeKey = this.a.b(key);
        this.d.a(safeKey);
        try {
            if (Log.isLoggable("DiskLruCacheWrapper", 2)) {
                Log.v("DiskLruCacheWrapper", "Put: Obtained: " + safeKey + " for for Key: " + key);
            }
            try {
                com.bumptech.glide.disklrucache.a diskCache = d();
                if (diskCache.E(safeKey) == null) {
                    editor = diskCache.v(safeKey);
                    if (editor != null) {
                        if (writer.d(editor.f(0))) {
                            editor.e();
                        }
                        editor.b();
                        this.d.b(safeKey);
                        return;
                    }
                    throw new IllegalStateException("Had two simultaneous puts for: " + safeKey);
                }
            } catch (IOException e2) {
                if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                    Log.w("DiskLruCacheWrapper", "Unable to put to disk cache", e2);
                }
            } catch (Throwable th) {
                editor.b();
                throw th;
            }
        } finally {
            this.d.b(safeKey);
        }
    }
}
