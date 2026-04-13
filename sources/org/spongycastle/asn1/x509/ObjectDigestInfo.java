package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class ObjectDigestInfo extends ASN1Object {
    ASN1Enumerated c;
    ASN1ObjectIdentifier d;
    AlgorithmIdentifier f;
    DERBitString q;

    public static ObjectDigestInfo g(Object obj) {
        if (obj instanceof ObjectDigestInfo) {
            return (ObjectDigestInfo) obj;
        }
        if (obj != null) {
            return new ObjectDigestInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public static ObjectDigestInfo h(ASN1TaggedObject obj, boolean explicit) {
        return g(ASN1Sequence.p(obj, explicit));
    }

    private ObjectDigestInfo(ASN1Sequence seq) {
        if (seq.size() > 4 || seq.size() < 3) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        this.c = ASN1Enumerated.p(seq.r(0));
        int offset = 0;
        if (seq.size() == 4) {
            this.d = ASN1ObjectIdentifier.t(seq.r(1));
            offset = 0 + 1;
        }
        this.f = AlgorithmIdentifier.f(seq.r(offset + 1));
        this.q = DERBitString.x(seq.r(offset + 2));
    }

    public ASN1Enumerated f() {
        return this.c;
    }

    public AlgorithmIdentifier e() {
        return this.f;
    }

    public DERBitString i() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.d;
        if (aSN1ObjectIdentifier != null) {
            v.a(aSN1ObjectIdentifier);
        }
        v.a(this.f);
        v.a(this.q);
        return new DERSequence(v);
    }
}
