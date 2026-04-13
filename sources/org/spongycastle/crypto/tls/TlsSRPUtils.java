package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Hashtable;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Integers;

public class TlsSRPUtils {
    public static final Integer a = Integers.b(12);

    public static byte[] a(Hashtable extensions) {
        byte[] extensionData = TlsUtils.C(extensions, a);
        if (extensionData == null) {
            return null;
        }
        return c(extensionData);
    }

    public static byte[] c(byte[] extensionData) {
        if (extensionData != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
            byte[] identity = TlsUtils.i0(buf);
            TlsProtocol.c(buf);
            return identity;
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static BigInteger d(InputStream input) {
        return new BigInteger(1, TlsUtils.g0(input));
    }

    public static void e(BigInteger x, OutputStream output) {
        TlsUtils.A0(BigIntegers.b(x), output);
    }

    public static boolean b(int cipherSuite) {
        switch (cipherSuite) {
            case 49178:
            case 49179:
            case 49180:
            case 49181:
            case 49182:
            case 49183:
            case 49184:
            case 49185:
            case 49186:
                return true;
            default:
                return false;
        }
    }
}
