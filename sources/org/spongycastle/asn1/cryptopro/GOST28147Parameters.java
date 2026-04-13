package org.spongycastle.asn1.cryptopro;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class GOST28147Parameters extends ASN1Object {
    private ASN1OctetString c;
    private ASN1ObjectIdentifier d;

    public static GOST28147Parameters f(Object obj) {
        if (obj instanceof GOST28147Parameters) {
            return (GOST28147Parameters) obj;
        }
        if (obj != null) {
            return new GOST28147Parameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    public GOST28147Parameters(byte[] iv, ASN1ObjectIdentifier paramSet) {
        this.c = new DEROctetString(iv);
        this.d = paramSet;
    }

    public GOST28147Parameters(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = (ASN1OctetString) e.nextElement();
        this.d = (ASN1ObjectIdentifier) e.nextElement();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }

    public ASN1ObjectIdentifier e() {
        return this.d;
    }

    public byte[] getIV() {
        return Arrays.h(this.c.q());
    }
}
