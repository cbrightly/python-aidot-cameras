package com.google.android.gms.internal.wearable;

public final class zzp implements Cloneable {
    private static final zzq zzhe = new zzq();
    private int mSize;
    private boolean zzhf;
    private int[] zzhg;
    private zzq[] zzhh;

    zzp() {
        this(10);
    }

    private zzp(int i) {
        this.zzhf = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzhg = new int[idealIntArraySize];
        this.zzhh = new zzq[idealIntArraySize];
        this.mSize = 0;
    }

    /* access modifiers changed from: package-private */
    public final zzq zzo(int i) {
        int zzq = zzq(i);
        if (zzq < 0) {
            return null;
        }
        zzq[] zzqArr = this.zzhh;
        if (zzqArr[zzq] == zzhe) {
            return null;
        }
        return zzqArr[zzq];
    }

    /* access modifiers changed from: package-private */
    public final void zza(int i, zzq zzq) {
        int zzq2 = zzq(i);
        if (zzq2 >= 0) {
            this.zzhh[zzq2] = zzq;
            return;
        }
        int i2 = ~zzq2;
        int i3 = this.mSize;
        if (i2 < i3) {
            zzq[] zzqArr = this.zzhh;
            if (zzqArr[i2] == zzhe) {
                this.zzhg[i2] = i;
                zzqArr[i2] = zzq;
                return;
            }
        }
        if (i3 >= this.zzhg.length) {
            int idealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            zzq[] zzqArr2 = new zzq[idealIntArraySize];
            int[] iArr2 = this.zzhg;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzq[] zzqArr3 = this.zzhh;
            System.arraycopy(zzqArr3, 0, zzqArr2, 0, zzqArr3.length);
            this.zzhg = iArr;
            this.zzhh = zzqArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzhg;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzq[] zzqArr4 = this.zzhh;
            System.arraycopy(zzqArr4, i2, zzqArr4, i5, this.mSize - i2);
        }
        this.zzhg[i2] = i;
        this.zzhh[i2] = zzq;
        this.mSize++;
    }

    /* access modifiers changed from: package-private */
    public final int size() {
        return this.mSize;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    /* access modifiers changed from: package-private */
    public final zzq zzp(int i) {
        return this.zzhh[i];
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzp)) {
            return false;
        }
        zzp zzp = (zzp) obj;
        int i = this.mSize;
        if (i != zzp.mSize) {
            return false;
        }
        int[] iArr = this.zzhg;
        int[] iArr2 = zzp.zzhg;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            } else if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            zzq[] zzqArr = this.zzhh;
            zzq[] zzqArr2 = zzp.zzhh;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!zzqArr[i4].equals(zzqArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzhg[i2]) * 31) + this.zzhh[i2].hashCode();
        }
        return i;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzq(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzhg[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return ~i3;
    }

    public final /* synthetic */ Object clone() {
        int i = this.mSize;
        zzp zzp = new zzp(i);
        System.arraycopy(this.zzhg, 0, zzp.zzhg, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzq[] zzqArr = this.zzhh;
            if (zzqArr[i2] != null) {
                zzp.zzhh[i2] = (zzq) zzqArr[i2].clone();
            }
        }
        zzp.mSize = i;
        return zzp;
    }
}
