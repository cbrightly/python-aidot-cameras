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
import org.spongycastle.pqc.crypto.rainbow.Layer;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;

public class RainbowPrivateKey extends ASN1Object {
    private ASN1Integer c;
    private ASN1ObjectIdentifier d;
    private byte[][] f;
    private Layer[] p0;
    private byte[] q;
    private byte[][] x;
    private byte[] y;
    private byte[] z;

    private RainbowPrivateKey(ASN1Sequence seq) {
        ASN1Sequence asnA1;
        ASN1Sequence aSN1Sequence = seq;
        int i = 0;
        if (aSN1Sequence.r(0) instanceof ASN1Integer) {
            this.c = ASN1Integer.o(aSN1Sequence.r(0));
        } else {
            this.d = ASN1ObjectIdentifier.t(aSN1Sequence.r(0));
        }
        ASN1Sequence asnA12 = (ASN1Sequence) aSN1Sequence.r(1);
        this.f = new byte[asnA12.size()][];
        for (int i2 = 0; i2 < asnA12.size(); i2++) {
            this.f[i2] = ((ASN1OctetString) asnA12.r(i2)).q();
        }
        this.q = ((ASN1OctetString) ((ASN1Sequence) aSN1Sequence.r(2)).r(0)).q();
        ASN1Sequence asnA2 = (ASN1Sequence) aSN1Sequence.r(3);
        this.x = new byte[asnA2.size()][];
        for (int j = 0; j < asnA2.size(); j++) {
            this.x[j] = ((ASN1OctetString) asnA2.r(j)).q();
        }
        this.y = ((ASN1OctetString) ((ASN1Sequence) aSN1Sequence.r(4)).r(0)).q();
        this.z = ((ASN1OctetString) ((ASN1Sequence) aSN1Sequence.r(5)).r(0)).q();
        ASN1Sequence asnLayers = (ASN1Sequence) aSN1Sequence.r(6);
        byte[][][][] alphas = new byte[asnLayers.size()][][][];
        byte[][][][] betas = new byte[asnLayers.size()][][][];
        byte[][][] gammas = new byte[asnLayers.size()][][];
        byte[][] etas = new byte[asnLayers.size()][];
        int l = 0;
        while (l < asnLayers.size()) {
            ASN1Sequence asnLayer = (ASN1Sequence) asnLayers.r(l);
            ASN1Sequence alphas3d = (ASN1Sequence) asnLayer.r(i);
            alphas[l] = new byte[alphas3d.size()][][];
            int m = 0;
            while (m < alphas3d.size()) {
                ASN1Sequence alphas2d = (ASN1Sequence) alphas3d.r(m);
                ASN1Sequence alphas3d2 = alphas3d;
                alphas[l][m] = new byte[alphas2d.size()][];
                int n = 0;
                while (true) {
                    asnA1 = asnA12;
                    if (n >= alphas2d.size()) {
                        break;
                    }
                    alphas[l][m][n] = ((ASN1OctetString) alphas2d.r(n)).q();
                    n++;
                    asnA12 = asnA1;
                }
                m++;
                ASN1Sequence aSN1Sequence2 = seq;
                asnA12 = asnA1;
                alphas3d = alphas3d2;
            }
            ASN1Sequence asnA13 = asnA12;
            ASN1Sequence betas3d = (ASN1Sequence) asnLayer.r(1);
            betas[l] = new byte[betas3d.size()][][];
            int mb = 0;
            while (mb < betas3d.size()) {
                ASN1Sequence betas2d = (ASN1Sequence) betas3d.r(mb);
                ASN1Sequence betas3d2 = betas3d;
                betas[l][mb] = new byte[betas2d.size()][];
                for (int nb = 0; nb < betas2d.size(); nb++) {
                    betas[l][mb][nb] = ((ASN1OctetString) betas2d.r(nb)).q();
                }
                mb++;
                betas3d = betas3d2;
            }
            ASN1Sequence gammas2d = (ASN1Sequence) asnLayer.r(2);
            gammas[l] = new byte[gammas2d.size()][];
            for (int mg = 0; mg < gammas2d.size(); mg++) {
                gammas[l][mg] = ((ASN1OctetString) gammas2d.r(mg)).q();
            }
            etas[l] = ((ASN1OctetString) asnLayer.r(3)).q();
            l++;
            asnA12 = asnA13;
            i = 0;
            ASN1Sequence aSN1Sequence3 = seq;
        }
        int numOfLayers = this.z.length - 1;
        this.p0 = new Layer[numOfLayers];
        for (int i3 = 0; i3 < numOfLayers; i3++) {
            byte[] bArr = this.z;
            this.p0[i3] = new Layer(bArr[i3], bArr[i3 + 1], RainbowUtil.f(alphas[i3]), RainbowUtil.f(betas[i3]), RainbowUtil.d(gammas[i3]), RainbowUtil.b(etas[i3]));
        }
    }

