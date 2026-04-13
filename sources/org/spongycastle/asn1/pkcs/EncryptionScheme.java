package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptionScheme extends ASN1Object {
    private AlgorithmIdentifier c;

    public EncryptionScheme(ASN1ObjectIdentifier objectId, ASN1Encodable parameters) {
        this.c = new AlgorithmIdentifier(objectId, parameters);
    }

    private EncryptionScheme(ASN1Sequence seq) {
        this.c = AlgorithmIdentifier.f(seq);
    }

    public static EncryptionScheme f(Object obj) {
        if (obj instanceof EncryptionScheme) {
            return (EncryptionScheme) obj;
        }
        if (obj != null) {
            return new EncryptionScheme(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1ObjectIdentifier e() {
        return this.c.e();
    }

    public ASN1Encodable g() {
        return this.c.h();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c.toASN1Primitive();
    }
}
