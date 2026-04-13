package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzzp extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzzp zzb;
    private int zzd;
    private String zze = "";
    private String zzf = "";
    private zzzk zzg;
    private zzqs zzh;
    private int zzi = 1;
    private String zzj = "";
    private int zzk;
    private int zzl;
    private String zzm = "";
    private int zzn;
    private byte zzo = 2;

    static {
        zzzp zzzp = new zzzp();
        zzb = zzzp;
        zzafz.zzI(zzzp.class, zzzp);
    }

    private zzzp() {
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzo);
            case 2:
                return zzafz.zzF(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0001\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဉ\u0002\u0004ᐉ\u0003\u0005ဌ\u0004\u0006ဈ\u0005\u0007ဌ\u0006\bင\u0007\tဈ\b\nဌ\t", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", zzzm.zza, "zzj", "zzk", zzzo.zza, "zzl", "zzm", "zzn", zzzn.zza});
            case 3:
                return new zzzp();
            case 4:
                return new zzzl((zzya) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzo = b;
                return null;
        }
    }
}
