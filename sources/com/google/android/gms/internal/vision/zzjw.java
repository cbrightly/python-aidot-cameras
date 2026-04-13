package com.google.android.gms.internal.vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzjw extends zzju {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzjw() {
        super();
    }

    /* access modifiers changed from: package-private */
    public final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, long j) {
        Object obj2;
        List list = (List) zzma.zzf(obj, j);
        if (list instanceof zzjv) {
            obj2 = ((zzjv) list).zze();
        } else if (!zza.isAssignableFrom(list.getClass())) {
            if (!(list instanceof zzkw) || !(list instanceof zzjl)) {
                obj2 = Collections.unmodifiableList(list);
            } else {
                zzjl zzjl = (zzjl) list;
                if (zzjl.zza()) {
                    zzjl.zzb();
                    return;
                }
                return;
            }
        } else {
            return;
        }
        zzma.zza(obj, j, obj2);
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> list;
        List<L> zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            if (zzc instanceof zzjv) {
                list = new zzjs(i);
            } else if (!(zzc instanceof zzkw) || !(zzc instanceof zzjl)) {
                list = new ArrayList<>(i);
            } else {
                list = ((zzjl) zzc).zza(i);
            }
            zzma.zza(obj, j, (Object) list);
            return list;
        } else if (zza.isAssignableFrom(zzc.getClass())) {
            ArrayList arrayList = new ArrayList(zzc.size() + i);
            arrayList.addAll(zzc);
            zzma.zza(obj, j, (Object) arrayList);
            return arrayList;
        } else if (zzc instanceof zzlz) {
            zzjs zzjs = new zzjs(zzc.size() + i);
            zzjs.addAll((zzlz) zzc);
            zzma.zza(obj, j, (Object) zzjs);
            return zzjs;
        } else if (!(zzc instanceof zzkw) || !(zzc instanceof zzjl)) {
            return zzc;
        } else {
            zzjl zzjl = (zzjl) zzc;
            if (zzjl.zza()) {
                return zzc;
            }
            zzjl zza2 = zzjl.zza(zzc.size() + i);
            zzma.zza(obj, j, (Object) zza2);
            return zza2;
        }
    }

    /* access modifiers changed from: package-private */
    public final <E> void zza(Object obj, Object obj2, long j) {
        List zzc = zzc(obj2, j);
        List zza2 = zza(obj, j, zzc.size());
        int size = zza2.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza2.addAll(zzc);
        }
        if (size > 0) {
            zzc = zza2;
        }
        zzma.zza(obj, j, (Object) zzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzma.zzf(obj, j);
    }
}
