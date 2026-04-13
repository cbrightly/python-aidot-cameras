package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedData extends ASN1Object {
    ASN1Sequence c;

    public static EncryptedData g(Object obj) {
        if (obj instanceof EncryptedData) {
            return (EncryptedData) obj;
        }
        if (obj != null) {
            return new EncryptedData(ASN1Sequence.o(obj));
        }
        return null;
    }

    private EncryptedData(ASN1Sequence seq) {
        if (((ASN1Integer) seq.r(0)).r().intValue() == 0) {
            this.c = ASN1Sequence.o(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("sequence not version 0");
    }

    public EncryptedData(ASN1ObjectIdentifier contentType, AlgorithmIdentifier encryptionAlgorithm, ASN1Encodable content) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(contentType);
        v.a(encryptionAlgorithm.toASN1Primitive());
        v.a(new BERTaggedObject(false, 0, content));
        this.c = new BERSequence(v);
    }

    public AlgorithmIdentifier f() {
        return AlgorithmIdentifier.f(this.c.r(1));
    }

    public ASN1OctetString e() {
        if (this.c.size() == 3) {
            return ASN1OctetString.p(ASN1TaggedObject.o(this.c.r(2)), false);
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(0));
        v.a(this.c);
        return new BERSequence(v);
    }
}
