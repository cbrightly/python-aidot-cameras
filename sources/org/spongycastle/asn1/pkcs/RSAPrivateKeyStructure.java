package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RSAPrivateKeyStructure extends ASN1Object {
    private BigInteger a1;
    private int c;
    private BigInteger d;
    private BigInteger f;
    private BigInteger p0;
    private ASN1Sequence p1;
    private BigInteger q;
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public BigInteger h() {
        return this.d;
    }

    public BigInteger o() {
        return this.f;
    }

    public BigInteger n() {
        return this.q;
    }

    public BigInteger i() {
        return this.x;
    }

    public BigInteger k() {
        return this.y;
    }

    public BigInteger f() {
        return this.z;
    }

    public BigInteger g() {
        return this.p0;
    }

    public BigInteger e() {
        return this.a1;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(new ASN1Integer(h()));
        v.a(new ASN1Integer(o()));
        v.a(new ASN1Integer(n()));
        v.a(new ASN1Integer(i()));
        v.a(new ASN1Integer(k()));
        v.a(new ASN1Integer(f()));
        v.a(new ASN1Integer(g()));
        v.a(new ASN1Integer(e()));
        ASN1Sequence aSN1Sequence = this.p1;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        return new DERSequence(v);
    }
}
