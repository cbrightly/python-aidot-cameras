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

public class McEliecePublicKey extends ASN1Object {
    private final int c;
    private final int d;
    private final GF2Matrix f;

    public McEliecePublicKey(int n, int t, GF2Matrix g) {
        this.c = n;
        this.d = t;
        this.f = new GF2Matrix(g);
    }

    private McEliecePublicKey(ASN1Sequence seq) {
        this.c = ((ASN1Integer) seq.r(0)).r().intValue();
        this.d = ((ASN1Integer) seq.r(1)).r().intValue();
        this.f = new GF2Matrix(((ASN1OctetString) seq.r(2)).q());
    }

    public int g() {
        return this.c;
    }

    public int h() {
        return this.d;
    }

    public GF2Matrix e() {
        return new GF2Matrix(this.f);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer((long) this.c));
        v.a(new ASN1Integer((long) this.d));
        v.a(new DEROctetString(this.f.m()));
        return new DERSequence(v);
    }

    public static McEliecePublicKey f(Object o) {
        if (o instanceof McEliecePublicKey) {
            return (McEliecePublicKey) o;
        }
        if (o != null) {
            return new McEliecePublicKey(ASN1Sequence.o(o));
        }
        return null;
    }
}
