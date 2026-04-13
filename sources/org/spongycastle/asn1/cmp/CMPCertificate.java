package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Certificate;

public class CMPCertificate extends ASN1Object implements ASN1Choice {
    private Certificate c;
    private int d;
    private ASN1Object f;

    public ASN1Primitive toASN1Primitive() {
        if (this.f != null) {
            return new DERTaggedObject(true, this.d, this.f);
        }
        return this.c.toASN1Primitive();
    }
}
