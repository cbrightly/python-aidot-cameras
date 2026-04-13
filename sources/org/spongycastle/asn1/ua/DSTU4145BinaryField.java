package org.spongycastle.asn1.ua;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DSTU4145BinaryField extends ASN1Object {
    private int c;
    private int d;
    private int f;
    private int q;

    private DSTU4145BinaryField(ASN1Sequence seq) {
        this.c = ASN1Integer.o(seq.r(0)).q().intValue();
        if (seq.r(1) instanceof ASN1Integer) {
            this.d = ((ASN1Integer) seq.r(1)).q().intValue();
        } else if (seq.r(1) instanceof ASN1Sequence) {
            ASN1Sequence coefs = ASN1Sequence.o(seq.r(1));
            this.d = ASN1Integer.o(coefs.r(0)).q().intValue();
            this.f = ASN1Integer.o(coefs.r(1)).q().intValue();
            this.q = ASN1Integer.o(coefs.r(2)).q().intValue();
        } else {
            throw new IllegalArgumentException("object parse error");
        }
    }

    public static DSTU4145BinaryField e(Object obj) {
        if (obj instanceof DSTU4145BinaryField) {
            return (DSTU4145BinaryField) obj;
        }
        if (obj != null) {
            return new DSTU4145BinaryField(ASN1Sequence.o(obj));
        }
        return null;
    }

    public int i() {
        return this.c;
    }

    public int f() {
        return this.d;
    }

    public int g() {
        return this.f;
    }

    public int h() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        if (this.f == 0) {
            v.a(new ASN1Integer((long) this.d));
        } else {
            ASN1EncodableVector coefs = new ASN1EncodableVector();
            coefs.a(new ASN1Integer((long) this.d));
            coefs.a(new ASN1Integer((long) this.f));
            coefs.a(new ASN1Integer((long) this.q));
            v.a(new DERSequence(coefs));
        }
        return new DERSequence(v);
    }
}
