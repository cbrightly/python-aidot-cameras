package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x500.X500Name;

public class Certificate extends ASN1Object {
    ASN1Sequence c;
    TBSCertificate d;
    AlgorithmIdentifier f;
    DERBitString q;

    public static Certificate g(ASN1TaggedObject obj, boolean explicit) {
        return f(ASN1Sequence.p(obj, explicit));
    }

    public static Certificate f(Object obj) {
        if (obj instanceof Certificate) {
            return (Certificate) obj;
        }
        if (obj != null) {
            return new Certificate(ASN1Sequence.o(obj));
        }
        return null;
    }

    private Certificate(ASN1Sequence seq) {
        this.c = seq;
        if (seq.size() == 3) {
            this.d = TBSCertificate.g(seq.r(0));
            this.f = AlgorithmIdentifier.f(seq.r(1));
            this.q = DERBitString.x(seq.r(2));
            return;
        }
        throw new IllegalArgumentException("sequence wrong size for a certificate");
    }

    public TBSCertificate r() {
        return this.d;
    }

    public int s() {
        return this.d.s();
    }

    public ASN1Integer i() {
        return this.d.k();
    }

    public X500Name h() {
        return this.d.h();
    }

    public Time o() {
        return this.d.o();
    }

    public Time e() {
        return this.d.e();
    }

    public X500Name p() {
        return this.d.p();
    }

    public SubjectPublicKeyInfo q() {
        return this.d.q();
    }

    public AlgorithmIdentifier n() {
        return this.f;
    }

    public DERBitString k() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
