package com.google.android.gms.internal.clearcut;

import java.util.Iterator;

public final class zzfc implements Iterator<String> {
    private final /* synthetic */ zzfa zzpe;
    private Iterator<String> zzpf;

    zzfc(zzfa zzfa) {
        this.zzpe = zzfa;
        this.zzpf = zzfa.zzpb.iterator();
    }

    public final boolean hasNext() {
        return this.zzpf.hasNext();
    }

    public final /* synthetic */ Object next() {
        return this.zzpf.next();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
