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
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;

public class McElieceCCA2PublicKey extends ASN1Object {
    private final int c;
    private final int d;
    private final GF2Matrix f;
    private final AlgorithmIdentifier q;

    public McElieceCCA2PublicKey(int n, int t, GF2Matrix g, AlgorithmIdentifier digest) {
        this.c = n;
        this.d = t;
        this.f = new GF2Matrix(g.m());
        this.q = digest;
    }

    private McElieceCCA2PublicKey(ASN1Sequence seq) {
        this.c = ((ASN1Integer) seq.r(0)).r().intValue();
        this.d = ((ASN1Integer) seq.r(1)).r().intValue();
        this.f = new GF2Matrix(((ASN1OctetString) seq.r(2)).q());
        this.q = AlgorithmIdentifier.f(seq.r(3));
    }

    public int h() {
        return this.c;
    }

    public int i() {
        return this.d;
    }

    public GF2Matrix f() {
        return this.f;
    }

    public AlgorithmIdentifier e() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(new ASN1Integer((long) this.d));
        v.a(new DEROctetString(this.f.m()));
        v.a(this.q);
        return new DERSequence(v);
    }

    public static McElieceCCA2PublicKey g(Object o) {
        if (o instanceof McElieceCCA2PublicKey) {
            return (McElieceCCA2PublicKey) o;
        }
        if (o != null) {
            return new McElieceCCA2PublicKey(ASN1Sequence.o(o));
        }
        return null;
    }
}
