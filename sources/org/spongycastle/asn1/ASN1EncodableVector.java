package org.spongycastle.asn1;

import java.util.Vector;

public class ASN1EncodableVector {
    private final Vector a = new Vector();

    public void a(ASN1Encodable obj) {
        this.a.addElement(obj);
    }

    public ASN1Encodable b(int i) {
        return (ASN1Encodable) this.a.elementAt(i);
    }

    public int c() {
        return this.a.size();
    }
}
