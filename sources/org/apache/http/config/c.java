package org.apache.http.config;

/* compiled from: MessageConstraints */
public class c implements Cloneable {
    public static final c c = new a().a();
    private final int d;
    private final int f;

    c(int maxLineLength, int maxHeaderCount) {
        this.d = maxLineLength;
        this.f = maxHeaderCount;
    }

    public int d() {
        return this.d;
    }

    public int c() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public c clone() {
        return (c) super.clone();
    }

    public String toString() {
        return "[maxLineLength=" + this.d + ", maxHeaderCount=" + this.f + "]";
    }

    public static a b() {
        return new a();
    }

    /* compiled from: MessageConstraints */
    public static class a {
        private int a = -1;
        private int b = -1;

        a() {
        }

        public a c(int maxLineLength) {
            this.a = maxLineLength;
            return this;
        }

        public a b(int maxHeaderCount) {
            this.b = maxHeaderCount;
            return this;
        }

        public c a() {
            return new c(this.a, this.b);
        }
    }
}
