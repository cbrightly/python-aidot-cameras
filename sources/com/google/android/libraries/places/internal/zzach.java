package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzach extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzach zzb;
    private int zzd;
    private zzqs zze;
    private int zzf;
    private int zzg;
    private String zzh = "";
    private int zzi;
    private byte zzj = 2;

    static {
        zzach zzach = new zzach();
        zzb = zzach;
        zzafz.zzI(zzach.class, zzach);
    }

    private zzach() {
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzj);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001ᐉ\u0000\u0002င\u0001\u0003င\u0002\u0004ဈ\u0003\u0005ဌ\u0004", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", zzacg.zza});
            case 3:
                return new zzach();
            case 4:
                return new zzacf((zzya) null);
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
