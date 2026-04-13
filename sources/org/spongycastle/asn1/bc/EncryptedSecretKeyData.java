package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;

public class EncryptedSecretKeyData extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final ASN1OctetString d;

    public EncryptedSecretKeyData(AlgorithmIdentifier keyEncryptionAlgorithm, byte[] encryptedKeyData) {
        this.c = keyEncryptionAlgorithm;
        this.d = new DEROctetString(Arrays.h(encryptedKeyData));
    }

    private EncryptedSecretKeyData(ASN1Sequence seq) {
        this.c = AlgorithmIdentifier.f(seq.r(0));
        this.d = ASN1OctetString.o(seq.r(1));
    }

    public static EncryptedSecretKeyData f(Object o) {
        if (o instanceof EncryptedSecretKeyData) {
            return (EncryptedSecretKeyData) o;
        }
        if (o != null) {
            return new EncryptedSecretKeyData(ASN1Sequence.o(o));
        }
        return null;
    }

    public AlgorithmIdentifier g() {
        return this.c;
    }

    public byte[] e() {
        return Arrays.h(this.d.q());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
