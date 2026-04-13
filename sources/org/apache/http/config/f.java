package org.apache.http.config;

/* compiled from: SocketConfig */
public class f implements Cloneable {
    public static final f c = new a().a();
    private final int a1;
    private final int d;
    private final boolean f;
    private final int p0;
    private final int q;
    private final boolean x;
    private final boolean y;
    private final int z;

    f(int soTimeout, boolean soReuseAddress, int soLinger, boolean soKeepAlive, boolean tcpNoDelay, int sndBufSize, int rcvBufSize, int backlogSize) {
        this.d = soTimeout;
        this.f = soReuseAddress;
        this.q = soLinger;
        this.x = soKeepAlive;
        this.y = tcpNoDelay;
        this.z = sndBufSize;
        this.p0 = rcvBufSize;
        this.a1 = backlogSize;
    }

    public int f() {
        return this.d;
    }

    public boolean i() {
        return this.f;
    }

    public int e() {
        return this.q;
    }

    public boolean h() {
        return this.x;
    }

    public boolean j() {
        return this.y;
    }

    public int d() {
        return this.z;
    }

    public int c() {
        return this.p0;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public f clone() {
        return (f) super.clone();
    }

    public String toString() {
        return "[soTimeout=" + this.d + ", soReuseAddress=" + this.f + ", soLinger=" + this.q + ", soKeepAlive=" + this.x + ", tcpNoDelay=" + this.y + ", sndBufSize=" + this.z + ", rcvBufSize=" + this.p0 + ", backlogSize=" + this.a1 + "]";
    }

    public static a b() {
        return new a();
    }

    /* compiled from: SocketConfig */
    public static class a {
        private int a;
        private boolean b;
        private int c = -1;
        private boolean d;
        private boolean e = true;
        private int f;
        private int g;
        private int h;

        a() {
        }

        public a b(int soTimeout) {
            this.a = soTimeout;
            return this;
        }

        public f a() {
            return new f(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }
    }
}
