package org.spongycastle.crypto.params;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class DHParameters implements CipherParameters {
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;
    private BigInteger q;
    private int x;
    private int y;
    private DHValidationParameters z;

    private static int a(int lParam) {
        if (lParam != 0 && lParam < 160) {
            return lParam;
        }
        return Opcodes.IF_ICMPNE;
    }

    public DHParameters(BigInteger p, BigInteger g) {
        this(p, g, (BigInteger) null, 0);
    }

    public DHParameters(BigInteger p, BigInteger g, BigInteger q2) {
        this(p, g, q2, 0);
    }

    public DHParameters(BigInteger p, BigInteger g, BigInteger q2, int l) {
        this(p, g, q2, a(l), l, (BigInteger) null, (DHValidationParameters) null);
    }

    public DHParameters(BigInteger p, BigInteger g, BigInteger q2, BigInteger j, DHValidationParameters validation) {
        this(p, g, q2, Opcodes.IF_ICMPNE, 0, j, validation);
    }

    public DHParameters(BigInteger p, BigInteger g, BigInteger q2, int m, int l, BigInteger j, DHValidationParameters validation) {
        if (l != 0) {
            if (l > p.bitLength()) {
                throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            } else if (l < m) {
                throw new IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        this.c = g;
        this.d = p;
        this.f = q2;
        this.x = m;
        this.y = l;
        this.q = j;
        this.z = validation;
    }

    public BigInteger e() {
        return this.d;
    }

    public BigInteger b() {
        return this.c;
    }

    public BigInteger f() {
        return this.f;
    }

    public int d() {
        return this.x;
    }

    public int c() {
        return this.y;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHParameters)) {
            return false;
        }
        DHParameters pm = (DHParameters) obj;
        if (f() != null) {
            if (!f().equals(pm.f())) {
                return false;
            }
        } else if (pm.f() != null) {
            return false;
        }
        if (!pm.e().equals(this.d) || !pm.b().equals(this.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (e().hashCode() ^ b().hashCode()) ^ (f() != null ? f().hashCode() : 0);
    }
}
