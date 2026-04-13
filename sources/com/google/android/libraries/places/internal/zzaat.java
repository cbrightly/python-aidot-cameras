package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaat extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzaat zzb;
    private int zzd;
    private zzagh zze = zzafz.zzB();
    private int zzf;
    private int zzg;
    private zzabs zzh;

    static {
        zzaat zzaat = new zzaat();
        zzb = zzaat;
        zzafz.zzI(zzaat.class, zzaat);
    }

    private zzaat() {
    }

    public static zzaas zza() {
        return (zzaas) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzaat zzaat, int i) {
        zzaat.zzd |= 2;
        zzaat.zzg = 1;
    }

    static /* synthetic */ void zze(zzaat zzaat, zzabs zzabs) {
        zzabs.getClass();
        zzaat.zzh = zzabs;
        zzaat.zzd |= 4;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u001a\u0002ဌ\u0000\u0003ဋ\u0001\u0004ဉ\u0002", new Object[]{"zzd", "zze", "zzf", zzyp.zza, "zzg", "zzh"});
            case 3:
                return new zzaat();
            case 4:
                return new zzaas((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
