package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class ValidationParams extends ASN1Object {
    private ASN1Integer pgenCounter;
    private DERBitString seed;

    public static ValidationParams getInstance(ASN1TaggedObject obj, boolean explicit) {
        return getInstance(ASN1Sequence.p(obj, explicit));
    }

    public static ValidationParams getInstance(Object obj) {
        if (obj instanceof ValidationParams) {
            return (ValidationParams) obj;
        }
        if (obj != null) {
            return new ValidationParams(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ValidationParams(byte[] seed2, int pgenCounter2) {
        if (seed2 != null) {
            this.seed = new DERBitString(seed2);
            this.pgenCounter = new ASN1Integer((long) pgenCounter2);
            return;
        }
        throw new IllegalArgumentException("'seed' cannot be null");
    }

    public ValidationParams(DERBitString seed2, ASN1Integer pgenCounter2) {
        if (seed2 == null) {
            throw new IllegalArgumentException("'seed' cannot be null");
        } else if (pgenCounter2 != null) {
            this.seed = seed2;
            this.pgenCounter = pgenCounter2;
        } else {
            throw new IllegalArgumentException("'pgenCounter' cannot be null");
        }
    }

    private ValidationParams(ASN1Sequence seq) {
        if (seq.size() == 2) {
            this.seed = DERBitString.x(seq.r(0));
            this.pgenCounter = ASN1Integer.o(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public byte[] getSeed() {
        return this.seed.q();
    }

    public BigInteger getPgenCounter() {
        return this.pgenCounter.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.seed);
        v.a(this.pgenCounter);
        return new DERSequence(v);
    }
}
