package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import com.bumptech.glide.load.engine.cache.d;
import java.io.File;

/* compiled from: InternalCacheDiskCacheFactory */
public final class f extends d {
    public f(Context context) {
        this(context, "image_manager_disk_cache", 262144000);
    }

    /* compiled from: InternalCacheDiskCacheFactory */
    public class a implements d.a {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;

        a(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public File a() {
            File cacheDirectory = this.a.getCacheDir();
            if (cacheDirectory == null) {
                return null;
            }
            if (this.b != null) {
                return new File(cacheDirectory, this.b);
            }
            return cacheDirectory;
        }
    }

    public f(Context context, String diskCacheName, long diskCacheSize) {
        super(new a(context, diskCacheName), diskCacheSize);
    }
}
