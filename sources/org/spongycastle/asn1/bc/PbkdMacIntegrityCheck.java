package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.KeyDerivationFunc;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;

public class PbkdMacIntegrityCheck extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final KeyDerivationFunc d;
    private final ASN1OctetString f;

    public PbkdMacIntegrityCheck(AlgorithmIdentifier macAlgorithm, KeyDerivationFunc pbkdAlgorithm, byte[] mac) {
        this.c = macAlgorithm;
        this.d = pbkdAlgorithm;
        this.f = new DEROctetString(Arrays.h(mac));
    }

    private PbkdMacIntegrityCheck(ASN1Sequence seq) {
        this.c = AlgorithmIdentifier.f(seq.r(0));
        this.d = KeyDerivationFunc.f(seq.r(1));
        this.f = ASN1OctetString.o(seq.r(2));
    }

    public static PbkdMacIntegrityCheck e(Object o) {
        if (o instanceof PbkdMacIntegrityCheck) {
            return (PbkdMacIntegrityCheck) o;
        }
        if (o != null) {
            return new PbkdMacIntegrityCheck(ASN1Sequence.o(o));
        }
        return null;
    }

    public AlgorithmIdentifier g() {
        return this.c;
    }

    public KeyDerivationFunc h() {
        return this.d;
    }

    public byte[] f() {
        return Arrays.h(this.f.q());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }
}
