package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzls extends zzlw {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzls() {
        super((zzlv) null);
    }

    /* synthetic */ zzls(zzlr zzlr) {
        super((zzlv) null);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Object obj, long j) {
        Object obj2;
        List list = (List) zznu.zzf(obj, j);
        if (list instanceof zzlq) {
            obj2 = ((zzlq) list).zze();
        } else if (!zza.isAssignableFrom(list.getClass())) {
            if (!(list instanceof zzmp) || !(list instanceof zzli)) {
                obj2 = Collections.unmodifiableList(list);
            } else {
                zzli zzli = (zzli) list;
                if (zzli.zzc()) {
                    zzli.zzb();
                    return;
                }
                return;
            }
        } else {
            return;
        }
        zznu.zzs(obj, j, obj2);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, Object obj2, long j) {
        List list = (List) zznu.zzf(obj2, j);
        int size = list.size();
        List list2 = (List) zznu.zzf(obj, j);
        if (list2.isEmpty()) {
            if (list2 instanceof zzlq) {
                list2 = new zzlp(size);
            } else if (!(list2 instanceof zzmp) || !(list2 instanceof zzli)) {
                list2 = new ArrayList(size);
            } else {
                list2 = ((zzli) list2).zzd(size);
            }
            zznu.zzs(obj, j, list2);
        } else if (zza.isAssignableFrom(list2.getClass())) {
            ArrayList arrayList = new ArrayList(list2.size() + size);
            arrayList.addAll(list2);
            zznu.zzs(obj, j, arrayList);
            list2 = arrayList;
        } else if (list2 instanceof zznp) {
            zzlp zzlp = new zzlp(list2.size() + size);
            zzlp.addAll(zzlp.size(), (zznp) list2);
            zznu.zzs(obj, j, zzlp);
            list2 = zzlp;
        } else if ((list2 instanceof zzmp) && (list2 instanceof zzli)) {
            zzli zzli = (zzli) list2;
            if (!zzli.zzc()) {
                list2 = zzli.zzd(list2.size() + size);
                zznu.zzs(obj, j, list2);
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
        zznu.zzs(obj, j, list);
    }
}
