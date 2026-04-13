package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class GeneralSubtree extends ASN1Object {
    private static final BigInteger c = BigInteger.valueOf(0);
    private GeneralName d;
    private ASN1Integer f;
    private ASN1Integer q;

    private GeneralSubtree(ASN1Sequence seq) {
        this.d = GeneralName.f(seq.r(0));
        switch (seq.size()) {
            case 1:
                return;
            case 2:
                ASN1TaggedObject o = ASN1TaggedObject.o(seq.r(1));
                switch (o.r()) {
                    case 0:
                        this.f = ASN1Integer.p(o, false);
                        return;
                    case 1:
                        this.q = ASN1Integer.p(o, false);
                        return;
                    default:
                        throw new IllegalArgumentException("Bad tag number: " + o.r());
                }
            case 3:
                ASN1TaggedObject oMin = ASN1TaggedObject.o(seq.r(1));
                if (oMin.r() == 0) {
                    this.f = ASN1Integer.p(oMin, false);
                    ASN1TaggedObject oMax = ASN1TaggedObject.o(seq.r(2));
                    if (oMax.r() == 1) {
                        this.q = ASN1Integer.p(oMax, false);
                        return;
                    }
                    throw new IllegalArgumentException("Bad tag number for 'maximum': " + oMax.r());
                }
                throw new IllegalArgumentException("Bad tag number for 'minimum': " + oMin.r());
            default:
                throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
    }

    public static GeneralSubtree f(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GeneralSubtree) {
            return (GeneralSubtree) obj;
        }
        return new GeneralSubtree(ASN1Sequence.o(obj));
    }

    public GeneralName e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        ASN1Integer aSN1Integer = this.f;
        if (aSN1Integer != null && !aSN1Integer.r().equals(c)) {
            v.a(new DERTaggedObject(false, 0, this.f));
        }
        if (this.q != null) {
            v.a(new DERTaggedObject(false, 1, this.q));
        }
        return new DERSequence(v);
    }
}
