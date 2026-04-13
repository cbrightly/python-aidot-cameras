package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    private ECParameterSpec a;
    private String b;

    /* access modifiers changed from: protected */
    public boolean a(String format) {
        return format == null || format.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof ECGenParameterSpec) {
            ECGenParameterSpec ecGenParameterSpec = (ECGenParameterSpec) algorithmParameterSpec;
            X9ECParameters params = ECUtils.b(ecGenParameterSpec);
            if (params != null) {
                this.b = ecGenParameterSpec.getName();
                this.a = EC5Util.i(params);
                return;
            }
            throw new InvalidParameterSpecException("EC curve name not recognized: " + ecGenParameterSpec.getName());
        } else if (algorithmParameterSpec instanceof ECParameterSpec) {
            if (algorithmParameterSpec instanceof ECNamedCurveSpec) {
                this.b = ((ECNamedCurveSpec) algorithmParameterSpec).d();
            } else {
                this.b = null;
            }
            this.a = (ECParameterSpec) algorithmParameterSpec;
        } else {
            throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + algorithmParameterSpec.getClass().getName());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bytes) {
        engineInit(bytes, "ASN.1");
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bytes, String format) {
        if (a(format)) {
            X962Parameters params = X962Parameters.e(bytes);
            ECCurve curve = EC5Util.j(BouncyCastleProvider.CONFIGURATION, params);
            if (params.isNamedCurve()) {
                ASN1ObjectIdentifier curveId = ASN1ObjectIdentifier.t(params.f());
                String d = ECNamedCurveTable.d(curveId);
                this.b = d;
                if (d == null) {
                    this.b = curveId.s();
                }
            }
            this.a = EC5Util.h(params, curve);
            return;
        }
        throw new IOException("Unknown encoded parameters format in AlgorithmParameters object: " + format);
    }

    /* access modifiers changed from: protected */
    public <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramSpec) {
        if (ECParameterSpec.class.isAssignableFrom(paramSpec) || paramSpec == AlgorithmParameterSpec.class) {
            return this.a;
        }
        if (ECGenParameterSpec.class.isAssignableFrom(paramSpec)) {
            String str = this.b;
            if (str != null) {
                ASN1ObjectIdentifier namedCurveOid = ECUtil.k(str);
                if (namedCurveOid != null) {
                    return new ECGenParameterSpec(namedCurveOid.s());
                }
                return new ECGenParameterSpec(this.b);
            }
            ASN1ObjectIdentifier namedCurveOid2 = ECUtil.l(EC5Util.g(this.a, false));
            if (namedCurveOid2 != null) {
                return new ECGenParameterSpec(namedCurveOid2.s());
            }
        }
        throw new InvalidParameterSpecException("EC AlgorithmParameters cannot convert to " + paramSpec.getName());
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        return engineGetEncoded("ASN.1");
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String format) {
        X962Parameters params;
        if (a(format)) {
            ECParameterSpec eCParameterSpec = this.a;
            if (eCParameterSpec == null) {
                params = new X962Parameters((ASN1Null) DERNull.c);
            } else {
                String str = this.b;
                if (str != null) {
                    params = new X962Parameters(ECUtil.k(str));
                } else {
                    org.spongycastle.jce.spec.ECParameterSpec ecSpec = EC5Util.g(eCParameterSpec, false);
                    params = new X962Parameters(new X9ECParameters(ecSpec.a(), ecSpec.b(), ecSpec.d(), ecSpec.c(), ecSpec.e()));
                }
            }
            return params.getEncoded();
        }
        throw new IOException("Unknown parameters format in AlgorithmParameters object: " + format);
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "EC AlgorithmParameters ";
    }
}
