package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzyo extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzyo zzb;
    private int zzd;
    private int zze;

    static {
        zzyo zzyo = new zzyo();
        zzb = zzyo;
        zzafz.zzI(zzyo.class, zzyo);
    }

    private zzyo() {
    }

    public static zzyn zza() {
        return (zzyn) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzyo zzyo, int i) {
        zzyo.zzd |= 1;
        zzyo.zze = i;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zzd", "zze"});
            case 3:
                return new zzyo();
            case 4:
                return new zzyn((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
