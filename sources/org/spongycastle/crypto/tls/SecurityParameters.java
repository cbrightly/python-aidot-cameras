package org.spongycastle.crypto.tls;

import org.spongycastle.util.Arrays;

public class SecurityParameters {
    int a = -1;
    int b = -1;
    short c = 0;
    int d = -1;
    int e = -1;
    byte[] f = null;
    byte[] g = null;
    byte[] h = null;
    byte[] i = null;
    byte[] j = null;
    byte[] k = null;
    short l = -1;
    boolean m = false;
    boolean n = false;
    boolean o = false;

    /* access modifiers changed from: package-private */
    public void a() {
        byte[] bArr = this.f;
        if (bArr != null) {
            Arrays.F(bArr, (byte) 0);
            this.f = null;
        }
    }

    public int b() {
        return this.b;
    }

    public short d() {
        return this.c;
    }

    public int g() {
        return this.d;
    }

    public int k() {
        return this.e;
    }

    public byte[] e() {
        return this.f;
    }

    public byte[] c() {
        return this.g;
    }

    public byte[] i() {
        return this.h;
    }

    public byte[] j() {
        return this.i;
    }

    public byte[] f() {
        return this.j;
    }

    public byte[] h() {
        return this.k;
    }
}
