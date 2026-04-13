package org.spongycastle.asn1.bc;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.util.Arrays;

public class ObjectData extends ASN1Object {
    private final BigInteger c;
    private final String d;
    private final ASN1GeneralizedTime f;
    private final ASN1GeneralizedTime q;
    private final ASN1OctetString x;
    private final String y;

    private ObjectData(ASN1Sequence seq) {
        this.c = ASN1Integer.o(seq.r(0)).r();
        this.d = DERUTF8String.o(seq.r(1)).a();
        this.f = ASN1GeneralizedTime.r(seq.r(2));
        this.q = ASN1GeneralizedTime.r(seq.r(3));
        this.x = ASN1OctetString.o(seq.r(4));
        this.y = seq.size() == 6 ? DERUTF8String.o(seq.r(5)).a() : null;
    }

    public ObjectData(BigInteger type, String identifier, Date creationDate, Date lastModifiedDate, byte[] data, String comment) {
        this.c = type;
        this.d = identifier;
        this.f = new DERGeneralizedTime(creationDate);
        this.q = new DERGeneralizedTime(lastModifiedDate);
        this.x = new DEROctetString(Arrays.h(data));
        this.y = comment;
    }

    public static ObjectData h(Object obj) {
        if (obj instanceof ObjectData) {
            return (ObjectData) obj;
        }
        if (obj != null) {
            return new ObjectData(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1GeneralizedTime e() {
        return this.f;
    }

    public byte[] f() {
        return Arrays.h(this.x.q());
    }

    public String g() {
        return this.d;
    }

    public ASN1GeneralizedTime i() {
        return this.q;
    }

    public BigInteger k() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(this.c));
        v.a(new DERUTF8String(this.d));
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
