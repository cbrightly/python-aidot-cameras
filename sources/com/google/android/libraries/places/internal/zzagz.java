package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzagz implements zzaht {
    private static final zzahf zza = new zzagx();
    private final zzahf zzb;

    public zzagz() {
        zzahf zzahf;
        zzahf[] zzahfArr = new zzahf[2];
        zzahfArr[0] = zzafv.zza();
        try {
            zzahf = (zzahf) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            zzahf = zza;
        }
        zzahfArr[1] = zzahf;
        zzagy zzagy = new zzagy(zzahfArr);
        byte[] bArr = zzagi.zzd;
        this.zzb = zzagy;
    }

    private static boolean zzb(zzahe zzahe) {
        return zzahe.zzc() == 1;
    }

    public final zzahs zza(Class cls) {
        Class<zzafz> cls2 = zzafz.class;
        zzahu.zzC(cls);
        zzahe zzb2 = this.zzb.zzb(cls);
        if (zzb2.zzb()) {
            if (cls2.isAssignableFrom(cls)) {
                return zzahl.zzi(zzahu.zzA(), zzafr.zzb(), zzb2.zza());
            }
            return zzahl.zzi(zzahu.zzy(), zzafr.zza(), zzb2.zza());
        } else if (cls2.isAssignableFrom(cls)) {
            if (zzb(zzb2)) {
                return zzahk.zzi(cls, zzb2, zzahn.zzb(), zzagv.zzd(), zzahu.zzA(), zzafr.zzb(), zzahd.zzb());
            }
            return zzahk.zzi(cls, zzb2, zzahn.zzb(), zzagv.zzd(), zzahu.zzA(), (zzafp) null, zzahd.zzb());
        } else if (zzb(zzb2)) {
            return zzahk.zzi(cls, zzb2, zzahn.zza(), zzagv.zzc(), zzahu.zzy(), zzafr.zza(), zzahd.zza());
        } else {
            return zzahk.zzi(cls, zzb2, zzahn.zza(), zzagv.zzc(), zzahu.zzz(), (zzafp) null, zzahd.zza());
        }
    }
}
