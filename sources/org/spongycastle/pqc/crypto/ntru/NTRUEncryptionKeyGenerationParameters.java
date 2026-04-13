package org.spongycastle.pqc.crypto.ntru;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.security.SecureRandom;
import java.util.Arrays;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUEncryptionKeyGenerationParameters extends KeyGenerationParameters implements Cloneable {
    public static final NTRUEncryptionKeyGenerationParameters a1 = new NTRUEncryptionKeyGenerationParameters(743, 2048, 11, 11, 15, 220, 256, 10, 27, 14, true, new byte[]{0, 7, 105}, false, true, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters f = new NTRUEncryptionKeyGenerationParameters(1087, 2048, 120, 120, 256, 13, 25, 14, true, new byte[]{0, 6, 3}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters p0 = new NTRUEncryptionKeyGenerationParameters(743, 2048, 248, 220, 256, 10, 27, 14, true, new byte[]{0, 7, 105}, false, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters q = new NTRUEncryptionKeyGenerationParameters(1171, 2048, 106, 106, 256, 13, 20, 15, true, new byte[]{0, 6, 4}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters x = new NTRUEncryptionKeyGenerationParameters(1499, 2048, 79, 79, 256, 13, 17, 19, true, new byte[]{0, 6, 5}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters y = new NTRUEncryptionKeyGenerationParameters(439, 2048, 146, NeedPermissionEvent.PER_IPC_ALBUM_PERM, 128, 9, 32, 9, true, new byte[]{0, 7, 101}, true, false, new SHA256Digest());
    public static final NTRUEncryptionKeyGenerationParameters z = new NTRUEncryptionKeyGenerationParameters(439, 2048, 9, 8, 5, NeedPermissionEvent.PER_IPC_ALBUM_PERM, 128, 9, 32, 9, true, new byte[]{0, 7, 101}, true, true, new SHA256Digest());
    public int A4;
    public int B4;
    public int C4;
    public int D4;
    public int E4;
    int F4;
    public int G4;
    public int H4;
    public int I4;
    int J4;
    public int K4;
    public int L4;
    public int M4;
    public int N4;
    public int O4;
    public boolean P4;
    public byte[] Q4;
    public boolean R4;
    public boolean S4;
    public int T4 = 1;
    public Digest U4;
    public int a2;
    public int p1;
    public int p2;
    public int p3;
    public int p4;
    public int z4;

    public NTRUEncryptionKeyGenerationParameters(int N, int q2, int df, int dm0, int db, int c, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid, boolean sparse, boolean fastFp, Digest hashAlg) {
        super(new SecureRandom(), db);
        this.p1 = N;
        this.a2 = q2;
        this.p2 = df;
        this.H4 = db;
        this.K4 = dm0;
        this.M4 = c;
        this.N4 = minCallsR;
        this.O4 = minCallsMask;
        this.P4 = hashSeed;
        this.Q4 = oid;
        this.R4 = sparse;
        this.S4 = fastFp;
        this.U4 = hashAlg;
        e();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NTRUEncryptionKeyGenerationParameters(int r17, int r18, int r19, int r20, int r21, int r22, int r23, int r24, int r25, int r26, boolean r27, byte[] r28, boolean r29, boolean r30, org.spongycastle.crypto.Digest r31) {
        /*
            r16 = this;
            r0 = r16
            r1 = r23
            java.security.SecureRandom r2 = new java.security.SecureRandom
            r2.<init>()
            r0.<init>(r2, r1)
            r2 = r17
            r0.p1 = r2
            r3 = r18
            r0.a2 = r3
            r4 = r19
            r0.p3 = r4
            r5 = r20
            r0.p4 = r5
            r6 = r21
            r0.z4 = r6
            r0.H4 = r1
            r7 = r22
            r0.K4 = r7
            r8 = r24
            r0.M4 = r8
            r9 = r25
            r0.N4 = r9
            r10 = r26
            r0.O4 = r10
            r11 = r27
            r0.P4 = r11
            r12 = r28
            r0.Q4 = r12
            r13 = r29
            r0.R4 = r13
            r14 = r30
            r0.S4 = r14
            r15 = 1
            r0.T4 = r15
            r15 = r31
            r0.U4 = r15
            r16.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.ntru.NTRUEncryptionKeyGenerationParameters.<init>(int, int, int, int, int, int, int, int, int, int, boolean, byte[], boolean, boolean, org.spongycastle.crypto.Digest):void");
    }

    private void e() {
        this.A4 = this.p2;
        this.B4 = this.p3;
        this.C4 = this.p4;
        this.D4 = this.z4;
        int i = this.p1;
        this.E4 = i / 3;
        this.F4 = 1;
        int i2 = this.H4;
        this.G4 = (((((i * 3) / 2) / 8) - 1) - (i2 / 8)) - 1;
        this.I4 = (((((i * 3) / 2) + 7) / 8) * 8) + 1;
        this.J4 = i - 1;
        this.L4 = i2;
    }

    public NTRUEncryptionParameters d() {
        if (this.T4 == 0) {
            return new NTRUEncryptionParameters(this.p1, this.a2, this.p2, this.K4, this.H4, this.M4, this.N4, this.O4, this.P4, this.Q4, this.R4, this.S4, this.U4);
        }
        int i = this.p1;
        int i2 = this.a2;
        int i3 = this.p3;
        int i4 = this.p4;
        int i5 = this.z4;
        int i6 = this.K4;
        int i7 = this.H4;
        int i8 = this.M4;
        int i9 = this.N4;
        int i10 = this.O4;
        boolean z2 = this.P4;
        byte[] bArr = this.Q4;
        return new NTRUEncryptionParameters(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, z2, bArr, this.R4, this.S4, this.U4);
    }

    /* renamed from: c */
    public NTRUEncryptionKeyGenerationParameters clone() {
        if (this.T4 == 0) {
            return new NTRUEncryptionKeyGenerationParameters(this.p1, this.a2, this.p2, this.K4, this.H4, this.M4, this.N4, this.O4, this.P4, this.Q4, this.R4, this.S4, this.U4);
        }
        int i = this.p1;
        int i2 = this.a2;
        int i3 = this.p3;
        int i4 = this.p4;
        int i5 = this.z4;
        int i6 = this.K4;
        int i7 = this.H4;
        int i8 = this.M4;
        int i9 = this.N4;
        int i10 = this.O4;
        boolean z2 = this.P4;
        byte[] bArr = this.Q4;
        return new NTRUEncryptionKeyGenerationParameters(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, z2, bArr, this.R4, this.S4, this.U4);
    }

    public int hashCode() {
        int i = 1231;
        int result = ((((((((((((((((((((((((((((((((1 * 31) + this.p1) * 31) + this.I4) * 31) + this.J4) * 31) + this.M4) * 31) + this.H4) * 31) + this.p2) * 31) + this.p3) * 31) + this.p4) * 31) + this.z4) * 31) + this.E4) * 31) + this.K4) * 31) + this.A4) * 31) + this.B4) * 31) + this.C4) * 31) + this.D4) * 31) + (this.S4 ? 1231 : 1237)) * 31;
        Digest digest = this.U4;
        int result2 = (((((((((((((((((((result + (digest == null ? 0 : digest.b().hashCode())) * 31) + (this.P4 ? 1231 : 1237)) * 31) + this.F4) * 31) + this.G4) * 31) + this.O4) * 31) + this.N4) * 31) + Arrays.hashCode(this.Q4)) * 31) + this.L4) * 31) + this.T4) * 31) + this.a2) * 31;
        if (!this.R4) {
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
        NTRUEncryptionKeyGenerationParameters other = (NTRUEncryptionKeyGenerationParameters) obj;
        if (this.p1 != other.p1 || this.I4 != other.I4 || this.J4 != other.J4 || this.M4 != other.M4 || this.H4 != other.H4 || this.p2 != other.p2 || this.p3 != other.p3 || this.p4 != other.p4 || this.z4 != other.z4 || this.E4 != other.E4 || this.K4 != other.K4 || this.A4 != other.A4 || this.B4 != other.B4 || this.C4 != other.C4 || this.D4 != other.D4 || this.S4 != other.S4) {
            return false;
        }
        Digest digest = this.U4;
        if (digest == null) {
            if (other.U4 != null) {
                return false;
            }
        } else if (!digest.b().equals(other.U4.b())) {
            return false;
        }
        if (this.P4 == other.P4 && this.F4 == other.F4 && this.G4 == other.G4 && this.O4 == other.O4 && this.N4 == other.N4 && Arrays.equals(this.Q4, other.Q4) && this.L4 == other.L4 && this.T4 == other.T4 && this.a2 == other.a2 && this.R4 == other.R4) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("EncryptionParameters(N=" + this.p1 + " q=" + this.a2);
        if (this.T4 == 0) {
            output.append(" polyType=SIMPLE df=" + this.p2);
        } else {
            output.append(" polyType=PRODUCT df1=" + this.p3 + " df2=" + this.p4 + " df3=" + this.z4);
        }
        output.append(" dm0=" + this.K4 + " db=" + this.H4 + " c=" + this.M4 + " minCallsR=" + this.N4 + " minCallsMask=" + this.O4 + " hashSeed=" + this.P4 + " hashAlg=" + this.U4 + " oid=" + Arrays.toString(this.Q4) + " sparse=" + this.R4 + ")");
        return output.toString();
    }
}
