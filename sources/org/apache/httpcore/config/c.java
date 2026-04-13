package org.apache.httpcore.config;

/* compiled from: SocketConfig */
public class c implements Cloneable {
    public static final c c = new a().a();
    private final int a1;
    private final int d;
    private final boolean f;
    private final int p0;
    private final int q;
    private final boolean x;
    private final boolean y;
    private final int z;

    c(int soTimeout, boolean soReuseAddress, int soLinger, boolean soKeepAlive, boolean tcpNoDelay, int sndBufSize, int rcvBufSize, int backlogSize) {
        this.d = soTimeout;
        this.f = soReuseAddress;
        this.q = soLinger;
        this.x = soKeepAlive;
        this.y = tcpNoDelay;
        this.z = sndBufSize;
        this.p0 = rcvBufSize;
        this.a1 = backlogSize;
    }

    public int h() {
        return this.d;
    }

    public boolean j() {
        return this.f;
    }

    public int f() {
        return this.q;
    }

    public boolean i() {
        return this.x;
    }

    public boolean k() {
        return this.y;
    }

    public int e() {
        return this.z;
    }

    public int d() {
        return this.p0;
    }

    public int c() {
        return this.a1;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public c clone() {
        return (c) super.clone();
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

        public a h(int soTimeout) {
            this.a = soTimeout;
            return this;
        }

        public a g(boolean soReuseAddress) {
            this.b = soReuseAddress;
            return this;
        }

        public a f(int soLinger) {
            this.c = soLinger;
            return this;
        }

        public a e(boolean soKeepAlive) {
            this.d = soKeepAlive;
            return this;
        }

        public a i(boolean tcpNoDelay) {
            this.e = tcpNoDelay;
            return this;
        }

        public a d(int sndBufSize) {
            this.f = sndBufSize;
            return this;
        }

        public a c(int rcvBufSize) {
            this.g = rcvBufSize;
            return this;
        }

        public a b(int backlogSize) {
            this.h = backlogSize;
            return this;
        }

        public c a() {
            return new c(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }
    }
}
