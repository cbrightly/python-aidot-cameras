package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzkq {
    private static final zzko zza = new zzkp();
    private static final zzko zzb;

    static {
        zzko zzko;
        try {
            zzko = (zzko) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            zzko = null;
        }
        zzb = zzko;
    }

    static zzko zza() {
        zzko zzko = zzb;
        if (zzko != null) {
            return zzko;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    static zzko zzb() {
        return zza;
    }
}
