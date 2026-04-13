package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class SignedData extends ASN1Object implements PKCSObjectIdentifiers {
    private ASN1Integer c;
    private ASN1Set d;
    private ContentInfo f;
    private ASN1Set q;
    private ASN1Set x;
    private ASN1Set y;

    public static SignedData g(Object o) {
        if (o instanceof SignedData) {
            return (SignedData) o;
        }
        if (o != null) {
            return new SignedData(ASN1Sequence.o(o));
        }
        return null;
    }

    public SignedData(ASN1Integer _version, ASN1Set _digestAlgorithms, ContentInfo _contentInfo, ASN1Set _certificates, ASN1Set _crls, ASN1Set _signerInfos) {
        this.c = _version;
        this.d = _digestAlgorithms;
        this.f = _contentInfo;
        this.q = _certificates;
        this.x = _crls;
        this.y = _signerInfos;
    }

    public SignedData(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = (ASN1Integer) e.nextElement();
        this.d = (ASN1Set) e.nextElement();
        this.f = ContentInfo.g(e.nextElement());
        while (e.hasMoreElements()) {
            ASN1Primitive o = (ASN1Primitive) e.nextElement();
            if (o instanceof ASN1TaggedObject) {
                ASN1TaggedObject tagged = (ASN1TaggedObject) o;
                switch (tagged.r()) {
                    case 0:
                        this.q = ASN1Set.q(tagged, false);
                        break;
                    case 1:
                        this.x = ASN1Set.q(tagged, false);
                        break;
                    default:
                        throw new IllegalArgumentException("unknown tag value " + tagged.r());
                }
            } else {
                this.y = (ASN1Set) o;
            }
        }
    }

    public ASN1Set f() {
        return this.q;
    }

    public ASN1Set e() {
        return this.x;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        if (this.q != null) {
            v.a(new DERTaggedObject(false, 0, this.q));
        }
        if (this.x != null) {
            v.a(new DERTaggedObject(false, 1, this.x));
        }
        v.a(this.y);
        return new BERSequence(v);
    }
}
