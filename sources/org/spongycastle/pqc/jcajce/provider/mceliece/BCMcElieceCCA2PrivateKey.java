package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class BCMcElieceCCA2PrivateKey implements PrivateKey {
    private static final long serialVersionUID = 1;
    private McElieceCCA2PrivateKeyParameters params;

    public BCMcElieceCCA2PrivateKey(McElieceCCA2PrivateKeyParameters params2) {
        this.params = params2;
    }

    public String getAlgorithm() {
        return "McEliece-CCA2";
    }

    public int getN() {
        return this.params.g();
    }

    public int getK() {
        return this.params.f();
    }

    public int getT() {
        return this.params.d().l();
    }

    public GF2mField getField() {
        return this.params.c();
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.params.d();
    }

    public Permutation getP() {
        return this.params.h();
    }

    public GF2Matrix getH() {
        return this.params.e();
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.params.i();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof BCMcElieceCCA2PrivateKey)) {
            return false;
        }
        BCMcElieceCCA2PrivateKey otherKey = (BCMcElieceCCA2PrivateKey) other;
        if (getN() != otherKey.getN() || getK() != otherKey.getK() || !getField().equals(otherKey.getField()) || !getGoppaPoly().equals(otherKey.getGoppaPoly()) || !getP().equals(otherKey.getP()) || !getH().equals(otherKey.getH())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((this.params.f() * 37) + this.params.g()) * 37) + this.params.c().hashCode()) * 37) + this.params.d().hashCode()) * 37) + this.params.h().hashCode()) * 37) + this.params.e().hashCode();
    }

    public byte[] getEncoded() {
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.n), new McElieceCCA2PrivateKey(getN(), getK(), getField(), getGoppaPoly(), getP(), Utils.a(this.params.b()))).getEncoded();
        } catch (IOException e) {
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
