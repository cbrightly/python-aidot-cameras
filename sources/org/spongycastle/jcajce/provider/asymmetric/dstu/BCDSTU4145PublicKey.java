package org.spongycastle.jcajce.provider.asymmetric.dstu;

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
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ua.DSTU4145BinaryField;
import org.spongycastle.asn1.ua.DSTU4145ECBinary;
import org.spongycastle.asn1.ua.DSTU4145NamedCurves;
import org.spongycastle.asn1.ua.DSTU4145Params;
import org.spongycastle.asn1.ua.DSTU4145PointEncoder;
import org.spongycastle.asn1.ua.UAObjectIdentifiers;
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
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class BCDSTU4145PublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    static final long serialVersionUID = 7026240464295649314L;
    private String algorithm = "DSTU4145";
    private transient ECPublicKeyParameters c;
    private transient ECParameterSpec d;
    private transient DSTU4145Params f;
    private boolean withCompression;

    public BCDSTU4145PublicKey(BCDSTU4145PublicKey key) {
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.f = key.f;
    }

    public BCDSTU4145PublicKey(ECPublicKeySpec spec) {
        ECParameterSpec params = spec.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, spec.getW(), false), EC5Util.k((ProviderConfiguration) null, this.d));
    }

    public BCDSTU4145PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration) {
        if (spec.a() != null) {
            EllipticCurve ellipticCurve = EC5Util.a(spec.a().a(), spec.a().e());
            this.c = new ECPublicKeyParameters(spec.b(), ECUtil.h(configuration, spec.a()));
            this.d = EC5Util.f(ellipticCurve, spec.a());
            return;
        }
        this.c = new ECPublicKeyParameters(configuration.b().a().f(spec.b().f().t(), spec.b().g().t()), EC5Util.k(configuration, (ECParameterSpec) null));
        this.d = null;
    }

    public BCDSTU4145PublicKey(String algorithm2, ECPublicKeyParameters params, ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = spec;
        }
    }

    public BCDSTU4145PublicKey(String algorithm2, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
        this.c = params;
    }

    public BCDSTU4145PublicKey(String algorithm2, ECPublicKeyParameters params) {
        this.algorithm = algorithm2;
        this.c = params;
        this.d = null;
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters dp) {
        return new ECParameterSpec(ellipticCurve, new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
    }

    BCDSTU4145PublicKey(SubjectPublicKeyInfo info) {
        b(info);
    }

    private void c(byte[] bytes) {
        for (int i = 0; i < bytes.length / 2; i++) {
            byte tmp = bytes[i];
            bytes[i] = bytes[(bytes.length - 1) - i];
            bytes[(bytes.length - 1) - i] = tmp;
        }
    }

    private void b(SubjectPublicKeyInfo info) {
        ECNamedCurveParameterSpec spec;
        DERBitString bits = info.h();
        this.algorithm = "DSTU4145";
        try {
            byte[] keyEnc = ((ASN1OctetString) ASN1Primitive.h(bits.q())).q();
            ASN1ObjectIdentifier e = info.e().e();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = UAObjectIdentifiers.b;
            if (e.equals(aSN1ObjectIdentifier)) {
                c(keyEnc);
            }
            DSTU4145Params instance = DSTU4145Params.getInstance((ASN1Sequence) info.e().h());
            this.f = instance;
            if (instance.isNamedCurve()) {
                ASN1ObjectIdentifier curveOid = this.f.getNamedCurve();
                ECDomainParameters ecP = DSTU4145NamedCurves.a(curveOid);
                spec = new ECNamedCurveParameterSpec(curveOid.s(), ecP.a(), ecP.b(), ecP.d(), ecP.c(), ecP.e());
            } else {
                DSTU4145ECBinary binary = this.f.getECBinary();
                byte[] b_bytes = binary.f();
                if (info.e().e().equals(aSN1ObjectIdentifier)) {
                    c(b_bytes);
                }
                DSTU4145BinaryField field = binary.g();
                ECCurve curve = new ECCurve.F2m(field.i(), field.f(), field.g(), field.h(), binary.e(), new BigInteger(1, b_bytes));
                byte[] g_bytes = binary.h();
                if (info.e().e().equals(aSN1ObjectIdentifier)) {
                    c(g_bytes);
                }
                spec = new org.spongycastle.jce.spec.ECParameterSpec(curve, DSTU4145PointEncoder.a(curve, g_bytes), binary.k());
            }
            ECCurve curve2 = spec.a();
            EllipticCurve ellipticCurve = EC5Util.a(curve2, spec.e());
            if (this.f.isNamedCurve()) {
                this.d = new ECNamedCurveSpec(this.f.getNamedCurve().s(), ellipticCurve, new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c());
            } else {
                this.d = new ECParameterSpec(ellipticCurve, new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c().intValue());
            }
            this.c = new ECPublicKeyParameters(DSTU4145PointEncoder.a(curve2, keyEnc), EC5Util.k((ProviderConfiguration) null, this.d));
        } catch (IOException e2) {
            throw new IllegalArgumentException("error recovering public key");
        }
    }

    public byte[] getSbox() {
        DSTU4145Params dSTU4145Params = this.f;
        if (dSTU4145Params != null) {
            return dSTU4145Params.getDKE();
        }
        return DSTU4145Params.getDefaultDKE();
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
                params = new DSTU4145Params(new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.d).d()));
            } else {
                ECCurve curve = EC5Util.b(eCParameterSpec.getCurve());
                params = new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, this.d.getGenerator(), this.withCompression), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed()));
            }
        }
        try {
            return KeyUtil.d(new SubjectPublicKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.c, params), (ASN1Encodable) new DEROctetString(DSTU4145PointEncoder.b(this.c.c()))));
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

    public ECPoint getW() {
        org.spongycastle.math.ec.ECPoint q = this.c.c();
        return new ECPoint(q.f().t(), q.g().t());
    }

    public org.spongycastle.math.ec.ECPoint getQ() {
        org.spongycastle.math.ec.ECPoint q = this.c.c();
        if (this.d == null) {
            return q.k();
        }
        return q;
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
        if (!(o instanceof BCDSTU4145PublicKey)) {
            return false;
        }
        BCDSTU4145PublicKey other = (BCDSTU4145PublicKey) o;
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
        b(SubjectPublicKeyInfo.g(ASN1Primitive.h((byte[]) in.readObject())));
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }
}
