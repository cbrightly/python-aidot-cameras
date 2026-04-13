package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class XMSSPublicKey extends ASN1Object {
    private final byte[] c;
    private final byte[] d;

    public XMSSPublicKey(byte[] publicSeed, byte[] root) {
        this.c = Arrays.h(publicSeed);
        this.d = Arrays.h(root);
    }

    private XMSSPublicKey(ASN1Sequence seq) {
        if (ASN1Integer.o(seq.r(0)).r().equals(BigInteger.valueOf(0))) {
            this.c = Arrays.h(ASN1OctetString.o(seq.r(1)).q());
            this.d = Arrays.h(ASN1OctetString.o(seq.r(2)).q());
            return;
        }
        throw new IllegalArgumentException("unknown version of sequence");
    }

    public static XMSSPublicKey e(Object o) {
        if (o instanceof XMSSPublicKey) {
            return (XMSSPublicKey) o;
        }
        if (o != null) {
            return new XMSSPublicKey(ASN1Sequence.o(o));
        }
        return null;
    }

    public byte[] f() {
        return Arrays.h(this.c);
    }

    public byte[] g() {
        return Arrays.h(this.d);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(0));
        v.a(new DEROctetString(this.c));
        v.a(new DEROctetString(this.d));
        return new DERSequence(v);
    }
}
