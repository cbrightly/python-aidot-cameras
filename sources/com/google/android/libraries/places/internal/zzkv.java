package com.google.android.libraries.places.internal;

import com.meituan.robust.Constants;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzkv {
    private final String zza;
    private final Class zzb;
    private final boolean zzc;

    protected zzkv(String str, Class cls, boolean z) {
        this(str, cls, z, true);
    }

    public static zzkv zza(String str, Class cls) {
        return new zzkv(str, cls, false, false);
    }

    public final String toString() {
        String name = getClass().getName();
        String str = this.zza;
        String name2 = this.zzb.getName();
        return name + "/" + str + Constants.ARRAY_TYPE + name2 + "]";
    }

    public final boolean zzb() {
        return this.zzc;
    }

    private zzkv(String str, Class cls, boolean z, boolean z2) {
        zzms.zzb(str);
        this.zza = str;
        this.zzb = cls;
        this.zzc = z;
        System.identityHashCode(this);
        for (int i = 0; i < 5; i++) {
        }
    }
}
