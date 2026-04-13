package com.google.android.libraries.places.internal;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmo extends AbstractMap {
    /* access modifiers changed from: private */
    public static final Comparator zza = new zzml();
    /* access modifiers changed from: private */
    public final Object[] zzb;
    /* access modifiers changed from: private */
    public final int[] zzc;
    private final Set zzd = new zzmn(this, -1);
    private Integer zze = null;
    private String zzf = null;

    zzmo(List list) {
        Iterator it = list.iterator();
        if (!it.hasNext()) {
            int size = list.size();
            Object[] objArr = new Object[size];
            int[] iArr = new int[1];
            Iterator it2 = list.iterator();
            if (!it2.hasNext()) {
                iArr[0] = 0;
                if (size > 16 && size * 9 > 0) {
                    objArr = Arrays.copyOf(objArr, 0);
                }
                this.zzb = objArr;
                this.zzc = iArr;
                return;
            }
            String unused = ((zzmk) it2.next()).zza;
            throw null;
        }
        String unused2 = ((zzmk) it.next()).zza;
        throw null;
    }

    public final Set entrySet() {
        return this.zzd;
    }

    public final int hashCode() {
        if (this.zze == null) {
            this.zze = Integer.valueOf(super.hashCode());
        }
        return this.zze.intValue();
    }

    public final String toString() {
        if (this.zzf == null) {
            this.zzf = super.toString();
        }
        return this.zzf;
    }
}
