package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfb extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfb zza;
    private int zzd;
    private String zze = "";
    private zzli zzf = zzlb.zzbH();
    private boolean zzg;

    static {
        zzfb zzfb = new zzfb();
        zza = zzfb;
        zzlb.zzbO(zzfb.class, zzfb);
    }

    private zzfb() {
    }

    public final String zzb() {
        return this.zze;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b\u0003ဇ\u0001", new Object[]{"zzd", "zze", "zzf", zzfh.class, "zzg"});
            case 3:
                return new zzfb();
            case 4:
                return new zzfa((zzez) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
