package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzet extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzet zza;
    private int zzd;
    private int zze;
    private String zzf = "";
    private zzem zzg;
    private boolean zzh;
    private boolean zzi;
    private boolean zzj;

    static {
        zzet zzet = new zzet();
        zza = zzet;
        zzlb.zzbO(zzet.class, zzet);
    }

    private zzet() {
    }

    public static zzes zzc() {
        return (zzes) zza.zzbA();
    }

    static /* synthetic */ void zzf(zzet zzet, String str) {
        zzet.zzd |= 2;
        zzet.zzf = str;
    }

    public final int zza() {
        return this.zze;
    }

    public final zzem zzb() {
        zzem zzem = this.zzg;
        return zzem == null ? zzem.zzb() : zzem;
    }

    public final String zze() {
        return this.zzf;
    }

    public final boolean zzg() {
        return this.zzh;
    }

    public final boolean zzh() {
        return this.zzi;
    }

    public final boolean zzi() {
        return this.zzj;
    }

    public final boolean zzj() {
        return (this.zzd & 1) != 0;
    }

    public final boolean zzk() {
        return (this.zzd & 32) != 0;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဉ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
            case 3:
                return new zzet();
            case 4:
                return new zzes((zzeg) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
