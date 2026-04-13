package com.google.android.gms.internal.common;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;
import org.jspecify.nullness.NullMarked;

@NullMarked
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class zzj implements Iterator {
    @CheckForNull
    private Object zza;
    private int zzb = 2;

    protected zzj() {
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
    @CanIgnoreReturnValue
    public final Object zzb() {
        this.zzb = 3;
        return null;
    }

    public final boolean hasNext() {
        int i = this.zzb;
        if (i != 4) {
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
        } else {
            throw new IllegalStateException();
        }
    }
}
