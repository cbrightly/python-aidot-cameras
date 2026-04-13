package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahd {
    private static final zzahc zza;
    private static final zzahc zzb = new zzahc();

    static {
        zzahc zzahc;
        try {
            zzahc = (zzahc) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            zzahc = null;
        }
        zza = zzahc;
    }

    static zzahc zza() {
        return zza;
    }

    static zzahc zzb() {
        return zzb;
    }
}
