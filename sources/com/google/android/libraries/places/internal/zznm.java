package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zznm extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zznm zzb;
    private int zzd;
    private String zze = "";
    private int zzf;
    private int zzg;
    private String zzh = "";
    private int zzi;
    private int zzj;
    private int zzk;
    private int zzl;
    private int zzm;

    static {
        zznm zznm = new zznm();
        zzb = zznm;
        zzafz.zzI(zznm.class, zznm);
    }

    private zznm() {
    }

    public static zznh zza() {
        return (zznh) zzb.zzw();
    }

    static /* synthetic */ void zzd(zznm zznm, String str) {
        zznm.zzd |= 1;
        zznm.zze = str;
    }

    static /* synthetic */ void zze(zznm zznm, int i) {
        zznm.zzd |= 2;
        zznm.zzf = i;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003င\u0002\u0004ဈ\u0003\u0005င\u0004\u0006ဌ\u0005\u0007ဌ\u0006\bဌ\u0007\tဌ\b", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzni.zza, "zzk", zznk.zza, "zzl", zznj.zza, "zzm", zznl.zza});
            case 3:
                return new zznm();
            case 4:
                return new zznh((zzng) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
