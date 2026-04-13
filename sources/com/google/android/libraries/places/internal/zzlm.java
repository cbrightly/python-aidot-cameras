package com.google.android.libraries.places.internal;

import java.util.Iterator;
import java.util.Set;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzlm {
    private static final zzli zza = new zzlk();
    private static final zzlh zzb = new zzll();

    public static zzle zza(Set set) {
        zzle zzle = new zzle(zza, (zzld) null);
        zzle.zza(zzb);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            zzle.zzg((zzkv) it.next());
        }
        return zzle;
    }
}
