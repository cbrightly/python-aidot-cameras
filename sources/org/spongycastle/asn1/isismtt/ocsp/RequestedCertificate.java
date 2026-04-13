package org.spongycastle.asn1.isismtt.ocsp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Certificate;

public class RequestedCertificate extends ASN1Object implements ASN1Choice {
    private Certificate c;
    private byte[] d;
    private byte[] f;

    public ASN1Primitive toASN1Primitive() {
        if (this.d != null) {
            return new DERTaggedObject(0, new DEROctetString(this.d));
        }
        if (this.f != null) {
            return new DERTaggedObject(1, new DEROctetString(this.f));
        }
        return this.c.toASN1Primitive();
    }
}
