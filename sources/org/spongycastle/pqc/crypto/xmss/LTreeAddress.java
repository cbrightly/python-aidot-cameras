package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.pqc.crypto.xmss.XMSSAddress;
import org.spongycastle.util.Pack;

public final class LTreeAddress extends XMSSAddress {
    private final int e;
    private final int f;
    private final int g;

    private LTreeAddress(Builder builder) {
        super(builder);
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
    }

    public static class Builder extends XMSSAddress.Builder<Builder> {
        /* access modifiers changed from: private */
        public int e = 0;
        /* access modifiers changed from: private */
        public int f = 0;
        /* access modifiers changed from: private */
        public int g = 0;

        protected Builder() {
            super(1);
        }

        /* access modifiers changed from: protected */
        public Builder n(int val) {
            this.e = val;
            return this;
        }

        /* access modifiers changed from: protected */
        public Builder o(int val) {
            this.f = val;
            return this;
        }

        /* access modifiers changed from: protected */
        public Builder p(int val) {
            this.g = val;
            return this;
        }

        /* access modifiers changed from: protected */
        public XMSSAddress l() {
            return new LTreeAddress(this);
        }

        /* access modifiers changed from: protected */
        /* renamed from: m */
        public Builder e() {
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] d() {
        byte[] byteRepresentation = super.d();
        Pack.d(this.e, byteRepresentation, 16);
        Pack.d(this.f, byteRepresentation, 20);
        Pack.d(this.g, byteRepresentation, 24);
        return byteRepresentation;
    }

    /* access modifiers changed from: protected */
    public int e() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public int f() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public int g() {
        return this.g;
    }
}
