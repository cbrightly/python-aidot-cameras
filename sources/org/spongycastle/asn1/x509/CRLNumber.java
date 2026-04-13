package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class CRLNumber extends ASN1Object {
    private BigInteger c;

    public CRLNumber(BigInteger number) {
        this.c = number;
    }

    public BigInteger e() {
        return this.c;
    }

    public String toString() {
        return "CRLNumber: " + e();
    }

    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.c);
    }
}
