package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class AlgorithmIdentifier extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;

    public static AlgorithmIdentifier g(ASN1TaggedObject obj, boolean explicit) {
        return f(ASN1Sequence.p(obj, explicit));
    }

    public static AlgorithmIdentifier f(Object obj) {
        if (obj instanceof AlgorithmIdentifier) {
            return (AlgorithmIdentifier) obj;
        }
        if (obj != null) {
            return new AlgorithmIdentifier(ASN1Sequence.o(obj));
        }
        return null;
    }

    public AlgorithmIdentifier(ASN1ObjectIdentifier algorithm) {
        this.c = algorithm;
    }

    public AlgorithmIdentifier(ASN1ObjectIdentifier algorithm, ASN1Encodable parameters) {
        this.c = algorithm;
        this.d = parameters;
    }

    private AlgorithmIdentifier(ASN1Sequence seq) {
        if (seq.size() < 1 || seq.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        this.c = ASN1ObjectIdentifier.t(seq.r(0));
        if (seq.size() == 2) {
            this.d = seq.r(1);
        } else {
            this.d = null;
        }
    }

    public ASN1ObjectIdentifier e() {
        return this.c;
    }

    public ASN1Encodable h() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            v.a(aSN1Encodable);
        }
        return new DERSequence(v);
    }
}
