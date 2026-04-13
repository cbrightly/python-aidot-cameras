package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class CAST5CBCParameters extends ASN1Object {
    ASN1Integer c;
    ASN1OctetString d;

    public static CAST5CBCParameters e(Object o) {
        if (o instanceof CAST5CBCParameters) {
            return (CAST5CBCParameters) o;
        }
        if (o != null) {
            return new CAST5CBCParameters(ASN1Sequence.o(o));
        }
        return null;
    }

    public CAST5CBCParameters(byte[] iv, int keyLength) {
        this.d = new DEROctetString(Arrays.h(iv));
        this.c = new ASN1Integer((long) keyLength);
    }

    public CAST5CBCParameters(ASN1Sequence seq) {
        this.d = (ASN1OctetString) seq.r(0);
        this.c = (ASN1Integer) seq.r(1);
    }

    public byte[] getIV() {
        return Arrays.h(this.d.q());
    }

    public int f() {
        return this.c.r().intValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(this.c);
        return new DERSequence(v);
    }
}
