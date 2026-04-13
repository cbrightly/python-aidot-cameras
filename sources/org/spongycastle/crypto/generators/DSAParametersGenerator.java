package org.spongycastle.crypto.generators;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.DSAParameterGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAValidationParameters;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

public class DSAParametersGenerator {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = BigInteger.valueOf(2);
    private Digest d;
    private int e;
    private int f;
    private int g;
    private int h;
    private SecureRandom i;
    private boolean j;
    private int k;

    public DSAParametersGenerator() {
        this(DigestFactory.b());
    }

    public DSAParametersGenerator(Digest digest) {
        this.d = digest;
    }

    public void k(int size, int certainty, SecureRandom random) {
        this.e = size;
        this.f = g(size);
        this.g = certainty;
        this.h = Math.max(h(this.e), (certainty + 1) / 2);
        this.i = random;
        this.j = false;
        this.k = -1;
    }

    public void l(DSAParameterGenerationParameters params) {
        int L = params.b();
        int N = params.c();
        if (L < 1024 || L > 3072 || L % 1024 != 0) {
            throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
        } else if (L == 1024 && N != 160) {
            throw new IllegalArgumentException("N must be 160 for L = 1024");
        } else if (L == 2048 && N != 224 && N != 256) {
            throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
        } else if (L == 3072 && N != 256) {
            throw new IllegalArgumentException("N must be 256 for L = 3072");
        } else if (this.d.e() * 8 >= N) {
            this.e = L;
            this.f = N;
            this.g = params.a();
            this.h = Math.max(h(L), (this.g + 1) / 2);
            this.i = params.d();
            this.j = true;
            this.k = params.e();
        } else {
            throw new IllegalStateException("Digest output size too small for value of N");
        }
    }

    public DSAParameters d() {
        if (this.j) {
            return f();
        }
        return e();
    }

    private DSAParameters e() {
        byte[] seed = new byte[20];
        byte[] part1 = new byte[20];
        byte[] part2 = new byte[20];
        byte[] u = new byte[20];
        int i2 = this.e;
        int n = (i2 - 1) / Opcodes.IF_ICMPNE;
        byte[] w = new byte[(i2 / 8)];
        if (this.d instanceof SHA1Digest) {
            while (true) {
                this.i.nextBytes(seed);
                int i3 = 0;
                i(this.d, seed, part1, 0);
                System.arraycopy(seed, 0, part2, 0, seed.length);
                j(part2);
                i(this.d, part2, part2, 0);
                for (int i4 = 0; i4 != u.length; i4++) {
                    u[i4] = (byte) (part1[i4] ^ part2[i4]);
                }
                u[0] = (byte) (u[0] | OTACommand.RESP_ID_VERSION);
                u[19] = (byte) (u[19] | 1);
                BigInteger q = new BigInteger(1, u);
                if (m(q)) {
                    byte[] offset = Arrays.h(seed);
                    j(offset);
                    int counter = 0;
                    while (counter < 4096) {
                        for (int k2 = 1; k2 <= n; k2++) {
                            j(offset);
                            i(this.d, offset, w, w.length - (part1.length * k2));
                        }
                        int remaining = w.length - (part1.length * n);
                        j(offset);
                        i(this.d, offset, part1, i3);
                        System.arraycopy(part1, part1.length - remaining, w, i3, remaining);
                        w[i3] = (byte) (w[i3] | OTACommand.RESP_ID_VERSION);
                        BigInteger x = new BigInteger(1, w);
                        BigInteger p = x.subtract(x.mod(q.shiftLeft(1)).subtract(b));
                        if (p.bitLength() == this.e && m(p)) {
                            return new DSAParameters(p, q, a(p, q, this.i), new DSAValidationParameters(seed, counter));
                        }
                        counter++;
                        i3 = 0;
                    }
                    continue;
                }
            }
        } else {
            throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        }
    }

    private static BigInteger a(BigInteger p, BigInteger q, SecureRandom r) {
        BigInteger g2;
        BigInteger e2 = p.subtract(b).divide(q);
        BigInteger pSub2 = p.subtract(c);
        do {
            g2 = BigIntegers.c(c, pSub2, r).modPow(e2, p);
        } while (g2.bitLength() <= 1);
        return g2;
    }

