package org.spongycastle.jce;

import java.io.IOException;
import java.security.Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.X509Name;

public class X509Principal extends X509Name implements Principal {
    private static ASN1Sequence p(ASN1InputStream aIn) {
        try {
            return ASN1Sequence.o(aIn.r());
        } catch (IllegalArgumentException e) {
            throw new IOException("not an ASN.1 Sequence: " + e);
        }
    }

    public X509Principal(byte[] bytes) {
        super(p(new ASN1InputStream(bytes)));
    }

    public X509Principal(X509Name name) {
        super((ASN1Sequence) name.toASN1Primitive());
    }

    public X509Principal(X500Name name) {
        super((ASN1Sequence) name.toASN1Primitive());
    }

    public String getName() {
        return toString();
    }

    public byte[] getEncoded() {
        try {
            return getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }
}
