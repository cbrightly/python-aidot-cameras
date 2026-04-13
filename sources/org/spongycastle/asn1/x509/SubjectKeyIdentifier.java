package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.util.Arrays;

public class SubjectKeyIdentifier extends ASN1Object {
    private byte[] c;

    public SubjectKeyIdentifier(byte[] keyid) {
        this.c = Arrays.h(keyid);
    }

    public byte[] e() {
        return Arrays.h(this.c);
    }

    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(e());
    }
}
