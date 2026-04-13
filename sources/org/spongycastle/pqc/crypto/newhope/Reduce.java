package org.spongycastle.pqc.crypto.newhope;

public class Reduce {
    Reduce() {
    }

    static short b(int a) {
        return (short) (((((a * 12287) & 262143) * 12289) + a) >>> 18);
    }

    static short a(short a) {
        int t = 65535 & a;
        return (short) (t - (((t * 5) >>> 16) * 12289));
    }
}
