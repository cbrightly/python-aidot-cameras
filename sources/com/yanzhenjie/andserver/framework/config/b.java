package com.yanzhenjie.andserver.framework.config;

import java.io.File;

/* compiled from: Multipart */
public class b {
    private final long a;
    private final long b;
    private final int c;
    private final File d;

    public static C0231b e() {
        return new C0231b();
    }

    private b(C0231b builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
    }

    public long a() {
        return this.a;
    }

    public long b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public File d() {
        return this.d;
    }

    /* renamed from: com.yanzhenjie.andserver.framework.config.b$b  reason: collision with other inner class name */
    /* compiled from: Multipart */
    public static class C0231b {
        /* access modifiers changed from: private */
        public long a;
        /* access modifiers changed from: private */
        public long b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public File d;

        private C0231b() {
        }

        public C0231b e(long allFileMaxSize) {
            this.a = allFileMaxSize;
            return this;
        }

        public C0231b g(long fileMaxSize) {
            this.b = fileMaxSize;
            return this;
        }

        public C0231b h(int maxInMemorySize) {
            this.c = maxInMemorySize;
            return this;
        }

        public C0231b i(File uploadTempDir) {
            this.d = uploadTempDir;
            return this;
        }

        public b f() {
            return new b(this);
        }
    }
}
