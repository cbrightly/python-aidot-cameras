package com.google.android.libraries.places.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzij implements Iterator {
    @CheckForNull
    private Object zza;
    private int zzb = 2;

    protected zzij() {
    }

    public final boolean hasNext() {
        boolean z;
        if (this.zzb != 4) {
            z = true;
        } else {
            z = false;
        }
        zziy.zzj(z);
        int i = this.zzb;
        int i2 = i - 1;
        if (i != 0) {
            switch (i2) {
                case 0:
                    return true;
                case 2:
                    return false;
                default:
                    this.zzb = 4;
                    this.zza = zza();
                    if (this.zzb == 3) {
                        return false;
                    }
                    this.zzb = 1;
                    return true;
            }
        } else {
            throw null;
        }
    }

    public final Object next() {
        if (hasNext()) {
            this.zzb = 2;
            Object obj = this.zza;
            this.zza = null;
            return obj;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    @CheckForNull
    public abstract Object zza();

    /* access modifiers changed from: protected */
    @CheckForNull
    public final Object zzb() {
        this.zzb = 3;
        return null;
    }
}
