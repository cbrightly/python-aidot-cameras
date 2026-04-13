package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x500.X500Name;

public class TBSCertificate extends ASN1Object {
    SubjectPublicKeyInfo a1;
    DERBitString a2;
    ASN1Sequence c;
    ASN1Integer d;
    ASN1Integer f;
    X500Name p0;
    DERBitString p1;
    Extensions p2;
    AlgorithmIdentifier q;
    X500Name x;
    Time y;
    Time z;

    public static TBSCertificate g(Object obj) {
        if (obj instanceof TBSCertificate) {
            return (TBSCertificate) obj;
        }
        if (obj != null) {
            return new TBSCertificate(ASN1Sequence.o(obj));
        }
        return null;
    }

    private TBSCertificate(ASN1Sequence seq) {
        int seqStart = 0;
        this.c = seq;
        if (seq.r(0) instanceof ASN1TaggedObject) {
            this.d = ASN1Integer.p((ASN1TaggedObject) seq.r(0), true);
        } else {
            seqStart = -1;
            this.d = new ASN1Integer(0);
        }
        boolean isV1 = false;
        boolean isV2 = false;
        if (this.d.r().equals(BigInteger.valueOf(0))) {
            isV1 = true;
        } else if (this.d.r().equals(BigInteger.valueOf(1))) {
            isV2 = true;
        } else if (!this.d.r().equals(BigInteger.valueOf(2))) {
            throw new IllegalArgumentException("version number not recognised");
        }
        this.f = ASN1Integer.o(seq.r(seqStart + 1));
        this.q = AlgorithmIdentifier.f(seq.r(seqStart + 2));
        this.x = X500Name.e(seq.r(seqStart + 3));
        ASN1Sequence dates = (ASN1Sequence) seq.r(seqStart + 4);
        this.y = Time.f(dates.r(0));
        this.z = Time.f(dates.r(1));
        this.p0 = X500Name.e(seq.r(seqStart + 5));
        this.a1 = SubjectPublicKeyInfo.g(seq.r(seqStart + 6));
        int extras = (seq.size() - (seqStart + 6)) - 1;
        if (extras == 0 || !isV1) {
            while (extras > 0) {
                ASN1TaggedObject extra = (ASN1TaggedObject) seq.r(seqStart + 6 + extras);
                switch (extra.r()) {
                    case 1:
                        this.p1 = DERBitString.y(extra, false);
                        break;
                    case 2:
                        this.a2 = DERBitString.y(extra, false);
                        break;
                    case 3:
                        if (!isV2) {
                            this.p2 = Extensions.g(ASN1Sequence.p(extra, true));
                            break;
                        } else {
                            throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                        }
                }
                extras--;
            }
            return;
        }
        throw new IllegalArgumentException("version 1 certificate contains extra data");
    }

    public int s() {
        return this.d.r().intValue() + 1;
    }

    public ASN1Integer k() {
        return this.f;
    }

    public AlgorithmIdentifier n() {
        return this.q;
    }

    public X500Name h() {
        return this.x;
    }

    public Time o() {
        return this.y;
    }

    public Time e() {
        return this.z;
    }

    public X500Name p() {
        return this.p0;
    }

    public SubjectPublicKeyInfo q() {
        return this.a1;
    }

    public DERBitString i() {
        return this.p1;
    }

    public DERBitString r() {
        return this.a2;
    }

    public Extensions f() {
        return this.p2;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
