package org.spongycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.anssi.ANSSINamedCurves;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.gm.GMNamedCurves;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECNamedDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Fingerprint;
import org.spongycastle.util.Strings;

public class ECUtil {
    static int[] b(int[] k) {
        int[] res = new int[3];
        if (k.length == 1) {
            res[0] = k[0];
        } else if (k.length != 3) {
            throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
        } else if (k[0] < k[1] && k[0] < k[2]) {
            res[0] = k[0];
            if (k[1] < k[2]) {
                res[1] = k[1];
                res[2] = k[2];
            } else {
                res[1] = k[2];
                res[2] = k[1];
            }
        } else if (k[1] < k[2]) {
            res[0] = k[1];
            if (k[0] < k[2]) {
                res[1] = k[0];
                res[2] = k[2];
            } else {
                res[1] = k[2];
                res[2] = k[0];
            }
        } else {
            res[0] = k[2];
            if (k[0] < k[1]) {
                res[1] = k[0];
                res[2] = k[1];
            } else {
                res[1] = k[1];
                res[2] = k[0];
            }
        }
        return res;
    }

    public static ECDomainParameters h(ProviderConfiguration configuration, ECParameterSpec params) {
        if (params instanceof ECNamedCurveParameterSpec) {
            ECNamedCurveParameterSpec nParams = (ECNamedCurveParameterSpec) params;
            return new ECNamedDomainParameters(k(nParams.f()), nParams.a(), nParams.b(), nParams.d(), nParams.c(), nParams.e());
        } else if (params != null) {
            return new ECDomainParameters(params.a(), params.b(), params.d(), params.c(), params.e());
        } else {
            ECParameterSpec iSpec = configuration.b();
            return new ECDomainParameters(iSpec.a(), iSpec.b(), iSpec.d(), iSpec.c(), iSpec.e());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: org.spongycastle.asn1.x9.X9ECParameters} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.spongycastle.crypto.params.ECDomainParameters g(org.spongycastle.jcajce.provider.config.ProviderConfiguration r10, org.spongycastle.asn1.x9.X962Parameters r11) {
        /*
            boolean r0 = r11.isNamedCurve()
            if (r0 == 0) goto L_0x003f
            org.spongycastle.asn1.ASN1Primitive r0 = r11.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.ASN1ObjectIdentifier.t(r0)
            org.spongycastle.asn1.x9.X9ECParameters r1 = j(r0)
            if (r1 != 0) goto L_0x0021
            java.util.Map r2 = r10.a()
            java.lang.Object r3 = r2.get(r0)
            r1 = r3
            org.spongycastle.asn1.x9.X9ECParameters r1 = (org.spongycastle.asn1.x9.X9ECParameters) r1
            r8 = r1
            goto L_0x0022
        L_0x0021:
            r8 = r1
        L_0x0022:
            org.spongycastle.crypto.params.ECNamedDomainParameters r9 = new org.spongycastle.crypto.params.ECNamedDomainParameters
            org.spongycastle.math.ec.ECCurve r3 = r8.e()
            org.spongycastle.math.ec.ECPoint r4 = r8.f()
            java.math.BigInteger r5 = r8.i()
            java.math.BigInteger r6 = r8.g()
            byte[] r7 = r8.getSeed()
            r1 = r9
            r2 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r0 = r9
            goto L_0x0088
        L_0x003f:
            boolean r0 = r11.g()
            if (r0 == 0) goto L_0x0065
            org.spongycastle.jce.spec.ECParameterSpec r0 = r10.b()
            org.spongycastle.crypto.params.ECDomainParameters r7 = new org.spongycastle.crypto.params.ECDomainParameters
            org.spongycastle.math.ec.ECCurve r2 = r0.a()
            org.spongycastle.math.ec.ECPoint r3 = r0.b()
            java.math.BigInteger r4 = r0.d()
            java.math.BigInteger r5 = r0.c()
            byte[] r6 = r0.e()
            r1 = r7
            r1.<init>(r2, r3, r4, r5, r6)
            r0 = r7
            goto L_0x0088
        L_0x0065:
            org.spongycastle.asn1.ASN1Primitive r0 = r11.f()
            org.spongycastle.asn1.x9.X9ECParameters r0 = org.spongycastle.asn1.x9.X9ECParameters.h(r0)
            org.spongycastle.crypto.params.ECDomainParameters r7 = new org.spongycastle.crypto.params.ECDomainParameters
            org.spongycastle.math.ec.ECCurve r2 = r0.e()
            org.spongycastle.math.ec.ECPoint r3 = r0.f()
            java.math.BigInteger r4 = r0.i()
            java.math.BigInteger r5 = r0.g()
            byte[] r6 = r0.getSeed()
            r1 = r7
            r1.<init>(r2, r3, r4, r5, r6)
            r0 = r1
        L_0x0088:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.g(org.spongycastle.jcajce.provider.config.ProviderConfiguration, org.spongycastle.asn1.x9.X962Parameters):org.spongycastle.crypto.params.ECDomainParameters");
    }

    public static AsymmetricKeyParameter e(PublicKey key) {
        if (key instanceof ECPublicKey) {
            ECPublicKey k = (ECPublicKey) key;
            ECParameterSpec s = k.getParameters();
            return new ECPublicKeyParameters(k.getQ(), new ECDomainParameters(s.a(), s.b(), s.d(), s.c(), s.e()));
        } else if (key instanceof java.security.interfaces.ECPublicKey) {
            java.security.interfaces.ECPublicKey pubKey = (java.security.interfaces.ECPublicKey) key;
            ECParameterSpec s2 = EC5Util.g(pubKey.getParams(), false);
            return new ECPublicKeyParameters(EC5Util.d(pubKey.getParams(), pubKey.getW(), false), new ECDomainParameters(s2.a(), s2.b(), s2.d(), s2.c(), s2.e()));
        } else {
            try {
                byte[] bytes = key.getEncoded();
                if (bytes != null) {
                    PublicKey publicKey = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.g(bytes));
                    if (publicKey instanceof java.security.interfaces.ECPublicKey) {
                        return e(publicKey);
                    }
                    throw new InvalidKeyException("cannot identify EC public key.");
                }
                throw new InvalidKeyException("no encoding for EC public key");
            } catch (Exception e) {
                throw new InvalidKeyException("cannot identify EC public key: " + e.toString());
            }
        }
    }

    public static AsymmetricKeyParameter d(PrivateKey key) {
        if (key instanceof ECPrivateKey) {
            ECPrivateKey k = (ECPrivateKey) key;
            ECParameterSpec s = k.getParameters();
            if (s == null) {
                s = BouncyCastleProvider.CONFIGURATION.b();
            }
            return new ECPrivateKeyParameters(k.getD(), new ECDomainParameters(s.a(), s.b(), s.d(), s.c(), s.e()));
        } else if (key instanceof java.security.interfaces.ECPrivateKey) {
            java.security.interfaces.ECPrivateKey privKey = (java.security.interfaces.ECPrivateKey) key;
            ECParameterSpec s2 = EC5Util.g(privKey.getParams(), false);
            return new ECPrivateKeyParameters(privKey.getS(), new ECDomainParameters(s2.a(), s2.b(), s2.d(), s2.c(), s2.e()));
        } else {
            try {
                byte[] bytes = key.getEncoded();
                if (bytes != null) {
                    PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.f(bytes));
                    if (privateKey instanceof java.security.interfaces.ECPrivateKey) {
                        return d(privateKey);
                    }
                    throw new InvalidKeyException("can't identify EC private key.");
                }
                throw new InvalidKeyException("no encoding for EC private key");
            } catch (Exception e) {
                throw new InvalidKeyException("cannot identify EC private key: " + e.toString());
            }
        }
    }

    public static int m(ProviderConfiguration configuration, BigInteger order, BigInteger privateValue) {
        if (order != null) {
            return order.bitLength();
        }
        ECParameterSpec implicitCA = configuration.b();
        if (implicitCA == null) {
            return privateValue.bitLength();
        }
        return implicitCA.d().bitLength();
    }

    public static ASN1ObjectIdentifier k(String curveName) {
        String name;
        if (curveName.indexOf(32) > 0) {
            name = curveName.substring(curveName.indexOf(32) + 1);
        } else {
            name = curveName;
        }
        try {
            if (name.charAt(0) < '0' || name.charAt(0) > '2') {
                return n(name);
            }
            return new ASN1ObjectIdentifier(name);
        } catch (IllegalArgumentException e) {
            return n(name);
        }
    }

    private static ASN1ObjectIdentifier n(String name) {
        ASN1ObjectIdentifier oid = X962NamedCurves.f(name);
        if (oid != null) {
            return oid;
        }
        ASN1ObjectIdentifier oid2 = SECNamedCurves.l(name);
        if (oid2 == null) {
            oid2 = NISTNamedCurves.f(name);
        }
        if (oid2 == null) {
            oid2 = TeleTrusTNamedCurves.h(name);
        }
        if (oid2 == null) {
            oid2 = ECGOST3410NamedCurves.d(name);
        }
        if (oid2 == null) {
            oid2 = ANSSINamedCurves.j(name);
        }
        if (oid2 == null) {
            return GMNamedCurves.j(name);
        }
        return oid2;
    }

    public static ASN1ObjectIdentifier l(ECParameterSpec ecParameterSpec) {
        Enumeration names = ECNamedCurveTable.e();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            X9ECParameters params = ECNamedCurveTable.b(name);
            if (params.i().equals(ecParameterSpec.d()) && params.g().equals(ecParameterSpec.c()) && params.e().l(ecParameterSpec.a()) && params.f().e(ecParameterSpec.b())) {
                return ECNamedCurveTable.f(name);
            }
        }
        return null;
    }

    public static X9ECParameters j(ASN1ObjectIdentifier oid) {
        X9ECParameters params = CustomNamedCurves.i(oid);
        if (params != null) {
            return params;
        }
        X9ECParameters params2 = X962NamedCurves.c(oid);
        if (params2 == null) {
            params2 = SECNamedCurves.i(oid);
        }
        if (params2 == null) {
            params2 = NISTNamedCurves.c(oid);
        }
        if (params2 == null) {
            params2 = TeleTrusTNamedCurves.e(oid);
        }
        if (params2 == null) {
            params2 = ANSSINamedCurves.g(oid);
        }
        if (params2 == null) {
            return GMNamedCurves.g(oid);
        }
        return params2;
    }

    public static X9ECParameters i(String curveName) {
        X9ECParameters params = CustomNamedCurves.h(curveName);
        if (params != null) {
            return params;
        }
        X9ECParameters params2 = X962NamedCurves.b(curveName);
        if (params2 == null) {
            params2 = SECNamedCurves.h(curveName);
        }
        if (params2 == null) {
            params2 = NISTNamedCurves.b(curveName);
        }
        if (params2 == null) {
            params2 = TeleTrusTNamedCurves.d(curveName);
        }
        if (params2 == null) {
            params2 = ANSSINamedCurves.f(curveName);
        }
        if (params2 == null) {
            return GMNamedCurves.f(curveName);
        }
        return params2;
    }

    public static String f(ASN1ObjectIdentifier oid) {
        String name = X962NamedCurves.d(oid);
        if (name != null) {
            return name;
        }
        String name2 = SECNamedCurves.j(oid);
        if (name2 == null) {
            name2 = NISTNamedCurves.d(oid);
        }
        if (name2 == null) {
            name2 = TeleTrusTNamedCurves.f(oid);
        }
        if (name2 == null) {
            name2 = ECGOST3410NamedCurves.c(oid);
        }
        if (name2 == null) {
            name2 = ANSSINamedCurves.h(oid);
        }
        if (name2 == null) {
            return GMNamedCurves.h(oid);
        }
        return name2;
    }

    public static String o(String algorithm, BigInteger d, ECParameterSpec spec) {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        ECPoint q = a(d, spec);
        buf.append(algorithm);
        buf.append(" Private Key [");
        buf.append(c(q, spec));
        buf.append("]");
        buf.append(nl);
        buf.append("            X: ");
        buf.append(q.f().t().toString(16));
        buf.append(nl);
        buf.append("            Y: ");
        buf.append(q.g().t().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    private static ECPoint a(BigInteger d, ECParameterSpec spec) {
        return spec.b().w(d).y();
    }

    public static String p(String algorithm, ECPoint q, ECParameterSpec spec) {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append(algorithm);
        buf.append(" Public Key [");
        buf.append(c(q, spec));
        buf.append("]");
        buf.append(nl);
        buf.append("            X: ");
        buf.append(q.f().t().toString(16));
        buf.append(nl);
        buf.append("            Y: ");
        buf.append(q.g().t().toString(16));
        buf.append(nl);
        return buf.toString();
    }

    public static String c(ECPoint publicPoint, ECParameterSpec spec) {
        ECCurve curve = spec.a();
        ECPoint g = spec.b();
        if (curve != null) {
            return new Fingerprint(Arrays.t(publicPoint.l(false), curve.n().e(), curve.o().e(), g.l(false))).toString();
        }
        return new Fingerprint(publicPoint.l(false)).toString();
    }
}
