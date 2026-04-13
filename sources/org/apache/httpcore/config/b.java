package org.apache.httpcore.config;

/* compiled from: MessageConstraints */
public class b implements Cloneable {
    public static final b c = new a().a();
    private final int d;
    private final int f;

    b(int maxLineLength, int maxHeaderCount) {
        this.d = maxLineLength;
        this.f = maxHeaderCount;
    }

    public int c() {
        return this.d;
    }

    public int b() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public b clone() {
        return (b) super.clone();
    }

    public String toString() {
        return "[maxLineLength=" + this.d + ", maxHeaderCount=" + this.f + "]";
    }

    /* compiled from: MessageConstraints */
    public static class a {
        private int a = -1;
        private int b = -1;

        a() {
        }

        public b a() {
            return new b(this.a, this.b);
        }
    }
}
