package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.x509.Certificate;

public class EncryptedPrivateKeyData extends ASN1Object {
    private final EncryptedPrivateKeyInfo c;
    private final Certificate[] d;

    public EncryptedPrivateKeyData(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo, Certificate[] certificateChain) {
        this.c = encryptedPrivateKeyInfo;
        Certificate[] certificateArr = new Certificate[certificateChain.length];
        this.d = certificateArr;
        System.arraycopy(certificateChain, 0, certificateArr, 0, certificateChain.length);
    }

    private EncryptedPrivateKeyData(ASN1Sequence seq) {
        this.c = EncryptedPrivateKeyInfo.g(seq.r(0));
        ASN1Sequence certSeq = ASN1Sequence.o(seq.r(1));
        this.d = new Certificate[certSeq.size()];
        int i = 0;
        while (true) {
            Certificate[] certificateArr = this.d;
            if (i != certificateArr.length) {
                certificateArr[i] = Certificate.f(certSeq.r(i));
                i++;
            } else {
                return;
            }
        }
    }

    public static EncryptedPrivateKeyData g(Object o) {
        if (o instanceof EncryptedPrivateKeyData) {
            return (EncryptedPrivateKeyData) o;
        }
        if (o != null) {
            return new EncryptedPrivateKeyData(ASN1Sequence.o(o));
        }
        return null;
    }

    public Certificate[] e() {
        Certificate[] certificateArr = this.d;
        Certificate[] tmp = new Certificate[certificateArr.length];
        System.arraycopy(certificateArr, 0, tmp, 0, certificateArr.length);
        return tmp;
    }

    public EncryptedPrivateKeyInfo f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DERSequence((ASN1Encodable[]) this.d));
        return new DERSequence(v);
    }
}
