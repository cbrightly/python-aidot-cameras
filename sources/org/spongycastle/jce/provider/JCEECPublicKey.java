package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Strings;

public class JCEECPublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    private String algorithm = "EC";
    private ECParameterSpec ecSpec;
    private GOST3410PublicKeyAlgParameters gostParams;
    private ECPoint q;
    private boolean withCompression;

    public JCEECPublicKey(String algorithm2, JCEECPublicKey key) {
        this.algorithm = algorithm2;
        this.q = key.q;
        this.ecSpec = key.ecSpec;
        this.withCompression = key.withCompression;
        this.gostParams = key.gostParams;
    }

    public JCEECPublicKey(String algorithm2, ECPublicKeySpec spec) {
        this.algorithm = algorithm2;
        ECParameterSpec params = spec.getParams();
        this.ecSpec = params;
        this.q = EC5Util.d(params, spec.getW(), false);
    }

    public JCEECPublicKey(String algorithm2, org.spongycastle.jce.spec.ECPublicKeySpec spec) {
        this.algorithm = algorithm2;
        this.q = spec.b();
        if (spec.a() != null) {
            this.ecSpec = EC5Util.f(EC5Util.a(spec.a().a(), spec.a().e()), spec.a());
            return;
        }
        if (this.q.i() == null) {
            this.q = BouncyCastleProvider.CONFIGURATION.b().a().g(this.q.f().t(), this.q.g().t(), false);
        }
        this.ecSpec = null;
    }

    public JCEECPublicKey(String algorithm2, ECPublicKeyParameters params, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.q = params.c();
        if (spec == null) {
            this.ecSpec = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.ecSpec = spec;
        }
    }

    public JCEECPublicKey(String algorithm2, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.q = params.c();
        if (spec == null) {
            this.ecSpec = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.ecSpec = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
    }

    public JCEECPublicKey(String algorithm2, ECPublicKeyParameters params) {
        this.algorithm = algorithm2;
        this.q = params.c();
        this.ecSpec = null;
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters dp) {
        return new ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
    }

    public JCEECPublicKey(ECPublicKey key) {
        this.algorithm = key.getAlgorithm();
        ECParameterSpec params = key.getParams();
        this.ecSpec = params;
        this.q = EC5Util.d(params, key.getW(), false);
    }

    JCEECPublicKey(SubjectPublicKeyInfo info) {
        c(info);
    }

    private void c(SubjectPublicKeyInfo info) {
        ECCurve curve;
        if (info.f().e().equals(CryptoProObjectIdentifiers.m)) {
            DERBitString bits = info.h();
            this.algorithm = "ECGOST3410";
            try {
                byte[] keyEnc = ((ASN1OctetString) ASN1Primitive.h(bits.q())).q();
                byte[] x = new byte[32];
                byte[] y = new byte[32];
                for (int i = 0; i != x.length; i++) {
                    x[i] = keyEnc[31 - i];
                }
                for (int i2 = 0; i2 != y.length; i2++) {
                    y[i2] = keyEnc[63 - i2];
                }
                GOST3410PublicKeyAlgParameters gOST3410PublicKeyAlgParameters = new GOST3410PublicKeyAlgParameters((ASN1Sequence) info.f().h());
                this.gostParams = gOST3410PublicKeyAlgParameters;
                ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.a(ECGOST3410NamedCurves.c(gOST3410PublicKeyAlgParameters.h()));
                ECCurve curve2 = spec.a();
                EllipticCurve ellipticCurve = EC5Util.a(curve2, spec.e());
                this.q = curve2.g(new BigInteger(1, x), new BigInteger(1, y), false);
                this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.c(this.gostParams.h()), ellipticCurve, new java.security.spec.ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c());
            } catch (IOException e) {
                throw new IllegalArgumentException("error recovering public key");
            }
        } else {
            X962Parameters params = new X962Parameters((ASN1Primitive) info.f().h());
            if (params.isNamedCurve()) {
                ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) params.f();
                X9ECParameters ecP = ECUtil.j(oid);
                curve = ecP.e();
                this.ecSpec = new ECNamedCurveSpec(ECUtil.f(oid), EC5Util.a(curve, ecP.getSeed()), new java.security.spec.ECPoint(ecP.f().f().t(), ecP.f().g().t()), ecP.i(), ecP.g());
            } else if (params.g()) {
                this.ecSpec = null;
                curve = BouncyCastleProvider.CONFIGURATION.b().a();
            } else {
                X9ECParameters ecP2 = X9ECParameters.h(params.f());
                curve = ecP2.e();
                this.ecSpec = new ECParameterSpec(EC5Util.a(curve, ecP2.getSeed()), new java.security.spec.ECPoint(ecP2.f().f().t(), ecP2.f().g().t()), ecP2.i(), ecP2.g().intValue());
            }
            byte[] data = info.h().q();
            ASN1OctetString key = new DEROctetString(data);
            if (data[0] == 4 && data[1] == data.length - 2 && ((data[2] == 2 || data[2] == 3) && new X9IntegerConverter().a(curve) >= data.length - 3)) {
                try {
                    key = (ASN1OctetString) ASN1Primitive.h(data);
                } catch (IOException e2) {
                    throw new IllegalArgumentException("error recovering public key");
                }
            }
            this.q = new X9ECPoint(curve, key).e();
        }
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        SubjectPublicKeyInfo info;
        ASN1Encodable params;
        GOST3410PublicKeyAlgParameters params2;
        if (this.algorithm.equals("ECGOST3410")) {
            if (this.gostParams != null) {
                params2 = this.gostParams;
            } else {
                ECParameterSpec eCParameterSpec = this.ecSpec;
                if (eCParameterSpec instanceof ECNamedCurveSpec) {
                    params2 = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.d(((ECNamedCurveSpec) eCParameterSpec).d()), CryptoProObjectIdentifiers.p);
                } else {
                    ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
                    params2 = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long) this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
                }
            }
            BigInteger bX = this.q.f().t();
            BigInteger bY = this.q.g().t();
            byte[] encKey = new byte[64];
            b(encKey, 0, bX);
            b(encKey, 32, bY);
            try {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.m, params2), (ASN1Encodable) new DEROctetString(encKey));
            } catch (IOException e) {
                return null;
            }
        } else {
            ECParameterSpec eCParameterSpec2 = this.ecSpec;
            if (eCParameterSpec2 instanceof ECNamedCurveSpec) {
                ASN1ObjectIdentifier curveOid = ECUtil.k(((ECNamedCurveSpec) eCParameterSpec2).d());
                if (curveOid == null) {
                    curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).d());
                }
                params = new X962Parameters(curveOid);
            } else if (eCParameterSpec2 == null) {
                params = new X962Parameters((ASN1Null) DERNull.c);
            } else {
                ECCurve curve2 = EC5Util.b(eCParameterSpec2.getCurve());
                params = new X962Parameters(new X9ECParameters(curve2, EC5Util.e(curve2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long) this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
            }
            info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.t3, params), ((ASN1OctetString) new X9ECPoint(engineGetQ().i().g(getQ().f().t(), getQ().g().t(), this.withCompression)).toASN1Primitive()).q());
        }
        return KeyUtil.d(info);
    }

    private void b(byte[] encKey, int offSet, BigInteger bI) {
        byte[] val = bI.toByteArray();
        if (val.length < 32) {
            byte[] tmp = new byte[32];
            System.arraycopy(val, 0, tmp, tmp.length - val.length, val.length);
            val = tmp;
        }
        for (int i = 0; i != 32; i++) {
            encKey[offSet + i] = val[(val.length - 1) - i];
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

    public java.security.spec.ECPoint getW() {
        return new java.security.spec.ECPoint(this.q.f().t(), this.q.g().t());
    }

    public ECPoint getQ() {
        if (this.ecSpec == null) {
            return this.q.k();
        }
        return this.q;
    }

    public ECPoint engineGetQ() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.g(eCParameterSpec, this.withCompression);
        }
        return BouncyCastleProvider.CONFIGURATION.b();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("EC Public Key");
        buf.append(nl);
        buf.append("            X: ");
        buf.append(this.q.f().t().toString(16));
        buf.append(nl);
        buf.append("            Y: ");
        buf.append(this.q.g().t().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof JCEECPublicKey)) {
            return false;
        }
        JCEECPublicKey other = (JCEECPublicKey) o;
        if (!engineGetQ().e(other.engineGetQ()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return engineGetQ().hashCode() ^ engineGetSpec().hashCode();
    }

    private void readObject(ObjectInputStream in) {
        c(SubjectPublicKeyInfo.g(ASN1Primitive.h((byte[]) in.readObject())));
        this.algorithm = (String) in.readObject();
        this.withCompression = in.readBoolean();
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(getEncoded());
        out.writeObject(this.algorithm);
        out.writeBoolean(this.withCompression);
    }
}
