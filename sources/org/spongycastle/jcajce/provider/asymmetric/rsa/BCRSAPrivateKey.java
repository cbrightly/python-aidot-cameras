package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class BCRSAPrivateKey implements RSAPrivateKey, PKCS12BagAttributeCarrier {
    private static BigInteger c = BigInteger.valueOf(0);
    static final long serialVersionUID = 5110188922551353628L;
    private transient PKCS12BagAttributeCarrierImpl d = new PKCS12BagAttributeCarrierImpl();
    protected BigInteger modulus;
    protected BigInteger privateExponent;

    protected BCRSAPrivateKey() {
    }

    BCRSAPrivateKey(RSAKeyParameters key) {
        this.modulus = key.c();
        this.privateExponent = key.b();
    }

    BCRSAPrivateKey(RSAPrivateKeySpec spec) {
        this.modulus = spec.getModulus();
        this.privateExponent = spec.getPrivateExponent();
    }

    BCRSAPrivateKey(RSAPrivateKey key) {
        this.modulus = key.getModulus();
        this.privateExponent = key.getPrivateExponent();
    }

    BCRSAPrivateKey(org.spongycastle.asn1.pkcs.RSAPrivateKey key) {
        this.modulus = key.i();
        this.privateExponent = key.o();
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    public String getAlgorithm() {
        return "RSA";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.K, DERNull.c);
        BigInteger modulus2 = getModulus();
        BigInteger bigInteger = c;
        BigInteger privateExponent2 = getPrivateExponent();
        BigInteger bigInteger2 = c;
        return KeyUtil.b(algorithmIdentifier, new org.spongycastle.asn1.pkcs.RSAPrivateKey(modulus2, bigInteger, privateExponent2, bigInteger2, bigInteger2, bigInteger2, bigInteger2, bigInteger2));
    }

    public boolean equals(Object o) {
        if (!(o instanceof RSAPrivateKey)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        RSAPrivateKey key = (RSAPrivateKey) o;
        if (!getModulus().equals(key.getModulus()) || !getPrivateExponent().equals(key.getPrivateExponent())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getModulus().hashCode() ^ getPrivateExponent().hashCode();
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.d.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.d.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.d.getBagAttributeKeys();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.d = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
    }
}
