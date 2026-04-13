package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PBES2Parameters extends ASN1Object implements PKCSObjectIdentifiers {
    private KeyDerivationFunc c;
    private EncryptionScheme d;

    public static PBES2Parameters f(Object obj) {
        if (obj instanceof PBES2Parameters) {
            return (PBES2Parameters) obj;
        }
        if (obj != null) {
            return new PBES2Parameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    public PBES2Parameters(KeyDerivationFunc keyDevFunc, EncryptionScheme encScheme) {
        this.c = keyDevFunc;
        this.d = encScheme;
    }

    private PBES2Parameters(ASN1Sequence obj) {
        Enumeration e = obj.s();
        ASN1Sequence funcSeq = ASN1Sequence.o(((ASN1Encodable) e.nextElement()).toASN1Primitive());
        ASN1Encodable r = funcSeq.r(0);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.k0;
        if (r.equals(aSN1ObjectIdentifier)) {
            this.c = new KeyDerivationFunc(aSN1ObjectIdentifier, PBKDF2Params.getInstance(funcSeq.r(1)));
        } else {
            this.c = KeyDerivationFunc.f(funcSeq);
        }
        this.d = EncryptionScheme.f(e.nextElement());
    }

    public KeyDerivationFunc g() {
        return this.c;
    }

    public EncryptionScheme e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
