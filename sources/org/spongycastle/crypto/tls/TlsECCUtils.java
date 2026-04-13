package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Integers;

public class TlsECCUtils {
    public static final Integer a = Integers.b(10);
    public static final Integer b = Integers.b(11);
    private static final String[] c = {"sect163k1", "sect163r1", "sect163r2", "sect193r1", "sect193r2", "sect233k1", "sect233r1", "sect239k1", "sect283k1", "sect283r1", "sect409k1", "sect409r1", "sect571k1", "sect571r1", "secp160k1", "secp160r1", "secp160r2", "secp192k1", "secp192r1", "secp224k1", "secp224r1", "secp256k1", "secp256r1", "secp384r1", "secp521r1", "brainpoolP256r1", "brainpoolP384r1", "brainpoolP512r1"};

    public static void a(Hashtable extensions, short[] ecPointFormats) {
        extensions.put(b, e(ecPointFormats));
    }

    public static int[] n(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, a);
        if (extensionData == null) {
            return null;
        }
        return x(extensionData);
    }

    public static short[] o(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, b);
        if (extensionData == null) {
            return null;
        }
        return y(extensionData);
    }

    public static byte[] e(short[] ecPointFormats) {
        if (ecPointFormats == null || !Arrays.w(ecPointFormats, 0)) {
            ecPointFormats = Arrays.a(ecPointFormats, 0);
        }
        return TlsUtils.t(ecPointFormats);
    }

    public static int[] x(byte[] extensionData) {
        if (extensionData != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
            int length = TlsUtils.k0(buf);
            if (length < 2 || (length & 1) != 0) {
                throw new TlsFatalAlert(50);
            }
            int[] namedCurves = TlsUtils.m0(length / 2, buf);
            TlsProtocol.c(buf);
            return namedCurves;
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static short[] y(byte[] extensionData) {
        if (extensionData != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
            short length = TlsUtils.q0(buf);
            if (length >= 1) {
                short[] ecPointFormats = TlsUtils.s0(length, buf);
                TlsProtocol.c(buf);
                if (Arrays.w(ecPointFormats, 0)) {
                    return ecPointFormats;
                }
                throw new TlsFatalAlert(47);
            }
            throw new TlsFatalAlert(50);
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static String l(int namedCurve) {
        if (s(namedCurve)) {
            return c[namedCurve - 1];
        }
        return null;
    }

    public static ECDomainParameters m(int namedCurve) {
        String curveName = l(namedCurve);
        if (curveName == null) {
            return null;
        }
        X9ECParameters ecP = CustomNamedCurves.h(curveName);
        if (ecP == null && (ecP = ECNamedCurveTable.b(curveName)) == null) {
            return null;
        }
        return new ECDomainParameters(ecP.e(), ecP.f(), ecP.i(), ecP.g(), ecP.getSeed());
    }

    public static boolean p() {
        return c.length > 0;
    }

    public static boolean d(int[] cipherSuites) {
        for (int r : cipherSuites) {
            if (r(r)) {
                return true;
            }
        }
        return false;
    }

    public static boolean r(int cipherSuite) {
        switch (cipherSuite) {
            case 49153:
            case 49154:
            case 49155:
            case 49156:
            case 49157:
            case 49158:
            case 49159:
            case 49160:
            case 49161:
            case 49162:
            case 49163:
            case 49164:
            case 49165:
            case 49166:
            case 49167:
            case 49168:
            case 49169:
            case 49170:
            case 49171:
            case 49172:
            case 49173:
            case 49174:
            case 49175:
            case 49176:
            case 49177:
            case 49187:
            case 49188:
            case 49189:
            case 49190:
            case 49191:
            case 49192:
            case 49193:
            case 49194:
            case 49195:
            case 49196:
            case 49197:
            case 49198:
            case 49199:
            case 49200:
            case 49201:
            case 49202:
            case 49203:
            case 49204:
            case 49205:
            case 49206:
            case 49207:
            case 49208:
            case 49209:
            case 49210:
            case 49211:
            case 49266:
            case 49267:
            case 49268:
            case 49269:
            case 49270:
            case 49271:
            case 49272:
            case 49273:
            case 49286:
            case 49287:
            case 49288:
            case 49289:
            case 49290:
            case 49291:
            case 49292:
            case 49293:
            case 49306:
            case 49307:
            case 49324:
            case 49325:
            case 49326:
            case 49327:
            case 52392:
            case 52393:
            case 52396:
            case 65282:
            case 65283:
            case 65284:
            case 65285:
            case 65300:
            case 65301:
                return true;
            default:
                return false;
        }
    }

    public static boolean s(int namedCurve) {
        return namedCurve > 0 && namedCurve <= c.length;
    }

    public static boolean q(short[] ecPointFormats, short compressionFormat) {
        short ecPointFormat;
        if (ecPointFormats == null) {
            return false;
        }
        int i = 0;
        while (i < ecPointFormats.length && (ecPointFormat = ecPointFormats[i]) != 0) {
            if (ecPointFormat == compressionFormat) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static byte[] z(short[] ecPointFormats, ECPoint point) {
        ECCurve curve = point.i();
        boolean compressed = false;
        if (ECAlgorithms.k(curve)) {
            compressed = q(ecPointFormats, 1);
        } else if (ECAlgorithms.i(curve)) {
            compressed = q(ecPointFormats, 2);
        }
        return point.l(compressed);
    }

    public static BigInteger f(int fieldSize, byte[] encoding) {
        if (encoding.length == (fieldSize + 7) / 8) {
            return new BigInteger(1, encoding);
        }
        throw new TlsFatalAlert(50);
    }

    public static ECPoint g(short[] ecPointFormats, ECCurve curve, byte[] encoding) {
        short actualFormat;
        if (encoding == null || encoding.length < 1) {
            throw new TlsFatalAlert(47);
        }
        switch (encoding[0]) {
            case 2:
            case 3:
                if (ECAlgorithms.i(curve) != 0) {
                    actualFormat = 2;
                    break;
                } else if (ECAlgorithms.k(curve) != 0) {
                    actualFormat = 1;
                    break;
                } else {
                    throw new TlsFatalAlert(47);
                }
            case 4:
                actualFormat = 0;
                break;
            default:
                throw new TlsFatalAlert(47);
        }
        if (actualFormat == 0 || (ecPointFormats != null && Arrays.w(ecPointFormats, actualFormat))) {
            return curve.j(encoding);
        }
        throw new TlsFatalAlert(47);
    }

    public static ECPublicKeyParameters h(short[] ecPointFormats, ECDomainParameters curve_params, byte[] encoding) {
        try {
            return new ECPublicKeyParameters(g(ecPointFormats, curve_params.a(), encoding), curve_params);
        } catch (RuntimeException e) {
            throw new TlsFatalAlert(47, e);
        }
    }

    public static byte[] b(ECPublicKeyParameters publicKey, ECPrivateKeyParameters privateKey) {
        ECDHBasicAgreement basicAgreement = new ECDHBasicAgreement();
        basicAgreement.a(privateKey);
        return BigIntegers.a(basicAgreement.b(), basicAgreement.c(publicKey));
    }

    public static AsymmetricCipherKeyPair i(SecureRandom random, ECDomainParameters ecParams) {
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        keyPairGenerator.c(new ECKeyGenerationParameters(ecParams, random));
        return keyPairGenerator.a();
    }

    public static ECPrivateKeyParameters j(SecureRandom random, short[] ecPointFormats, ECDomainParameters ecParams, OutputStream output) {
        AsymmetricCipherKeyPair kp = i(random, ecParams);
        E(ecPointFormats, ((ECPublicKeyParameters) kp.b()).c(), output);
        return (ECPrivateKeyParameters) kp.a();
    }

    static ECPrivateKeyParameters k(SecureRandom random, int[] namedCurves, short[] ecPointFormats, OutputStream output) {
        int namedCurve = -1;
        if (namedCurves != null) {
            int i = 0;
            while (true) {
                if (i >= namedCurves.length) {
                    break;
                }
                int entry = namedCurves[i];
                if (NamedCurve.a(entry) && s(entry)) {
                    namedCurve = entry;
                    break;
                }
                i++;
            }
        } else {
            namedCurve = 23;
        }
        ECDomainParameters ecParams = null;
        if (namedCurve >= 0) {
            ecParams = m(namedCurve);
        } else if (Arrays.v(namedCurves, 65281)) {
            ecParams = m(23);
        } else if (Arrays.v(namedCurves, 65282)) {
            ecParams = m(10);
        }
        if (ecParams != null) {
            if (namedCurve < 0) {
                F(ecPointFormats, ecParams, output);
            } else {
                G(namedCurve, output);
            }
            return j(random, ecPointFormats, ecParams, output);
        }
        throw new TlsFatalAlert(80);
    }

    public static ECPublicKeyParameters A(ECPublicKeyParameters key) {
        return key;
    }

    public static int t(int fieldSize, InputStream input) {
        int k;
        BigInteger K = v(input);
        if (K.bitLength() < 32 && (k = K.intValue()) > 0 && k < fieldSize) {
            return k;
        }
        throw new TlsFatalAlert(47);
    }

    public static BigInteger u(int fieldSize, InputStream input) {
        return f(fieldSize, TlsUtils.i0(input));
    }

    public static BigInteger v(InputStream input) {
        return new BigInteger(1, TlsUtils.i0(input));
    }

    public static ECDomainParameters w(int[] namedCurves, short[] ecPointFormats, InputStream input) {
        int k3;
        int k2;
        BigInteger order;
        BigInteger cofactor;
        ECCurve.F2m f2m;
        byte[] baseEncoding;
        int[] iArr = namedCurves;
        short[] sArr = ecPointFormats;
        InputStream inputStream = input;
        try {
            switch (TlsUtils.q0(input)) {
                case 1:
                    c(iArr, 65281);
                    BigInteger prime_p = v(input);
                    BigInteger a2 = u(prime_p.bitLength(), inputStream);
                    BigInteger b2 = u(prime_p.bitLength(), inputStream);
                    byte[] baseEncoding2 = TlsUtils.i0(input);
                    BigInteger order2 = v(input);
                    BigInteger cofactor2 = v(input);
                    ECCurve curve = new ECCurve.Fp(prime_p, a2, b2, order2, cofactor2);
                    return new ECDomainParameters(curve, g(sArr, curve, baseEncoding2), order2, cofactor2);
                case 2:
                    c(iArr, 65282);
                    int m = TlsUtils.k0(input);
                    short basis = TlsUtils.q0(input);
                    if (ECBasisType.a(basis)) {
                        int k1 = t(m, inputStream);
                        if (basis == 2) {
                            k2 = t(m, inputStream);
                            k3 = t(m, inputStream);
                        } else {
                            k2 = -1;
                            k3 = -1;
                        }
                        BigInteger a3 = u(m, inputStream);
                        BigInteger b3 = u(m, inputStream);
                        byte[] baseEncoding3 = TlsUtils.i0(input);
                        BigInteger order3 = v(input);
                        BigInteger cofactor3 = v(input);
                        if (basis == 2) {
                            cofactor = cofactor3;
                            order = order3;
                            baseEncoding = baseEncoding3;
                            f2m = new ECCurve.F2m(m, k1, k2, k3, a3, b3, order3, cofactor);
                            short s = basis;
                        } else {
                            cofactor = cofactor3;
                            order = order3;
                            baseEncoding = baseEncoding3;
                            short s2 = basis;
                            f2m = new ECCurve.F2m(m, k1, a3, b3, order, cofactor);
                        }
                        ECCurve curve2 = f2m;
                        return new ECDomainParameters(curve2, g(sArr, curve2, baseEncoding), order, cofactor);
                    }
                    throw new TlsFatalAlert(47);
                case 3:
                    int namedCurve = TlsUtils.k0(input);
                    if (NamedCurve.b(namedCurve)) {
                        c(iArr, namedCurve);
                        return m(namedCurve);
                    }
                    throw new TlsFatalAlert(47);
                default:
                    throw new TlsFatalAlert(47);
            }
        } catch (RuntimeException e) {
            throw new TlsFatalAlert(47, e);
        }
    }

    private static void c(int[] namedCurves, int namedCurve) {
        if (namedCurves != null && !Arrays.v(namedCurves, namedCurve)) {
            throw new TlsFatalAlert(47);
        }
    }

    public static void B(int k, OutputStream output) {
        D(BigInteger.valueOf((long) k), output);
    }

    public static void C(ECFieldElement x, OutputStream output) {
        TlsUtils.C0(x.e(), output);
    }

    public static void D(BigInteger x, OutputStream output) {
        TlsUtils.C0(BigIntegers.b(x), output);
    }

    public static void F(short[] ecPointFormats, ECDomainParameters ecParameters, OutputStream output) {
        ECCurve curve = ecParameters.a();
        if (ECAlgorithms.k(curve)) {
            TlsUtils.L0(1, output);
            D(curve.s().b(), output);
        } else if (ECAlgorithms.i(curve)) {
            int[] exponents = ((PolynomialExtensionField) curve.s()).c().a();
            TlsUtils.L0(2, output);
            int m = exponents[exponents.length - 1];
            TlsUtils.h(m);
            TlsUtils.D0(m, output);
            if (exponents.length == 3) {
                TlsUtils.L0(1, output);
                B(exponents[1], output);
            } else if (exponents.length == 5) {
                TlsUtils.L0(2, output);
                B(exponents[1], output);
                B(exponents[2], output);
                B(exponents[3], output);
            } else {
                throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
            }
        } else {
            throw new IllegalArgumentException("'ecParameters' not a known curve type");
        }
        C(curve.n(), output);
        C(curve.o(), output);
        TlsUtils.C0(z(ecPointFormats, ecParameters.b()), output);
        D(ecParameters.d(), output);
        D(ecParameters.c(), output);
    }

    public static void E(short[] ecPointFormats, ECPoint point, OutputStream output) {
        TlsUtils.C0(z(ecPointFormats, point), output);
    }

    public static void G(int namedCurve, OutputStream output) {
        if (NamedCurve.b(namedCurve)) {
            TlsUtils.L0(3, output);
            TlsUtils.h(namedCurve);
            TlsUtils.D0(namedCurve, output);
            return;
        }
        throw new TlsFatalAlert(80);
    }
}
