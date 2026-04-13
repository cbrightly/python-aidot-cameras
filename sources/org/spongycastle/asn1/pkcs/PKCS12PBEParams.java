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

public class PKCS12PBEParams extends ASN1Object {
    ASN1Integer iterations;
    ASN1OctetString iv;

    public PKCS12PBEParams(byte[] salt, int iterations2) {
        this.iv = new DEROctetString(salt);
        this.iterations = new ASN1Integer((long) iterations2);
    }

    private PKCS12PBEParams(ASN1Sequence seq) {
        this.iv = (ASN1OctetString) seq.r(0);
        this.iterations = ASN1Integer.o(seq.r(1));
    }

    public static PKCS12PBEParams getInstance(Object obj) {
        if (obj instanceof PKCS12PBEParams) {
            return (PKCS12PBEParams) obj;
        }
        if (obj != null) {
            return new PKCS12PBEParams(ASN1Sequence.o(obj));
        }
        return null;
    }

    public BigInteger getIterations() {
        return this.iterations.r();
    }

    public byte[] getIV() {
        return this.iv.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.iv);
        v.a(this.iterations);
        return new DERSequence(v);
    }
}
