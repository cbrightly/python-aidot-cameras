package com.bumptech.glide.integration.webp.decoder;

/* compiled from: WebpFrameCacheStrategy */
public final class n {
    public static final n a = new b().f().c();
    public static final n b = new b().e().c();
    public static final n c = new b().d().c();
    private c d;
    private int e;

    /* compiled from: WebpFrameCacheStrategy */
    public enum c {
        CACHE_NONE,
        CACHE_LIMITED,
        CACHE_AUTO,
        CACHE_ALL
    }

    private n(b builder) {
        this.d = builder.a;
        this.e = builder.b;
    }

    public boolean c() {
        return this.d == c.CACHE_NONE;
    }

    public boolean a() {
        return this.d == c.CACHE_ALL;
    }

    public int b() {
        return this.e;
    }

    /* compiled from: WebpFrameCacheStrategy */
    public static final class b {
        /* access modifiers changed from: private */
        public c a;
        /* access modifiers changed from: private */
        public int b;

        public b f() {
            this.a = c.CACHE_NONE;
            return this;
        }

        public b d() {
            this.a = c.CACHE_ALL;
            return this;
        }

        public b e() {
            this.a = c.CACHE_AUTO;
            return this;
        }

        public n c() {
            return new n(this);
        }
    }
}
