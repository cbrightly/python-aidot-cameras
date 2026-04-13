package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class X9FieldID extends ASN1Object implements X9ObjectIdentifiers {
    private ASN1ObjectIdentifier c;
    private ASN1Primitive d;

    public X9FieldID(BigInteger primeP) {
        this.c = X9ObjectIdentifiers.k3;
        this.d = new ASN1Integer(primeP);
    }

    public X9FieldID(int m, int k1) {
        this(m, k1, 0, 0);
    }

    public X9FieldID(int m, int k1, int k2, int k3) {
        this.c = X9ObjectIdentifiers.l3;
        ASN1EncodableVector fieldIdParams = new ASN1EncodableVector();
        fieldIdParams.a(new ASN1Integer((long) m));
        if (k2 == 0) {
            if (k3 == 0) {
                fieldIdParams.a(X9ObjectIdentifiers.n3);
                fieldIdParams.a(new ASN1Integer((long) k1));
            } else {
                throw new IllegalArgumentException("inconsistent k values");
            }
        } else if (k2 <= k1 || k3 <= k2) {
            throw new IllegalArgumentException("inconsistent k values");
        } else {
            fieldIdParams.a(X9ObjectIdentifiers.o3);
            ASN1EncodableVector pentanomialParams = new ASN1EncodableVector();
            pentanomialParams.a(new ASN1Integer((long) k1));
            pentanomialParams.a(new ASN1Integer((long) k2));
            pentanomialParams.a(new ASN1Integer((long) k3));
            fieldIdParams.a(new DERSequence(pentanomialParams));
        }
        this.d = new DERSequence(fieldIdParams);
    }

    private X9FieldID(ASN1Sequence seq) {
        this.c = ASN1ObjectIdentifier.t(seq.r(0));
        this.d = seq.r(1).toASN1Primitive();
    }

    public static X9FieldID f(Object obj) {
        if (obj instanceof X9FieldID) {
            return (X9FieldID) obj;
        }
        if (obj != null) {
            return new X9FieldID(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1ObjectIdentifier e() {
        return this.c;
    }

    public ASN1Primitive g() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
