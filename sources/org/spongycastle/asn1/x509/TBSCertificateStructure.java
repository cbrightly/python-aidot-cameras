package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;

public class TBSCertificateStructure extends ASN1Object implements X509ObjectIdentifiers, PKCSObjectIdentifiers {
    SubjectPublicKeyInfo a1;
    DERBitString a2;
    ASN1Sequence c;
    ASN1Integer d;
    ASN1Integer f;
    X500Name p0;
    DERBitString p1;
    X509Extensions p2;
    AlgorithmIdentifier q;
    X500Name x;
    Time y;
    Time z;

    public static TBSCertificateStructure e(Object obj) {
        if (obj instanceof TBSCertificateStructure) {
            return (TBSCertificateStructure) obj;
        }
        if (obj != null) {
            return new TBSCertificateStructure(ASN1Sequence.o(obj));
        }
        return null;
    }

    public TBSCertificateStructure(ASN1Sequence seq) {
        int seqStart = 0;
        this.c = seq;
        if (seq.r(0) instanceof DERTaggedObject) {
            this.d = ASN1Integer.p((ASN1TaggedObject) seq.r(0), true);
        } else {
            seqStart = -1;
            this.d = new ASN1Integer(0);
        }
        this.f = ASN1Integer.o(seq.r(seqStart + 1));
        this.q = AlgorithmIdentifier.f(seq.r(seqStart + 2));
        this.x = X500Name.e(seq.r(seqStart + 3));
        ASN1Sequence dates = (ASN1Sequence) seq.r(seqStart + 4);
        this.y = Time.f(dates.r(0));
        this.z = Time.f(dates.r(1));
        this.p0 = X500Name.e(seq.r(seqStart + 5));
        this.a1 = SubjectPublicKeyInfo.g(seq.r(seqStart + 6));
        for (int extras = (seq.size() - (seqStart + 6)) - 1; extras > 0; extras--) {
            DERTaggedObject extra = (DERTaggedObject) seq.r(seqStart + 6 + extras);
            switch (extra.r()) {
                case 1:
                    this.p1 = DERBitString.y(extra, false);
                    break;
                case 2:
                    this.a2 = DERBitString.y(extra, false);
                    break;
                case 3:
                    this.p2 = X509Extensions.e(extra);
                    break;
            }
        }
    }

    public X500Name f() {
        return this.x;
    }

    public X500Name g() {
        return this.p0;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
