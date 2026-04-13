package org.spongycastle.math;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.util.BigIntegers;

public abstract class Primes {
    private static final BigInteger a = BigInteger.valueOf(1);
    private static final BigInteger b = BigInteger.valueOf(2);
    private static final BigInteger c = BigInteger.valueOf(3);

    public static class MROutput {
    }

    public static class STOutput {
    }

    public static boolean b(BigInteger candidate) {
        a(candidate, "candidate");
        return c(candidate);
    }

    public static boolean e(BigInteger candidate, SecureRandom random, int iterations) {
        a(candidate, "candidate");
        if (random == null) {
            throw new IllegalArgumentException("'random' cannot be null");
        } else if (iterations < 1) {
            throw new IllegalArgumentException("'iterations' must be > 0");
        } else if (candidate.bitLength() == 2) {
            return true;
        } else {
            if (!candidate.testBit(0)) {
                return false;
            }
            BigInteger w = candidate;
            BigInteger wSubOne = candidate.subtract(a);
            BigInteger wSubTwo = candidate.subtract(b);
            int a2 = wSubOne.getLowestSetBit();
            BigInteger m = wSubOne.shiftRight(a2);
            for (int i = 0; i < iterations; i++) {
                if (!d(w, wSubOne, m, a2, BigIntegers.c(b, wSubTwo, random))) {
                    return false;
                }
            }
            return true;
        }
    }

    private static void a(BigInteger n, String name) {
        if (n == null || n.signum() < 1 || n.bitLength() < 2) {
            throw new IllegalArgumentException("'" + name + "' must be non-null and >= 2");
        }
    }

    private static boolean c(BigInteger x) {
        int r = x.mod(BigInteger.valueOf((long) 223092870)).intValue();
        if (r % 2 == 0 || r % 3 == 0 || r % 5 == 0 || r % 7 == 0 || r % 11 == 0 || r % 13 == 0 || r % 17 == 0 || r % 19 == 0 || r % 23 == 0) {
            return true;
        }
        int r2 = x.mod(BigInteger.valueOf((long) 58642669)).intValue();
        if (r2 % 29 == 0 || r2 % 31 == 0 || r2 % 37 == 0 || r2 % 41 == 0 || r2 % 43 == 0) {
            return true;
        }
        int r3 = x.mod(BigInteger.valueOf((long) 600662303)).intValue();
        if (r3 % 47 == 0 || r3 % 53 == 0 || r3 % 59 == 0 || r3 % 61 == 0 || r3 % 67 == 0) {
            return true;
        }
        int r4 = x.mod(BigInteger.valueOf((long) 33984931)).intValue();
        if (r4 % 71 == 0 || r4 % 73 == 0 || r4 % 79 == 0 || r4 % 83 == 0) {
            return true;
        }
        int r5 = x.mod(BigInteger.valueOf((long) 89809099)).intValue();
        if (r5 % 89 == 0 || r5 % 97 == 0 || r5 % 101 == 0 || r5 % 103 == 0) {
            return true;
        }
        int r6 = x.mod(BigInteger.valueOf((long) 167375713)).intValue();
        if (r6 % 107 == 0 || r6 % 109 == 0 || r6 % 113 == 0 || r6 % NeedPermissionEvent.PER_IPC_SPEAK_PERM == 0) {
            return true;
        }
        int r7 = x.mod(BigInteger.valueOf((long) 371700317)).intValue();
        if (r7 % 131 == 0 || r7 % NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV == 0 || r7 % NeedPermissionEvent.PER_GET_LOCATION_BLE == 0 || r7 % Opcodes.FCMPL == 0) {
            return true;
        }
        int r8 = x.mod(BigInteger.valueOf((long) 645328247)).intValue();
        if (r8 % Opcodes.DCMPL == 0 || r8 % 157 == 0 || r8 % Opcodes.IF_ICMPGT == 0 || r8 % Opcodes.GOTO == 0) {
            return true;
        }
        int r9 = x.mod(BigInteger.valueOf((long) 1070560157)).intValue();
        if (r9 % 173 == 0 || r9 % 179 == 0 || r9 % Opcodes.PUTFIELD == 0 || r9 % 191 == 0) {
            return true;
        }
        int r10 = x.mod(BigInteger.valueOf((long) 1596463769)).intValue();
        if (r10 % Opcodes.INSTANCEOF == 0 || r10 % 197 == 0 || r10 % 199 == 0 || r10 % 211 == 0) {
            return true;
        }
        return false;
    }

    private static boolean d(BigInteger w, BigInteger wSubOne, BigInteger m, int a2, BigInteger b2) {
        BigInteger z = b2.modPow(m, w);
        if (z.equals(a) || z.equals(wSubOne)) {
            return true;
        }
        for (int j = 1; j < a2; j++) {
            z = z.modPow(b, w);
            if (z.equals(wSubOne)) {
                return true;
            }
            if (z.equals(a)) {
                return false;
            }
        }
        return false;
    }
}
