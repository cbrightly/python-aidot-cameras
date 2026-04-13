package com.google.android.libraries.places.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzago extends zzaes implements RandomAccess, zzagp {
    @Deprecated
    public static final zzagp zza;
    private static final zzago zzb;
    private final List zzc;

    static {
        zzago zzago = new zzago(false);
        zzb = zzago;
        zza = zzago;
    }

    public zzago() {
        this(10);
    }

    private static String zzi(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzafe) {
            return ((zzafe) obj).zzl(zzagi.zzb);
        }
        return zzagi.zzd((byte[]) obj);
    }

    public final /* bridge */ /* synthetic */ void add(int i, Object obj) {
        zza();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    public final boolean addAll(int i, Collection collection) {
        zza();
        if (collection instanceof zzagp) {
            collection = ((zzagp) collection).zzh();
        }
        boolean addAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zza();
        this.zzc.clear();
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zza();
        Object remove = this.zzc.remove(i);
        this.modCount++;
        return zzi(remove);
    }

    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        zza();
        return zzi(this.zzc.set(i, (String) obj));
    }

    public final int size() {
        return this.zzc.size();
    }

    public final zzagp zzd() {
        return zzc() ? new zzaio(this) : this;
    }

    public final Object zze(int i) {
        return this.zzc.get(i);
    }

    public final /* bridge */ /* synthetic */ zzagh zzf(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzc);
            return new zzago(arrayList);
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: zzg */
    public final String get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzafe) {
            zzafe zzafe = (zzafe) obj;
            String zzl = zzafe.zzl(zzagi.zzb);
            if (zzafe.zzi()) {
                this.zzc.set(i, zzl);
            }
            return zzl;
        }
        byte[] bArr = (byte[]) obj;
        String zzd = zzagi.zzd(bArr);
        if (zzaix.zzd(bArr)) {
            this.zzc.set(i, zzd);
        }
        return zzd;
    }

    public final List zzh() {
        return Collections.unmodifiableList(this.zzc);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzago(int i) {
        super(true);
        ArrayList arrayList = new ArrayList(i);
        this.zzc = arrayList;
    }

    private zzago(ArrayList arrayList) {
        super(true);
        this.zzc = arrayList;
    }

    private zzago(boolean z) {
        super(false);
        this.zzc = Collections.emptyList();
    }

    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }
}
