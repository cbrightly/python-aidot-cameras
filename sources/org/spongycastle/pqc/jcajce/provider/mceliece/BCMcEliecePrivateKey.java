package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McEliecePrivateKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class BCMcEliecePrivateKey implements CipherParameters, PrivateKey {
    private static final long serialVersionUID = 1;
    private McEliecePrivateKeyParameters params;

    public BCMcEliecePrivateKey(McEliecePrivateKeyParameters params2) {
        this.params = params2;
    }

    public String getAlgorithm() {
        return "McEliece";
    }

    public int getN() {
        return this.params.f();
    }

    public int getK() {
        return this.params.e();
    }

    public GF2mField getField() {
        return this.params.b();
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.params.c();
    }

    public GF2Matrix getSInv() {
        return this.params.j();
    }

    public Permutation getP1() {
        return this.params.g();
    }

    public Permutation getP2() {
        return this.params.h();
    }

    public GF2Matrix getH() {
        return this.params.d();
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.params.i();
    }

    public boolean equals(Object other) {
        if (!(other instanceof BCMcEliecePrivateKey)) {
            return false;
        }
        BCMcEliecePrivateKey otherKey = (BCMcEliecePrivateKey) other;
        if (getN() != otherKey.getN() || getK() != otherKey.getK() || !getField().equals(otherKey.getField()) || !getGoppaPoly().equals(otherKey.getGoppaPoly()) || !getSInv().equals(otherKey.getSInv()) || !getP1().equals(otherKey.getP1()) || !getP2().equals(otherKey.getP2())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((((this.params.e() * 37) + this.params.f()) * 37) + this.params.b().hashCode()) * 37) + this.params.c().hashCode()) * 37) + this.params.g().hashCode()) * 37) + this.params.h().hashCode()) * 37) + this.params.j().hashCode();
    }

    public byte[] getEncoded() {
        try {
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.m), new McEliecePrivateKey(this.params.f(), this.params.e(), this.params.b(), this.params.c(), this.params.g(), this.params.h(), this.params.j())).getEncoded();
            } catch (IOException e) {
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    /* access modifiers changed from: package-private */
    public AsymmetricKeyParameter getKeyParams() {
        return this.params;
    }
}
