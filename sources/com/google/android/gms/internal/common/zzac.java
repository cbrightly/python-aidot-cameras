package com.google.android.gms.internal.common;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.CheckForNull;
import org.jspecify.nullness.NullMarked;

@NullMarked
@DoNotMock("Use ImmutableList.of or another implementation")
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class zzac extends AbstractCollection implements Serializable {
    private static final Object[] zza = new Object[0];

    zzac() {
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final boolean remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public final Object[] toArray() {
        return toArray(zza);
    }

    /* access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public int zza(Object[] objArr, int i) {
        throw null;
    }

    /* access modifiers changed from: package-private */
    public int zzb() {
        throw null;
    }

    /* access modifiers changed from: package-private */
    public int zzc() {
        throw null;
    }

    public zzag zzd() {
        throw null;
    }

    /* renamed from: zze */
    public abstract zzaj iterator();

    /* access modifiers changed from: package-private */
    public abstract boolean zzf();

    /* access modifiers changed from: package-private */
    @CheckForNull
    public Object[] zzg() {
        throw null;
    }

    @CanIgnoreReturnValue
    public final Object[] toArray(Object[] objArr) {
        if (objArr != null) {
            int size = size();
            int length = objArr.length;
            if (length < size) {
                Object[] zzg = zzg();
                if (zzg != null) {
                    return Arrays.copyOfRange(zzg, zzc(), zzb(), objArr.getClass());
                }
                if (length != 0) {
                    objArr = Arrays.copyOf(objArr, 0);
                }
                objArr = Arrays.copyOf(objArr, size);
            } else if (length > size) {
                objArr[size] = null;
            }
            zza(objArr, 0);
            return objArr;
        }
        throw null;
    }
}
