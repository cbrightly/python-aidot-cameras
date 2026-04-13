package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.math.ec.ECCurve;

public class BCECPublicKey implements ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    static final long serialVersionUID = 2422789860422731812L;
    private String algorithm = "EC";
    private transient ECPublicKeyParameters c;
    private transient ECParameterSpec d;
    private transient ProviderConfiguration f;
    private boolean withCompression;

    public BCECPublicKey(String algorithm2, BCECPublicKey key) {
        this.algorithm = algorithm2;
        this.c = key.c;
        this.d = key.d;
        this.withCompression = key.withCompression;
        this.f = key.f;
    }

    public BCECPublicKey(String algorithm2, ECPublicKeySpec spec, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        ECParameterSpec params = spec.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, spec.getW(), false), EC5Util.k(configuration, spec.getParams()));
        this.f = configuration;
    }

    public BCECPublicKey(String algorithm2, org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        if (spec.a() != null) {
            EllipticCurve ellipticCurve = EC5Util.a(spec.a().a(), spec.a().e());
            this.c = new ECPublicKeyParameters(spec.b(), ECUtil.h(configuration, spec.a()));
            this.d = EC5Util.f(ellipticCurve, spec.a());
        } else {
            this.c = new ECPublicKeyParameters(configuration.b().a().f(spec.b().f().t(), spec.b().g().t()), EC5Util.k(configuration, (ECParameterSpec) null));
            this.d = null;
        }
        this.f = configuration;
    }

    public BCECPublicKey(String algorithm2, ECPublicKeyParameters params, ECParameterSpec spec, ProviderConfiguration configuration) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        this.c = params;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = spec;
        }
        this.f = configuration;
    }

    public BCECPublicKey(String algorithm2, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec, ProviderConfiguration configuration) {
        ECDomainParameters dp = params.b();
        this.algorithm = algorithm2;
        if (spec == null) {
            this.d = a(EC5Util.a(dp.a(), dp.e()), dp);
        } else {
            this.d = EC5Util.f(EC5Util.a(spec.a(), spec.e()), spec);
        }
        this.c = params;
        this.f = configuration;
    }

    public BCECPublicKey(String algorithm2, ECPublicKeyParameters params, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.c = params;
        this.d = null;
        this.f = configuration;
    }

    public BCECPublicKey(ECPublicKey key, ProviderConfiguration configuration) {
        this.algorithm = key.getAlgorithm();
        ECParameterSpec params = key.getParams();
        this.d = params;
        this.c = new ECPublicKeyParameters(EC5Util.d(params, key.getW(), false), EC5Util.k(configuration, key.getParams()));
    }

    BCECPublicKey(String algorithm2, SubjectPublicKeyInfo info, ProviderConfiguration configuration) {
        this.algorithm = algorithm2;
        this.f = configuration;
        b(info);
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters dp) {
        return new ECParameterSpec(ellipticCurve, new ECPoint(dp.b().f().t(), dp.b().g().t()), dp.d(), dp.c().intValue());
    }

    private void b(SubjectPublicKeyInfo info) {
        X962Parameters params = X962Parameters.e(info.e().h());
        ECCurve curve = EC5Util.j(this.f, params);
        this.d = EC5Util.h(params, curve);
        byte[] data = info.h().q();
        ASN1OctetString key = new DEROctetString(data);
        if (data[0] == 4 && data[1] == data.length - 2 && ((data[2] == 2 || data[2] == 3) && new X9IntegerConverter().a(curve) >= data.length - 3)) {
            try {
                key = (ASN1OctetString) ASN1Primitive.h(data);
            } catch (IOException e) {
                throw new IllegalArgumentException("error recovering public key");
            }
        }
        this.c = new ECPublicKeyParameters(new X9ECPoint(curve, key).e(), ECUtil.g(this.f, params));
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        return KeyUtil.d(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.t3, ECUtils.c(this.d, this.withCompression)), ASN1OctetString.o(new X9ECPoint(this.c.c(), this.withCompression).toASN1Primitive()).q()));
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
        return this.f.b();
    }

    public String toString() {
        return ECUtil.p("EC", this.c.c(), engineGetSpec());
    }

    public void setPointFormat(String style) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(style);
    }

    public boolean equals(Object o) {
        if (!(o instanceof BCECPublicKey)) {
            return false;
        }
        BCECPublicKey other = (BCECPublicKey) o;
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
        this.f = BouncyCastleProvider.CONFIGURATION;
        b(SubjectPublicKeyInfo.g(ASN1Primitive.h((byte[]) in.readObject())));
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        out.writeObject(getEncoded());
    }
}
