package com.google.android.libraries.places.internal;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzafj extends zzafm {
    private final byte[] zzc;
    private final int zzd;
    private int zze;

    zzafj(byte[] bArr, int i, int i2) {
        super((zzafl) null);
        if (bArr != null) {
            int length = bArr.length;
            if (((length - i2) | i2) >= 0) {
                this.zzc = bArr;
                this.zze = 0;
                this.zzd = i2;
                return;
            }
            throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(length), 0, Integer.valueOf(i2)}));
        }
        throw new NullPointerException("buffer");
    }

    public final int zza() {
        return this.zzd - this.zze;
    }

    public final void zzd(int i, boolean z) {
        zzq(i << 3);
        zzb(z ? (byte) 1 : 0);
    }

    public final void zze(int i, zzafe zzafe) {
        zzq((i << 3) | 2);
        zzq(zzafe.zzd());
        zzafe.zzh(this);
    }

    public final void zzf(int i, int i2) {
        zzq((i << 3) | 5);
        zzg(i2);
    }

    public final void zzg(int i) {
        try {
            byte[] bArr = this.zzc;
            int i2 = this.zze;
            int i3 = i2 + 1;
            this.zze = i3;
            bArr[i2] = (byte) (i & 255);
            int i4 = i3 + 1;
            this.zze = i4;
            bArr[i3] = (byte) ((i >> 8) & 255);
            int i5 = i4 + 1;
            this.zze = i5;
            bArr[i4] = (byte) ((i >> 16) & 255);
            this.zze = i5 + 1;
            bArr[i5] = (byte) ((i >> 24) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
        }
    }

    public final void zzh(int i, long j) {
        zzq((i << 3) | 1);
        zzi(j);
    }

    public final void zzi(long j) {
        try {
            byte[] bArr = this.zzc;
            int i = this.zze;
            int i2 = i + 1;
            this.zze = i2;
            bArr[i] = (byte) (((int) j) & 255);
            int i3 = i2 + 1;
            this.zze = i3;
            bArr[i2] = (byte) (((int) (j >> 8)) & 255);
            int i4 = i3 + 1;
            this.zze = i4;
            bArr[i3] = (byte) (((int) (j >> 16)) & 255);
            int i5 = i4 + 1;
            this.zze = i5;
            bArr[i4] = (byte) (((int) (j >> 24)) & 255);
            int i6 = i5 + 1;
            this.zze = i6;
            bArr[i5] = (byte) (((int) (j >> 32)) & 255);
            int i7 = i6 + 1;
            this.zze = i7;
            bArr[i6] = (byte) (((int) (j >> 40)) & 255);
            int i8 = i7 + 1;
            this.zze = i8;
            bArr[i7] = (byte) (((int) (j >> 48)) & 255);
            this.zze = i8 + 1;
            bArr[i8] = (byte) (((int) (j >> 56)) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
        }
    }

    public final void zzj(int i, int i2) {
        zzq(i << 3);
        zzk(i2);
    }

    public final void zzk(int i) {
        if (i >= 0) {
            zzq(i);
        } else {
            zzs((long) i);
        }
    }

    public final void zzl(byte[] bArr, int i, int i2) {
        zzc(bArr, 0, i2);
    }

    public final void zzm(int i, String str) {
        zzq((i << 3) | 2);
        zzn(str);
    }

    public final void zzn(String str) {
        int i = this.zze;
        try {
            int zzx = zzafm.zzx(str.length() * 3);
            int zzx2 = zzafm.zzx(str.length());
            if (zzx2 == zzx) {
                int i2 = i + zzx2;
                this.zze = i2;
                int zzb = zzaix.zzb(str, this.zzc, i2, this.zzd - i2);
                this.zze = i;
                zzq((zzb - i) - zzx2);
                this.zze = zzb;
                return;
            }
            zzq(zzaix.zzc(str));
            byte[] bArr = this.zzc;
            int i3 = this.zze;
            this.zze = zzaix.zzb(str, bArr, i3, this.zzd - i3);
        } catch (zzaiw e) {
            this.zze = i;
            zzB(str, e);
        } catch (IndexOutOfBoundsException e2) {
            throw new zzafk(e2);
        }
    }

    public final void zzo(int i, int i2) {
        zzq((i << 3) | i2);
    }

    public final void zzp(int i, int i2) {
        zzq(i << 3);
        zzq(i2);
    }

    public final void zzr(int i, long j) {
        zzq(i << 3);
        zzs(j);
    }

    public final void zzb(byte b) {
        try {
            byte[] bArr = this.zzc;
            int i = this.zze;
            this.zze = i + 1;
            bArr[i] = b;
        } catch (IndexOutOfBoundsException e) {
            throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
        }
    }

    public final void zzc(byte[] bArr, int i, int i2) {
        try {
            System.arraycopy(bArr, 0, this.zzc, this.zze, i2);
            this.zze += i2;
        } catch (IndexOutOfBoundsException e) {
            throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(i2)}), e);
        }
    }

    public final void zzq(int i) {
        while ((i & -128) != 0) {
            byte[] bArr = this.zzc;
            int i2 = this.zze;
            this.zze = i2 + 1;
            bArr[i2] = (byte) ((i & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            i >>>= 7;
        }
        try {
            byte[] bArr2 = this.zzc;
            int i3 = this.zze;
            this.zze = i3 + 1;
            bArr2[i3] = (byte) i;
        } catch (IndexOutOfBoundsException e) {
            throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
        }
    }

    public final void zzs(long j) {
        if (!zzafm.zzd || this.zzd - this.zze < 10) {
            while ((j & -128) != 0) {
                byte[] bArr = this.zzc;
                int i = this.zze;
                this.zze = i + 1;
                bArr[i] = (byte) ((((int) j) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
                j >>>= 7;
            }
            try {
                byte[] bArr2 = this.zzc;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr2[i2] = (byte) ((int) j);
            } catch (IndexOutOfBoundsException e) {
                throw new zzafk(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
            }
        } else {
            while ((j & -128) != 0) {
                byte[] bArr3 = this.zzc;
                int i3 = this.zze;
                this.zze = i3 + 1;
                zzait.zzn(bArr3, (long) i3, (byte) ((((int) j) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128));
                j >>>= 7;
            }
            byte[] bArr4 = this.zzc;
            int i4 = this.zze;
            this.zze = i4 + 1;
            zzait.zzn(bArr4, (long) i4, (byte) ((int) j));
        }
    }
}
