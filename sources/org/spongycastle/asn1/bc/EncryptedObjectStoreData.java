package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedObjectStoreData extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final ASN1OctetString d;

    public EncryptedObjectStoreData(AlgorithmIdentifier encryptionAlgorithm, byte[] encryptedContent) {
        this.c = encryptionAlgorithm;
        this.d = new DEROctetString(encryptedContent);
    }

    private EncryptedObjectStoreData(ASN1Sequence seq) {
        this.c = AlgorithmIdentifier.f(seq.r(0));
        this.d = ASN1OctetString.o(seq.r(1));
    }

    public static EncryptedObjectStoreData g(Object o) {
        if (o instanceof EncryptedObjectStoreData) {
            return (EncryptedObjectStoreData) o;
        }
        if (o != null) {
            return new EncryptedObjectStoreData(ASN1Sequence.o(o));
        }
        return null;
    }

    public ASN1OctetString e() {
        return this.d;
    }

    public AlgorithmIdentifier f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
