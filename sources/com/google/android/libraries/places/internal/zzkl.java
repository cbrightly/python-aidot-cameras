package com.google.android.libraries.places.internal;

import java.util.Iterator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzkl implements Iterator {
    final Iterator zzb;

    zzkl(Iterator it) {
        if (it != null) {
            this.zzb = it;
            return;
        }
        throw null;
    }

    public final boolean hasNext() {
        return this.zzb.hasNext();
    }

    public final Object next() {
        return zza(this.zzb.next());
    }

    public final void remove() {
        this.zzb.remove();
    }

    /* access modifiers changed from: package-private */
    public abstract Object zza(Object obj);
}
