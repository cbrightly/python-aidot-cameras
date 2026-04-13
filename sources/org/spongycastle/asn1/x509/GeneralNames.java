package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Strings;

public class GeneralNames extends ASN1Object {
    private final GeneralName[] c;

    public static GeneralNames e(Object obj) {
        if (obj instanceof GeneralNames) {
            return (GeneralNames) obj;
        }
        if (obj != null) {
            return new GeneralNames(ASN1Sequence.o(obj));
        }
        return null;
    }

    public static GeneralNames f(ASN1TaggedObject obj, boolean explicit) {
        return e(ASN1Sequence.p(obj, explicit));
    }

    public GeneralNames(GeneralName name) {
        this.c = new GeneralName[]{name};
    }

    private GeneralNames(ASN1Sequence seq) {
        this.c = new GeneralName[seq.size()];
        for (int i = 0; i != seq.size(); i++) {
            this.c[i] = GeneralName.f(seq.r(i));
        }
    }

    public GeneralName[] g() {
        GeneralName[] generalNameArr = this.c;
        GeneralName[] tmp = new GeneralName[generalNameArr.length];
        System.arraycopy(generalNameArr, 0, tmp, 0, generalNameArr.length);
        return tmp;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.c);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String sep = Strings.d();
        buf.append("GeneralNames:");
        buf.append(sep);
        for (int i = 0; i != this.c.length; i++) {
            buf.append("    ");
            buf.append(this.c[i]);
            buf.append(sep);
        }
        return buf.toString();
    }
}
