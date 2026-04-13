package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CertificatePair extends ASN1Object {
    private Certificate c;
    private Certificate d;

    public static CertificatePair f(Object obj) {
        if (obj == null || (obj instanceof CertificatePair)) {
            return (CertificatePair) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new CertificatePair((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    private CertificatePair(ASN1Sequence seq) {
        if (seq.size() == 1 || seq.size() == 2) {
            Enumeration e = seq.s();
            while (e.hasMoreElements()) {
                ASN1TaggedObject o = ASN1TaggedObject.o(e.nextElement());
                if (o.r() == 0) {
                    this.c = Certificate.g(o, true);
                } else if (o.r() == 1) {
                    this.d = Certificate.g(o, true);
                } else {
                    throw new IllegalArgumentException("Bad tag number: " + o.r());
                }
            }
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public CertificatePair(Certificate forward, Certificate reverse) {
        this.c = forward;
        this.d = reverse;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        if (this.c != null) {
            vec.a(new DERTaggedObject(0, this.c));
        }
        if (this.d != null) {
            vec.a(new DERTaggedObject(1, this.d));
        }
        return new DERSequence(vec);
    }

    public Certificate e() {
        return this.c;
    }

    public Certificate g() {
        return this.d;
    }
}
