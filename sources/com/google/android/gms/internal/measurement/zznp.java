package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

@Deprecated
/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zznp extends AbstractList implements RandomAccess, zzlq {
    /* access modifiers changed from: private */
    public final zzlq zza;

    public zznp(zzlq zzlq) {
        this.zza = zzlq;
    }

    public final /* bridge */ /* synthetic */ Object get(int i) {
        return ((zzlp) this.zza).get(i);
    }

    public final Iterator iterator() {
        return new zzno(this);
    }

    public final ListIterator listIterator(int i) {
        return new zznn(this, i);
    }

    public final int size() {
        return this.zza.size();
    }

    public final zzlq zze() {
        return this;
    }

    public final Object zzf(int i) {
        return this.zza.zzf(i);
    }

    public final List zzh() {
        return this.zza.zzh();
    }

    public final void zzi(zzka zzka) {
        throw new UnsupportedOperationException();
    }
}
