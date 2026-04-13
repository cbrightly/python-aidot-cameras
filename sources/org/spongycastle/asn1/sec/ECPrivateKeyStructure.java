package org.spongycastle.asn1.sec;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.BigIntegers;

public class ECPrivateKeyStructure extends ASN1Object {
    private ASN1Sequence c;

    public ECPrivateKeyStructure(ASN1Sequence seq) {
        this.c = seq;
    }

    public ECPrivateKeyStructure(BigInteger key, ASN1Encodable parameters) {
        this(key, (DERBitString) null, parameters);
    }

    public ECPrivateKeyStructure(BigInteger key, DERBitString publicKey, ASN1Encodable parameters) {
        byte[] bytes = BigIntegers.b(key);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(1));
        v.a(new DEROctetString(bytes));
        if (parameters != null) {
            v.a(new DERTaggedObject(true, 0, parameters));
        }
        if (publicKey != null) {
            v.a(new DERTaggedObject(true, 1, publicKey));
        }
        this.c = new DERSequence(v);
    }

    public BigInteger e() {
        return new BigInteger(1, ((ASN1OctetString) this.c.r(1)).q());
    }

    public DERBitString g() {
        return (DERBitString) f(1);
    }

    private ASN1Primitive f(int tagNo) {
        Enumeration e = this.c.s();
        while (e.hasMoreElements()) {
            ASN1Encodable obj = (ASN1Encodable) e.nextElement();
            if (obj instanceof ASN1TaggedObject) {
                ASN1TaggedObject tag = (ASN1TaggedObject) obj;
                if (tag.r() == tagNo) {
                    return tag.q().toASN1Primitive();
                }
            }
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
