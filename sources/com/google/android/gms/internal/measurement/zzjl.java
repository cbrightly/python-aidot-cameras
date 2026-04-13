package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzjl extends AbstractList implements zzli {
    private boolean zza;

    zzjl(boolean z) {
        this.zza = z;
    }

    public void add(int i, Object obj) {
        zzbW();
        super.add(i, obj);
    }

    public boolean addAll(int i, Collection collection) {
        zzbW();
        return super.addAll(i, collection);
    }

    public void clear() {
        zzbW();
        super.clear();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        if (!(obj instanceof RandomAccess)) {
            return super.equals(obj);
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public Object remove(int i) {
        zzbW();
        return super.remove(i);
    }

    public final boolean removeAll(Collection collection) {
        zzbW();
        return super.removeAll(collection);
    }

    public final boolean retainAll(Collection collection) {
        zzbW();
        return super.retainAll(collection);
    }

    public Object set(int i, Object obj) {
        zzbW();
        return super.set(i, obj);
    }

    public final void zzb() {
        if (this.zza) {
            this.zza = false;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzbW() {
        if (!this.zza) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean zzc() {
        return this.zza;
    }

    public boolean add(Object obj) {
        zzbW();
        return super.add(obj);
    }

    public boolean addAll(Collection collection) {
        zzbW();
        return super.addAll(collection);
    }

    public final boolean remove(Object obj) {
        zzbW();
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        remove(indexOf);
        return true;
    }
}
