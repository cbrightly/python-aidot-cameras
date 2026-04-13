package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzq implements Cloneable {
    private Object value;
    private zzo<?, ?> zzhi;
    private List<zzv> zzhj = new ArrayList();

    zzq() {
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzv zzv) {
        List<zzv> list = this.zzhj;
        if (list != null) {
            list.add(zzv);
            return;
        }
        Object obj = this.value;
        if (obj instanceof zzt) {
            byte[] bArr = zzv.zzhm;
            zzk zza = zzk.zza(bArr, 0, bArr.length);
            int zzk = zza.zzk();
            if (zzk == bArr.length - zzl.zzi(zzk)) {
                zzt zza2 = ((zzt) this.value).zza(zza);
                this.zzhi = this.zzhi;
                this.value = zza2;
                this.zzhj = null;
                return;
            }
            throw zzs.zzu();
        } else if (obj instanceof zzt[]) {
            Collections.singletonList(zzv);
            throw new NoSuchMethodError();
        } else {
            Collections.singletonList(zzv);
            throw new NoSuchMethodError();
        }
    }

    /* access modifiers changed from: package-private */
    public final int zzg() {
        if (this.value == null) {
            int i = 0;
            for (zzv next : this.zzhj) {
                i += zzl.zzm(next.tag) + 0 + next.zzhm.length;
            }
            return i;
        }
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzl zzl) {
        if (this.value == null) {
            for (zzv next : this.zzhj) {
                zzl.zzl(next.tag);
                zzl.zzc(next.zzhm);
            }
            return;
        }
        throw new NoSuchMethodError();
    }

    public final boolean equals(Object obj) {
        List<zzv> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzq)) {
            return false;
        }
        zzq zzq = (zzq) obj;
        if (this.value == null || zzq.value == null) {
            List<zzv> list2 = this.zzhj;
            if (list2 != null && (list = zzq.zzhj) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(toByteArray(), zzq.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            zzo<?, ?> zzo = this.zzhi;
            if (zzo != zzq.zzhi) {
                return false;
            }
            if (!zzo.zzhd.isArray()) {
                return this.value.equals(zzq.value);
            }
            Object obj2 = this.value;
            if (obj2 instanceof byte[]) {
                return Arrays.equals((byte[]) obj2, (byte[]) zzq.value);
            }
            if (obj2 instanceof int[]) {
                return Arrays.equals((int[]) obj2, (int[]) zzq.value);
            }
            if (obj2 instanceof long[]) {
                return Arrays.equals((long[]) obj2, (long[]) zzq.value);
            }
            if (obj2 instanceof float[]) {
                return Arrays.equals((float[]) obj2, (float[]) zzq.value);
            }
            if (obj2 instanceof double[]) {
                return Arrays.equals((double[]) obj2, (double[]) zzq.value);
            }
            if (obj2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj2, (boolean[]) zzq.value);
            }
            return Arrays.deepEquals((Object[]) obj2, (Object[]) zzq.value);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private final byte[] toByteArray() {
        byte[] bArr = new byte[zzg()];
        zza(zzl.zzb(bArr));
        return bArr;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzt */
    public final zzq clone() {
        zzq zzq = new zzq();
        try {
            zzq.zzhi = this.zzhi;
            List<zzv> list = this.zzhj;
            if (list == null) {
                zzq.zzhj = null;
            } else {
                zzq.zzhj.addAll(list);
            }
            Object obj = this.value;
            if (obj != null) {
                if (obj instanceof zzt) {
                    zzq.value = (zzt) ((zzt) obj).clone();
                } else if (obj instanceof byte[]) {
                    zzq.value = ((byte[]) obj).clone();
                } else {
                    int i = 0;
                    if (obj instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) obj;
                        byte[][] bArr2 = new byte[bArr.length][];
                        zzq.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (obj instanceof boolean[]) {
                        zzq.value = ((boolean[]) obj).clone();
                    } else if (obj instanceof int[]) {
                        zzq.value = ((int[]) obj).clone();
                    } else if (obj instanceof long[]) {
                        zzq.value = ((long[]) obj).clone();
                    } else if (obj instanceof float[]) {
                        zzq.value = ((float[]) obj).clone();
                    } else if (obj instanceof double[]) {
                        zzq.value = ((double[]) obj).clone();
                    } else if (obj instanceof zzt[]) {
                        zzt[] zztArr = (zzt[]) obj;
                        zzt[] zztArr2 = new zzt[zztArr.length];
                        zzq.value = zztArr2;
                        while (i < zztArr.length) {
                            zztArr2[i] = (zzt) zztArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return zzq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
