package org.spongycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class WOTSPlusOid implements XMSSOid {
    private static final Map<String, WOTSPlusOid> a;
    private final int b;
    private final String c;

    static {
        Map<String, WOTSPlusOid> map = new HashMap<>();
        map.put(a("SHA-256", 32, 16, 67), new WOTSPlusOid(16777217, "WOTSP_SHA2-256_W16"));
        map.put(a("SHA-512", 64, 16, 131), new WOTSPlusOid(33554434, "WOTSP_SHA2-512_W16"));
        map.put(a("SHAKE128", 32, 16, 67), new WOTSPlusOid(50331651, "WOTSP_SHAKE128_W16"));
        map.put(a("SHAKE256", 64, 16, 131), new WOTSPlusOid(67108868, "WOTSP_SHAKE256_W16"));
        a = Collections.unmodifiableMap(map);
    }

    private WOTSPlusOid(int oid, String stringRepresentation) {
        this.b = oid;
        this.c = stringRepresentation;
    }

    protected static WOTSPlusOid b(String algorithmName, int digestSize, int winternitzParameter, int len) {
        if (algorithmName != null) {
            return a.get(a(algorithmName, digestSize, winternitzParameter, len));
        }
        throw new NullPointerException("algorithmName == null");
    }

    private static String a(String algorithmName, int digestSize, int winternitzParameter, int len) {
        if (algorithmName != null) {
            return algorithmName + "-" + digestSize + "-" + winternitzParameter + "-" + len;
        }
        throw new NullPointerException("algorithmName == null");
    }

    public String toString() {
        return this.c;
    }
}
