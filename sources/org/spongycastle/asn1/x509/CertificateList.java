package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;

public class CertificateList extends ASN1Object {
    TBSCertList c;
    AlgorithmIdentifier d;
    DERBitString f;
    boolean q = false;
    int x;

    public static CertificateList e(Object obj) {
        if (obj instanceof CertificateList) {
            return (CertificateList) obj;
        }
        if (obj != null) {
            return new CertificateList(ASN1Sequence.o(obj));
        }
        return null;
    }

    public CertificateList(ASN1Sequence seq) {
        if (seq.size() == 3) {
            this.c = TBSCertList.f(seq.r(0));
            this.d = AlgorithmIdentifier.f(seq.r(1));
            this.f = DERBitString.x(seq.r(2));
            return;
        }
        throw new IllegalArgumentException("sequence wrong size for CertificateList");
    }

    public TBSCertList n() {
        return this.c;
    }

    public Enumeration h() {
        return this.c.i();
    }

    public AlgorithmIdentifier k() {
        return this.d;
    }

    public DERBitString i() {
        return this.f;
    }

    public int p() {
        return this.c.o();
    }

    public X500Name f() {
        return this.c.g();
    }

    public Time o() {
        return this.c.n();
    }

    public Time g() {
        return this.c.h();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }

    public int hashCode() {
        if (!this.q) {
            this.x = super.hashCode();
            this.q = true;
        }
        return this.x;
    }
}
