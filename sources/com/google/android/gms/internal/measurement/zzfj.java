package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfj extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzfj zza;
    private int zzd;
    private String zze = "";
    private String zzf = "";

    static {
        zzfj zzfj = new zzfj();
        zza = zzfj;
        zzlb.zzbO(zzfj.class, zzfj);
    }

    private zzfj() {
    }

    public final String zzb() {
        return this.zze;
    }

    public final String zzc() {
        return this.zzf;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzd", "zze", "zzf"});
            case 3:
                return new zzfj();
            case 4:
                return new zzfi((zzez) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
