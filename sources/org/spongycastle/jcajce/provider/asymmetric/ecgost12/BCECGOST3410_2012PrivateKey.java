package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

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
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECCurve;

public class BCECGOST3410_2012PrivateKey implements ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm = "ECGOST3410-2012";
    private transient GOST3410PublicKeyAlgParameters c;
    private transient BigInteger d;
    private transient ECParameterSpec f;
    private transient DERBitString q;
    private boolean withCompression;
    private transient PKCS12BagAttributeCarrierImpl x = new PKCS12BagAttributeCarrierImpl();

    protected BCECGOST3410_2012PrivateKey() {
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKey key) {
        this.d = key.getS();
        this.algorithm = key.getAlgorithm();
        this.f = key.getParams();
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKeySpec spec) {
        this.d = spec.b();
        if (spec.a() != null) {
            this.f = EC5Util.f(EC5Util.a(spec.a().a(), spec.a().e()), spec.a());
        } else {
            this.f = null;
        }
    }

    public BCECGOST3410_2012PrivateKey(java.security.spec.ECPrivateKeySpec spec) {
        this.d = spec.getS();
        this.f = spec.getParams();
    }

    public BCECGOST3410_2012PrivateKey(BCECGOST3410_2012PrivateKey key) {
        this.d = key.d;
        this.f = key.f;
        this.withCompression = key.withCompression;
        this.x = key.x;
        this.q = key.q;
        this.c = key.c;
    }

    public BCECGOST3410_2012PrivateKey(String algorithm2, ECPrivateKeyParameters params, BCECGOST3410_2012PublicKey pubKey, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.d = params.c();
        if (spec == null) {
            this.f = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.f = spec;
        }
        this.c = pubKey.getGostParams();
        this.q = b(pubKey);
    }

    public BCECGOST3410_2012PrivateKey(String algorithm2, ECPrivateKeyParameters params, BCECGOST3410_2012PublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.d = params.c();
        if (spec == null) {
            this.f = new ECParameterSpec(EC5Util.a(dp.a(), dp.e()), new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
        } else {
            this.f = new ECParameterSpec(EC5Util.a(spec.a(), spec.e()), new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c().intValue());
        }
        this.c = pubKey.getGostParams();
        this.q = b(pubKey);
    }

    public BCECGOST3410_2012PrivateKey(String algorithm2, ECPrivateKeyParameters params) {
        this.algorithm = algorithm2;
        this.d = params.c();
        this.f = null;
    }

    BCECGOST3410_2012PrivateKey(PrivateKeyInfo info) {
        c(info);
    }

    private void c(PrivateKeyInfo info) {
        ASN1Primitive p = info.g().h().toASN1Primitive();
        if (!(p instanceof ASN1Sequence) || !(ASN1Sequence.o(p).size() == 2 || ASN1Sequence.o(p).size() == 3)) {
            X962Parameters params = X962Parameters.e(info.g().h());
            if (params.isNamedCurve()) {
                ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.t(params.f());
                X9ECParameters ecP = ECUtil.j(oid);
                if (ecP == null) {
                    ECDomainParameters gParam = ECGOST3410NamedCurves.b(oid);
                    this.f = new ECNamedCurveSpec(ECGOST3410NamedCurves.c(oid), EC5Util.a(gParam.a(), gParam.e()), new ECPoint(gParam.b().f().t(), gParam.b().g().t()), gParam.d(), gParam.c());
                } else {
                    this.f = new ECNamedCurveSpec(ECUtil.f(oid), EC5Util.a(ecP.e(), ecP.getSeed()), new ECPoint(ecP.f().f().t(), ecP.f().g().t()), ecP.i(), ecP.g());
                }
            } else if (params.g()) {
                this.f = null;
            } else {
                X9ECParameters ecP2 = X9ECParameters.h(params.f());
                this.f = new ECParameterSpec(EC5Util.a(ecP2.e(), ecP2.getSeed()), new ECPoint(ecP2.f().f().t(), ecP2.f().g().t()), ecP2.i(), ecP2.g().intValue());
            }
            ASN1Encodable privKey = info.h();
            if (privKey instanceof ASN1Integer) {
                this.d = ASN1Integer.o(privKey).r();
                return;
            }
            org.spongycastle.asn1.sec.ECPrivateKey ec = org.spongycastle.asn1.sec.ECPrivateKey.e(privKey);
            this.d = ec.f();
            this.q = ec.h();
            return;
        }
        GOST3410PublicKeyAlgParameters g = GOST3410PublicKeyAlgParameters.g(info.g().h());
        this.c = g;
        ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.a(ECGOST3410NamedCurves.c(g.h()));
        this.f = new ECNamedCurveSpec(ECGOST3410NamedCurves.c(this.c.h()), EC5Util.a(spec.a(), spec.e()), new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c());
        ASN1Encodable privKey2 = info.h();
        if (privKey2 instanceof ASN1Integer) {
            this.d = ASN1Integer.o(privKey2).q();
            return;
        }
        byte[] encVal = ASN1OctetString.o(privKey2).q();
        byte[] dVal = new byte[encVal.length];
        for (int i = 0; i != encVal.length; i++) {
            dVal[i] = encVal[(encVal.length - 1) - i];
        }
        this.d = new BigInteger(1, dVal);
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
        boolean is512 = this.d.bitLength() > 256;
        ASN1ObjectIdentifier identifier = is512 ? RosstandartObjectIdentifiers.h : RosstandartObjectIdentifiers.g;
        int size = is512 ? 64 : 32;
        if (this.c != null) {
            byte[] encKey = new byte[size];
            a(encKey, size, 0, getS());
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(identifier, this.c), new DEROctetString(encKey)).getEncoded("DER");
            } catch (IOException e) {
                return null;
            }
        } else {
            ECParameterSpec eCParameterSpec = this.f;
            if (eCParameterSpec instanceof ECNamedCurveSpec) {
                ASN1ObjectIdentifier curveOid = ECUtil.k(((ECNamedCurveSpec) eCParameterSpec).d());
                if (curveOid == null) {
                    curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.f).d());
                }
                params = new X962Parameters(curveOid);
                orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, this.f.getOrder(), getS());
            } else if (eCParameterSpec == null) {
                params = new X962Parameters((ASN1Null) DERNull.c);
                orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, (BigInteger) null, getS());
            } else {
                ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
                params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.f.getGenerator(), this.withCompression), this.f.getOrder(), BigInteger.valueOf((long) this.f.getCofactor()), this.f.getCurve().getSeed()));
                orderBitLength = ECUtil.m(BouncyCastleProvider.CONFIGURATION, this.f.getOrder(), getS());
            }
            if (this.q != null) {
                keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.q, params);
            } else {
                keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), params);
            }
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(identifier, params.toASN1Primitive()), keyStructure.toASN1Primitive()).getEncoded("DER");
            } catch (IOException e2) {
                return null;
            }
        }
    }

    private void a(byte[] encKey, int size, int offSet, BigInteger bI) {
        byte[] val = bI.toByteArray();
        if (val.length < size) {
            byte[] tmp = new byte[size];
            System.arraycopy(val, 0, tmp, tmp.length - val.length, val.length);
            val = tmp;
        }
        for (int i = 0; i != size; i++) {
            encKey[offSet + i] = val[(val.length - 1) - i];
        }
    }

    public ECParameterSpec getParams() {
        return this.f;
    }

    public org.spongycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.f;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.g(eCParameterSpec, this.withCompression);
    }

    /* access modifiers changed from: package-private */
    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.f;
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
        if (!(o instanceof BCECGOST3410_2012PrivateKey)) {
            return false;
        }
        BCECGOST3410_2012PrivateKey other = (BCECGOST3410_2012PrivateKey) o;
        if (!getD().equals(other.getD()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public String toString() {
        return ECUtil.o(this.algorithm, this.d, engineGetSpec());
    }

    private DERBitString b(BCECGOST3410_2012PublicKey pub2) {
        try {
            return SubjectPublicKeyInfo.g(ASN1Primitive.h(pub2.getEncoded())).h();
        } catch (IOException e) {
            return null;
        }
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        c(PrivateKeyInfo.f(ASN1Primitive.h((byte[]) in.readObject())));
        this.x = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }
}
