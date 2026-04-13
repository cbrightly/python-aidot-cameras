package com.bumptech.glide.load.engine.cache;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.pool.FactoryPools;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: SafeKeyGenerator */
public class j {
    private final f<com.bumptech.glide.load.f, String> a = new f<>(1000);
    private final Pools.Pool<b> b = FactoryPools.d(10, new a());

    /* compiled from: SafeKeyGenerator */
    public class a implements FactoryPools.d<b> {
        a() {
        }

        /* renamed from: a */
        public b create() {
            try {
                return new b(MessageDigest.getInstance("SHA-256"));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String b(com.bumptech.glide.load.f key) {
        String safeKey;
        synchronized (this.a) {
            safeKey = this.a.g(key);
        }
        if (safeKey == null) {
            safeKey = a(key);
        }
        synchronized (this.a) {
            this.a.k(key, safeKey);
        }
        return safeKey;
    }

    private String a(com.bumptech.glide.load.f key) {
        b container = (b) i.d(this.b.acquire());
        try {
            key.updateDiskCacheKey(container.c);
            return com.bumptech.glide.util.j.w(container.c.digest());
        } finally {
            this.b.release(container);
        }
    }

    /* compiled from: SafeKeyGenerator */
    public static final class b implements FactoryPools.e {
        final MessageDigest c;
        private final com.bumptech.glide.util.pool.b d = com.bumptech.glide.util.pool.b.a();

        b(MessageDigest messageDigest) {
            this.c = messageDigest;
        }

        @NonNull
        public com.bumptech.glide.util.pool.b d() {
            return this.d;
        }
    }
}
