package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class GCMParameters extends ASN1Object {
    private byte[] c;
    private int d;

    public static GCMParameters f(Object obj) {
        if (obj instanceof GCMParameters) {
            return (GCMParameters) obj;
        }
        if (obj != null) {
            return new GCMParameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    private GCMParameters(ASN1Sequence seq) {
        this.c = ASN1OctetString.o(seq.r(0)).q();
        if (seq.size() == 2) {
            this.d = ASN1Integer.o(seq.r(1)).r().intValue();
        } else {
            this.d = 12;
        }
    }

    public GCMParameters(byte[] nonce, int icvLen) {
        this.c = Arrays.h(nonce);
        this.d = icvLen;
    }

    public byte[] g() {
        return Arrays.h(this.c);
    }

    public int e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new DEROctetString(this.c));
        int i = this.d;
        if (i != 12) {
            v.a(new ASN1Integer((long) i));
        }
        return new DERSequence(v);
    }
}
