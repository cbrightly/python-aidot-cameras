package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1UTCTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;

public class CrlIdentifier extends ASN1Object {
    private X500Name c;
    private ASN1UTCTime d;
    private ASN1Integer f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c.toASN1Primitive());
        v.a(this.d);
        ASN1Integer aSN1Integer = this.f;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        return new DERSequence(v);
    }
}
