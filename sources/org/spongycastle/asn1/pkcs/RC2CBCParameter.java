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

public class RC2CBCParameter extends ASN1Object {
    ASN1Integer c;
    ASN1OctetString d;

    public static RC2CBCParameter e(Object o) {
        if (o instanceof RC2CBCParameter) {
            return (RC2CBCParameter) o;
        }
        if (o != null) {
            return new RC2CBCParameter(ASN1Sequence.o(o));
        }
        return null;
    }

    public RC2CBCParameter(byte[] iv) {
        this.c = null;
        this.d = new DEROctetString(iv);
    }

    public RC2CBCParameter(int parameterVersion, byte[] iv) {
        this.c = new ASN1Integer((long) parameterVersion);
        this.d = new DEROctetString(iv);
    }

    private RC2CBCParameter(ASN1Sequence seq) {
        if (seq.size() == 1) {
            this.c = null;
            this.d = (ASN1OctetString) seq.r(0);
            return;
        }
        this.c = (ASN1Integer) seq.r(0);
        this.d = (ASN1OctetString) seq.r(1);
    }

    public BigInteger f() {
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.r();
    }

    public byte[] getIV() {
        return this.d.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        v.a(this.d);
        return new DERSequence(v);
    }
}
