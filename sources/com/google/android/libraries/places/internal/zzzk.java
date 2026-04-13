package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzzk extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzzk zzb;
    private int zzd;
    private int zze;
    private zzagh zzf = zzafz.zzB();
    private String zzg = "";
    private String zzh = "";
    private boolean zzi;
    private zzagh zzj = zzafz.zzB();

    static {
        zzzk zzzk = new zzzk();
        zzb = zzzk;
        zzafz.zzI(zzzk.class, zzzk);
    }

    private zzzk() {
    }

    public static zzzj zza() {
        return (zzzj) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzzk zzzk, String str) {
        zzagh zzagh = zzzk.zzf;
        if (!zzagh.zzc()) {
            zzzk.zzf = zzafz.zzC(zzagh);
        }
        zzzk.zzf.add(str);
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0002\u0000\u0001င\u0000\u0002\u001a\u0003ဈ\u0001\u0004ဈ\u0002\u0005ဇ\u0003\u0006\u001a", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
            case 3:
                return new zzzk();
            case 4:
                return new zzzj((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
