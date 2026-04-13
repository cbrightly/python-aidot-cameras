package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo extends ASN1Object {
    private ASN1OctetString c;
    private AlgorithmIdentifier d;
    private ASN1Set f;

    public static PrivateKeyInfo f(Object obj) {
        if (obj instanceof PrivateKeyInfo) {
            return (PrivateKeyInfo) obj;
        }
        if (obj != null) {
            return new PrivateKeyInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public PrivateKeyInfo(AlgorithmIdentifier algId, ASN1Encodable privateKey) {
        this(algId, privateKey, (ASN1Set) null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier algId, ASN1Encodable privateKey, ASN1Set attributes) {
        this.c = new DEROctetString(privateKey.toASN1Primitive().getEncoded("DER"));
        this.d = algId;
        this.f = attributes;
    }

    public PrivateKeyInfo(ASN1Sequence seq) {
        Enumeration e = seq.s();
        if (((ASN1Integer) e.nextElement()).r().intValue() == 0) {
            this.d = AlgorithmIdentifier.f(e.nextElement());
            this.c = ASN1OctetString.o(e.nextElement());
            if (e.hasMoreElements()) {
                this.f = ASN1Set.q((ASN1TaggedObject) e.nextElement(), false);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for private key info");
    }

    public AlgorithmIdentifier g() {
        return this.d;
    }

    public AlgorithmIdentifier e() {
        return this.d;
    }

    public ASN1Encodable h() {
        return ASN1Primitive.h(this.c.q());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(0));
        v.a(this.d);
        v.a(this.c);
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 0, this.f));
        }
        return new DERSequence(v);
    }
}
