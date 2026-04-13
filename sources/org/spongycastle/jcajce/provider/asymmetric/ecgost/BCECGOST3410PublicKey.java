package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class BCECGOST3410PublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    static final long serialVersionUID = 7026240464295649314L;
    private String algorithm = "ECGOST3410";
    private transient ECPublicKeyParameters c;
    private transient ECParameterSpec d;
    private transient ASN1Encodable f;
    private boolean withCompression;

    public BCECGOST3410PublicKey(BCECGOST3410PublicKey key) {
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.f = key.f;
    }

    public BCECGOST3410PublicKey(ECPublicKeySpec spec) {
        ECParameterSpec params = spec.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, spec.getW(), false), EC5Util.k((ProviderConfiguration) null, spec.getParams()));
    }

    public BCECGOST3410PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration) {
        if (spec.a() != null) {
            EllipticCurve ellipticCurve = EC5Util.a(spec.a().a(), spec.a().e());
            this.c = new ECPublicKeyParameters(spec.b(), ECUtil.h(configuration, spec.a()));
            this.d = EC5Util.f(ellipticCurve, spec.a());
            return;
        }
        this.c = new ECPublicKeyParameters(configuration.b().a().f(spec.b().f().t(), spec.b().g().t()), EC5Util.k(configuration, (ECParameterSpec) null));
        this.d = null;
    }

    public BCECGOST3410PublicKey(String algorithm2, ECPublicKeyParameters params, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = spec;
        }
    }

    public BCECGOST3410PublicKey(String algorithm2, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
    }

    public BCECGOST3410PublicKey(String algorithm2, ECPublicKeyParameters params) {
        this.algorithm = algorithm2;
        this.c = params;
        this.d = null;
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters dp) {
        return new ECParameterSpec(ellipticCurve, new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
    }

    public BCECGOST3410PublicKey(ECPublicKey key) {
        this.algorithm = key.getAlgorithm();
        ECParameterSpec params = key.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, key.getW(), false), EC5Util.k((ProviderConfiguration) null, key.getParams()));
    }

    BCECGOST3410PublicKey(SubjectPublicKeyInfo info) {
        c(info);
    }

    private void c(SubjectPublicKeyInfo info) {
        ASN1ObjectIdentifier paramOID;
        DERBitString bits = info.h();
        this.algorithm = "ECGOST3410";
        try {
            ASN1OctetString key = (ASN1OctetString) ASN1Primitive.h(bits.q());
            byte[] keyEnc = key.q();
            byte[] x = new byte[32];
            byte[] y = new byte[32];
            for (int i = 0; i != x.length; i++) {
                x[i] = keyEnc[31 - i];
            }
            for (int i2 = 0; i2 != y.length; i2++) {
                y[i2] = keyEnc[63 - i2];
            }
            if (info.e().h() instanceof ASN1ObjectIdentifier) {
                paramOID = ASN1ObjectIdentifier.t(info.e().h());
                this.f = paramOID;
            } else {
                GOST3410PublicKeyAlgParameters params = GOST3410PublicKeyAlgParameters.g(info.e().h());
                this.f = params;
                paramOID = params.h();
            }
            ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.a(ECGOST3410NamedCurves.c(paramOID));
            ECCurve curve = spec.a();
            EllipticCurve ellipticCurve = EC5Util.a(curve, spec.e());
            this.c = new ECPublicKeyParameters(curve.f(new BigInteger(1, x), new BigInteger(1, y)), ECUtil.h((ProviderConfiguration) null, spec));
            ASN1OctetString aSN1OctetString = key;
            ECNamedCurveSpec eCNamedCurveSpec = r10;
            ECNamedCurveSpec eCNamedCurveSpec2 = new ECNamedCurveSpec(ECGOST3410NamedCurves.c(paramOID), ellipticCurve, new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c());
            this.d = eCNamedCurveSpec;
        } catch (IOException e) {
            throw new IllegalArgumentException("error recovering public key");
        }
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        ASN1Encodable params;
        if (this.f != null) {
            params = this.f;
        } else {
            ECParameterSpec eCParameterSpec = this.d;
            if (eCParameterSpec instanceof ECNamedCurveSpec) {
                params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.d(((ECNamedCurveSpec) eCParameterSpec).d()), CryptoProObjectIdentifiers.p);
            } else {
                ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
                params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.d.getGenerator(), this.withCompression), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed()));
            }
        }
        BigInteger bX = this.c.c().f().t();
        BigInteger bY = this.c.c().g().t();
        byte[] encKey = new byte[64];
        b(encKey, 0, bX);
        b(encKey, 32, bY);
        try {
            return KeyUtil.d(new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.m, params), (ASN1Encodable) new DEROctetString(encKey)));
        } catch (IOException e) {
            return null;
        }
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
        return this.d;
    }

    public org.spongycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.g(eCParameterSpec, this.withCompression);
    }

    public ECPoint getW() {
        return new ECPoint(this.c.c().f().t(), this.c.c().g().t());
    }

    public org.spongycastle.math.ec.ECPoint getQ() {
        if (this.d == null) {
            return this.c.c().k();
        }
        return this.c.c();
    }

    /* access modifiers changed from: package-private */
    public ECPublicKeyParameters engineGetKeyParameters() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public org.spongycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.d;
        if (eCParameterSpec != null) {
            return EC5Util.g(eCParameterSpec, this.withCompression);
        }
        return BouncyCastleProvider.CONFIGURATION.b();
    }

    public String toString() {
        return ECUtil.p(this.algorithm, this.c.c(), engineGetSpec());
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof BCECGOST3410PublicKey)) {
            return false;
        }
        BCECGOST3410PublicKey other = (BCECGOST3410PublicKey) o;
        if (!this.c.c().e(other.c.c()) || !engineGetSpec().equals(other.engineGetSpec())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.c.c().hashCode() ^ engineGetSpec().hashCode();
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        c(SubjectPublicKeyInfo.g(ASN1Primitive.h((byte[]) in.readObject())));
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }

    /* access modifiers changed from: package-private */
    public ASN1Encodable getGostParams() {
        return this.f;
    }
}
