package com.google.android.libraries.places.internal;

import java.io.Serializable;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzjl implements Comparable, Serializable {
    final Comparable zza;

    zzjl(Comparable comparable) {
        this.zza = comparable;
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzjl) {
            try {
                if (compareTo((zzjl) obj) == 0) {
                    return true;
                }
                return false;
            } catch (ClassCastException e) {
            }
        }
        return false;
    }

    public abstract int hashCode();

    /* renamed from: zza */
    public int compareTo(zzjl zzjl) {
        if (zzjl != zzjj.zzb) {
            if (zzjl == zzjh.zzb) {
                return -1;
            }
            Comparable comparable = this.zza;
            Comparable comparable2 = zzjl.zza;
            int i = zzkc.zzc;
            int compareTo = comparable.compareTo(comparable2);
            if (compareTo != 0) {
                return compareTo;
            }
            boolean z = this instanceof zzji;
            if (z == (zzjl instanceof zzji)) {
                return 0;
            }
            if (!z) {
                return -1;
            }
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public abstract void zzc(StringBuilder sb);

    /* access modifiers changed from: package-private */
    public abstract void zzd(StringBuilder sb);

    /* access modifiers changed from: package-private */
    public abstract boolean zze(Comparable comparable);
}
