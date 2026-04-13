package org.spongycastle.crypto.generators;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.asm.Opcodes;
import com.github.druk.dnssd.NSType;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import meshsdk.BaseResp;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.spongycastle.crypto.params.NaccacheSternKeyParameters;
import org.spongycastle.crypto.params.NaccacheSternPrivateKeyParameters;

public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static int[] g = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, NeedPermissionEvent.PER_IPC_SPEAK_PERM, 131, NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, NeedPermissionEvent.PER_GET_LOCATION_BLE, Opcodes.FCMPL, Opcodes.DCMPL, 157, Opcodes.IF_ICMPGT, Opcodes.GOTO, 173, 179, Opcodes.PUTFIELD, 191, Opcodes.INSTANCEOF, 197, 199, 211, 223, 227, 229, 233, 239, 241, NSType.IXFR, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, TypedValues.AttributesType.TYPE_EASING, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, BaseResp.ERR_DISCONNECT_FAIL, BaseResp.ERR_SCENE_NOT_EXIST, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, TypedValues.PositionType.TYPE_PERCENT_WIDTH, 509, 521, 523, 541, 547, 557};
    private static final BigInteger h = BigInteger.valueOf(1);
    private NaccacheSternKeyGenerationParameters i;

    public AsymmetricCipherKeyPair a() {
        BigInteger q_;
        BigInteger p_;
        BigInteger p;
        BigInteger b;
        int remainingStrength;
        BigInteger _2bv;
        BigInteger v;
        BigInteger q_2;
        BigInteger _2bv2;
        BigInteger q;
        BigInteger a;
        long tries;
        BigInteger q2;
        BigInteger p2;
        long tries2;
        BigInteger g2;
        boolean divisible;
        BigInteger a2;
        BigInteger b2;
        long tries3;
        BigInteger p3;
        BigInteger g3;
        int strength;
        SecureRandom rand;
        int strength2 = this.i.b();
        SecureRandom rand2 = this.i.a();
        int certainty = this.i.c();
        boolean debug = this.i.e();
        if (debug) {
            System.out.println("Fetching first " + this.i.d() + " primes.");
        }
        Vector smallPrimes = e(b(this.i.d()), rand2);
        BigInteger u = h;
        BigInteger v2 = h;
        BigInteger u2 = u;
        for (int i2 = 0; i2 < smallPrimes.size() / 2; i2++) {
            u2 = u2.multiply((BigInteger) smallPrimes.elementAt(i2));
        }
        BigInteger q_3 = v2;
        for (int i3 = smallPrimes.size() / 2; i3 < smallPrimes.size(); i3++) {
            q_3 = q_3.multiply((BigInteger) smallPrimes.elementAt(i3));
        }
        BigInteger sigma = u2.multiply(q_3);
        int remainingStrength2 = (strength2 - sigma.bitLength()) - 48;
        BigInteger a3 = c((remainingStrength2 / 2) + 1, certainty, rand2);
        BigInteger b3 = c((remainingStrength2 / 2) + 1, certainty, rand2);
        long tries4 = 0;
        if (debug) {
            System.out.println("generating p and q");
        }
        BigInteger _2au = a3.multiply(u2).shiftLeft(1);
        BigInteger _2bv3 = b3.multiply(q_3).shiftLeft(1);
        while (true) {
            tries4++;
            BigInteger u3 = u2;
            p_ = c(24, certainty, rand2);
            BigInteger _2au2 = _2au;
            p = p_.multiply(_2au).add(h);
            if (p.isProbablePrime(certainty)) {
                while (true) {
                    v = q_;
                    q_2 = c(24, certainty, rand2);
                    if (p_.equals(q_2)) {
                        q_ = v;
                    } else {
                        BigInteger multiply = q_2.multiply(_2bv3);
                        _2bv = _2bv3;
                        _2bv2 = h;
                        q = multiply.add(_2bv2);
                        if (q.isProbablePrime(certainty)) {
                            break;
                        }
                        SecureRandom secureRandom = rand2;
                        int i4 = certainty;
                        boolean z = debug;
                        BigInteger bigInteger = q;
                        BigInteger bigInteger2 = p;
                        long j = tries4;
                        BigInteger bigInteger3 = b3;
                        BigInteger bigInteger4 = a3;
                        int i5 = remainingStrength2;
                        q_ = v;
                        _2bv3 = _2bv;
                    }
                }
                remainingStrength = remainingStrength2;
                if (sigma.gcd(p_.multiply(q_2)).equals(_2bv2)) {
                    if (p.multiply(q).bitLength() >= strength2) {
                        break;
                    } else if (debug) {
                        PrintStream printStream = System.out;
                        StringBuilder sb = new StringBuilder();
                        b = b3;
                        sb.append("key size too small. Should be ");
                        sb.append(strength2);
                        sb.append(" but is actually ");
                        sb.append(p.multiply(q).bitLength());
                        printStream.println(sb.toString());
                    } else {
                        b = b3;
                    }
                } else {
                    b = b3;
                }
            } else {
                _2bv = _2bv3;
                b = b3;
                v = q_;
                remainingStrength = remainingStrength2;
            }
            u2 = u3;
            q_3 = v;
            _2au = _2au2;
            _2bv3 = _2bv;
            remainingStrength2 = remainingStrength;
            b3 = b;
        }
        BigInteger b4 = b3;
        if (debug) {
            PrintStream printStream2 = System.out;
            a = a3;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("needed ");
            sb2.append(tries4);
            long j2 = tries4;
            sb2.append(" tries to generate p and q.");
            printStream2.println(sb2.toString());
        } else {
            a = a3;
        }
        BigInteger n = p.multiply(q);
        BigInteger phi_n = p.subtract(_2bv2).multiply(q.subtract(_2bv2));
        if (debug) {
            tries = 0;
            System.out.println("generating g");
        } else {
            tries = 0;
        }
        long tries5 = tries;
        while (true) {
            Vector gParts = new Vector();
            q2 = q;
            int ind = 0;
            while (true) {
                p2 = p;
                if (ind == smallPrimes.size()) {
                    break;
                }
                long tries6 = tries5;
                BigInteger e = phi_n.divide((BigInteger) smallPrimes.elementAt(ind));
                while (true) {
                    tries6++;
                    g3 = new BigInteger(strength2, certainty, rand2);
                    strength = strength2;
                    rand = rand2;
                    if (!g3.modPow(e, n).equals(h)) {
                        break;
                    }
                    strength2 = strength;
                    rand2 = rand;
                }
                gParts.addElement(g3);
                ind++;
                tries5 = tries6;
                p = p2;
                strength2 = strength;
                rand2 = rand;
            }
            int strength3 = strength2;
            SecureRandom rand3 = rand2;
            tries2 = tries5;
            g2 = h;
            for (int i6 = 0; i6 < smallPrimes.size(); i6++) {
                g2 = g2.multiply(((BigInteger) gParts.elementAt(i6)).modPow(sigma.divide((BigInteger) smallPrimes.elementAt(i6)), n)).mod(n);
            }
            divisible = false;
            int i7 = 0;
            while (true) {
                if (i7 >= smallPrimes.size()) {
                    break;
                } else if (g2.modPow(phi_n.divide((BigInteger) smallPrimes.elementAt(i7)), n).equals(h)) {
                    if (debug) {
                        System.out.println("g has order phi(n)/" + smallPrimes.elementAt(i7) + "\n g: " + g2);
                    }
                    divisible = true;
                } else {
                    i7++;
                }
            }
            if (!divisible) {
                BigInteger modPow = g2.modPow(phi_n.divide(BigInteger.valueOf(4)), n);
                BigInteger bigInteger5 = h;
                if (!modPow.equals(bigInteger5)) {
                    if (!g2.modPow(phi_n.divide(p_), n).equals(bigInteger5)) {
                        if (!g2.modPow(phi_n.divide(q_2), n).equals(bigInteger5)) {
                            a2 = a;
                            if (!g2.modPow(phi_n.divide(a2), n).equals(bigInteger5)) {
                                b2 = b4;
                                if (!g2.modPow(phi_n.divide(b2), n).equals(bigInteger5)) {
                                    break;
                                } else if (debug) {
                                    PrintStream printStream3 = System.out;
                                    StringBuilder sb3 = new StringBuilder();
                                    boolean z2 = divisible;
                                    sb3.append("g has order phi(n)/b\n g: ");
                                    sb3.append(g2);
                                    printStream3.println(sb3.toString());
                                }
                            } else if (debug) {
                                System.out.println("g has order phi(n)/a\n g: " + g2);
                                b2 = b4;
                            } else {
                                b2 = b4;
                            }
                        } else if (debug) {
                            System.out.println("g has order phi(n)/q'\n g: " + g2);
                            b2 = b4;
                            a2 = a;
                        } else {
                            b2 = b4;
                            a2 = a;
                        }
                    } else if (debug) {
                        System.out.println("g has order phi(n)/p'\n g: " + g2);
                        b2 = b4;
                        a2 = a;
                    } else {
                        b2 = b4;
                        a2 = a;
                    }
                } else if (debug) {
                    System.out.println("g has order phi(n)/4\n g:" + g2);
                    b2 = b4;
                    a2 = a;
                } else {
                    b2 = b4;
                    a2 = a;
                }
            } else {
                b2 = b4;
                a2 = a;
            }
            b4 = b2;
            a = a2;
            tries5 = tries2;
            q = q2;
            p = p2;
            strength2 = strength3;
            rand2 = rand3;
        }
        boolean z3 = divisible;
        if (debug) {
            PrintStream printStream4 = System.out;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("needed ");
            tries3 = tries2;
            sb4.append(tries3);
            sb4.append(" tries to generate g");
            printStream4.println(sb4.toString());
            System.out.println();
            System.out.println("found new NaccacheStern cipher variables:");
            System.out.println("smallPrimes: " + smallPrimes);
            System.out.println("sigma:...... " + sigma + " (" + sigma.bitLength() + " bits)");
            PrintStream printStream5 = System.out;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("a:.......... ");
            sb5.append(a2);
            printStream5.println(sb5.toString());
            System.out.println("b:.......... " + b2);
            System.out.println("p':......... " + p_);
            System.out.println("q':......... " + q_2);
            PrintStream printStream6 = System.out;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("p:.......... ");
            p3 = p2;
            sb6.append(p3);
            printStream6.println(sb6.toString());
            PrintStream printStream7 = System.out;
            StringBuilder sb7 = new StringBuilder();
            int i8 = certainty;
            sb7.append("q:.......... ");
            sb7.append(q2);
            printStream7.println(sb7.toString());
            System.out.println("n:.......... " + n);
            System.out.println("phi(n):..... " + phi_n);
            System.out.println("g:.......... " + g2);
            System.out.println();
        } else {
            tries3 = tries2;
            p3 = p2;
        }
        boolean z4 = debug;
        BigInteger bigInteger6 = p3;
        long j3 = tries3;
        BigInteger bigInteger7 = b2;
        BigInteger bigInteger8 = a2;
        return new AsymmetricCipherKeyPair(new NaccacheSternKeyParameters(false, g2, n, sigma.bitLength()), new NaccacheSternPrivateKeyParameters(g2, n, sigma.bitLength(), smallPrimes, phi_n));
    }

    private static BigInteger c(int bitLength, int certainty, SecureRandom rand) {
        BigInteger p_ = new BigInteger(bitLength, certainty, rand);
        while (p_.bitLength() != bitLength) {
            p_ = new BigInteger(bitLength, certainty, rand);
        }
        return p_;
    }

    private static Vector e(Vector arr, SecureRandom rand) {
        Vector retval = new Vector();
        Vector tmp = new Vector();
        for (int i2 = 0; i2 < arr.size(); i2++) {
            tmp.addElement(arr.elementAt(i2));
        }
        retval.addElement(tmp.elementAt(0));
        tmp.removeElementAt(0);
        while (tmp.size() != 0) {
            retval.insertElementAt(tmp.elementAt(0), d(rand, retval.size() + 1));
            tmp.removeElementAt(0);
        }
        return retval;
    }

    private static int d(SecureRandom rand, int n) {
        int bits;
        int val;
        if (((-n) & n) == n) {
            return (int) ((((long) n) * ((long) (rand.nextInt() & Integer.MAX_VALUE))) >> 31);
        }
        do {
            bits = rand.nextInt() & Integer.MAX_VALUE;
            val = bits % n;
        } while ((bits - val) + (n - 1) < 0);
        return val;
    }

    private static Vector b(int count) {
        Vector primes = new Vector(count);
        for (int i2 = 0; i2 != count; i2++) {
            primes.addElement(BigInteger.valueOf((long) g[i2]));
        }
        return primes;
    }
}
