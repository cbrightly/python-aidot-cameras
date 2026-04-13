package org.spongycastle.asn1;

import java.util.Enumeration;

public class DLSet extends ASN1Set {
    private int f = -1;

    public DLSet() {
    }

    public DLSet(ASN1Encodable obj) {
        super(obj);
    }

    public DLSet(ASN1EncodableVector v) {
        super(v, false);
    }

    public DLSet(ASN1Encodable[] a) {
        super(a, false);
    }

    private int x() {
        if (this.f < 0) {
            int length = 0;
            Enumeration e = t();
            while (e.hasMoreElements()) {
                length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().n().g();
            }
            this.f = length;
        }
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = x();
        return StreamUtil.a(length) + 1 + length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        ASN1OutputStream dOut = out.b();
        int length = x();
        out.c(49);
        out.i(length);
        Enumeration e = t();
        while (e.hasMoreElements()) {
            dOut.j((ASN1Encodable) e.nextElement());
        }
    }
}