    private DSAParameters f() {
        BigInteger q;
        int counter;
        BigInteger p;
        Digest d2 = this.d;
        int outlen = d2.e() * 8;
        int seedlen = this.f;
        byte[] seed = new byte[(seedlen / 8)];
        int i2 = this.e;
        int n = (i2 - 1) / outlen;
        int i3 = (i2 - 1) % outlen;
        byte[] w = new byte[(i2 / 8)];
        byte[] output = new byte[d2.e()];
        loop0:
        while (true) {
            this.i.nextBytes(seed);
            i(d2, seed, output, 0);
            q = new BigInteger(1, output).mod(b.shiftLeft(this.f - 1)).setBit(0).setBit(this.f - 1);
            if (m(q)) {
                byte[] offset = Arrays.h(seed);
                int counterLimit = this.e * 4;
                counter = 0;
                while (counter < counterLimit) {
                    int j2 = 1;
                    while (j2 <= n) {
                        j(offset);
                        i(d2, offset, w, w.length - (output.length * j2));
                        j2++;
                        outlen = outlen;
                    }
                    int outlen2 = outlen;
                    int remaining = w.length - (output.length * n);
                    j(offset);
                    i(d2, offset, output, 0);
                    System.arraycopy(output, output.length - remaining, w, 0, remaining);
                    w[0] = (byte) (w[0] | OTACommand.RESP_ID_VERSION);
                    BigInteger X = new BigInteger(1, w);
                    p = X.subtract(X.mod(q.shiftLeft(1)).subtract(b));
                    BigInteger bigInteger = X;
                    int seedlen2 = seedlen;
                    if (p.bitLength() == this.e && m(p)) {
                        break loop0;
                    }
                    counter++;
                    outlen = outlen2;
                    seedlen = seedlen2;
                    d2 = d2;
                    w = w;
                }
                int i4 = outlen;
                int i5 = seedlen;
                byte[] bArr = w;
            }
        }
        int i6 = this.k;
        if (i6 >= 0) {
            BigInteger g2 = c(d2, p, q, seed, i6);
            if (g2 != null) {
                Digest digest = d2;
                byte[] bArr2 = w;
                return new DSAParameters(p, q, g2, new DSAValidationParameters(seed, counter, this.k));
            }
            byte[] bArr3 = w;
        } else {
            byte[] bArr4 = w;
        }
        return new DSAParameters(p, q, b(p, q, this.i), new DSAValidationParameters(seed, counter));
    }

    private boolean m(BigInteger x) {
        return x.isProbablePrime(this.g);
    }

    private static BigInteger b(BigInteger p, BigInteger q, SecureRandom r) {
        return a(p, q, r);
    }

    private static BigInteger c(Digest d2, BigInteger p, BigInteger q, byte[] seed, int index) {
        BigInteger e2 = p.subtract(b).divide(q);
        byte[] ggen = Hex.a("6767656E");
        byte[] U = new byte[(seed.length + ggen.length + 1 + 2)];
        System.arraycopy(seed, 0, U, 0, seed.length);
        System.arraycopy(ggen, 0, U, seed.length, ggen.length);
        U[U.length - 3] = (byte) index;
        byte[] w = new byte[d2.e()];
        for (int count = 1; count < 65536; count++) {
            j(U);
            i(d2, U, w, 0);
            BigInteger g2 = new BigInteger(1, w).modPow(e2, p);
            if (g2.compareTo(c) >= 0) {
                return g2;
            }
        }
        return null;
    }

    private static void i(Digest d2, byte[] input, byte[] output, int outputPos) {
        d2.update(input, 0, input.length);
        d2.c(output, outputPos);
    }

    private static int g(int L) {
        if (L > 1024) {
            return 256;
        }
        return Opcodes.IF_ICMPNE;
    }

    private static int h(int L) {
        if (L <= 1024) {
            return 40;
        }
        return (((L - 1) / 1024) * 8) + 48;
    }

    private static void j(byte[] buf) {
        int i2 = buf.length - 1;
        while (i2 >= 0) {
            byte b2 = (byte) ((buf[i2] + 1) & 255);
            buf[i2] = b2;
            if (b2 == 0) {
                i2--;
            } else {
                return;
            }
        }
    }
}
