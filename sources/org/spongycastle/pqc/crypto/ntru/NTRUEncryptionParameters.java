package org.spongycastle.pqc.crypto.ntru;

import java.util.Arrays;
import org.spongycastle.crypto.Digest;

public class NTRUEncryptionParameters implements Cloneable {
    int A4;
    public int B4;
    public int C4;
    public int D4;
    public int E4;
    public int F4;
    public boolean G4;
    public byte[] H4;
    public boolean I4;
    public boolean J4;
    public int K4 = 1;
    public Digest L4;
    public int a1;
    public int a2;
    public int c;
    public int d;
    public int f;
    public int p0;
    public int p1;
    int p2;
    public int p3;
    public int p4;
    public int q;
    public int x;
    public int y;
    public int z;
    public int z4;

    public NTRUEncryptionParameters(int N, int q2, int df, int dm0, int db, int c2, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid, boolean sparse, boolean fastFp, Digest hashAlg) {
        this.c = N;
        this.d = q2;
        this.f = df;
        this.p4 = db;
        this.B4 = dm0;
        this.D4 = c2;
        this.E4 = minCallsR;
        this.F4 = minCallsMask;
        this.G4 = hashSeed;
        this.H4 = oid;
        this.I4 = sparse;
        this.J4 = fastFp;
        this.L4 = hashAlg;
        b();
    }

    public NTRUEncryptionParameters(int N, int q2, int df1, int df2, int df3, int dm0, int db, int c2, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid, boolean sparse, boolean fastFp, Digest hashAlg) {
        this.c = N;
        this.d = q2;
        this.q = df1;
        this.x = df2;
        this.y = df3;
        this.p4 = db;
        this.B4 = dm0;
        this.D4 = c2;
        this.E4 = minCallsR;
        this.F4 = minCallsMask;
        this.G4 = hashSeed;
        this.H4 = oid;
        this.I4 = sparse;
        this.J4 = fastFp;
        this.L4 = hashAlg;
        b();
    }

    private void b() {
        this.z = this.f;
        this.p0 = this.q;
        this.a1 = this.x;
        this.p1 = this.y;
        int i = this.c;
        this.a2 = i / 3;
        this.p2 = 1;
        int i2 = this.p4;
        this.p3 = (((((i * 3) / 2) / 8) - 1) - (i2 / 8)) - 1;
        this.z4 = (((((i * 3) / 2) + 7) / 8) * 8) + 1;
        this.A4 = i - 1;
        this.C4 = i2;
    }

    /* renamed from: a */
    public NTRUEncryptionParameters clone() {
        if (this.K4 == 0) {
            return new NTRUEncryptionParameters(this.c, this.d, this.f, this.B4, this.p4, this.D4, this.E4, this.F4, this.G4, this.H4, this.I4, this.J4, this.L4);
        }
        int i = this.c;
        int i2 = this.d;
        int i3 = this.q;
        int i4 = this.x;
        int i5 = this.y;
        int i6 = this.B4;
        int i7 = this.p4;
        int i8 = this.D4;
        int i9 = this.E4;
        int i10 = this.F4;
        boolean z2 = this.G4;
        byte[] bArr = this.H4;
        return new NTRUEncryptionParameters(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, z2, bArr, this.I4, this.J4, this.L4);
    }

    public int hashCode() {
        int i = 1231;
        int result = ((((((((((((((((((((((((((((((((1 * 31) + this.c) * 31) + this.z4) * 31) + this.A4) * 31) + this.D4) * 31) + this.p4) * 31) + this.f) * 31) + this.q) * 31) + this.x) * 31) + this.y) * 31) + this.a2) * 31) + this.B4) * 31) + this.z) * 31) + this.p0) * 31) + this.a1) * 31) + this.p1) * 31) + (this.J4 ? 1231 : 1237)) * 31;
        Digest digest = this.L4;
        int result2 = (((((((((((((((((((result + (digest == null ? 0 : digest.b().hashCode())) * 31) + (this.G4 ? 1231 : 1237)) * 31) + this.p2) * 31) + this.p3) * 31) + this.F4) * 31) + this.E4) * 31) + Arrays.hashCode(this.H4)) * 31) + this.C4) * 31) + this.K4) * 31) + this.d) * 31;
        if (!this.I4) {
            i = 1237;
        }
        return result2 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NTRUEncryptionParameters other = (NTRUEncryptionParameters) obj;
        if (this.c != other.c || this.z4 != other.z4 || this.A4 != other.A4 || this.D4 != other.D4 || this.p4 != other.p4 || this.f != other.f || this.q != other.q || this.x != other.x || this.y != other.y || this.a2 != other.a2 || this.B4 != other.B4 || this.z != other.z || this.p0 != other.p0 || this.a1 != other.a1 || this.p1 != other.p1 || this.J4 != other.J4) {
            return false;
        }
        Digest digest = this.L4;
        if (digest == null) {
            if (other.L4 != null) {
                return false;
            }
        } else if (!digest.b().equals(other.L4.b())) {
            return false;
        }
        if (this.G4 == other.G4 && this.p2 == other.p2 && this.p3 == other.p3 && this.F4 == other.F4 && this.E4 == other.E4 && Arrays.equals(this.H4, other.H4) && this.C4 == other.C4 && this.K4 == other.K4 && this.d == other.d && this.I4 == other.I4) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("EncryptionParameters(N=" + this.c + " q=" + this.d);
        if (this.K4 == 0) {
            output.append(" polyType=SIMPLE df=" + this.f);
        } else {
            output.append(" polyType=PRODUCT df1=" + this.q + " df2=" + this.x + " df3=" + this.y);
        }
        output.append(" dm0=" + this.B4 + " db=" + this.p4 + " c=" + this.D4 + " minCallsR=" + this.E4 + " minCallsMask=" + this.F4 + " hashSeed=" + this.G4 + " hashAlg=" + this.L4 + " oid=" + Arrays.toString(this.H4) + " sparse=" + this.I4 + ")");
        return output.toString();
    }
}
