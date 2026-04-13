package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzjq extends zzjn implements List, RandomAccess {
    private static final zzko zza = new zzjo(zzke.zza, 0);
    public static final /* synthetic */ int zzd = 0;

    zzjq() {
    }

    static zzjq zzi(Object[] objArr, int i) {
        if (i == 0) {
            return zzke.zza;
        }
        return new zzke(objArr, i);
    }

    public static zzjq zzj(Collection collection) {
        if (collection instanceof zzjn) {
            zzjq zzd2 = ((zzjn) collection).zzd();
            if (!zzd2.zzf()) {
                return zzd2;
            }
            Object[] array = zzd2.toArray();
            return zzi(array, array.length);
        }
        Object[] array2 = collection.toArray();
        int length = array2.length;
        zzka.zza(array2, length);
        return zzi(array2, length);
    }

    public static zzjq zzk(Object[] objArr) {
        if (objArr.length == 0) {
            return zzke.zza;
        }
        Object[] objArr2 = (Object[]) objArr.clone();
        int length = objArr2.length;
        zzka.zza(objArr2, length);
        return zzi(objArr2, length);
    }

    public static zzjq zzl() {
        return zzke.zza;
    }

    public static zzjq zzm(Object obj) {
        Object[] objArr = {obj};
        zzka.zza(objArr, 1);
        return zzi(objArr, 1);
    }

    public static zzjq zzn(Object obj, Object obj2) {
        Object[] objArr = {obj, obj2};
        zzka.zza(objArr, 2);
        return zzi(objArr, 2);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.Collection, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.libraries.places.internal.zzjq zzo(java.util.Comparator r1, java.lang.Iterable r2) {
        /*
            java.lang.Object[] r2 = r2.toArray()
            int r0 = r2.length
            com.google.android.libraries.places.internal.zzka.zza(r2, r0)
            java.util.Arrays.sort(r2, r1)
            com.google.android.libraries.places.internal.zzjq r1 = zzi(r2, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzjq.zzo(java.util.Comparator, java.lang.Iterable):com.google.android.libraries.places.internal.zzjq");
    }

    @Deprecated
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    public final boolean contains(@CheckForNull Object obj) {
        return indexOf(obj) >= 0;
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        if (list instanceof RandomAccess) {
            for (int i = 0; i < size; i++) {
                if (!zziu.zza(get(i), list.get(i))) {
                    return false;
                }
            }
            return true;
        }
        Iterator it = list.iterator();
        for (Object zza2 : this) {
            if (!it.hasNext()) {
                return false;
            }
            if (!zziu.zza(zza2, it.next())) {
                return false;
            }
        }
        return !it.hasNext();
    }

    public final int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public final int indexOf(@CheckForNull Object obj) {
        if (obj == null) {
            return -1;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (obj.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public final /* synthetic */ Iterator iterator() {
        return listIterator(0);
    }

    public final int lastIndexOf(@CheckForNull Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int size = size() - 1; size >= 0; size--) {
            if (obj.equals(get(size))) {
                return size;
            }
        }
        return -1;
    }

    public final /* synthetic */ ListIterator listIterator() {
        return listIterator(0);
    }

    @Deprecated
    public final Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public int zza(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = get(i2);
        }
        return size;
    }

    @Deprecated
    public final zzjq zzd() {
        return this;
    }

    public final zzkn zze() {
        return listIterator(0);
    }

    /* renamed from: zzh */
    public zzjq subList(int i, int i2) {
        zziy.zzi(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return zzke.zza;
        }
        return new zzjp(this, i, i3);
    }

    /* renamed from: zzp */
    public final zzko listIterator(int i) {
        zziy.zzb(i, size(), FirebaseAnalytics.Param.INDEX);
        if (isEmpty()) {
            return zza;
        }
        return new zzjo(this, i);
    }
}
