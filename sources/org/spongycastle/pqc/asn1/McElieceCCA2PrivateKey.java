package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCCA2PrivateKey extends ASN1Object {
    private int c;
    private int d;
    private byte[] f;
    private byte[] q;
    private byte[] x;
    private AlgorithmIdentifier y;

    public McElieceCCA2PrivateKey(int n, int k, GF2mField field, PolynomialGF2mSmallM goppaPoly, Permutation p, AlgorithmIdentifier digest) {
        this.c = n;
        this.d = k;
        this.f = field.e();
        this.q = goppaPoly.m();
        this.x = p.b();
        this.y = digest;
    }

    private McElieceCCA2PrivateKey(ASN1Sequence seq) {
        this.c = ((ASN1Integer) seq.r(0)).r().intValue();
        this.d = ((ASN1Integer) seq.r(1)).r().intValue();
        this.f = ((ASN1OctetString) seq.r(2)).q();
        this.q = ((ASN1OctetString) seq.r(3)).q();
        this.x = ((ASN1OctetString) seq.r(4)).q();
        this.y = AlgorithmIdentifier.f(seq.r(5));
    }

    public int k() {
        return this.c;
    }

    public int i() {
        return this.d;
    }

    public GF2mField f() {
        return new GF2mField(this.f);
    }

    public PolynomialGF2mSmallM g() {
        return new PolynomialGF2mSmallM(f(), this.q);
    }

    public Permutation n() {
        return new Permutation(this.x);
    }

    public AlgorithmIdentifier e() {
        return this.y;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(new ASN1Integer((long) this.d));
        v.a(new DEROctetString(this.f));
        v.a(new DEROctetString(this.q));
        v.a(new DEROctetString(this.x));
        v.a(this.y);
        return new DERSequence(v);
    }

    public static McElieceCCA2PrivateKey h(Object o) {
        if (o instanceof McElieceCCA2PrivateKey) {
            return (McElieceCCA2PrivateKey) o;
        }
        if (o != null) {
            return new McElieceCCA2PrivateKey(ASN1Sequence.o(o));
        }
        return null;
    }
}
