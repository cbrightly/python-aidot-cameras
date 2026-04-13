package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class SecretKeyData extends ASN1Object {
    private final ASN1ObjectIdentifier c;
    private final ASN1OctetString d;

    public SecretKeyData(ASN1ObjectIdentifier keyAlgorithm, byte[] keyBytes) {
        this.c = keyAlgorithm;
        this.d = new DEROctetString(Arrays.h(keyBytes));
    }

    private SecretKeyData(ASN1Sequence seq) {
        this.c = ASN1ObjectIdentifier.t(seq.r(0));
        this.d = ASN1OctetString.o(seq.r(1));
    }

    public static SecretKeyData e(Object o) {
        if (o instanceof SecretKeyData) {
            return (SecretKeyData) o;
        }
        if (o != null) {
            return new SecretKeyData(ASN1Sequence.o(o));
        }
        return null;
    }

    public byte[] g() {
        return Arrays.h(this.d.q());
    }

    public ASN1ObjectIdentifier f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
