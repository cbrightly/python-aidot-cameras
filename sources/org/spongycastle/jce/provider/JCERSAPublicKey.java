package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.util.Strings;

public class JCERSAPublicKey implements RSAPublicKey {
    static final long serialVersionUID = 2675817738516720772L;
    private BigInteger modulus;
    private BigInteger publicExponent;

    JCERSAPublicKey(RSAKeyParameters key) {
        this.modulus = key.c();
        this.publicExponent = key.b();
    }

    JCERSAPublicKey(RSAPublicKeySpec spec) {
        this.modulus = spec.getModulus();
        this.publicExponent = spec.getPublicExponent();
    }

    JCERSAPublicKey(RSAPublicKey key) {
        this.modulus = key.getModulus();
        this.publicExponent = key.getPublicExponent();
    }

    JCERSAPublicKey(SubjectPublicKeyInfo info) {
        try {
            org.spongycastle.asn1.pkcs.RSAPublicKey pubKey = org.spongycastle.asn1.pkcs.RSAPublicKey.e(info.i());
            this.modulus = pubKey.f();
            this.publicExponent = pubKey.g();
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in RSA public key");
        }
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public String getAlgorithm() {
        return "RSA";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        return KeyUtil.c(new AlgorithmIdentifier(PKCSObjectIdentifiers.K, DERNull.c), new org.spongycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
    }

    public int hashCode() {
        return getModulus().hashCode() ^ getPublicExponent().hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RSAPublicKey)) {
            return false;
        }
        RSAPublicKey key = (RSAPublicKey) o;
        if (!getModulus().equals(key.getModulus()) || !getPublicExponent().equals(key.getPublicExponent())) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("RSA Public Key");
        buf.append(nl);
        buf.append("            modulus: ");
        buf.append(getModulus().toString(16));
        buf.append(nl);
        buf.append("    public exponent: ");
        buf.append(getPublicExponent().toString(16));
        buf.append(nl);
        return buf.toString();
    }
}
