package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedPrivateKeyInfo extends ASN1Object {
    private AlgorithmIdentifier c;
    private ASN1OctetString d;

    private EncryptedPrivateKeyInfo(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = AlgorithmIdentifier.f(e.nextElement());
        this.d = ASN1OctetString.o(e.nextElement());
    }

    public EncryptedPrivateKeyInfo(AlgorithmIdentifier algId, byte[] encoding) {
        this.c = algId;
        this.d = new DEROctetString(encoding);
    }

    public static EncryptedPrivateKeyInfo g(Object obj) {
        if (obj instanceof EncryptedPrivateKeyInfo) {
            return (EncryptedPrivateKeyInfo) obj;
        }
        if (obj != null) {
            return new EncryptedPrivateKeyInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public AlgorithmIdentifier f() {
        return this.c;
    }

    public byte[] e() {
        return this.d.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
