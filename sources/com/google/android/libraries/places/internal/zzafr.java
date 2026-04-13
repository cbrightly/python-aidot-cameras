package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzafr {
    private static final zzafp zza = new zzafq();
    private static final zzafp zzb;

    static {
        zzafp zzafp;
        try {
            zzafp = (zzafp) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            zzafp = null;
        }
        zzb = zzafp;
    }

    static zzafp zza() {
        zzafp zzafp = zzb;
        if (zzafp != null) {
            return zzafp;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    static zzafp zzb() {
        return zza;
    }
}
