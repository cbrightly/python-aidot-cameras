package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.e;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.InputStream;

/* compiled from: InputStreamRewinder */
public final class k implements e<InputStream> {
    private final RecyclableBufferedInputStream a;

    public k(InputStream is, b byteArrayPool) {
        RecyclableBufferedInputStream recyclableBufferedInputStream = new RecyclableBufferedInputStream(is, byteArrayPool);
        this.a = recyclableBufferedInputStream;
        recyclableBufferedInputStream.mark(5242880);
    }

    @NonNull
    /* renamed from: d */
    public InputStream a() {
        this.a.reset();
        return this.a;
    }

    public void b() {
        this.a.release();
    }

    public void c() {
        this.a.c();
    }

    /* compiled from: InputStreamRewinder */
    public static final class a implements e.a<InputStream> {
        private final b a;

        public a(b byteArrayPool) {
            this.a = byteArrayPool;
        }

        @NonNull
        /* renamed from: c */
        public e<InputStream> b(InputStream data) {
            return new k(data, this.a);
        }

        @NonNull
        public Class<InputStream> a() {
            return InputStream.class;
        }
    }
}
