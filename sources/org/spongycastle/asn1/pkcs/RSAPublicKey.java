package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RSAPublicKey extends ASN1Object {
    private BigInteger c;
    private BigInteger d;

    public static RSAPublicKey e(Object obj) {
        if (obj instanceof RSAPublicKey) {
            return (RSAPublicKey) obj;
        }
        if (obj != null) {
            return new RSAPublicKey(ASN1Sequence.o(obj));
        }
        return null;
    }

    public RSAPublicKey(BigInteger modulus, BigInteger publicExponent) {
        this.c = modulus;
        this.d = publicExponent;
    }

    private RSAPublicKey(ASN1Sequence seq) {
        if (seq.size() == 2) {
            Enumeration e = seq.s();
            this.c = ASN1Integer.o(e.nextElement()).q();
            this.d = ASN1Integer.o(e.nextElement()).q();
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public BigInteger f() {
        return this.c;
    }

    public BigInteger g() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(f()));
        v.a(new ASN1Integer(g()));
        return new DERSequence(v);
    }
}
