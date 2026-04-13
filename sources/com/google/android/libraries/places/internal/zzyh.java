package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzyh extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzyh zzb;
    private int zzd;
    private int zze;

    static {
        zzyh zzyh = new zzyh();
        zzb = zzyh;
        zzafz.zzI(zzyh.class, zzyh);
    }

    private zzyh() {
    }

    public static zzyg zza() {
        return (zzyg) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzyh zzyh, int i) {
        zzyh.zzd |= 1;
        zzyh.zze = i;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zzd", "zze"});
            case 3:
                return new zzyh();
            case 4:
                return new zzyg((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
