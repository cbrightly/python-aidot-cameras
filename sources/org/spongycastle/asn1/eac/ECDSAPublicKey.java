package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;

public class ECDSAPublicKey extends PublicKeyDataObject {
    private int a1;
    private ASN1ObjectIdentifier c;
    private BigInteger d;
    private BigInteger f;
    private BigInteger p0;
    private BigInteger q;
    private byte[] x;
    private BigInteger y;
    private byte[] z;

    public byte[] f() {
        if ((this.a1 & 8) != 0) {
            return Arrays.h(this.x);
        }
        return null;
    }

    public BigInteger g() {
        if ((this.a1 & 64) != 0) {
            return this.p0;
        }
        return null;
    }

    public BigInteger h() {
        if ((this.a1 & 2) != 0) {
            return this.f;
        }
        return null;
    }

    public BigInteger i() {
        if ((this.a1 & 16) != 0) {
            return this.y;
        }
        return null;
    }

    public BigInteger k() {
        if ((this.a1 & 1) != 0) {
            return this.d;
        }
        return null;
    }

    public byte[] n() {
        if ((this.a1 & 32) != 0) {
            return Arrays.h(this.z);
        }
        return null;
    }

    public BigInteger o() {
        if ((this.a1 & 4) != 0) {
            return this.q;
        }
        return null;
    }

    public boolean p() {
        return this.d != null;
    }

    public ASN1EncodableVector e(ASN1ObjectIdentifier oid, boolean publicPointOnly) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(oid);
        if (!publicPointOnly) {
            v.a(new UnsignedInteger(1, k()));
            v.a(new UnsignedInteger(2, h()));
            v.a(new UnsignedInteger(3, o()));
            v.a(new DERTaggedObject(false, 4, new DEROctetString(f())));
            v.a(new UnsignedInteger(5, i()));
        }
        v.a(new DERTaggedObject(false, 6, new DEROctetString(n())));
        if (!publicPointOnly) {
            v.a(new UnsignedInteger(7, g()));
        }
        return v;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(e(this.c, !p()));
    }
}
