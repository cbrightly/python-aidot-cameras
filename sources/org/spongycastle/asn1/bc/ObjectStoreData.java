package org.spongycastle.asn1.bc;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class ObjectStoreData extends ASN1Object {
    private final BigInteger c;
    private final AlgorithmIdentifier d;
    private final ASN1GeneralizedTime f;
    private final ASN1GeneralizedTime q;
    private final ObjectDataSequence x;
    private final String y;

    public ObjectStoreData(AlgorithmIdentifier integrityAlgorithm, Date creationDate, Date lastModifiedDate, ObjectDataSequence objectDataSequence, String comment) {
        this.c = BigInteger.valueOf(1);
        this.d = integrityAlgorithm;
        this.f = new DERGeneralizedTime(creationDate);
        this.q = new DERGeneralizedTime(lastModifiedDate);
        this.x = objectDataSequence;
        this.y = comment;
    }

    private ObjectStoreData(ASN1Sequence seq) {
        this.c = ASN1Integer.o(seq.r(0)).r();
        this.d = AlgorithmIdentifier.f(seq.r(1));
        this.f = ASN1GeneralizedTime.r(seq.r(2));
        this.q = ASN1GeneralizedTime.r(seq.r(3));
        this.x = ObjectDataSequence.e(seq.r(4));
        this.y = seq.size() == 6 ? DERUTF8String.o(seq.r(5)).a() : null;
    }

    public static ObjectStoreData f(Object o) {
        if (o instanceof ObjectStoreData) {
            return (ObjectStoreData) o;
        }
        if (o != null) {
            return new ObjectStoreData(ASN1Sequence.o(o));
        }
        return null;
    }

    public ASN1GeneralizedTime e() {
        return this.f;
    }

    public AlgorithmIdentifier g() {
        return this.d;
    }

    public ASN1GeneralizedTime h() {
        return this.q;
    }

    public ObjectDataSequence i() {
        return this.x;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(this.c));
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        v.a(this.x);
        String str = this.y;
        if (str != null) {
            v.a(new DERUTF8String(str));
        }
        return new DERSequence(v);
    }
}
