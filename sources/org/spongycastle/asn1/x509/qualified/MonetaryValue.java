package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class MonetaryValue extends ASN1Object {
    private Iso4217CurrencyCode c;
    private ASN1Integer d;
    private ASN1Integer f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        seq.a(this.d);
        seq.a(this.f);
        return new DERSequence(seq);
    }
}
