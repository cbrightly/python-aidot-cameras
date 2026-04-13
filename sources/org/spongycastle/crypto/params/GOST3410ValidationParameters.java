package org.spongycastle.crypto.params;

public class GOST3410ValidationParameters {
    private int a;
    private int b;
    private long c;
    private long d;

    public GOST3410ValidationParameters(int x0, int c2) {
        this.a = x0;
        this.b = c2;
    }

    public GOST3410ValidationParameters(long x0L, long cL) {
        this.c = x0L;
        this.d = cL;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GOST3410ValidationParameters)) {
            return false;
        }
        GOST3410ValidationParameters other = (GOST3410ValidationParameters) o;
        if (other.b == this.b && other.a == this.a && other.d == this.d && other.c == this.c) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = this.a ^ this.b;
        long j = this.c;
        int i2 = (i ^ ((int) j)) ^ ((int) (j >> 32));
        long j2 = this.d;
        return (i2 ^ ((int) j2)) ^ ((int) (j2 >> 32));
    }
}
