package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfv extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfv zza;
    private int zzd;
    private String zze = "";
    private long zzf;

    static {
        zzfv zzfv = new zzfv();
        zza = zzfv;
        zzlb.zzbO(zzfv.class, zzfv);
    }

    private zzfv() {
    }

    public static zzfu zza() {
        return (zzfu) zza.zzbA();
    }

    static /* synthetic */ void zzc(zzfv zzfv, String str) {
        str.getClass();
        zzfv.zzd |= 1;
        zzfv.zze = str;
    }

    static /* synthetic */ void zzd(zzfv zzfv, long j) {
        zzfv.zzd |= 2;
        zzfv.zzf = j;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001", new Object[]{"zzd", "zze", "zzf"});
            case 3:
                return new zzfv();
            case 4:
                return new zzfu((zzfk) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
