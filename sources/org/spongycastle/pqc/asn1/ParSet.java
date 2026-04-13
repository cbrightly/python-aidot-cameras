package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class ParSet extends ASN1Object {
    private static final BigInteger c = BigInteger.valueOf(0);
    private int d;
    private int[] f;
    private int[] q;
    private int[] x;

    public ParSet(int t, int[] h, int[] w, int[] k) {
        this.d = t;
        this.f = h;
        this.q = w;
        this.x = k;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seqOfPSh = new ASN1EncodableVector();
        ASN1EncodableVector seqOfPSw = new ASN1EncodableVector();
        ASN1EncodableVector seqOfPSK = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            int[] iArr = this.f;
            if (i < iArr.length) {
                seqOfPSh.a(new ASN1Integer((long) iArr[i]));
                seqOfPSw.a(new ASN1Integer((long) this.q[i]));
                seqOfPSK.a(new ASN1Integer((long) this.x[i]));
                i++;
            } else {
                ASN1EncodableVector v = new ASN1EncodableVector();
                v.a(new ASN1Integer((long) this.d));
                v.a(new DERSequence(seqOfPSh));
                v.a(new DERSequence(seqOfPSw));
                v.a(new DERSequence(seqOfPSK));
                return new DERSequence(v);
            }
        }
    }
}
