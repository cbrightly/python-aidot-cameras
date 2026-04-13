package org.spongycastle.asn1;

import java.util.Enumeration;

public class DERSequence extends ASN1Sequence {
    private int d = -1;

    public DERSequence() {
    }

    public DERSequence(ASN1Encodable obj) {
        super(obj);
    }

    public DERSequence(ASN1EncodableVector v) {
        super(v);
    }

    public DERSequence(ASN1Encodable[] array) {
        super(array);
    }

    private int u() {
        if (this.d < 0) {
            int length = 0;
            Enumeration e = s();
            while (e.hasMoreElements()) {
                length += ((ASN1Encodable) e.nextElement()).toASN1Primitive().k().g();
            }
            this.d = length;
        }
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = u();
        return StreamUtil.a(length) + 1 + length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        ASN1OutputStream dOut = out.a();
        int length = u();
        out.c(48);
        out.i(length);
        Enumeration e = s();
        while (e.hasMoreElements()) {
            dOut.j((ASN1Encodable) e.nextElement());
        }
    }
}
