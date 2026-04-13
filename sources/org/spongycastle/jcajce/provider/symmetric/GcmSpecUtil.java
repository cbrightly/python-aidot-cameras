package org.spongycastle.jcajce.provider.symmetric;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cms.GCMParameters;
import org.spongycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.spongycastle.util.Integers;

public class GcmSpecUtil {
    static final Class a = ClassUtil.a(GcmSpecUtil.class, "javax.crypto.spec.GCMParameterSpec");

    GcmSpecUtil() {
    }

    static boolean c() {
        return a != null;
    }

    static boolean e(AlgorithmParameterSpec paramSpec) {
        Class cls = a;
        return cls != null && cls.isInstance(paramSpec);
    }

    static boolean d(Class paramSpecClass) {
        return a == paramSpecClass;
    }

    static AlgorithmParameterSpec b(ASN1Primitive spec) {
        try {
            GCMParameters gcmParams = GCMParameters.f(spec);
            return (AlgorithmParameterSpec) a.getConstructor(new Class[]{Integer.TYPE, byte[].class}).newInstance(new Object[]{Integers.b(gcmParams.e() * 8), gcmParams.g()});
        } catch (NoSuchMethodException e) {
            throw new InvalidParameterSpecException("No constructor found!");
        } catch (Exception e2) {
            throw new InvalidParameterSpecException("Construction failed: " + e2.getMessage());
        }
    }

    static GCMParameters a(AlgorithmParameterSpec paramSpec) {
        try {
            Class cls = a;
            return new GCMParameters((byte[]) cls.getDeclaredMethod("getIV", new Class[0]).invoke(paramSpec, new Object[0]), ((Integer) cls.getDeclaredMethod("getTLen", new Class[0]).invoke(paramSpec, new Object[0])).intValue() / 8);
        } catch (Exception e) {
            throw new InvalidParameterSpecException("Cannot process GCMParameterSpec");
        }
    }
}
