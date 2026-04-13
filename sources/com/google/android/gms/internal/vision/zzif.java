package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzif {
    int zza;
    int zzb;
    zzig zzc;
    private int zzd;
    private boolean zze;

    static zzif zza(byte[] bArr, int i, int i2, boolean z) {
        zzih zzih = new zzih(bArr, i2);
        try {
            zzih.zzc(i2);
            return zzih;
        } catch (zzjk e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int zza();

    public abstract void zza(int i);

    public abstract double zzb();

    public abstract boolean zzb(int i);

    public abstract float zzc();

    public abstract int zzc(int i);

    public abstract long zzd();

    public abstract void zzd(int i);

    public abstract long zze();

    public abstract int zzf();

    public abstract long zzg();

    public abstract int zzh();

    public abstract boolean zzi();

    public abstract String zzj();

    public abstract String zzk();

    public abstract zzht zzl();

    public abstract int zzm();

    public abstract int zzn();

    public abstract int zzo();

    public abstract long zzp();

    public abstract int zzq();

    public abstract long zzr();

    /* access modifiers changed from: package-private */
    public abstract long zzs();

    public abstract boolean zzt();

    public abstract int zzu();

    private zzif() {
        this.zzb = 100;
        this.zzd = Integer.MAX_VALUE;
        this.zze = false;
    }

    public static int zze(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long zza(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }
}
