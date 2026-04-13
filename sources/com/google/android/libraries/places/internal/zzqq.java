package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzqq extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzqq zzb;
    private int zzd;
    private int zze;
    private int zzf;
    private byte zzg = 2;

    static {
        zzqq zzqq = new zzqq();
        zzb = zzqq;
        zzafz.zzI(zzqq.class, zzqq);
    }

    private zzqq() {
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzg);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔆ\u0000\u0002ᔆ\u0001", new Object[]{"zzd", "zze", "zzf"});
            case 3:
                return new zzqq();
            case 4:
                return new zzqp((zzqo) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzg = b;
                return null;
        }
    }
}
