package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzafv implements zzahf {
    private static final zzafv zza = new zzafv();

    private zzafv() {
    }

    public static zzafv zza() {
        return zza;
    }

    public final zzahe zzb(Class cls) {
        Class<zzafz> cls2 = zzafz.class;
        if (cls2.isAssignableFrom(cls)) {
            try {
                return (zzahe) zzafz.zzx(cls.asSubclass(cls2)).zzb(3, (Object) null, (Object) null);
            } catch (Exception e) {
                throw new RuntimeException("Unable to get message info for ".concat(String.valueOf(cls.getName())), e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported message type: ".concat(String.valueOf(cls.getName())));
        }
    }

    public final boolean zzc(Class cls) {
        return zzafz.class.isAssignableFrom(cls);
    }
}
