package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzzf extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzzf zzb;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private zzyt zzj;
    private zzym zzk;
    private zzyh zzl;
    private zzacn zzm;
    private zzyo zzn;
    private zzyr zzo;
    private zzacp zzp;
    private zzacx zzq;
    private zzact zzr;
    private int zzs;

    static {
        zzzf zzzf = new zzzf();
        zzb = zzzf;
        zzafz.zzI(zzzf.class, zzzf);
    }

    private zzzf() {
    }

    public static zzza zza() {
        return (zzza) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzzf zzzf, int i) {
        zzzf.zzd |= 4;
        zzzf.zzg = i;
    }

    static /* synthetic */ void zze(zzzf zzzf, zzyt zzyt) {
        zzyt.getClass();
        zzzf.zzj = zzyt;
        zzzf.zzd |= 32;
    }

    static /* synthetic */ void zzf(zzzf zzzf, zzyh zzyh) {
        zzyh.getClass();
        zzzf.zzl = zzyh;
        zzzf.zzd |= 128;
    }

    static /* synthetic */ void zzg(zzzf zzzf, zzyo zzyo) {
        zzyo.getClass();
        zzzf.zzn = zzyo;
        zzzf.zzd |= 512;
    }

    static /* synthetic */ void zzh(zzzf zzzf, int i) {
        zzzf.zze = i - 1;
        zzzf.zzd |= 1;
    }

    static /* synthetic */ void zzi(zzzf zzzf, int i) {
        zzzf.zzf = i - 1;
        zzzf.zzd |= 2;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u000f\u0000\u0001\u0001\u000f\u000f\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003င\u0002\u0004ဌ\u0003\u0005ဌ\u0004\u0006ဉ\u0005\u0007ဉ\u0006\bဉ\u0007\tဉ\b\nဉ\t\u000bဉ\n\fဉ\u000b\rဉ\f\u000eဉ\r\u000fဌ\u000e", new Object[]{"zzd", "zze", zzzc.zza, "zzf", zzze.zza, "zzg", "zzh", zzzb.zza, "zzi", zzyz.zza, "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", zzzd.zza});
            case 3:
                return new zzzf();
            case 4:
                return new zzza((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