    public RainbowPrivateKey(short[][] invA1, short[] b1, short[][] invA2, short[] b2, int[] vi, Layer[] layers) {
        this.c = new ASN1Integer(1);
        this.f = RainbowUtil.c(invA1);
        this.q = RainbowUtil.a(b1);
        this.x = RainbowUtil.c(invA2);
        this.y = RainbowUtil.a(b2);
        this.z = RainbowUtil.h(vi);
        this.p0 = layers;
    }

    public static RainbowPrivateKey g(Object o) {
        if (o instanceof RainbowPrivateKey) {
            return (RainbowPrivateKey) o;
        }
        if (o != null) {
            return new RainbowPrivateKey(ASN1Sequence.o(o));
        }
        return null;
    }

    public short[][] h() {
        return RainbowUtil.d(this.f);
    }

    public short[] e() {
        return RainbowUtil.b(this.q);
    }

    public short[] f() {
        return RainbowUtil.b(this.y);
    }

    public short[][] i() {
        return RainbowUtil.d(this.x);
    }

    public Layer[] k() {
        return this.p0;
    }

    public int[] n() {
        return RainbowUtil.g(this.z);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector asnb1;
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        } else {
            v.a(this.d);
        }
        ASN1EncodableVector asnA1 = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            byte[][] bArr = this.f;
            if (i >= bArr.length) {
                break;
            }
            asnA1.a(new DEROctetString(bArr[i]));
            i++;
        }
        v.a(new DERSequence(asnA1));
        ASN1EncodableVector asnb12 = new ASN1EncodableVector();
        asnb12.a(new DEROctetString(this.q));
        v.a(new DERSequence(asnb12));
        ASN1EncodableVector asnA2 = new ASN1EncodableVector();
        int i2 = 0;
        while (true) {
            byte[][] bArr2 = this.x;
            if (i2 >= bArr2.length) {
                break;
            }
            asnA2.a(new DEROctetString(bArr2[i2]));
            i2++;
        }
        v.a(new DERSequence(asnA2));
        ASN1EncodableVector asnb2 = new ASN1EncodableVector();
        asnb2.a(new DEROctetString(this.y));
        v.a(new DERSequence(asnb2));
        ASN1EncodableVector asnvi = new ASN1EncodableVector();
        asnvi.a(new DEROctetString(this.z));
        v.a(new DERSequence(asnvi));
        ASN1EncodableVector asnLayers = new ASN1EncodableVector();
        int l = 0;
        while (l < this.p0.length) {
            ASN1EncodableVector aLayer = new ASN1EncodableVector();
            byte[][][] alphas = RainbowUtil.e(this.p0[l].a());
            ASN1EncodableVector alphas3d = new ASN1EncodableVector();
            int i3 = 0;
            while (i3 < alphas.length) {
                ASN1EncodableVector alphas2d = new ASN1EncodableVector();
                int j = 0;
                while (j < alphas[i3].length) {
                    alphas2d.a(new DEROctetString(alphas[i3][j]));
                    j++;
                    asnA1 = asnA1;
                }
                alphas3d.a(new DERSequence(alphas2d));
                i3++;
                asnA1 = asnA1;
            }
            ASN1EncodableVector asnA12 = asnA1;
            aLayer.a(new DERSequence(alphas3d));
            byte[][][] betas = RainbowUtil.e(this.p0[l].b());
            ASN1EncodableVector betas3d = new ASN1EncodableVector();
            int i4 = 0;
            while (i4 < betas.length) {
                ASN1EncodableVector betas2d = new ASN1EncodableVector();
                int j2 = 0;
                while (true) {
                    asnb1 = asnb12;
                    if (j2 >= betas[i4].length) {
                        break;
                    }
                    betas2d.a(new DEROctetString(betas[i4][j2]));
                    j2++;
                    asnb12 = asnb1;
                    betas = betas;
                }
                betas3d.a(new DERSequence(betas2d));
                i4++;
                asnb12 = asnb1;
                betas = betas;
            }
            ASN1EncodableVector asnb13 = asnb12;
            aLayer.a(new DERSequence(betas3d));
            byte[][] gammas = RainbowUtil.c(this.p0[l].d());
            ASN1EncodableVector asnG = new ASN1EncodableVector();
            for (byte[] dEROctetString : gammas) {
                asnG.a(new DEROctetString(dEROctetString));
            }
            aLayer.a(new DERSequence(asnG));
            aLayer.a(new DEROctetString(RainbowUtil.a(this.p0[l].c())));
            asnLayers.a(new DERSequence(aLayer));
            l++;
            asnb12 = asnb13;
            asnA1 = asnA12;
        }
        v.a(new DERSequence(asnLayers));
        return new DERSequence(v);
    }
}
