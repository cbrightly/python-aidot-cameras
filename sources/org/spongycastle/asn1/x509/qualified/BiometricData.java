package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class BiometricData extends ASN1Object {
    private TypeOfBiometricData c;
    private AlgorithmIdentifier d;
    private ASN1OctetString f;
    private DERIA5String q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        seq.a(this.d);
        seq.a(this.f);
        DERIA5String dERIA5String = this.q;
        if (dERIA5String != null) {
            seq.a(dERIA5String);
        }
        return new DERSequence(seq);
    }
}
