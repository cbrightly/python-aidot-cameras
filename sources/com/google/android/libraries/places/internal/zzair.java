package com.google.android.libraries.places.internal;

import sun.misc.Unsafe;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzair extends zzais {
    zzair(Unsafe unsafe) {
        super(unsafe);
    }

    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    public final void zzc(Object obj, long j, boolean z) {
        if (zzait.zzb) {
            zzait.zzD(obj, j, r3 ? (byte) 1 : 0);
        } else {
            zzait.zzE(obj, j, r3 ? (byte) 1 : 0);
        }
    }

    public final void zzd(Object obj, long j, byte b) {
        if (zzait.zzb) {
            zzait.zzD(obj, j, b);
        } else {
            zzait.zzE(obj, j, b);
        }
    }

    public final void zze(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    public final boolean zzg(Object obj, long j) {
        if (zzait.zzb) {
            return zzait.zzt(obj, j);
        }
        return zzait.zzu(obj, j);
    }
}
