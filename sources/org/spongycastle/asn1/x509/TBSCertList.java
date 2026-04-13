package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.ASN1UTCTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;

public class TBSCertList extends ASN1Object {
    ASN1Integer c;
    AlgorithmIdentifier d;
    X500Name f;
    Time q;
    Time x;
    ASN1Sequence y;
    Extensions z;

    public static class CRLEntry extends ASN1Object {
        ASN1Sequence c;
        Extensions d;

        private CRLEntry(ASN1Sequence seq) {
            if (seq.size() < 2 || seq.size() > 3) {
                throw new IllegalArgumentException("Bad sequence size: " + seq.size());
            }
            this.c = seq;
        }

        public static CRLEntry f(Object o) {
            if (o instanceof CRLEntry) {
                return (CRLEntry) o;
            }
            if (o != null) {
                return new CRLEntry(ASN1Sequence.o(o));
            }
            return null;
        }

        public ASN1Integer h() {
            return ASN1Integer.o(this.c.r(0));
        }

        public Time g() {
            return Time.f(this.c.r(1));
        }

        public Extensions e() {
            if (this.d == null && this.c.size() == 3) {
                this.d = Extensions.g(this.c.r(2));
            }
            return this.d;
        }

        public ASN1Primitive toASN1Primitive() {
            return this.c;
        }

        public boolean i() {
            return this.c.size() == 3;
        }
    }

    public class RevokedCertificatesEnumeration implements Enumeration {
        private final Enumeration a;

        RevokedCertificatesEnumeration(Enumeration en) {
            this.a = en;
        }

        public boolean hasMoreElements() {
            return this.a.hasMoreElements();
        }

        public Object nextElement() {
            return CRLEntry.f(this.a.nextElement());
        }
    }

    public class EmptyEnumeration implements Enumeration {
        private EmptyEnumeration() {
        }

        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            throw new NoSuchElementException("Empty Enumeration");
        }
    }

    public static TBSCertList f(Object obj) {
        if (obj instanceof TBSCertList) {
            return (TBSCertList) obj;
        }
        if (obj != null) {
            return new TBSCertList(ASN1Sequence.o(obj));
        }
        return null;
    }

    public TBSCertList(ASN1Sequence seq) {
        if (seq.size() < 3 || seq.size() > 7) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        int seqPos = 0;
        if (seq.r(0) instanceof ASN1Integer) {
            this.c = ASN1Integer.o(seq.r(0));
            seqPos = 0 + 1;
        } else {
            this.c = null;
        }
        int seqPos2 = seqPos + 1;
        this.d = AlgorithmIdentifier.f(seq.r(seqPos));
        int seqPos3 = seqPos2 + 1;
        this.f = X500Name.e(seq.r(seqPos2));
        int seqPos4 = seqPos3 + 1;
        this.q = Time.f(seq.r(seqPos3));
        if (seqPos4 < seq.size() && ((seq.r(seqPos4) instanceof ASN1UTCTime) || (seq.r(seqPos4) instanceof ASN1GeneralizedTime) || (seq.r(seqPos4) instanceof Time))) {
            this.x = Time.f(seq.r(seqPos4));
            seqPos4++;
        }
        if (seqPos4 < seq.size() && !(seq.r(seqPos4) instanceof ASN1TaggedObject)) {
            this.y = ASN1Sequence.o(seq.r(seqPos4));
            seqPos4++;
        }
        if (seqPos4 < seq.size() && (seq.r(seqPos4) instanceof ASN1TaggedObject)) {
            this.z = Extensions.g(ASN1Sequence.p((ASN1TaggedObject) seq.r(seqPos4), true));
        }
    }

    public int o() {
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer == null) {
            return 1;
        }
        return aSN1Integer.r().intValue() + 1;
    }

    public AlgorithmIdentifier k() {
        return this.d;
    }

    public X500Name g() {
        return this.f;
    }

    public Time n() {
        return this.q;
    }

    public Time h() {
        return this.x;
    }

    public Enumeration i() {
        ASN1Sequence aSN1Sequence = this.y;
        if (aSN1Sequence == null) {
            return new EmptyEnumeration();
        }
        return new RevokedCertificatesEnumeration(aSN1Sequence.s());
    }

    public Extensions e() {
        return this.z;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        Time time = this.x;
        if (time != null) {
            v.a(time);
        }
        ASN1Sequence aSN1Sequence = this.y;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        if (this.z != null) {
            v.a(new DERTaggedObject(0, this.z));
        }
        return new DERSequence(v);
    }
}
