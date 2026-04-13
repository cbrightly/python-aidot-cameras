package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaba extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzaba zzb;
    private int zzd;
    private zzzk zze;
    private int zzf;
    private int zzg;
    private zzabs zzh;

    static {
        zzaba zzaba = new zzaba();
        zzb = zzaba;
        zzafz.zzI(zzaba.class, zzaba);
    }

    private zzaba() {
    }

    public static zzaay zza() {
        return (zzaay) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzaba zzaba, int i) {
        zzaba.zzd |= 4;
        zzaba.zzg = i;
    }

    static /* synthetic */ void zze(zzaba zzaba, zzabs zzabs) {
        zzabs.getClass();
        zzaba.zzh = zzabs;
        zzaba.zzd |= 8;
    }

    static /* synthetic */ void zzf(zzaba zzaba, int i) {
        zzaba.zzf = i - 1;
        zzaba.zzd |= 2;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003င\u0002\u0004ဉ\u0003", new Object[]{"zzd", "zze", "zzf", zzaaz.zza, "zzg", "zzh"});
            case 3:
                return new zzaba();
            case 4:
                return new zzaay((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
