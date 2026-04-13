package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzjb extends zziw implements Set {
    @CheckForNull
    private transient zzja zza;

    zzjb() {
    }

    static int zzf(int i) {
        int max = Math.max(i, 2);
        if (max < 751619276) {
            int highestOneBit = Integer.highestOneBit(max - 1);
            do {
                highestOneBit += highestOneBit;
            } while (((double) highestOneBit) * 0.7d < ((double) max));
            return highestOneBit;
        } else if (max < 1073741824) {
            return 1073741824;
        } else {
            throw new IllegalArgumentException("collection too large");
        }
    }

    @SafeVarargs
    public static zzjb zzi(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object... objArr) {
        Object[] objArr2 = new Object[15];
        objArr2[0] = "_in";
        objArr2[1] = "_xa";
        objArr2[2] = "_xu";
        objArr2[3] = "_aq";
        objArr2[4] = "_aa";
        objArr2[5] = "_ai";
        System.arraycopy(objArr, 0, objArr2, 6, 9);
        return zzk(15, objArr2);
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzjb) && zzj() && ((zzjb) obj).zzj() && hashCode() != obj.hashCode()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size() && containsAll(set)) {
                    return true;
                }
            } catch (ClassCastException | NullPointerException e) {
            }
        }
        return false;
    }

    public int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i += next != null ? next.hashCode() : 0;
        }
        return i;
    }

    /* renamed from: zzd */
    public abstract zzjh iterator();

    public final zzja zzg() {
        zzja zzja = this.zza;
        if (zzja != null) {
            return zzja;
        }
        zzja zzh = zzh();
        this.zza = zzh;
        return zzh;
    }

    /* access modifiers changed from: package-private */
    public zzja zzh() {
        Object[] array = toArray();
        int i = zzja.zzd;
        return zzja.zzg(array, array.length);
    }

    /* access modifiers changed from: package-private */
    public boolean zzj() {
        return false;
    }

    private static zzjb zzk(int i, Object... objArr) {
        Object[] objArr2;
        switch (i) {
            case 0:
                return zzjf.zza;
            case 1:
                Object obj = objArr[0];
                obj.getClass();
                return new zzjg(obj);
            default:
                int zzf = zzf(i);
                Object[] objArr3 = new Object[zzf];
                int i2 = zzf - 1;
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < i; i5++) {
                    Object obj2 = objArr[i5];
                    zzjd.zza(obj2, i5);
                    int hashCode = obj2.hashCode();
                    int zza2 = zzit.zza(hashCode);
                    while (true) {
                        int i6 = zza2 & i2;
                        Object obj3 = objArr3[i6];
                        if (obj3 == null) {
                            objArr[i4] = obj2;
                            objArr3[i6] = obj2;
                            i3 += hashCode;
                            i4++;
                        } else if (!obj3.equals(obj2)) {
                            zza2++;
                        }
                    }
                }
                Arrays.fill(objArr, i4, i, (Object) null);
                if (i4 == 1) {
                    Object obj4 = objArr[0];
                    obj4.getClass();
                    return new zzjg(obj4);
                }
                if (zzf(i4) < zzf / 2) {
                    return zzk(i4, objArr);
                }
                if (i4 < 10) {
                    objArr2 = Arrays.copyOf(objArr, i4);
                } else {
                    objArr2 = objArr;
                }
                return new zzjf(objArr2, i3, objArr3, i2, i4);
        }
    }
}
