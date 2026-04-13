package com.google.android.libraries.places.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzagr extends zzagv {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzagr() {
        super((zzagu) null);
    }

    /* synthetic */ zzagr(zzagq zzagq) {
        super((zzagu) null);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Object obj, long j) {
        Object obj2;
        List list = (List) zzait.zzf(obj, j);
        if (list instanceof zzagp) {
            obj2 = ((zzagp) list).zzd();
        } else if (!zza.isAssignableFrom(list.getClass())) {
            if (!(list instanceof zzaho) || !(list instanceof zzagh)) {
                obj2 = Collections.unmodifiableList(list);
            } else {
                zzagh zzagh = (zzagh) list;
                if (zzagh.zzc()) {
                    zzagh.zzb();
                    return;
                }
                return;
            }
        } else {
            return;
        }
        zzait.zzs(obj, j, obj2);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, Object obj2, long j) {
        List list = (List) zzait.zzf(obj2, j);
        int size = list.size();
        List list2 = (List) zzait.zzf(obj, j);
        if (list2.isEmpty()) {
            if (list2 instanceof zzagp) {
                list2 = new zzago(size);
            } else if (!(list2 instanceof zzaho) || !(list2 instanceof zzagh)) {
                list2 = new ArrayList(size);
            } else {
                list2 = ((zzagh) list2).zzf(size);
            }
            zzait.zzs(obj, j, list2);
        } else if (zza.isAssignableFrom(list2.getClass())) {
            ArrayList arrayList = new ArrayList(list2.size() + size);
            arrayList.addAll(list2);
            zzait.zzs(obj, j, arrayList);
            list2 = arrayList;
        } else if (list2 instanceof zzaio) {
            zzago zzago = new zzago(list2.size() + size);
            zzago.addAll(zzago.size(), (zzaio) list2);
            zzait.zzs(obj, j, zzago);
            list2 = zzago;
        } else if ((list2 instanceof zzaho) && (list2 instanceof zzagh)) {
            zzagh zzagh = (zzagh) list2;
            if (!zzagh.zzc()) {
                list2 = zzagh.zzf(list2.size() + size);
                zzait.zzs(obj, j, list2);
            }
        }
        int size2 = list2.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            list2.addAll(list);
        }
        if (size2 > 0) {
            list = list2;
        }
        zzait.zzs(obj, j, list);
    }
}
