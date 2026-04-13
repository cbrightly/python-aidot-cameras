package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.math.ec.ECFieldElement;

public class X9FieldElement extends ASN1Object {
    private static X9IntegerConverter c = new X9IntegerConverter();
    protected ECFieldElement d;

    public X9FieldElement(ECFieldElement f) {
        this.d = f;
    }

    public X9FieldElement(BigInteger p, ASN1OctetString s) {
        this(new ECFieldElement.Fp(p, new BigInteger(1, s.q())));
    }

    public X9FieldElement(int m, int k1, int k2, int k3, ASN1OctetString s) {
        this(new ECFieldElement.F2m(m, k1, k2, k3, new BigInteger(1, s.q())));
    }

    public ECFieldElement e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(c.c(this.d.t(), c.b(this.d)));
    }
}
