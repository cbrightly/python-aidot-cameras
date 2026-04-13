package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.EncryptedValue;

public class CertOrEncCert extends ASN1Object implements ASN1Choice {
    private CMPCertificate c;
    private EncryptedValue d;

    public ASN1Primitive toASN1Primitive() {
        if (this.c != null) {
            return new DERTaggedObject(true, 0, this.c);
        }
        return new DERTaggedObject(true, 1, this.d);
    }
}
