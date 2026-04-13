package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzph extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzph zzb;
    private int zzd;
    private int zze = 1;
    private zzpj zzf;
    private zzpl zzg;
    private zzaej zzh;
    private zzpn zzi;
    private byte zzj = 2;

    static {
        zzph zzph = new zzph();
        zzb = zzph;
        zzafz.zzI(zzph.class, zzph);
    }

    private zzph() {
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzj);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ᐉ\u0003\u0005ဉ\u0004", new Object[]{"zzd", "zze", zzpg.zza, "zzf", "zzg", "zzh", "zzi"});
            case 3:
                return new zzph();
            case 4:
                return new zzpf((zzpe) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzj = b;
                return null;
        }
    }
}
