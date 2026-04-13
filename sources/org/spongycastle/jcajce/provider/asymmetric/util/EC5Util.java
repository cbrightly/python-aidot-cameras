package org.spongycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;

public class EC5Util {
    private static Map a = new HashMap();

    static {
        Enumeration e = CustomNamedCurves.j();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            X9ECParameters curveParams = ECNamedCurveTable.b(name);
            if (curveParams != null) {
                a.put(curveParams.e(), CustomNamedCurves.h(name).e());
            }
        }
        X9ECParameters c25519 = CustomNamedCurves.h("Curve25519");
        a.put(new ECCurve.Fp(c25519.e().s().b(), c25519.e().n().t(), c25519.e().o().t()), c25519.e());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: org.spongycastle.asn1.x9.X9ECParameters} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.spongycastle.math.ec.ECCurve j(org.spongycastle.jcajce.provider.config.ProviderConfiguration r4, org.spongycastle.asn1.x9.X962Parameters r5) {
        /*
            java.util.Set r0 = r4.c()
            boolean r1 = r5.isNamedCurve()
            if (r1 == 0) goto L_0x003e
            org.spongycastle.asn1.ASN1Primitive r1 = r5.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.ASN1ObjectIdentifier.t(r1)
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x0027
            boolean r2 = r0.contains(r1)
            if (r2 == 0) goto L_0x001f
            goto L_0x0027
        L_0x001f:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "named curve not acceptable"
            r2.<init>(r3)
            throw r2
        L_0x0027:
            org.spongycastle.asn1.x9.X9ECParameters r2 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.j(r1)
            if (r2 != 0) goto L_0x0038
            java.util.Map r3 = r4.a()
            java.lang.Object r3 = r3.get(r1)
            r2 = r3
            org.spongycastle.asn1.x9.X9ECParameters r2 = (org.spongycastle.asn1.x9.X9ECParameters) r2
        L_0x0038:
            org.spongycastle.math.ec.ECCurve r2 = r2.e()
            goto L_0x0060
        L_0x003e:
            boolean r1 = r5.g()
            if (r1 == 0) goto L_0x004d
            org.spongycastle.jce.spec.ECParameterSpec r1 = r4.b()
            org.spongycastle.math.ec.ECCurve r2 = r1.a()
            goto L_0x0060
        L_0x004d:
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x0061
            org.spongycastle.asn1.ASN1Primitive r1 = r5.f()
            org.spongycastle.asn1.x9.X9ECParameters r1 = org.spongycastle.asn1.x9.X9ECParameters.h(r1)
            org.spongycastle.math.ec.ECCurve r2 = r1.e()
        L_0x0060:
            return r2
        L_0x0061:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "encoded parameters not acceptable"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.util.EC5Util.j(org.spongycastle.jcajce.provider.config.ProviderConfiguration, org.spongycastle.asn1.x9.X962Parameters):org.spongycastle.math.ec.ECCurve");
    }

    public static ECDomainParameters k(ProviderConfiguration configuration, ECParameterSpec params) {
        if (params != null) {
            return ECUtil.h(configuration, g(params, false));
        }
        org.spongycastle.jce.spec.ECParameterSpec iSpec = configuration.b();
        return new ECDomainParameters(iSpec.a(), iSpec.b(), iSpec.d(), iSpec.c(), iSpec.e());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: org.spongycastle.asn1.x9.X9ECParameters} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.spec.ECParameterSpec h(org.spongycastle.asn1.x9.X962Parameters r10, org.spongycastle.math.ec.ECCurve r11) {
        /*
            boolean r0 = r10.isNamedCurve()
            if (r0 == 0) goto L_0x0060
            org.spongycastle.asn1.ASN1Primitive r0 = r10.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r0
            org.spongycastle.asn1.x9.X9ECParameters r1 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.j(r0)
            if (r1 != 0) goto L_0x0025
            org.spongycastle.jcajce.provider.config.ProviderConfiguration r2 = org.spongycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.util.Map r2 = r2.a()
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0025
            java.lang.Object r3 = r2.get(r0)
            r1 = r3
            org.spongycastle.asn1.x9.X9ECParameters r1 = (org.spongycastle.asn1.x9.X9ECParameters) r1
        L_0x0025:
            byte[] r2 = r1.getSeed()
            java.security.spec.EllipticCurve r2 = a(r11, r2)
            org.spongycastle.jce.spec.ECNamedCurveSpec r9 = new org.spongycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r4 = org.spongycastle.jcajce.provider.asymmetric.util.ECUtil.f(r0)
            java.security.spec.ECPoint r6 = new java.security.spec.ECPoint
            org.spongycastle.math.ec.ECPoint r3 = r1.f()
            org.spongycastle.math.ec.ECFieldElement r3 = r3.f()
            java.math.BigInteger r3 = r3.t()
            org.spongycastle.math.ec.ECPoint r5 = r1.f()
            org.spongycastle.math.ec.ECFieldElement r5 = r5.g()
            java.math.BigInteger r5 = r5.t()
            r6.<init>(r3, r5)
            java.math.BigInteger r7 = r1.i()
            java.math.BigInteger r8 = r1.g()
            r3 = r9
            r5 = r2
            r3.<init>(r4, r5, r6, r7, r8)
            r0 = r9
            goto L_0x00d6
        L_0x0060:
            boolean r0 = r10.g()
            if (r0 == 0) goto L_0x0068
            r0 = 0
            goto L_0x00d6
        L_0x0068:
            org.spongycastle.asn1.ASN1Primitive r0 = r10.f()
            org.spongycastle.asn1.x9.X9ECParameters r0 = org.spongycastle.asn1.x9.X9ECParameters.h(r0)
            byte[] r1 = r0.getSeed()
            java.security.spec.EllipticCurve r1 = a(r11, r1)
            java.math.BigInteger r2 = r0.g()
            if (r2 == 0) goto L_0x00ae
            java.security.spec.ECParameterSpec r2 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r3 = new java.security.spec.ECPoint
            org.spongycastle.math.ec.ECPoint r4 = r0.f()
            org.spongycastle.math.ec.ECFieldElement r4 = r4.f()
            java.math.BigInteger r4 = r4.t()
            org.spongycastle.math.ec.ECPoint r5 = r0.f()
            org.spongycastle.math.ec.ECFieldElement r5 = r5.g()
            java.math.BigInteger r5 = r5.t()
            r3.<init>(r4, r5)
            java.math.BigInteger r4 = r0.i()
            java.math.BigInteger r5 = r0.g()
            int r5 = r5.intValue()
            r2.<init>(r1, r3, r4, r5)
            r0 = r2
            goto L_0x00d6
        L_0x00ae:
            java.security.spec.ECParameterSpec r2 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r3 = new java.security.spec.ECPoint
            org.spongycastle.math.ec.ECPoint r4 = r0.f()
            org.spongycastle.math.ec.ECFieldElement r4 = r4.f()
            java.math.BigInteger r4 = r4.t()
            org.spongycastle.math.ec.ECPoint r5 = r0.f()
            org.spongycastle.math.ec.ECFieldElement r5 = r5.g()
            java.math.BigInteger r5 = r5.t()
            r3.<init>(r4, r5)
            java.math.BigInteger r4 = r0.i()
            r5 = 1
            r2.<init>(r1, r3, r4, r5)
            r0 = r2
        L_0x00d6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.util.EC5Util.h(org.spongycastle.asn1.x9.X962Parameters, org.spongycastle.math.ec.ECCurve):java.security.spec.ECParameterSpec");
    }

    public static ECParameterSpec i(X9ECParameters domainParameters) {
        return new ECParameterSpec(a(domainParameters.e(), (byte[]) null), new ECPoint(domainParameters.f().f().t(), domainParameters.f().g().t()), domainParameters.i(), domainParameters.g().intValue());
    }

    public static EllipticCurve a(ECCurve curve, byte[] seed) {
        return new EllipticCurve(c(curve.s()), curve.n().t(), curve.o().t(), (byte[]) null);
    }

    public static ECCurve b(EllipticCurve ec) {
        ECField field = ec.getField();
        BigInteger a2 = ec.getA();
        BigInteger b = ec.getB();
        if (field instanceof ECFieldFp) {
            ECCurve.Fp curve = new ECCurve.Fp(((ECFieldFp) field).getP(), a2, b);
            if (a.containsKey(curve)) {
                return (ECCurve) a.get(curve);
            }
            return curve;
        }
        ECFieldF2m fieldF2m = (ECFieldF2m) field;
        int m = fieldF2m.getM();
        int[] ks = ECUtil.b(fieldF2m.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(m, ks[0], ks[1], ks[2], a2, b);
    }

    public static ECField c(FiniteField field) {
        if (ECAlgorithms.l(field)) {
            return new ECFieldFp(field.b());
        }
        Polynomial poly = ((PolynomialExtensionField) field).c();
        int[] exponents = poly.a();
        return new ECFieldF2m(poly.b(), Arrays.U(Arrays.C(exponents, 1, exponents.length - 1)));
    }

    public static ECParameterSpec f(EllipticCurve ellipticCurve, org.spongycastle.jce.spec.ECParameterSpec spec) {
        if (!(spec instanceof ECNamedCurveParameterSpec)) {
            return new ECParameterSpec(ellipticCurve, new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c().intValue());
        }
        return new ECNamedCurveSpec(((ECNamedCurveParameterSpec) spec).f(), ellipticCurve, new ECPoint(spec.b().f().t(), spec.b().g().t()), spec.d(), spec.c());
    }

    public static org.spongycastle.jce.spec.ECParameterSpec g(ECParameterSpec ecSpec, boolean withCompression) {
        ECCurve curve = b(ecSpec.getCurve());
        return new org.spongycastle.jce.spec.ECParameterSpec(curve, e(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf((long) ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
    }

    public static org.spongycastle.math.ec.ECPoint d(ECParameterSpec ecSpec, ECPoint point, boolean withCompression) {
        return e(b(ecSpec.getCurve()), point, withCompression);
    }

    public static org.spongycastle.math.ec.ECPoint e(ECCurve curve, ECPoint point, boolean withCompression) {
        return curve.f(point.getAffineX(), point.getAffineY());
    }
}
