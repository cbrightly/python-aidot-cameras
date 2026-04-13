package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzzu extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzzu zzb;
    private int zzd;
    private String zze = "";
    private zzqq zzf;
    private String zzg = "";
    private zzagh zzh = zzafz.zzB();
    private String zzi = "";
    private String zzj = "";
    private byte zzk = 2;

    static {
        zzzu zzzu = new zzzu();
        zzb = zzzu;
        zzafz.zzI(zzzu.class, zzzu);
    }

    private zzzu() {
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzk);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0001\u0001ဈ\u0000\u0002ᐉ\u0001\u0003ဈ\u0002\u0004\u001a\u0005ဈ\u0003\u0006ဈ\u0004", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
            case 3:
                return new zzzu();
            case 4:
                return new zzzt((zzya) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzk = b;
                return null;
        }
    }
}
