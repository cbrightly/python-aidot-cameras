package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzme {
    private static final zzmd zza;
    private static final zzmd zzb = new zzmd();

    static {
        zzmd zzmd;
        try {
            zzmd = (zzmd) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            zzmd = null;
        }
        zza = zzmd;
    }

    static zzmd zza() {
        return zza;
    }

    static zzmd zzb() {
        return zzb;
    }
}
