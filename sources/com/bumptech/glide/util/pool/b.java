package com.bumptech.glide.util.pool;

import androidx.annotation.NonNull;

/* compiled from: StateVerifier */
public abstract class b {
    /* access modifiers changed from: package-private */
    public abstract void b(boolean z);

    public abstract void c();

    @NonNull
    public static b a() {
        return new C0048b();
    }

    private b() {
    }

    /* renamed from: com.bumptech.glide.util.pool.b$b  reason: collision with other inner class name */
    /* compiled from: StateVerifier */
    public static class C0048b extends b {
        private volatile boolean a;

        C0048b() {
            super();
        }

        public void c() {
            if (this.a) {
                throw new IllegalStateException("Already released");
            }
        }

        public void b(boolean isRecycled) {
            this.a = isRecycled;
        }
    }
}
