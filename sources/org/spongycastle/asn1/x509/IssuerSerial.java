package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class IssuerSerial extends ASN1Object {
    GeneralNames c;
    ASN1Integer d;
    DERBitString f;

    public static IssuerSerial e(Object obj) {
        if (obj instanceof IssuerSerial) {
            return (IssuerSerial) obj;
        }
        if (obj != null) {
            return new IssuerSerial(ASN1Sequence.o(obj));
        }
        return null;
    }

    public static IssuerSerial f(ASN1TaggedObject obj, boolean explicit) {
        return e(ASN1Sequence.p(obj, explicit));
    }

    private IssuerSerial(ASN1Sequence seq) {
        if (seq.size() == 2 || seq.size() == 3) {
            this.c = GeneralNames.e(seq.r(0));
            this.d = ASN1Integer.o(seq.r(1));
            if (seq.size() == 3) {
                this.f = DERBitString.x(seq.r(2));
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public GeneralNames g() {
        return this.c;
    }

    public ASN1Integer h() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        DERBitString dERBitString = this.f;
        if (dERBitString != null) {
            v.a(dERBitString);
        }
        return new DERSequence(v);
    }
}
