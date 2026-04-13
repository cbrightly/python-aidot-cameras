package org.spongycastle.jce.netscape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class NetscapeCertRequest extends ASN1Object {
    AlgorithmIdentifier c;
    byte[] d;
    String f;
    PublicKey q;

    private ASN1Primitive e() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(this.q.getEncoded());
            baos.close();
            return new ASN1InputStream((InputStream) new ByteArrayInputStream(baos.toByteArray())).r();
        } catch (IOException ioe) {
            throw new InvalidKeySpecException(ioe.getMessage());
        }
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector spkac = new ASN1EncodableVector();
        ASN1EncodableVector pkac = new ASN1EncodableVector();
        try {
            pkac.a(e());
        } catch (Exception e) {
        }
        pkac.a(new DERIA5String(this.f));
        spkac.a(new DERSequence(pkac));
        spkac.a(this.c);
        spkac.a(new DERBitString(this.d));
        return new DERSequence(spkac);
    }
}
