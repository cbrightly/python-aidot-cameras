package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class RSAPublicKey extends PublicKeyDataObject {
    private static int c = 1;
    private static int d = 2;
    private ASN1ObjectIdentifier f;
    private BigInteger q;
    private BigInteger x;

    public BigInteger e() {
        return this.q;
    }

    public BigInteger f() {
        return this.x;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.f);
        v.a(new UnsignedInteger(1, e()));
        v.a(new UnsignedInteger(2, f()));
        return new DERSequence(v);
    }
}
