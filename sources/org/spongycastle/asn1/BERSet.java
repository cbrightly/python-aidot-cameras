package org.spongycastle.asn1;

import java.util.Enumeration;

public class BERSet extends ASN1Set {
    public BERSet() {
    }

    public BERSet(ASN1Encodable obj) {
        super(obj);
    }

    public BERSet(ASN1EncodableVector v) {
        super(v, false);
    }

    public BERSet(ASN1Encodable[] a) {
        super(a, false);
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = 0;
        Enumeration e = t();
        while (e.hasMoreElements()) {
            length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().g();
        }
        return length + 2 + 2;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.c(49);
        out.c(128);
        Enumeration e = t();
        while (e.hasMoreElements()) {
            out.j((ASN1Encodable) e.nextElement());
        }
        out.c(0);
        out.c(0);
    }
}
