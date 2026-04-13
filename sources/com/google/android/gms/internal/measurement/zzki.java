package com.google.android.gms.internal.measurement;

import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzki extends zzjq {
    public static final /* synthetic */ int zzb = 0;
    private static final Logger zzc = Logger.getLogger(zzki.class.getName());
    /* access modifiers changed from: private */
    public static final boolean zzd = zznu.zzx();
    zzkj zza;

    private zzki() {
    }

    /* synthetic */ zzki(zzkh zzkh) {
    }

    @Deprecated
    static int zzt(int i, zzmi zzmi, zzmt zzmt) {
        int zzbu = ((zzjk) zzmi).zzbu(zzmt);
        int zzx = zzx(i << 3);
        return zzx + zzx + zzbu;
    }

    public static int zzu(int i) {
        if (i >= 0) {
            return zzx(i);
        }
        return 10;
    }

    static int zzv(zzmi zzmi, zzmt zzmt) {
        int zzbu = ((zzjk) zzmi).zzbu(zzmt);
        return zzx(zzbu) + zzbu;
    }

    public static int zzw(String str) {
        int i;
        try {
            i = zznz.zzc(str);
        } catch (zzny e) {
            i = str.getBytes(zzlj.zzb).length;
        }
        return zzx(i) + i;
    }

    public static int zzx(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzy(long j) {
        int i;
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            j >>>= 14;
            i += 2;
        }
        return (j & -16384) != 0 ? i + 1 : i;
    }

    public static zzki zzz(byte[] bArr, int i, int i2) {
        return new zzkf(bArr, 0, i2);
    }

    public final void zzA() {
        if (zza() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzB(String str, zzny zzny) {
        zzc.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzny);
        byte[] bytes = str.getBytes(zzlj.zzb);
        try {
            int length = bytes.length;
            zzq(length);
            zzl(bytes, 0, length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzkg(e);
        }
    }

    public abstract int zza();

    public abstract void zzb(byte b);

    public abstract void zzd(int i, boolean z);

    public abstract void zze(int i, zzka zzka);

    public abstract void zzf(int i, int i2);

    public abstract void zzg(int i);

    public abstract void zzh(int i, long j);

    public abstract void zzi(long j);

    public abstract void zzj(int i, int i2);

    public abstract void zzk(int i);

    public abstract void zzl(byte[] bArr, int i, int i2);

    public abstract void zzm(int i, String str);

    public abstract void zzo(int i, int i2);

    public abstract void zzp(int i, int i2);

    public abstract void zzq(int i);

    public abstract void zzr(int i, long j);

    public abstract void zzs(long j);
}
