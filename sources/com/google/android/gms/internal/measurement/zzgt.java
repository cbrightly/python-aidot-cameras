package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgt extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzgt zza;
    private int zzd;
    private zzli zze = zzlb.zzbH();
    private zzgp zzf;

    static {
        zzgt zzgt = new zzgt();
        zza = zzgt;
        zzlb.zzbO(zzgt.class, zzgt);
    }

    private zzgt() {
    }

    public final zzgp zza() {
        zzgp zzgp = this.zzf;
        return zzgp == null ? zzgp.zzc() : zzgp;
    }

    public final List zzc() {
        return this.zze;
    }

    /* access modifiers changed from: protected */
    public final Object zzl(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzlb.zzbL(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဉ\u0000", new Object[]{"zzd", "zze", zzgy.class, "zzf"});
            case 3:
                return new zzgt();
            case 4:
                return new zzgs((zzgn) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
