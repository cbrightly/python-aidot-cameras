package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgb extends zzlb implements zzmj {
    /* access modifiers changed from: private */
    public static final zzgb zza;
    private zzli zzd = zzlb.zzbH();

    static {
        zzgb zzgb = new zzgb();
        zza = zzgb;
        zzlb.zzbO(zzgb.class, zzgb);
    }

    private zzgb() {
    }

    public static zzga zza() {
        return (zzga) zza.zzbA();
    }

    static /* synthetic */ void zze(zzgb zzgb, zzgd zzgd) {
        zzgd.getClass();
        zzli zzli = zzgb.zzd;
        if (!zzli.zzc()) {
            zzgb.zzd = zzlb.zzbI(zzli);
        }
        zzgb.zzd.add(zzgd);
    }

    public final zzgd zzc(int i) {
        return (zzgd) this.zzd.get(0);
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
                return zzlb.zzbL(zza, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzd", zzgd.class});
            case 3:
                return new zzgb();
            case 4:
                return new zzga((zzfk) null);
            case 5:
                return zza;
            default:
                return null;
        }
    }
}
