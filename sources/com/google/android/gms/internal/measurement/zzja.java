package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzja extends zziw implements List, RandomAccess {
    private static final zzji zza = new zziy(zzje.zza, 0);
    public static final /* synthetic */ int zzd = 0;

    zzja() {
    }

    static zzja zzg(Object[] objArr, int i) {
        if (i == 0) {
            return zzje.zza;
        }
        return new zzje(objArr, i);
    }

    public static zzja zzh(Object obj, Object obj2) {
        Object[] objArr = {obj, obj2};
        zzjd.zzb(objArr, 2);
        return zzg(objArr, 2);
    }

    public static zzja zzi(Object obj, Object obj2, Object obj3) {
        Object[] objArr = {"auto", "app", "am"};
        zzjd.zzb(objArr, 3);
        return zzg(objArr, 3);
    }

    public static zzja zzj(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        Object[] objArr = {"_e", "_f", "_iap", "_s", "_au", "_ui", "_cd"};
        zzjd.zzb(objArr, 7);
        return zzg(objArr, 7);
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
                if (!zzih.zza(get(i), list.get(i))) {
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
            if (!zzih.zza(zza2, it.next())) {
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

    public final zzjh zzd() {
        return listIterator(0);
    }

    /* renamed from: zzf */
    public zzja subList(int i, int i2) {
        zzij.zzc(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return zzje.zza;
        }
        return new zziz(this, i, i3);
    }

    /* renamed from: zzk */
    public final zzji listIterator(int i) {
        zzij.zzb(i, size(), FirebaseAnalytics.Param.INDEX);
        if (isEmpty()) {
            return zza;
        }
        return new zziy(this, i);
    }
}
