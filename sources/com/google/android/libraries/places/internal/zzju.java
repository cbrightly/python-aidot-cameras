package com.google.android.libraries.places.internal;

import java.util.Set;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzju extends zzjn implements Set {
    @CheckForNull
    private transient zzjq zza;

    zzju() {
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this || obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size() && containsAll(set)) {
                    return true;
                }
            } catch (ClassCastException | NullPointerException e) {
            }
        }
        return false;
    }

    public final int hashCode() {
        return zzkk.zza(this);
    }

    public zzjq zzd() {
        zzjq zzjq = this.zza;
        if (zzjq != null) {
            return zzjq;
        }
        zzjq zzh = zzh();
        this.zza = zzh;
        return zzh;
    }

    /* renamed from: zze */
    public abstract zzkn iterator();

    /* access modifiers changed from: package-private */
    public zzjq zzh() {
        Object[] array = toArray();
        int i = zzjq.zzd;
        return zzjq.zzi(array, array.length);
    }
}
