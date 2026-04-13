package org.spongycastle.pqc.crypto.ntru;

import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUSigningKeyGenerationParameters extends KeyGenerationParameters implements Cloneable {
    public static final NTRUSigningKeyGenerationParameters f = new NTRUSigningKeyGenerationParameters(439, 2048, 146, 1, 1, 0.165d, 490.0d, 280.0d, false, true, 0, new SHA256Digest());
    public static final NTRUSigningKeyGenerationParameters p0;
    public static final NTRUSigningKeyGenerationParameters q;
    public static final NTRUSigningKeyGenerationParameters x = new NTRUSigningKeyGenerationParameters(743, 2048, 248, 1, 1, 0.127d, 560.0d, 360.0d, true, false, 0, new SHA512Digest());
    public static final NTRUSigningKeyGenerationParameters y;
    public static final NTRUSigningKeyGenerationParameters z = new NTRUSigningKeyGenerationParameters(157, 256, 29, 1, 1, 0.38d, 200.0d, 80.0d, false, false, 0, new SHA256Digest());
    double A4;
    public double B4;
    double C4;
    public double D4;
    public int E4 = 100;
    double F4;
    public double G4;
    public boolean H4;
    public int I4;
    int J4 = 6;
    public boolean K4;
    public int L4;
    public Digest M4;
    public int N4;
    public int a1;
    public int a2;
    public int p1;
    public int p2;
    public int p3;
    public int p4;
    public int z4;

    static {
        SHA256Digest sHA256Digest = r1;
        SHA256Digest sHA256Digest2 = new SHA256Digest();
        q = new NTRUSigningKeyGenerationParameters(439, 2048, 9, 8, 5, 1, 1, 0.165d, 490.0d, 280.0d, false, true, 0, sHA256Digest);
        SHA512Digest sHA512Digest = r1;
        SHA512Digest sHA512Digest2 = new SHA512Digest();
        y = new NTRUSigningKeyGenerationParameters(743, 2048, 11, 11, 15, 1, 1, 0.127d, 560.0d, 360.0d, true, false, 0, sHA512Digest);
        SHA256Digest sHA256Digest3 = r1;
        SHA256Digest sHA256Digest4 = new SHA256Digest();
        p0 = new NTRUSigningKeyGenerationParameters(157, 256, 5, 5, 8, 1, 1, 0.38d, 200.0d, 80.0d, false, false, 0, sHA256Digest3);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NTRUSigningKeyGenerationParameters(int r17, int r18, int r19, int r20, int r21, double r22, double r24, double r26, boolean r28, boolean r29, int r30, org.spongycastle.crypto.Digest r31) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            java.security.SecureRandom r2 = new java.security.SecureRandom
            r2.<init>()
            r0.<init>(r2, r1)
            r2 = 100
            r0.E4 = r2
            r2 = 6
            r0.J4 = r2
            r0.a1 = r1
            r2 = r18
            r0.p1 = r2
            r3 = r19
            r0.a2 = r3
            r4 = r20
            r0.z4 = r4
            r5 = r21
            r0.I4 = r5
            r6 = r22
            r0.A4 = r6
            r8 = r24
            r0.C4 = r8
            r10 = r26
            r0.F4 = r10
            r12 = r28
            r0.H4 = r12
            r13 = r29
            r0.K4 = r13
            r14 = r30
            r0.L4 = r14
            r15 = r31
            r0.M4 = r15
            r1 = 0
            r0.N4 = r1
            r16.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.ntru.NTRUSigningKeyGenerationParameters.<init>(int, int, int, int, int, double, double, double, boolean, boolean, int, org.spongycastle.crypto.Digest):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NTRUSigningKeyGenerationParameters(int r17, int r18, int r19, int r20, int r21, int r22, int r23, double r24, double r26, double r28, boolean r30, boolean r31, int r32, org.spongycastle.crypto.Digest r33) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            java.security.SecureRandom r2 = new java.security.SecureRandom
            r2.<init>()
            r0.<init>(r2, r1)
            r2 = 100
            r0.E4 = r2
            r2 = 6
            r0.J4 = r2
            r0.a1 = r1
            r2 = r18
            r0.p1 = r2
            r3 = r19
            r0.p2 = r3
            r4 = r20
            r0.p3 = r4
            r5 = r21
            r0.p4 = r5
            r6 = r22
            r0.z4 = r6
            r7 = r23
            r0.I4 = r7
            r8 = r24
            r0.A4 = r8
            r10 = r26
            r0.C4 = r10
            r12 = r28
            r0.F4 = r12
            r14 = r30
            r0.H4 = r14
            r15 = r31
            r0.K4 = r15
            r1 = r32
            r0.L4 = r1
            r1 = r33
            r0.M4 = r1
            r1 = 1
            r0.N4 = r1
            r16.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.ntru.NTRUSigningKeyGenerationParameters.<init>(int, int, int, int, int, int, int, double, double, double, boolean, boolean, int, org.spongycastle.crypto.Digest):void");
    }

    private void e() {
        double d = this.A4;
        this.B4 = d * d;
        double d2 = this.C4;
        this.D4 = d2 * d2;
        double d3 = this.F4;
        this.G4 = d3 * d3;
    }

    public NTRUSigningParameters d() {
        return new NTRUSigningParameters(this.a1, this.p1, this.a2, this.z4, this.A4, this.C4, this.M4);
    }

    /* renamed from: c */
    public NTRUSigningKeyGenerationParameters clone() {
        if (this.N4 != 0) {
            return new NTRUSigningKeyGenerationParameters(this.a1, this.p1, this.p2, this.p3, this.p4, this.z4, this.I4, this.A4, this.C4, this.F4, this.H4, this.K4, this.L4, this.M4);
        }
        int i = this.a1;
        int i2 = this.p1;
        int i3 = this.a2;
        int i4 = this.z4;
        int i5 = this.I4;
        double d = this.A4;
        double d2 = this.C4;
        double d3 = this.F4;
        boolean z2 = this.H4;
        boolean z3 = this.K4;
        return new NTRUSigningKeyGenerationParameters(i, i2, i3, i4, i5, d, d2, d3, z2, z3, this.L4, this.M4);
    }

    public int hashCode() {
        int result = (((((1 * 31) + this.z4) * 31) + this.a1) * 31) + this.I4;
        long temp = Double.doubleToLongBits(this.A4);
        int result2 = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        long temp2 = Double.doubleToLongBits(this.B4);
        int result3 = ((((((((((((result2 * 31) + ((int) ((temp2 >>> 32) ^ temp2))) * 31) + this.J4) * 31) + this.a2) * 31) + this.p2) * 31) + this.p3) * 31) + this.p4) * 31;
        Digest digest = this.M4;
        int result4 = ((result3 + (digest == null ? 0 : digest.b().hashCode())) * 31) + this.L4;
        long temp3 = Double.doubleToLongBits(this.F4);
        int result5 = (result4 * 31) + ((int) ((temp3 >>> 32) ^ temp3));
        long temp4 = Double.doubleToLongBits(this.G4);
        int result6 = (result5 * 31) + ((int) ((temp4 >>> 32) ^ temp4));
        long temp5 = Double.doubleToLongBits(this.C4);
        int result7 = (result6 * 31) + ((int) ((temp5 >>> 32) ^ temp5));
        long temp6 = Double.doubleToLongBits(this.D4);
        int i = 1231;
        int result8 = ((((((((((result7 * 31) + ((int) ((temp6 >>> 32) ^ temp6))) * 31) + this.N4) * 31) + (this.H4 ? 1231 : 1237)) * 31) + this.p1) * 31) + this.E4) * 31;
        if (!this.K4) {
            i = 1237;
        }
        return result8 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NTRUSigningKeyGenerationParameters)) {
            return false;
        }
        NTRUSigningKeyGenerationParameters other = (NTRUSigningKeyGenerationParameters) obj;
        if (this.z4 != other.z4 || this.a1 != other.a1 || this.I4 != other.I4 || Double.doubleToLongBits(this.A4) != Double.doubleToLongBits(other.A4) || Double.doubleToLongBits(this.B4) != Double.doubleToLongBits(other.B4) || this.J4 != other.J4 || this.a2 != other.a2 || this.p2 != other.p2 || this.p3 != other.p3 || this.p4 != other.p4) {
            return false;
        }
        Digest digest = this.M4;
        if (digest == null) {
            if (other.M4 != null) {
                return false;
            }
        } else if (!digest.b().equals(other.M4.b())) {
            return false;
        }
        if (this.L4 == other.L4 && Double.doubleToLongBits(this.F4) == Double.doubleToLongBits(other.F4) && Double.doubleToLongBits(this.G4) == Double.doubleToLongBits(other.G4) && Double.doubleToLongBits(this.C4) == Double.doubleToLongBits(other.C4) && Double.doubleToLongBits(this.D4) == Double.doubleToLongBits(other.D4) && this.N4 == other.N4 && this.H4 == other.H4 && this.p1 == other.p1 && this.E4 == other.E4 && this.K4 == other.K4) {
            return true;
        }
        return false;
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("0.00");
        StringBuilder output = new StringBuilder("SignatureParameters(N=" + this.a1 + " q=" + this.p1);
        if (this.N4 == 0) {
            output.append(" polyType=SIMPLE d=" + this.a2);
        } else {
            output.append(" polyType=PRODUCT d1=" + this.p2 + " d2=" + this.p3 + " d3=" + this.p4);
        }
        output.append(" B=" + this.z4 + " basisType=" + this.I4 + " beta=" + format.format(this.A4) + " normBound=" + format.format(this.C4) + " keyNormBound=" + format.format(this.F4) + " prime=" + this.H4 + " sparse=" + this.K4 + " keyGenAlg=" + this.L4 + " hashAlg=" + this.M4 + ")");
        return output.toString();
    }
}
