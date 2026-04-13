package org.spongycastle.jce.provider;

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
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.sec.ECPrivateKeyStructure;
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
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.Strings;

public class JCEECPrivateKey implements ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    private String algorithm = "EC";
    private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private BigInteger d;
    private ECParameterSpec ecSpec;
    private DERBitString publicKey;
    private boolean withCompression;

    protected JCEECPrivateKey() {
    }

    public JCEECPrivateKey(ECPrivateKey key) {
        this.d = key.getS();
        this.algorithm = key.getAlgorithm();
        this.ecSpec = key.getParams();
    }

    public JCEECPrivateKey(String algorithm2, ECPrivateKeySpec spec) {
        this.algorithm = algorithm2;
        this.d = spec.b();
        if (spec.a() != null) {
            this.ecSpec = EC5Util.f(EC5Util.a(spec.a().a(), spec.a().e()), spec.a());
        } else {
            this.ecSpec = null;
        }
    }

    public JCEECPrivateKey(String algorithm2, java.security.spec.ECPrivateKeySpec spec) {
        this.algorithm = algorithm2;
        this.d = spec.getS();
        this.ecSpec = spec.getParams();
    }

    public JCEECPrivateKey(String algorithm2, JCEECPrivateKey key) {
        this.algorithm = algorithm2;
        this.d = key.d;
        this.ecSpec = key.ecSpec;
        this.withCompression = key.withCompression;
        this.attrCarrier = key.attrCarrier;
        this.publicKey = key.publicKey;
    }

    public JCEECPrivateKey(String algorithm2, ECPrivateKeyParameters params, JCEECPublicKey pubKey, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.d = params.c();
        if (spec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.ecSpec = spec;
        }
        this.publicKey = a(pubKey);
    }

    public JCEECPrivateKey(String algorithm2, ECPrivateKeyParameters params, JCEECPublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.d = params.c();
        if (spec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.ecSpec = new ECParameterSpec(EC5Util.a(spec.a(), spec.e()), new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c().intValue());
        }
        this.publicKey = a(pubKey);
    }

    public JCEECPrivateKey(String algorithm2, ECPrivateKeyParameters params) {
        this.algorithm = algorithm2;
        this.d = params.c();
        this.ecSpec = null;
    }

    JCEECPrivateKey(PrivateKeyInfo info) {
        b(info);
    }

    private void b(PrivateKeyInfo info) {
        X962Parameters params = new X962Parameters((ASN1Primitive) info.g().h());
        if (params.isNamedCurve()) {
            ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.t(params.f());
            X9ECParameters ecP = ECUtil.j(oid);
            if (ecP == null) {
                ECDomainParameters gParam = ECGOST3410NamedCurves.b(oid);
                this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.c(oid), EC5Util.a(gParam.a(), gParam.e()), new ECPoint(gParam.b().f().t(), gParam.b().g().t()), gParam.d(), gParam.c());
            } else {
                this.ecSpec = new ECNamedCurveSpec(ECUtil.f(oid), EC5Util.a(ecP.e(), ecP.getSeed()), new ECPoint(ecP.f().f().t(), ecP.f().g().t()), ecP.i(), ecP.g());
            }
        } else if (params.g()) {
            this.ecSpec = null;
        } else {
            X9ECParameters ecP2 = X9ECParameters.h(params.f());
            this.ecSpec = new ECParameterSpec(EC5Util.a(ecP2.e(), ecP2.getSeed()), new ECPoint(ecP2.f().f().t(), ecP2.f().g().t()), ecP2.i(), ecP2.g().intValue());
        }
        ASN1Encodable privKey = info.h();
        if (privKey instanceof ASN1Integer) {
            this.d = ASN1Integer.o(privKey).r();
            return;
        }
        ECPrivateKeyStructure ec = new ECPrivateKeyStructure((ASN1Sequence) privKey);
        this.d = ec.e();
        this.publicKey = ec.g();
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        X962Parameters params;
        ECPrivateKeyStructure keyStructure;
        PrivateKeyInfo info;
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier curveOid = ECUtil.k(((ECNamedCurveSpec) eCParameterSpec).d());
            if (curveOid == null) {
                curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).d());
            }
            params = new X962Parameters(curveOid);
        } else if (eCParameterSpec == null) {
            params = new X962Parameters((ASN1Null) DERNull.c);
        } else {
            ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
            params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long) this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        if (this.publicKey != null) {
            keyStructure = new ECPrivateKeyStructure(getS(), this.publicKey, params);
        } else {
            keyStructure = new ECPrivateKeyStructure(getS(), params);
        }
        try {
            if (this.algorithm.equals("ECGOST3410")) {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.m, params.toASN1Primitive()), keyStructure.toASN1Primitive());
            } else {
                info = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.t3, params.toASN1Primitive()), keyStructure.toASN1Primitive());
            }
            return info.getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    public org.spongycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.g(eCParameterSpec, this.withCompression);
    }

    /* access modifiers changed from: package-private */
    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.g(eCParameterSpec, this.withCompression);
        }
        return BouncyCastleProvider.CONFIGURATION.b();
    }

    public BigInteger getS() {
        return this.d;
    }

    public BigInteger getD() {
        return this.d;
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.attrCarrier.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.attrCarrier.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof JCEECPrivateKey)) {
            return false;
        }
        JCEECPrivateKey other = (JCEECPrivateKey) o;
        if (!getD().equals(other.getD()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("EC Private Key");
        buf.append(nl);
        buf.append("             S: ");
        buf.append(this.d.toString(16));
        buf.append(nl);
        return buf.toString();
    }

    private DERBitString a(JCEECPublicKey pub2) {
        try {
            return SubjectPublicKeyInfo.g(ASN1Primitive.h(pub2.getEncoded())).h();
        } catch (IOException e) {
            return null;
        }
    }

    private void readObject(ObjectInputStream in) {
        b(PrivateKeyInfo.f(ASN1Primitive.h((byte[]) in.readObject())));
        this.algorithm = (String) in.readObject();
        this.withCompression = in.readBoolean();
        PKCS12BagAttributeCarrierImpl pKCS12BagAttributeCarrierImpl = new PKCS12BagAttributeCarrierImpl();
        this.attrCarrier = pKCS12BagAttributeCarrierImpl;
        pKCS12BagAttributeCarrierImpl.a(in);
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getEncoded());
        out.writeObject(this.algorithm);
        out.writeBoolean(this.withCompression);
        this.attrCarrier.b(out);
    }
}
