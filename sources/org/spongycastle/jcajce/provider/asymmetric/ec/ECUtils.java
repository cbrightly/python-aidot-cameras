package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class ECUtils {
    ECUtils() {
    }

    static AsymmetricKeyParameter a(PublicKey key) {
        return key instanceof BCECPublicKey ? ((BCECPublicKey) key).engineGetKeyParameters() : ECUtil.e(key);
    }

    static X9ECParameters b(ECGenParameterSpec genSpec) {
        return d(genSpec.getName());
    }

    static X9ECParameters d(String curveName) {
        try {
            if (curveName.charAt(0) >= '0' && curveName.charAt(0) <= '2') {
                return ECUtil.j(new ASN1ObjectIdentifier(curveName));
            }
            if (curveName.indexOf(32) > 0) {
                return ECUtil.i(curveName.substring(curveName.indexOf(32) + 1));
            }
            return ECUtil.i(curveName);
        } catch (IllegalArgumentException e) {
            return ECUtil.i(curveName);
        }
    }

    static X962Parameters c(ECParameterSpec ecSpec, boolean withCompression) {
        if (ecSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier curveOid = ECUtil.k(((ECNamedCurveSpec) ecSpec).d());
            if (curveOid == null) {
                curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) ecSpec).d());
            }
            return new X962Parameters(curveOid);
        } else if (ecSpec == null) {
            return new X962Parameters((ASN1Null) DERNull.c);
        } else {
            ECCurve curve = EC5Util.b(ecSpec.getCurve());
            return new X962Parameters(new X9ECParameters(curve, EC5Util.e(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf((long) ecSpec.getCofactor()), ecSpec.getCurve().getSeed()));
        }
    }
}
