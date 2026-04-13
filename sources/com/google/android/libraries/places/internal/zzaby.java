package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaby extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzaby zzb;
    private int zzd;
    private int zze;
    private String zzf = "";
    private String zzg = "";
    private zzzk zzh;
    private zzach zzi;
    private zzaav zzj;
    private zzzu zzk;
    private zzaat zzl;
    private zzzw zzm;
    private zzaar zzn;
    private zzacj zzo;
    private zzacj zzp;
    private zzaax zzq;
    private zzaag zzr;
    private zzaca zzs;
    private zzacc zzt;
    private zzabn zzu;
    private zzabd zzv;
    private zzace zzw;
    private byte zzx = 2;

    static {
        zzaby zzaby = new zzaby();
        zzb = zzaby;
        zzafz.zzI(zzaby.class, zzaby);
    }

    private zzaby() {
    }

    public static zzabw zza() {
        return (zzabw) zzb.zzw();
    }

    static /* synthetic */ void zzd(zzaby zzaby, String str) {
        str.getClass();
        zzaby.zzd |= 2;
        zzaby.zzf = str;
    }

    static /* synthetic */ void zze(zzaby zzaby, String str) {
        str.getClass();
        zzaby.zzd |= 4;
        zzaby.zzg = str;
    }

    static /* synthetic */ void zzf(zzaby zzaby, zzaat zzaat) {
        zzaat.getClass();
        zzaby.zzl = zzaat;
        zzaby.zzd |= 128;
    }

    static /* synthetic */ void zzg(zzaby zzaby, zzzw zzzw) {
        zzzw.getClass();
        zzaby.zzm = zzzw;
        zzaby.zzd |= 256;
    }

    static /* synthetic */ void zzh(zzaby zzaby, int i) {
        zzaby.zze = i - 1;
        zzaby.zzd |= 1;
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        byte b = 1;
        switch (i - 1) {
            case 0:
                return Byte.valueOf(this.zzx);
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0013\u0000\u0001\u0001\u0013\u0013\u0000\u0000\u0004\u0001ဌ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဉ\u0003\u0005ᐉ\u0004\u0006ᐉ\u0005\u0007ᐉ\u0006\bဉ\u0007\tᐉ\b\nဉ\t\u000bဉ\u000b\fဉ\n\rဉ\f\u000eဉ\r\u000fဉ\u000e\u0010ဉ\u000f\u0011ဉ\u0010\u0012ဉ\u0011\u0013ဉ\u0012", new Object[]{"zzd", "zze", zzabx.zza, "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzp", "zzo", "zzq", "zzr", "zzs", "zzt", "zzu", "zzv", "zzw"});
            case 3:
                return new zzaby();
            case 4:
                return new zzabw((zzya) null);
            case 5:
                return zzb;
            default:
                if (obj == null) {
                    b = 0;
                }
                this.zzx = b;
                return null;
        }
    }
}
