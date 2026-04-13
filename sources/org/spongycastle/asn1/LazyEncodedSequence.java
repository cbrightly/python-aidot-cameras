package org.spongycastle.asn1;

import java.util.Enumeration;

public class LazyEncodedSequence extends ASN1Sequence {
    private byte[] d;

    LazyEncodedSequence(byte[] encoded) {
        this.d = encoded;
    }

    private void u() {
        Enumeration en = new LazyConstructionEnumeration(this.d);
        while (en.hasMoreElements()) {
            this.c.addElement(en.nextElement());
        }
        this.d = null;
    }

    public synchronized ASN1Encodable r(int index) {
        if (this.d != null) {
            u();
        }
        return super.r(index);
    }

    public synchronized Enumeration s() {
        byte[] bArr = this.d;
        if (bArr == null) {
            return super.s();
        }
        return new LazyConstructionEnumeration(bArr);
    }

    public synchronized int size() {
        if (this.d != null) {
            u();
        }
        return super.size();
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        if (this.d != null) {
            u();
        }
        return super.k();
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        if (this.d != null) {
            u();
        }
        return super.n();
    }

    /* access modifiers changed from: package-private */
    public int g() {
        byte[] bArr = this.d;
        if (bArr != null) {
            return StreamUtil.a(bArr.length) + 1 + this.d.length;
        }
        return super.n().g();
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        byte[] bArr = this.d;
        if (bArr != null) {
            out.g(48, bArr);
        } else {
            super.n().f(out);
        }
    }
}
