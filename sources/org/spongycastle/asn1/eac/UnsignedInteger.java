package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;

public class UnsignedInteger extends ASN1Object {
    private int c;
    private BigInteger d;

    public UnsignedInteger(int tagNo, BigInteger value) {
        this.c = tagNo;
        this.d = value;
    }

    private byte[] e() {
        byte[] v = this.d.toByteArray();
        if (v[0] != 0) {
            return v;
        }
        byte[] tmp = new byte[(v.length - 1)];
        System.arraycopy(v, 1, tmp, 0, tmp.length);
        return tmp;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.c, new DEROctetString(e()));
    }
}
