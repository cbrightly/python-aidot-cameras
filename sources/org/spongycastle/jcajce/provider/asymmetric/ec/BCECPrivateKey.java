package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECPrivateKeySpec;

public class BCECPrivateKey implements ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 994553197664784084L;
    private String algorithm = "EC";
    private transient BigInteger c;
    private transient ECParameterSpec d;
    private transient ProviderConfiguration f;
    private transient DERBitString q;
    private boolean withCompression;
    private transient PKCS12BagAttributeCarrierImpl x = new PKCS12BagAttributeCarrierImpl();

    protected BCECPrivateKey() {
    }

    public BCECPrivateKey(ECPrivateKey key, ProviderConfiguration configuration) {
        this.c = key.getS();
        this.algorithm = key.getAlgorithm();
        this.d = key.getParams();
        this.f = configuration;
    }

    public BCECPrivateKey(String algorithm2, ECPrivateKeySpec spec, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.c = spec.b();
        if (spec.a() != null) {
            this.d = EC5Util.f(EC5Util.a(spec.a().a(), spec.a().e()), spec.a());
        } else {
            this.d = null;
        }
        this.f = configuration;
    }

    public BCECPrivateKey(String algorithm2, java.security.spec.ECPrivateKeySpec spec, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.c = spec.getS();
        this.d = spec.getParams();
        this.f = configuration;
    }

    public BCECPrivateKey(String algorithm2, BCECPrivateKey key) {
        this.algorithm = algorithm2;
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.x = key.x;
        this.q = key.q;
        this.f = key.f;
    }

    public BCECPrivateKey(String algorithm2, ECPrivateKeyParameters params, BCECPublicKey pubKey, ECParameterSpec spec, ProviderConfiguration configuration) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params.c();
        this.f = configuration;
        if (spec == null) {
            this.d = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.d = spec;
        }
        this.q = a(pubKey);
    }

    public BCECPrivateKey(String algorithm2, ECPrivateKeyParameters params, BCECPublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec, ProviderConfiguration configuration) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params.c();
        this.f = configuration;
        if (spec == null) {
            this.d = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.d = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
        try {
            this.q = a(pubKey);
        } catch (Exception e) {
            this.q = null;
        }
    }

    public BCECPrivateKey(String algorithm2, ECPrivateKeyParameters params, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.c = params.c();
        this.d = null;
        this.f = configuration;
    }

    BCECPrivateKey(String algorithm2, PrivateKeyInfo info, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.f = configuration;
        b(info);
    }

    private void b(PrivateKeyInfo info) {
        X962Parameters params = X962Parameters.e(info.g().h());
        this.d = EC5Util.h(params, EC5Util.j(this.f, params));
        ASN1Encodable privKey = info.h();
        if (privKey instanceof ASN1Integer) {
            this.c = ASN1Integer.o(privKey).r();
            return;
        }
        org.spongycastle.asn1.sec.ECPrivateKey ec = org.spongycastle.asn1.sec.ECPrivateKey.e(privKey);
        this.c = ec.f();
        this.q = ec.h();
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        int orderBitLength;
        org.spongycastle.asn1.sec.ECPrivateKey keyStructure;
        X962Parameters params = ECUtils.c(this.d, this.withCompression);
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec == null) {
            orderBitLength = ECUtil.m(this.f, (BigInteger) null, getS());
        } else {
            orderBitLength = ECUtil.m(this.f, eCParameterSpec.getOrder(), getS());
        }
        if (this.q != null) {
            keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.q, params);
        } else {
            keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), params);
        }
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.t3, params), keyStructure).getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public ECParameterSpec getParams() {
        return this.d;
    }

    public org.spongycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.g(eCParameterSpec, this.withCompression);
    }

    /* access modifiers changed from: package-private */
    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec != null) {
            return EC5Util.g(eCParameterSpec, this.withCompression);
        }
        return this.f.b();
    }

    public BigInteger getS() {
        return this.c;
    }

    public BigInteger getD() {
        return this.c;
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.x.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.x.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.x.getBagAttributeKeys();
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof BCECPrivateKey)) {
            return false;
        }
        BCECPrivateKey other = (BCECPrivateKey) o;
        if (!getD().equals(other.getD()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public String toString() {
        return ECUtil.o("EC", this.c, engineGetSpec());
    }

    private DERBitString a(BCECPublicKey pub2) {
        try {
            return SubjectPublicKeyInfo.g(ASN1Primitive.h(pub2.getEncoded())).h();
        } catch (IOException e) {
            return null;
        }
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.f = BouncyCastleProvider.CONFIGURATION;
        b(PrivateKeyInfo.f(ASN1Primitive.h((byte[]) in.readObject())));
        this.x = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }
}
