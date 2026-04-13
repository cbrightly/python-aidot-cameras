package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfh extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfh zza;
    private int zzd;
    private String zze = "";
    private String zzf = "";

    static {
        zzfh zzfh = new zzfh();
        zza = zzfh;
        zzlb.zzbO(zzfh.class, zzfh);
    }

    private zzfh() {
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzd", "zze", "zzf"});
            case 3:
                return new zzfh();
            case 4:
                return new zzfg((zzez) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
