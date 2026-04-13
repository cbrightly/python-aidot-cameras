package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.util.Pack;

public abstract class XMSSAddress {
    private final int a;
    private final long b;
    private final int c;
    private final int d;

    protected XMSSAddress(Builder builder) {
        this.a = builder.b;
        this.b = builder.c;
        this.c = builder.a;
        this.d = builder.d;
    }

    public static abstract class Builder<T extends Builder> {
        /* access modifiers changed from: private */
        public final int a;
        /* access modifiers changed from: private */
        public int b = 0;
        /* access modifiers changed from: private */
        public long c = 0;
        /* access modifiers changed from: private */
        public int d = 0;

        /* access modifiers changed from: protected */
        public abstract T e();

        protected Builder(int type) {
            this.a = type;
        }

        /* access modifiers changed from: protected */
        public T g(int val) {
            this.b = val;
            return e();
        }

        /* access modifiers changed from: protected */
        public T h(long val) {
            this.c = val;
            return e();
        }

        /* access modifiers changed from: protected */
        public T f(int val) {
            this.d = val;
            return e();
        }
    }

    /* access modifiers changed from: protected */
    public byte[] d() {
        byte[] byteRepresentation = new byte[32];
        Pack.d(this.a, byteRepresentation, 0);
        Pack.p(this.b, byteRepresentation, 4);
        Pack.d(this.c, byteRepresentation, 12);
        Pack.d(this.d, byteRepresentation, 28);
        return byteRepresentation;
    }

    /* access modifiers changed from: protected */
    public final int b() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final long c() {
        return this.b;
    }

    public final int a() {
        return this.d;
    }
}
