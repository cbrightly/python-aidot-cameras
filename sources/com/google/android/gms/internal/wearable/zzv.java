package com.google.android.gms.internal.wearable;

import java.util.Arrays;

public final class zzv {
    final int tag;
    final byte[] zzhm;

    zzv(int i, byte[] bArr) {
        this.tag = i;
        this.zzhm = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzv)) {
            return false;
        }
        zzv zzv = (zzv) obj;
        if (this.tag != zzv.tag || !Arrays.equals(this.zzhm, zzv.zzhm)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzhm);
    }
}
