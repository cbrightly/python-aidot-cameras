package org.spongycastle.asn1.cryptopro;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class GOST3410ParamSetParameters extends ASN1Object {
    int c;
    ASN1Integer d;
    ASN1Integer f;
    ASN1Integer q;

    public GOST3410ParamSetParameters(int keySize, BigInteger p, BigInteger q2, BigInteger a) {
        this.c = keySize;
        this.d = new ASN1Integer(p);
        this.f = new ASN1Integer(q2);
        this.q = new ASN1Integer(a);
    }

    public BigInteger f() {
        return this.d.q();
    }

    public BigInteger g() {
        return this.f.q();
    }

    public BigInteger e() {
        return this.q.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        return new DERSequence(v);
    }
}
