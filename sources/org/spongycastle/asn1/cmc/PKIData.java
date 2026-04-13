package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class PKIData extends ASN1Object {
    private final TaggedAttribute[] c;
    private final TaggedRequest[] d;
    private final TaggedContentInfo[] f;
    private final OtherMsg[] q;

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1Encodable[]{new DERSequence((ASN1Encodable[]) this.c), new DERSequence((ASN1Encodable[]) this.d), new DERSequence((ASN1Encodable[]) this.f), new DERSequence((ASN1Encodable[]) this.q)});
    }
}
