package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzzw extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzzw zzb;
    private int zzd;
    private String zze = "";
    private zzqs zzf;
    private zzzk zzg;
    private byte zzh = 2;

    static {
        zzzw zzzw = new zzzw();
        zzb = zzzw;
        zzafz.zzI(zzzw.class, zzzw);
    }

    private zzzw() {
    }

    public static zzzv zza() {
        return (zzzv) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzzw zzzw, zzzk zzzk) {
        zzzw.zzg = zzzk;
        zzzw.zzd |= 4;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzh);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001ဈ\u0000\u0002ᐉ\u0001\u0003ဉ\u0002", new Object[]{"zzd", "zze", "zzf", "zzg"});
            case 3:
                return new zzzw();
            case 4:
                return new zzzv((zzya) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzh = b;
                return null;
        }
    }
}
