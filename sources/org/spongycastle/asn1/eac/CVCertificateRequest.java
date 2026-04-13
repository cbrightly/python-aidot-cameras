package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ApplicationSpecific;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CVCertificateRequest extends ASN1Object {
    private final ASN1ApplicationSpecific c;
    private CertificateBody d;
    private byte[] f;

    public ASN1Primitive toASN1Primitive() {
        ASN1ApplicationSpecific aSN1ApplicationSpecific = this.c;
        if (aSN1ApplicationSpecific != null) {
            return aSN1ApplicationSpecific;
        }
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        try {
            v.a(new DERApplicationSpecific(false, 55, (ASN1Encodable) new DEROctetString(this.f)));
            return new DERApplicationSpecific(33, v);
        } catch (IOException e) {
            throw new IllegalStateException("unable to convert signature!");
        }
    }
}
