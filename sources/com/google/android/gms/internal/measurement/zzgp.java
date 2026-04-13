package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgp extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzgp zza;
    private zzli zzd = zzlb.zzbH();

    static {
        zzgp zzgp = new zzgp();
        zza = zzgp;
        zzlb.zzbO(zzgp.class, zzgp);
    }

    private zzgp() {
    }

    public static zzgp zzc() {
        return zza;
    }

    public final int zza() {
        return this.zzd.size();
    }

    public final List zzd() {
        return this.zzd;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzd", zzgr.class});
            case 3:
                return new zzgp();
            case 4:
                return new zzgo((zzgn) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
