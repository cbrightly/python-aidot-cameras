package com.google.android.gms.internal.vision;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzhv implements Comparator<zzht> {
    zzhv() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzht zzht = (zzht) obj;
        zzht zzht2 = (zzht) obj2;
        zzhy zzhy = (zzhy) zzht.iterator();
        zzhy zzhy2 = (zzhy) zzht2.iterator();
        while (zzhy.hasNext() && zzhy2.hasNext()) {
            int compare = Integer.compare(zzht.zzb(zzhy.zza()), zzht.zzb(zzhy2.zza()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzht.zza(), zzht2.zza());
    }
}
