package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class RSAPublicKeyStructure extends ASN1Object {
    private BigInteger c;
    private BigInteger d;

    public BigInteger e() {
        return this.c;
    }

    public BigInteger f() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(e()));
        v.a(new ASN1Integer(f()));
        return new DERSequence(v);
    }
}
