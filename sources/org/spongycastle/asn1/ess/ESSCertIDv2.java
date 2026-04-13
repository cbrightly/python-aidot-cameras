package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.IssuerSerial;

public class ESSCertIDv2 extends ASN1Object {
    private static final AlgorithmIdentifier c = new AlgorithmIdentifier(NISTObjectIdentifiers.c);
    private AlgorithmIdentifier d;
    private byte[] f;
    private IssuerSerial q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (!this.d.equals(c)) {
            v.a(this.d);
        }
        v.a(new DEROctetString(this.f).toASN1Primitive());
        IssuerSerial issuerSerial = this.q;
        if (issuerSerial != null) {
            v.a(issuerSerial);
        }
        return new DERSequence(v);
    }
}
