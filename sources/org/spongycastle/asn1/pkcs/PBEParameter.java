package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class PBEParameter extends ASN1Object {
    ASN1Integer c;
    ASN1OctetString d;

    public PBEParameter(byte[] salt, int iterations) {
        if (salt.length == 8) {
            this.d = new DEROctetString(salt);
            this.c = new ASN1Integer((long) iterations);
            return;
        }
        throw new IllegalArgumentException("salt length must be 8");
    }

    private PBEParameter(ASN1Sequence seq) {
        this.d = (ASN1OctetString) seq.r(0);
        this.c = (ASN1Integer) seq.r(1);
    }

    public static PBEParameter e(Object obj) {
        if (obj instanceof PBEParameter) {
            return (PBEParameter) obj;
        }
        if (obj != null) {
            return new PBEParameter(ASN1Sequence.o(obj));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.c.r();
    }

    public byte[] getSalt() {
        return this.d.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(this.c);
        return new DERSequence(v);
    }
}
