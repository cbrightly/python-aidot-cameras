package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.PKIPublicationInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CMCPublicationInfo extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final ASN1Sequence d;
    private final PKIPublicationInfo f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }
}
