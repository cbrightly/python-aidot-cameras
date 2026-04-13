package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class RSASSAPSSparams extends ASN1Object {
    public static final AlgorithmIdentifier c;
    public static final AlgorithmIdentifier d;
    public static final ASN1Integer f = new ASN1Integer(20);
    public static final ASN1Integer q = new ASN1Integer(1);
    private ASN1Integer p0;
    private AlgorithmIdentifier x;
    private AlgorithmIdentifier y;
    private ASN1Integer z;

    static {
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.i, DERNull.c);
        c = algorithmIdentifier;
        d = new AlgorithmIdentifier(PKCSObjectIdentifiers.R, algorithmIdentifier);
    }

    public static RSASSAPSSparams f(Object obj) {
        if (obj instanceof RSASSAPSSparams) {
            return (RSASSAPSSparams) obj;
        }
        if (obj != null) {
            return new RSASSAPSSparams(ASN1Sequence.o(obj));
        }
        return null;
    }

    public RSASSAPSSparams() {
        this.x = c;
        this.y = d;
        this.z = f;
        this.p0 = q;
    }

    public RSASSAPSSparams(AlgorithmIdentifier hashAlgorithm, AlgorithmIdentifier maskGenAlgorithm, ASN1Integer saltLength, ASN1Integer trailerField) {
        this.x = hashAlgorithm;
        this.y = maskGenAlgorithm;
        this.z = saltLength;
        this.p0 = trailerField;
    }

    private RSASSAPSSparams(ASN1Sequence seq) {
        this.x = c;
        this.y = d;
        this.z = f;
        this.p0 = q;
        for (int i = 0; i != seq.size(); i++) {
            ASN1TaggedObject o = (ASN1TaggedObject) seq.r(i);
            switch (o.r()) {
                case 0:
                    this.x = AlgorithmIdentifier.g(o, true);
                    break;
                case 1:
                    this.y = AlgorithmIdentifier.g(o, true);
                    break;
                case 2:
                    this.z = ASN1Integer.p(o, true);
                    break;
                case 3:
                    this.p0 = ASN1Integer.p(o, true);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag");
            }
        }
    }

    public AlgorithmIdentifier e() {
        return this.x;
    }

    public AlgorithmIdentifier g() {
        return this.y;
    }

    public BigInteger h() {
        return this.z.r();
    }

    public BigInteger i() {
        return this.p0.r();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (!this.x.equals(c)) {
            v.a(new DERTaggedObject(true, 0, this.x));
        }
        if (!this.y.equals(d)) {
            v.a(new DERTaggedObject(true, 1, this.y));
        }
        if (!this.z.equals(f)) {
            v.a(new DERTaggedObject(true, 2, this.z));
        }
        if (!this.p0.equals(q)) {
            v.a(new DERTaggedObject(true, 3, this.p0));
        }
        return new DERSequence(v);
    }
}
