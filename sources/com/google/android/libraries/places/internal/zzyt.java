package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzyt extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzyt zzb;
    private int zzd;
    private int zze;
    private int zzf;

    static {
        zzyt zzyt = new zzyt();
        zzb = zzyt;
        zzafz.zzI(zzyt.class, zzyt);
    }

    private zzyt() {
    }

    public static zzys zza() {
        return (zzys) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzyt zzyt, int i) {
        zzyt.zzd |= 1;
        zzyt.zze = 1;
    }

    static /* synthetic */ void zze(zzyt zzyt, int i) {
        zzyt.zzd |= 2;
        zzyt.zzf = i;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"zzd", "zze", "zzf"});
            case 3:
                return new zzyt();
            case 4:
                return new zzys((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
