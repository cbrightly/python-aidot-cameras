package org.spongycastle.asn1.ua;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;

public class DSTU4145ECBinary extends ASN1Object {
    BigInteger c = BigInteger.valueOf(0);
    DSTU4145BinaryField d;
    ASN1Integer f;
    ASN1OctetString q;
    ASN1Integer x;
    ASN1OctetString y;

    private DSTU4145ECBinary(ASN1Sequence seq) {
        int index = 0;
        if (seq.r(0) instanceof ASN1TaggedObject) {
            ASN1TaggedObject taggedVersion = (ASN1TaggedObject) seq.r(0);
            if (!taggedVersion.s() || taggedVersion.r() != 0) {
                throw new IllegalArgumentException("object parse error");
            }
            this.c = ASN1Integer.o(taggedVersion.d()).r();
            index = 0 + 1;
        }
        this.d = DSTU4145BinaryField.e(seq.r(index));
        int index2 = index + 1;
        this.f = ASN1Integer.o(seq.r(index2));
        int index3 = index2 + 1;
        this.q = ASN1OctetString.o(seq.r(index3));
        int index4 = index3 + 1;
        this.x = ASN1Integer.o(seq.r(index4));
        this.y = ASN1OctetString.o(seq.r(index4 + 1));
    }

    public static DSTU4145ECBinary i(Object obj) {
        if (obj instanceof DSTU4145ECBinary) {
            return (DSTU4145ECBinary) obj;
        }
        if (obj != null) {
            return new DSTU4145ECBinary(ASN1Sequence.o(obj));
        }
        return null;
    }

    public DSTU4145BinaryField g() {
        return this.d;
    }

    public BigInteger e() {
        return this.f.r();
    }

    public byte[] f() {
        return Arrays.h(this.q.q());
    }

    public BigInteger k() {
        return this.x.r();
    }

    public byte[] h() {
        return Arrays.h(this.y.q());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c.compareTo(BigInteger.valueOf(0)) != 0) {
            v.a(new DERTaggedObject(true, 0, new ASN1Integer(this.c)));
        }
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        v.a(this.x);
        v.a(this.y);
        return new DERSequence(v);
    }
}
