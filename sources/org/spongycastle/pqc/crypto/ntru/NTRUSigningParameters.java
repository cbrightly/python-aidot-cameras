package org.spongycastle.pqc.crypto.ntru;

import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;

public class NTRUSigningParameters implements Cloneable {
    public double a1;
    public double a2;
    public int c;
    public int d;
    public int f;
    double p0;
    double p1;
    public int p2 = 100;
    int p3 = 6;
    public Digest p4;
    public int q;
    public int x;
    public int y;
    public int z;

    public NTRUSigningParameters(int N, int q2, int d2, int B, double beta, double normBound, Digest hashAlg) {
        this.c = N;
        this.d = q2;
        this.f = d2;
        this.z = B;
        this.p0 = beta;
        this.p1 = normBound;
        this.p4 = hashAlg;
        b();
    }

    private void b() {
        double d2 = this.p0;
        this.a1 = d2 * d2;
        double d3 = this.p1;
        this.a2 = d3 * d3;
    }

    /* renamed from: a */
    public NTRUSigningParameters clone() {
        return new NTRUSigningParameters(this.c, this.d, this.f, this.z, this.p0, this.p1, this.p4);
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.p0);
        int result = (((((1 * 31) + this.z) * 31) + this.c) * 31) + ((int) ((temp >>> 32) ^ temp));
        long temp2 = Double.doubleToLongBits(this.a1);
        int result2 = ((((((((((((result * 31) + ((int) ((temp2 >>> 32) ^ temp2))) * 31) + this.p3) * 31) + this.f) * 31) + this.q) * 31) + this.x) * 31) + this.y) * 31;
        Digest digest = this.p4;
        int result3 = result2 + (digest == null ? 0 : digest.b().hashCode());
        long temp3 = Double.doubleToLongBits(this.p1);
        int result4 = (result3 * 31) + ((int) ((temp3 >>> 32) ^ temp3));
        long temp4 = Double.doubleToLongBits(this.a2);
        return (((((result4 * 31) + ((int) ((temp4 >>> 32) ^ temp4))) * 31) + this.d) * 31) + this.p2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NTRUSigningParameters)) {
            return false;
        }
        NTRUSigningParameters other = (NTRUSigningParameters) obj;
        if (this.z != other.z || this.c != other.c || Double.doubleToLongBits(this.p0) != Double.doubleToLongBits(other.p0) || Double.doubleToLongBits(this.a1) != Double.doubleToLongBits(other.a1) || this.p3 != other.p3 || this.f != other.f || this.q != other.q || this.x != other.x || this.y != other.y) {
            return false;
        }
        Digest digest = this.p4;
        if (digest == null) {
            if (other.p4 != null) {
                return false;
            }
        } else if (!digest.b().equals(other.p4.b())) {
            return false;
        }
        if (Double.doubleToLongBits(this.p1) == Double.doubleToLongBits(other.p1) && Double.doubleToLongBits(this.a2) == Double.doubleToLongBits(other.a2) && this.d == other.d && this.p2 == other.p2) {
            return true;
        }
        return false;
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("0.00");
        StringBuilder output = new StringBuilder("SignatureParameters(N=" + this.c + " q=" + this.d);
        output.append(" B=" + this.z + " beta=" + format.format(this.p0) + " normBound=" + format.format(this.p1) + " hashAlg=" + this.p4 + ")");
        return output.toString();
    }
}
