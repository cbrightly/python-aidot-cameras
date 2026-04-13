package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKey extends ASN1Object {
    private int c;
    private int d;
    private byte[] f;
    private byte[] q;
    private byte[] x;
    private byte[] y;
    private byte[] z;

    public McEliecePrivateKey(int n, int k, GF2mField field, PolynomialGF2mSmallM goppaPoly, Permutation p1, Permutation p2, GF2Matrix sInv) {
        this.c = n;
        this.d = k;
        this.f = field.e();
        this.q = goppaPoly.m();
        this.x = sInv.m();
        this.y = p1.b();
        this.z = p2.b();
    }

    public static McEliecePrivateKey g(Object o) {
        if (o instanceof McEliecePrivateKey) {
            return (McEliecePrivateKey) o;
        }
        if (o != null) {
            return new McEliecePrivateKey(ASN1Sequence.o(o));
        }
        return null;
    }

    private McEliecePrivateKey(ASN1Sequence seq) {
        this.c = ((ASN1Integer) seq.r(0)).r().intValue();
        this.d = ((ASN1Integer) seq.r(1)).r().intValue();
        this.f = ((ASN1OctetString) seq.r(2)).q();
        this.q = ((ASN1OctetString) seq.r(3)).q();
        this.y = ((ASN1OctetString) seq.r(4)).q();
        this.z = ((ASN1OctetString) seq.r(5)).q();
        this.x = ((ASN1OctetString) seq.r(6)).q();
    }

    public int i() {
        return this.c;
    }

    public int h() {
        return this.d;
    }

    public GF2mField e() {
        return new GF2mField(this.f);
    }

    public PolynomialGF2mSmallM f() {
        return new PolynomialGF2mSmallM(e(), this.q);
    }

    public GF2Matrix o() {
        return new GF2Matrix(this.x);
    }

    public Permutation k() {
        return new Permutation(this.y);
    }

    public Permutation n() {
        return new Permutation(this.z);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(new ASN1Integer((long) this.d));
        v.a(new DEROctetString(this.f));
        v.a(new DEROctetString(this.q));
        v.a(new DEROctetString(this.y));
        v.a(new DEROctetString(this.z));
        v.a(new DEROctetString(this.x));
        return new DERSequence(v);
    }
}
