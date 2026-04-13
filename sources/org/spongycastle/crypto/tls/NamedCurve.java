package org.spongycastle.crypto.tls;

public class NamedCurve {
    public static boolean a(int namedCurve) {
        if (namedCurve < 1 || namedCurve > 28) {
            return namedCurve >= 65281 && namedCurve <= 65282;
        }
        return true;
    }

    public static boolean b(int namedCurve) {
        switch (namedCurve) {
            case 65281:
            case 65282:
                return false;
            default:
                return true;
        }
    }
}
