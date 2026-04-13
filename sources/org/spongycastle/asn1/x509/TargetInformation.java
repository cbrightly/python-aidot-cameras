package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class TargetInformation extends ASN1Object {
    private ASN1Sequence c;

    public static TargetInformation e(Object obj) {
        if (obj instanceof TargetInformation) {
            return (TargetInformation) obj;
        }
        if (obj != null) {
            return new TargetInformation(ASN1Sequence.o(obj));
        }
        return null;
    }

    private TargetInformation(ASN1Sequence seq) {
        this.c = seq;
    }

    public Targets[] f() {
        Targets[] copy = new Targets[this.c.size()];
        int count = 0;
        Enumeration e = this.c.s();
        while (e.hasMoreElements()) {
            copy[count] = Targets.e(e.nextElement());
            count++;
        }
        return copy;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
