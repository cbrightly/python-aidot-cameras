package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

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
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
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

public class BCECGOST3410_2012PublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    static final long serialVersionUID = 7026240464295649314L;
    private String algorithm = "ECGOST3410-2012";
    private transient ECPublicKeyParameters c;
    private transient ECParameterSpec d;
    private transient GOST3410PublicKeyAlgParameters f;
    private boolean withCompression;

    public BCECGOST3410_2012PublicKey(BCECGOST3410_2012PublicKey key) {
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.f = key.f;
    }

    public BCECGOST3410_2012PublicKey(ECPublicKeySpec spec) {
        ECParameterSpec params = spec.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, spec.getW(), false), EC5Util.k((ProviderConfiguration) null, spec.getParams()));
    }

    public BCECGOST3410_2012PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration) {
        if (spec.a() != null) {
            EllipticCurve ellipticCurve = EC5Util.a(spec.a().a(), spec.a().e());
            this.c = new ECPublicKeyParameters(spec.b(), ECUtil.h(configuration, spec.a()));
            this.d = EC5Util.f(ellipticCurve, spec.a());
            return;
        }
        this.c = new ECPublicKeyParameters(configuration.b().a().f(spec.b().f().t(), spec.b().g().t()), EC5Util.k(configuration, (ECParameterSpec) null));
        this.d = null;
    }

    public BCECGOST3410_2012PublicKey(String algorithm2, ECPublicKeyParameters params, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = spec;
        }
    }

    public BCECGOST3410_2012PublicKey(String algorithm2, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
    }

    public BCECGOST3410_2012PublicKey(String algorithm2, ECPublicKeyParameters params) {
        this.algorithm = algorithm2;
        this.c = params;
        this.d = null;
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters dp) {
        return new ECParameterSpec(ellipticCurve, new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
    }

    public BCECGOST3410_2012PublicKey(ECPublicKey key) {
        this.algorithm = key.getAlgorithm();
        ECParameterSpec params = key.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, key.getW(), false), EC5Util.k((ProviderConfiguration) null, key.getParams()));
    }

    BCECGOST3410_2012PublicKey(SubjectPublicKeyInfo info) {
        c(info);
    }

    private void c(SubjectPublicKeyInfo info) {
        ASN1ObjectIdentifier algOid = info.e().e();
        DERBitString bits = info.h();
        this.algorithm = "ECGOST3410-2012";
        try {
            ASN1OctetString key = (ASN1OctetString) ASN1Primitive.h(bits.q());
            byte[] keyEnc = key.q();
            int keySize = 64;
            if (algOid.equals(RosstandartObjectIdentifiers.h)) {
                keySize = 128;
            }
            int arraySize = keySize / 2;
            byte[] x = new byte[arraySize];
            byte[] y = new byte[arraySize];
            for (int i = 0; i != x.length; i++) {
                x[i] = keyEnc[(arraySize - 1) - i];
            }
            for (int i2 = 0; i2 != y.length; i2++) {
                y[i2] = keyEnc[(keySize - 1) - i2];
            }
            GOST3410PublicKeyAlgParameters g = GOST3410PublicKeyAlgParameters.g(info.e().h());
            this.f = g;
            ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.a(ECGOST3410NamedCurves.c(g.h()));
            ECCurve curve = spec.a();
            EllipticCurve ellipticCurve = EC5Util.a(curve, spec.e());
            this.c = new ECPublicKeyParameters(curve.f(new BigInteger(1, x), new BigInteger(1, y)), ECUtil.h((ProviderConfiguration) null, spec));
            String c2 = ECGOST3410NamedCurves.c(this.f.h());
            ASN1OctetString aSN1OctetString = key;
            ECPoint eCPoint = new ECPoint(spec.b().f().t(), spec.b().g().t());
            ASN1ObjectIdentifier aSN1ObjectIdentifier = algOid;
            ECNamedCurveSpec eCNamedCurveSpec = r12;
            ECNamedCurveSpec eCNamedCurveSpec2 = new ECNamedCurveSpec(c2, ellipticCurve, eCPoint, spec.d(), spec.c());
            this.d = eCNamedCurveSpec;
        } catch (IOException e) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = algOid;
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
        GOST3410PublicKeyAlgParameters params;
        ASN1ObjectIdentifier algIdentifier;
        int offset;
        int encKeySize;
        BigInteger bX = this.c.c().f().t();
        BigInteger bY = this.c.c().g().t();
        boolean is512 = bX.bitLength() > 256;
        if (this.f != null) {
            params = this.f;
        } else {
            ECParameterSpec eCParameterSpec = this.d;
            if (!(eCParameterSpec instanceof ECNamedCurveSpec)) {
                ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
                params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.d.getGenerator(), this.withCompression), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed()));
            } else if (is512) {
                params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.d(((ECNamedCurveSpec) eCParameterSpec).d()), RosstandartObjectIdentifiers.h);
            } else {
                params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.d(((ECNamedCurveSpec) eCParameterSpec).d()), RosstandartObjectIdentifiers.g);
            }
        }
        if (is512) {
            encKeySize = 128;
            offset = 64;
            algIdentifier = RosstandartObjectIdentifiers.h;
        } else {
            encKeySize = 64;
            offset = 32;
            algIdentifier = RosstandartObjectIdentifiers.g;
        }
        byte[] encKey = new byte[encKeySize];
        b(encKey, encKeySize / 2, 0, bX);
        b(encKey, encKeySize / 2, offset, bY);
        try {
            return KeyUtil.d(new SubjectPublicKeyInfo(new AlgorithmIdentifier(algIdentifier, params), (ASN1Encodable) new DEROctetString(encKey)));
        } catch (IOException e) {
            return null;
        }
    }

    private void b(byte[] encKey, int size, int offSet, BigInteger bI) {
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
        if (!(o instanceof BCECGOST3410_2012PublicKey)) {
            return false;
        }
        BCECGOST3410_2012PublicKey other = (BCECGOST3410_2012PublicKey) o;
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

    public GOST3410PublicKeyAlgParameters getGostParams() {
        return this.f;
    }
}
