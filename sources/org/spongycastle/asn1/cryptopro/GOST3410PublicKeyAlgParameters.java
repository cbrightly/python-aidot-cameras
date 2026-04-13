package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class GOST3410PublicKeyAlgParameters extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1ObjectIdentifier d;
    private ASN1ObjectIdentifier f;

    public static GOST3410PublicKeyAlgParameters g(Object obj) {
        if (obj instanceof GOST3410PublicKeyAlgParameters) {
            return (GOST3410PublicKeyAlgParameters) obj;
        }
        if (obj != null) {
            return new GOST3410PublicKeyAlgParameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier publicKeyParamSet, ASN1ObjectIdentifier digestParamSet) {
        this.c = publicKeyParamSet;
        this.d = digestParamSet;
        this.f = null;
    }

    public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier publicKeyParamSet, ASN1ObjectIdentifier digestParamSet, ASN1ObjectIdentifier encryptionParamSet) {
        this.c = publicKeyParamSet;
        this.d = digestParamSet;
        this.f = encryptionParamSet;
    }

    public GOST3410PublicKeyAlgParameters(ASN1Sequence seq) {
        this.c = (ASN1ObjectIdentifier) seq.r(0);
        this.d = (ASN1ObjectIdentifier) seq.r(1);
        if (seq.size() > 2) {
            this.f = (ASN1ObjectIdentifier) seq.r(2);
        }
    }

    public ASN1ObjectIdentifier h() {
        return this.c;
    }

    public ASN1ObjectIdentifier e() {
        return this.d;
    }

    public ASN1ObjectIdentifier f() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.f;
        if (aSN1ObjectIdentifier != null) {
            v.a(aSN1ObjectIdentifier);
        }
        return new DERSequence(v);
    }
}
