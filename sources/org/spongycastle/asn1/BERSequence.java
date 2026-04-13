package org.spongycastle.asn1;

import java.util.Enumeration;

public class BERSequence extends ASN1Sequence {
    public BERSequence() {
    }

    public BERSequence(ASN1Encodable obj) {
        super(obj);
    }

    public BERSequence(ASN1EncodableVector v) {
        super(v);
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = 0;
        Enumeration e = s();
        while (e.hasMoreElements()) {
            length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().g();
        }
        return length + 2 + 2;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.c(48);
        out.c(128);
        Enumeration e = s();
        while (e.hasMoreElements()) {
            out.j((ASN1Encodable) e.nextElement());
        }
        out.c(0);
        out.c(0);
    }
}
