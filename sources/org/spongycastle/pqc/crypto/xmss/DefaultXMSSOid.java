package org.spongycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultXMSSOid implements XMSSOid {
    private static final Map<String, DefaultXMSSOid> a;
    private final int b;
    private final String c;

    static {
        Map<String, DefaultXMSSOid> map = new HashMap<>();
        map.put(a("SHA-256", 32, 16, 67, 10), new DefaultXMSSOid(16777217, "XMSS_SHA2-256_W16_H10"));
        map.put(a("SHA-256", 32, 16, 67, 16), new DefaultXMSSOid(33554434, "XMSS_SHA2-256_W16_H16"));
        map.put(a("SHA-256", 32, 16, 67, 20), new DefaultXMSSOid(50331651, "XMSS_SHA2-256_W16_H20"));
        map.put(a("SHA-512", 64, 16, 131, 10), new DefaultXMSSOid(67108868, "XMSS_SHA2-512_W16_H10"));
        map.put(a("SHA-512", 64, 16, 131, 16), new DefaultXMSSOid(83886085, "XMSS_SHA2-512_W16_H16"));
        map.put(a("SHA-512", 64, 16, 131, 20), new DefaultXMSSOid(100663302, "XMSS_SHA2-512_W16_H20"));
        map.put(a("SHAKE128", 32, 16, 67, 10), new DefaultXMSSOid(117440519, "XMSS_SHAKE128_W16_H10"));
        map.put(a("SHAKE128", 32, 16, 67, 16), new DefaultXMSSOid(134217736, "XMSS_SHAKE128_W16_H16"));
        map.put(a("SHAKE128", 32, 16, 67, 20), new DefaultXMSSOid(150994953, "XMSS_SHAKE128_W16_H20"));
        map.put(a("SHAKE256", 64, 16, 131, 10), new DefaultXMSSOid(167772170, "XMSS_SHAKE256_W16_H10"));
        map.put(a("SHAKE256", 64, 16, 131, 16), new DefaultXMSSOid(184549387, "XMSS_SHAKE256_W16_H16"));
        map.put(a("SHAKE256", 64, 16, 131, 20), new DefaultXMSSOid(201326604, "XMSS_SHAKE256_W16_H20"));
        a = Collections.unmodifiableMap(map);
    }

    private DefaultXMSSOid(int oid, String stringRepresentation) {
        this.b = oid;
        this.c = stringRepresentation;
    }

    public static DefaultXMSSOid b(String algorithmName, int digestSize, int winternitzParameter, int len, int height) {
        if (algorithmName != null) {
            return a.get(a(algorithmName, digestSize, winternitzParameter, len, height));
        }
        throw new NullPointerException("algorithmName == null");
    }

    private static String a(String algorithmName, int digestSize, int winternitzParameter, int len, int height) {
        if (algorithmName != null) {
            return algorithmName + "-" + digestSize + "-" + winternitzParameter + "-" + len + "-" + height;
        }
        throw new NullPointerException("algorithmName == null");
    }

    public String toString() {
        return this.c;
    }
}
