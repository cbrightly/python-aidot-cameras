package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class BCRSAPublicKey implements RSAPublicKey {
    private static final AlgorithmIdentifier c = new AlgorithmIdentifier(PKCSObjectIdentifiers.K, DERNull.c);
    static final long serialVersionUID = 2675817738516720772L;
    private transient AlgorithmIdentifier d;
    private BigInteger modulus;
    private BigInteger publicExponent;

    BCRSAPublicKey(RSAKeyParameters key) {
        this.d = c;
        this.modulus = key.c();
        this.publicExponent = key.b();
    }

    BCRSAPublicKey(RSAPublicKeySpec spec) {
        this.d = c;
        this.modulus = spec.getModulus();
        this.publicExponent = spec.getPublicExponent();
    }

    BCRSAPublicKey(RSAPublicKey key) {
        this.d = c;
        this.modulus = key.getModulus();
        this.publicExponent = key.getPublicExponent();
    }

    BCRSAPublicKey(SubjectPublicKeyInfo info) {
        a(info);
    }

    private void a(SubjectPublicKeyInfo info) {
        try {
            org.spongycastle.asn1.pkcs.RSAPublicKey pubKey = org.spongycastle.asn1.pkcs.RSAPublicKey.e(info.i());
            this.d = info.e();
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
        return KeyUtil.c(this.d, new org.spongycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
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
        buf.append("RSA Public Key [");
        buf.append(RSAUtil.a(getModulus(), getPublicExponent()));
        buf.append("]");
        buf.append(nl);
        buf.append("            modulus: ");
        buf.append(getModulus().toString(16));
        buf.append(nl);
        buf.append("    public exponent: ");
        buf.append(getPublicExponent().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        try {
            this.d = AlgorithmIdentifier.f(in.readObject());
        } catch (Exception e) {
            this.d = c;
        }
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        if (!this.d.equals(c)) {
            out.writeObject(this.d.getEncoded());
        }
    }
}
