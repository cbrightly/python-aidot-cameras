package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;

public class BCMcElieceCCA2PublicKey implements CipherParameters, PublicKey {
    private static final long serialVersionUID = 1;
    private McElieceCCA2PublicKeyParameters params;

    public BCMcElieceCCA2PublicKey(McElieceCCA2PublicKeyParameters params2) {
        this.params = params2;
    }

    public String getAlgorithm() {
        return "McEliece-CCA2";
    }

    public int getN() {
        return this.params.e();
    }

    public int getK() {
        return this.params.d();
    }

    public int getT() {
        return this.params.f();
    }

    public GF2Matrix getG() {
        return this.params.c();
    }

    public String toString() {
        return (("McEliecePublicKey:\n" + " length of the code         : " + this.params.e() + "\n") + " error correction capability: " + this.params.f() + "\n") + " generator matrix           : " + this.params.c().toString();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof BCMcElieceCCA2PublicKey)) {
            return false;
        }
        BCMcElieceCCA2PublicKey otherKey = (BCMcElieceCCA2PublicKey) other;
        if (this.params.e() == otherKey.getN() && this.params.f() == otherKey.getT() && this.params.c().equals(otherKey.getG())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.params.e() + (this.params.f() * 37)) * 37) + this.params.c().hashCode();
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.n), (ASN1Encodable) new McElieceCCA2PublicKey(this.params.e(), this.params.f(), this.params.c(), Utils.a(this.params.b()))).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
    }

    /* access modifiers changed from: package-private */
    public AsymmetricKeyParameter getKeyParams() {
        return this.params;
    }
}
