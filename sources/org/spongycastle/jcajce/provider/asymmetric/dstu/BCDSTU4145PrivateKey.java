package org.spongycastle.jcajce.provider.asymmetric.dstu;

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
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.ua.DSTU4145NamedCurves;
import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECCurve;

public class BCDSTU4145PrivateKey implements ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm = "DSTU4145";
    private transient BigInteger c;
    private transient ECParameterSpec d;
    private transient DERBitString f;
    private transient PKCS12BagAttributeCarrierImpl q = new PKCS12BagAttributeCarrierImpl();
    private boolean withCompression;

    protected BCDSTU4145PrivateKey() {
    }

    public BCDSTU4145PrivateKey(ECPrivateKey key) {
        this.c = key.getS();
        this.algorithm = key.getAlgorithm();
        this.d = key.getParams();
    }

    public BCDSTU4145PrivateKey(ECPrivateKeySpec spec) {
        this.c = spec.b();
        if (spec.a() != null) {
            this.d = EC5Util.f(EC5Util.a(spec.a().a(), spec.a().e()), spec.a());
        } else {
            this.d = null;
        }
    }

    public BCDSTU4145PrivateKey(java.security.spec.ECPrivateKeySpec spec) {
        this.c = spec.getS();
        this.d = spec.getParams();
    }

    public BCDSTU4145PrivateKey(BCDSTU4145PrivateKey key) {
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.q = key.q;
        this.f = key.f;
    }

    public BCDSTU4145PrivateKey(String algorithm2, ECPrivateKeyParameters params, BCDSTU4145PublicKey pubKey, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params.c();
        if (spec == null) {
            this.d = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.d = spec;
        }
        this.f = a(pubKey);
    }

    public BCDSTU4145PrivateKey(String algorithm2, ECPrivateKeyParameters params, BCDSTU4145PublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params.c();
        if (spec == null) {
            this.d = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.d = new ECParameterSpec(EC5Util.a(spec.a(), spec.e()), new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c().intValue());
        }
        this.f = a(pubKey);
    }

    public BCDSTU4145PrivateKey(String algorithm2, ECPrivateKeyParameters params) {
        this.algorithm = algorithm2;
        this.c = params.c();
        this.d = null;
    }

    BCDSTU4145PrivateKey(PrivateKeyInfo info) {
        b(info);
    }

    private void b(PrivateKeyInfo info) {
        X962Parameters params = new X962Parameters((ASN1Primitive) info.g().h());
        if (params.isNamedCurve()) {
            ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.t(params.f());
            X9ECParameters ecP = ECUtil.j(oid);
            if (ecP == null) {
                ECDomainParameters gParam = DSTU4145NamedCurves.a(oid);
                this.d = new ECNamedCurveSpec(oid.s(), EC5Util.a(gParam.a(), gParam.e()), new ECPoint(gParam.b().f().t(), gParam.b().g().t()), gParam.d(), gParam.c());
            } else {
                this.d = new ECNamedCurveSpec(ECUtil.f(oid), EC5Util.a(ecP.e(), ecP.getSeed()), new ECPoint(ecP.f().f().t(), ecP.f().g().t()), ecP.i(), ecP.g());
            }
        } else if (params.g()) {
            this.d = null;
        } else {
            X9ECParameters ecP2 = X9ECParameters.h(params.f());
            this.d = new ECParameterSpec(EC5Util.a(ecP2.e(), ecP2.getSeed()), new ECPoint(ecP2.f().f().t(), ecP2.f().g().t()), ecP2.i(), ecP2.g().intValue());
        }
        ASN1Encodable privKey = info.h();
        if (privKey instanceof ASN1Integer) {
            this.c = ASN1Integer.o(privKey).r();
            return;
        }
        org.spongycastle.asn1.sec.ECPrivateKey ec = org.spongycastle.asn1.sec.ECPrivateKey.e(privKey);
        this.c = ec.f();
        this.f = ec.h();
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        X962Parameters params;
        int orderBitLength;
        org.spongycastle.asn1.sec.ECPrivateKey keyStructure;
        PrivateKeyInfo info;
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier curveOid = ECUtil.k(((ECNamedCurveSpec) eCParameterSpec).d());
            if (curveOid == null) {
                curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.d).d());
            }
            params = new X962Parameters(curveOid);
            orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, this.d.getOrder(), getS());
        } else if (eCParameterSpec == null) {
            params = new X962Parameters((ASN1Null) DERNull.c);
            orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, (BigInteger) null, getS());
        } else {
            ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
            params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.d.getGenerator(), this.withCompression), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed()));
            orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, this.d.getOrder(), getS());
        }
        if (this.f != null) {
            keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.f, params);
        } else {
            keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), params);
        }
        try {
            if (this.algorithm.equals("DSTU4145")) {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.c, params.toASN1Primitive()), keyStructure.toASN1Primitive());
            } else {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.t3, params.toASN1Primitive()), keyStructure.toASN1Primitive());
            }
            return info.getEncoded("DER");
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
        return BouncyCastleProvider.CONFIGURATION.b();
    }

    public BigInteger getS() {
        return this.c;
    }

    public BigInteger getD() {
        return this.c;
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.q.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.q.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.q.getBagAttributeKeys();
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof BCDSTU4145PrivateKey)) {
            return false;
        }
        BCDSTU4145PrivateKey other = (BCDSTU4145PrivateKey) o;
        if (!getD().equals(other.getD()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public String toString() {
        return ECUtil.o(this.algorithm, this.c, engineGetSpec());
    }

    private DERBitString a(BCDSTU4145PublicKey pub2) {
        try {
            return SubjectPublicKeyInfo.g(ASN1Primitive.h(pub2.getEncoded())).h();
        } catch (IOException e) {
            return null;
        }
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        b(PrivateKeyInfo.f(ASN1Primitive.h((byte[]) in.readObject())));
        this.q = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }
}
