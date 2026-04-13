package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;

public class Pfx extends ASN1Object implements PKCSObjectIdentifiers {
    private ContentInfo c;
    private MacData d = null;

    private Pfx(ASN1Sequence seq) {
        if (((ASN1Integer) seq.r(0)).r().intValue() == 3) {
            this.c = ContentInfo.g(seq.r(1));
            if (seq.size() == 3) {
                this.d = MacData.e(seq.r(2));
                return;
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for PFX PDU");
    }

    public static Pfx f(Object obj) {
        if (obj instanceof Pfx) {
            return (Pfx) obj;
        }
        if (obj != null) {
            return new Pfx(ASN1Sequence.o(obj));
        }
        return null;
    }

    public Pfx(ContentInfo contentInfo, MacData macData) {
        this.c = contentInfo;
        this.d = macData;
    }

    public ContentInfo e() {
        return this.c;
    }

    public MacData g() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(3));
        v.a(this.c);
        MacData macData = this.d;
        if (macData != null) {
            v.a(macData);
        }
        return new BERSequence(v);
    }
}
