package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import org.spongycastle.util.Integers;

public class TlsExtensionsUtils {
    public static final Integer a = Integers.b(22);
    public static final Integer b = Integers.b(23);
    public static final Integer c = Integers.b(15);
    public static final Integer d = Integers.b(1);
    public static final Integer e = Integers.b(21);
    public static final Integer f = Integers.b(0);
    public static final Integer g = Integers.b(5);
    public static final Integer h = Integers.b(4);

    public static Hashtable j(Hashtable extensions) {
        return extensions == null ? new Hashtable() : extensions;
    }

    public static void a(Hashtable extensions) {
        extensions.put(a, f());
    }

    public static void b(Hashtable extensions) {
        extensions.put(b, g());
    }

    public static void c(Hashtable extensions, short maxFragmentLength) {
        extensions.put(d, h(maxFragmentLength));
    }

    public static void d(Hashtable extensions) {
        extensions.put(h, i());
    }

    public static short k(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, d);
        if (extensionData == null) {
            return -1;
        }
        return s(extensionData);
    }

    public static int l(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, e);
        if (extensionData == null) {
            return -1;
        }
        return t(extensionData);
    }

    public static boolean m(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, a);
        if (extensionData == null) {
            return false;
        }
        return q(extensionData);
    }

    public static boolean n(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, b);
        if (extensionData == null) {
            return false;
        }
        return r(extensionData);
    }

    public static boolean o(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, h);
        if (extensionData == null) {
            return false;
        }
        return u(extensionData);
    }

    public static byte[] e() {
        return TlsUtils.a;
    }

    public static byte[] f() {
        return e();
    }

    public static byte[] g() {
        return e();
    }

    public static byte[] h(short maxFragmentLength) {
        TlsUtils.k(maxFragmentLength);
        byte[] extensionData = new byte[1];
        TlsUtils.M0(maxFragmentLength, extensionData, 0);
        return extensionData;
    }

    public static byte[] i() {
        return e();
    }

    private static boolean p(byte[] extensionData) {
        if (extensionData == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        } else if (extensionData.length == 0) {
            return true;
        } else {
            throw new TlsFatalAlert(47);
        }
    }

    public static boolean q(byte[] extensionData) {
        return p(extensionData);
    }

    public static boolean r(byte[] extensionData) {
        return p(extensionData);
    }

    public static short s(byte[] extensionData) {
        if (extensionData == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        } else if (extensionData.length == 1) {
            return TlsUtils.r0(extensionData, 0);
        } else {
            throw new TlsFatalAlert(50);
        }
    }

    public static int t(byte[] extensionData) {
        if (extensionData != null) {
            int i = 0;
            while (i < extensionData.length) {
                if (extensionData[i] == 0) {
                    i++;
                } else {
                    throw new TlsFatalAlert(47);
                }
            }
            return extensionData.length;
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static boolean u(byte[] extensionData) {
        return p(extensionData);
    }
}
