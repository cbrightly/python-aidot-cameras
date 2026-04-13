package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class Targets extends ASN1Object {
    private ASN1Sequence c;

    public static Targets e(Object obj) {
        if (obj instanceof Targets) {
            return (Targets) obj;
        }
        if (obj != null) {
            return new Targets(ASN1Sequence.o(obj));
        }
        return null;
    }

    private Targets(ASN1Sequence targets) {
        this.c = targets;
    }

    public Target[] f() {
        Target[] targs = new Target[this.c.size()];
        int count = 0;
        Enumeration e = this.c.s();
        while (e.hasMoreElements()) {
            targs[count] = Target.e(e.nextElement());
            count++;
        }
        return targs;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
