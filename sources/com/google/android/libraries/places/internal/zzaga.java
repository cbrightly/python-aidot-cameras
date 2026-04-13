package com.google.android.libraries.places.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaga extends zzaes implements RandomAccess, zzage, zzaho {
    private static final zzaga zza = new zzaga(new int[0], 0, false);
    private int[] zzb;
    private int zzc;

    zzaga() {
        this(new int[10], 0, true);
    }

    public static zzaga zze() {
        return zza;
    }

    private final String zzg(int i) {
        int i2 = this.zzc;
        return "Index:" + i + ", Size:" + i2;
    }

    private final void zzh(int i) {
        if (i < 0 || i >= this.zzc) {
            throw new IndexOutOfBoundsException(zzg(i));
        }
    }

    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        int intValue = ((Integer) obj).intValue();
        zza();
        if (i < 0 || i > (i2 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzg(i));
        }
        int[] iArr = this.zzb;
        if (i2 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i2 - i);
        } else {
            int[] iArr2 = new int[(((i2 * 3) / 2) + 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzb, i, iArr2, i + 1, this.zzc - i);
            this.zzb = iArr2;
        }
        this.zzb[i] = intValue;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection collection) {
        zza();
        byte[] bArr = zzagi.zzd;
        if (collection == null) {
            throw null;
        } else if (!(collection instanceof zzaga)) {
            return super.addAll(collection);
        } else {
            zzaga zzaga = (zzaga) collection;
            int i = zzaga.zzc;
            if (i == 0) {
                return false;
            }
            int i2 = this.zzc;
            if (Integer.MAX_VALUE - i2 >= i) {
                int i3 = i2 + i;
                int[] iArr = this.zzb;
                if (i3 > iArr.length) {
                    this.zzb = Arrays.copyOf(iArr, i3);
                }
                System.arraycopy(zzaga.zzb, 0, this.zzb, this.zzc, zzaga.zzc);
                this.zzc = i3;
                this.modCount++;
                return true;
            }
            throw new OutOfMemoryError();
        }
    }

    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaga)) {
            return super.equals(obj);
        }
        zzaga zzaga = (zzaga) obj;
        if (this.zzc != zzaga.zzc) {
            return false;
        }
        int[] iArr = zzaga.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzh(i);
        return Integer.valueOf(this.zzb[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + this.zzb[i2];
        }
        return i;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int intValue = ((Integer) obj).intValue();
        int i = this.zzc;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzb[i2] == intValue) {
                return i2;
            }
        }
        return -1;
    }

    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zza();
        zzh(i);
        int[] iArr = this.zzb;
        int i2 = iArr[i];
        int i3 = this.zzc;
        if (i < i3 - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (i3 - i) - 1);
        }
        this.zzc--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zza();
        if (i2 >= i) {
            int[] iArr = this.zzb;
            System.arraycopy(iArr, i2, iArr, i, this.zzc - i2);
            this.zzc -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zza();
        zzh(i);
        int[] iArr = this.zzb;
        int i2 = iArr[i];
        iArr[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final int size() {
        return this.zzc;
    }

    public final int zzd(int i) {
        zzh(i);
        return this.zzb[i];
    }

    public final /* bridge */ /* synthetic */ zzagh zzf(int i) {
        if (i >= this.zzc) {
            return new zzaga(Arrays.copyOf(this.zzb, i), this.zzc, true);
        }
        throw new IllegalArgumentException();
    }

    private zzaga(int[] iArr, int i, boolean z) {
        super(z);
        this.zzb = iArr;
        this.zzc = i;
    }

    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        int intValue = ((Integer) obj).intValue();
        zza();
        int i = this.zzc;
        int[] iArr = this.zzb;
        if (i == iArr.length) {
            int[] iArr2 = new int[(((i * 3) / 2) + 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            this.zzb = iArr2;
        }
        int[] iArr3 = this.zzb;
        int i2 = this.zzc;
        this.zzc = i2 + 1;
        iArr3[i2] = intValue;
        return true;
    }
}
