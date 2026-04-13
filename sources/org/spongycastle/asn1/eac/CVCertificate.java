package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CVCertificate extends ASN1Object {
    private static int c = 1;
    private static int d = 2;
    private CertificateBody f;
    private byte[] q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.f);
        try {
            v.a(new DERApplicationSpecific(false, 55, (ASN1Encodable) new DEROctetString(this.q)));
            return new DERApplicationSpecific(33, v);
        } catch (IOException e) {
            throw new IllegalStateException("unable to convert signature!");
        }
    }
}
