package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;

public class RainbowPublicKey extends ASN1Object {
    private ASN1Integer c;
    private ASN1ObjectIdentifier d;
    private ASN1Integer f;
    private byte[][] q;
    private byte[][] x;
    private byte[] y;

    private RainbowPublicKey(ASN1Sequence seq) {
        if (seq.r(0) instanceof ASN1Integer) {
            this.c = ASN1Integer.o(seq.r(0));
        } else {
            this.d = ASN1ObjectIdentifier.t(seq.r(0));
        }
        this.f = ASN1Integer.o(seq.r(1));
        ASN1Sequence asnCoeffQuad = ASN1Sequence.o(seq.r(2));
        this.q = new byte[asnCoeffQuad.size()][];
        for (int quadSize = 0; quadSize < asnCoeffQuad.size(); quadSize++) {
            this.q[quadSize] = ASN1OctetString.o(asnCoeffQuad.r(quadSize)).q();
        }
        ASN1Sequence asnCoeffSing = (ASN1Sequence) seq.r(3);
        this.x = new byte[asnCoeffSing.size()][];
        for (int singSize = 0; singSize < asnCoeffSing.size(); singSize++) {
            this.x[singSize] = ASN1OctetString.o(asnCoeffSing.r(singSize)).q();
        }
        this.y = ASN1OctetString.o(((ASN1Sequence) seq.r(4)).r(0)).q();
    }

    public RainbowPublicKey(int docLength, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar) {
        this.c = new ASN1Integer(0);
        this.f = new ASN1Integer((long) docLength);
        this.q = RainbowUtil.c(coeffQuadratic);
        this.x = RainbowUtil.c(coeffSingular);
        this.y = RainbowUtil.a(coeffScalar);
    }

    public static RainbowPublicKey i(Object o) {
        if (o instanceof RainbowPublicKey) {
            return (RainbowPublicKey) o;
        }
        if (o != null) {
            return new RainbowPublicKey(ASN1Sequence.o(o));
        }
        return null;
    }

    public int h() {
        return this.f.r().intValue();
    }

    public short[][] e() {
        return RainbowUtil.d(this.q);
    }

    public short[][] g() {
        return RainbowUtil.d(this.x);
    }

    public short[] f() {
        return RainbowUtil.b(this.y);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        } else {
            v.a(this.d);
        }
        v.a(this.f);
        ASN1EncodableVector asnCoeffQuad = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            byte[][] bArr = this.q;
            if (i >= bArr.length) {
                break;
            }
            asnCoeffQuad.a(new DEROctetString(bArr[i]));
            i++;
        }
        v.a(new DERSequence(asnCoeffQuad));
        ASN1EncodableVector asnCoeffSing = new ASN1EncodableVector();
        int i2 = 0;
        while (true) {
            byte[][] bArr2 = this.x;
            if (i2 < bArr2.length) {
                asnCoeffSing.a(new DEROctetString(bArr2[i2]));
                i2++;
            } else {
                v.a(new DERSequence(asnCoeffSing));
                ASN1EncodableVector asnCoeffScalar = new ASN1EncodableVector();
                asnCoeffScalar.a(new DEROctetString(this.y));
                v.a(new DERSequence(asnCoeffScalar));
                return new DERSequence(v);
            }
        }
    }
}
