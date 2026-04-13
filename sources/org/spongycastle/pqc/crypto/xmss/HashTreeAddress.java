package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.pqc.crypto.xmss.XMSSAddress;
import org.spongycastle.util.Pack;

public final class HashTreeAddress extends XMSSAddress {
    private final int e;
    private final int f;
    private final int g;

    private HashTreeAddress(Builder builder) {
        super(builder);
        this.e = 0;
        this.f = builder.e;
        this.g = builder.f;
    }

    public static class Builder extends XMSSAddress.Builder<Builder> {
        /* access modifiers changed from: private */
        public int e = 0;
        /* access modifiers changed from: private */
        public int f = 0;

        protected Builder() {
            super(2);
        }

        /* access modifiers changed from: protected */
        public Builder m(int val) {
            this.e = val;
            return this;
        }

        /* access modifiers changed from: protected */
        public Builder n(int val) {
            this.f = val;
            return this;
        }

        /* access modifiers changed from: protected */
        public XMSSAddress k() {
            return new HashTreeAddress(this);
        }

        /* access modifiers changed from: protected */
        /* renamed from: l */
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
        return this.f;
    }

    /* access modifiers changed from: protected */
    public int f() {
        return this.g;
    }
}
