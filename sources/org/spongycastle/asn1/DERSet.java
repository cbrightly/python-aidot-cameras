package org.spongycastle.asn1;

import java.util.Enumeration;

public class DERSet extends ASN1Set {
    private int f = -1;

    public DERSet() {
    }

    public DERSet(ASN1Encodable obj) {
        super(obj);
    }

    public DERSet(ASN1EncodableVector v) {
        super(v, true);
    }

    public DERSet(ASN1Encodable[] a) {
        super(a, true);
    }

    DERSet(ASN1EncodableVector v, boolean doSort) {
        super(v, doSort);
    }

    private int x() {
        if (this.f < 0) {
            int length = 0;
            Enumeration e = t();
            while (e.hasMoreElements()) {
                length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().k().g();
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
        ASN1OutputStream dOut = out.a();
        int length = x();
        out.c(49);
        out.i(length);
        Enumeration e = t();
        while (e.hasMoreElements()) {
            dOut.j((ASN1Encodable) e.nextElement());
        }
    }
}
