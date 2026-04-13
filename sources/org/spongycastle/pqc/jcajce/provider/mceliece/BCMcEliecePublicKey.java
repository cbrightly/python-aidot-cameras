package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McEliecePublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;

public class BCMcEliecePublicKey implements PublicKey {
    private static final long serialVersionUID = 1;
    private McEliecePublicKeyParameters params;

    public BCMcEliecePublicKey(McEliecePublicKeyParameters params2) {
        this.params = params2;
    }

    public String getAlgorithm() {
        return "McEliece";
    }

    public int getN() {
        return this.params.d();
    }

    public int getK() {
        return this.params.c();
    }

    public int getT() {
        return this.params.e();
    }

    public GF2Matrix getG() {
        return this.params.b();
    }

    public String toString() {
        return (("McEliecePublicKey:\n" + " length of the code         : " + this.params.d() + "\n") + " error correction capability: " + this.params.e() + "\n") + " generator matrix           : " + this.params.b();
    }

    public boolean equals(Object other) {
        if (!(other instanceof BCMcEliecePublicKey)) {
            return false;
        }
        BCMcEliecePublicKey otherKey = (BCMcEliecePublicKey) other;
        if (this.params.d() == otherKey.getN() && this.params.e() == otherKey.getT() && this.params.b().equals(otherKey.getG())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.params.d() + (this.params.e() * 37)) * 37) + this.params.b().hashCode();
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.m), (ASN1Encodable) new McEliecePublicKey(this.params.d(), this.params.e(), this.params.b())).getEncoded();
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
