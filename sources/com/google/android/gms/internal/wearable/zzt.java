package com.google.android.gms.internal.wearable;

import java.io.IOException;

public abstract class zzt {
    protected volatile int zzhl = -1;

    public abstract zzt zza(zzk zzk);

    public final int zzx() {
        int zzg = zzg();
        this.zzhl = zzg;
        return zzg;
    }

    /* access modifiers changed from: protected */
    public int zzg() {
        return 0;
    }

    public void zza(zzl zzl) {
    }

    public static final byte[] zzb(zzt zzt) {
        int zzx = zzt.zzx();
        byte[] bArr = new byte[zzx];
        try {
            zzl zzb = zzl.zzb(bArr, 0, zzx);
            zzt.zza(zzb);
            zzb.zzr();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends zzt> T zza(T t, byte[] bArr, int i, int i2) {
        try {
            zzk zza = zzk.zza(bArr, 0, i2);
            t.zza(zza);
            zza.zzc(0);
            return t;
        } catch (zzs e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public String toString() {
        return zzu.zzc(this);
    }

    /* renamed from: zzs */
    public zzt clone() {
        return (zzt) super.clone();
    }
}
