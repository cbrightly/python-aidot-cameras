package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfp extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfp zza;
    private int zzd;
    private int zze;
    private zzgi zzf;
    private zzgi zzg;
    private boolean zzh;

    static {
        zzfp zzfp = new zzfp();
        zza = zzfp;
        zzlb.zzbO(zzfp.class, zzfp);
    }

    private zzfp() {
    }

    public static zzfo zzb() {
        return (zzfo) zza.zzbA();
    }

    static /* synthetic */ void zzf(zzfp zzfp, int i) {
        zzfp.zzd |= 1;
        zzfp.zze = i;
    }

    static /* synthetic */ void zzg(zzfp zzfp, zzgi zzgi) {
        zzgi.getClass();
        zzfp.zzf = zzgi;
        zzfp.zzd |= 2;
    }

    static /* synthetic */ void zzh(zzfp zzfp, zzgi zzgi) {
        zzfp.zzg = zzgi;
        zzfp.zzd |= 4;
    }

    static /* synthetic */ void zzi(zzfp zzfp, boolean z) {
        zzfp.zzd |= 8;
        zzfp.zzh = z;
    }

    public final int zza() {
        return this.zze;
    }

    public final zzgi zzd() {
        zzgi zzgi = this.zzf;
        return zzgi == null ? zzgi.zzg() : zzgi;
    }

    public final zzgi zze() {
        zzgi zzgi = this.zzg;
        return zzgi == null ? zzgi.zzg() : zzgi;
    }

    public final boolean zzj() {
        return this.zzh;
    }

    public final boolean zzk() {
        return (this.zzd & 1) != 0;
    }

    public final boolean zzm() {
        return (this.zzd & 8) != 0;
    }

    public final boolean zzn() {
        return (this.zzd & 4) != 0;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh"});
            case 3:
                return new zzfp();
            case 4:
                return new zzfo((zzfk) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
