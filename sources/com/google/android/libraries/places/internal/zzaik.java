package com.google.android.libraries.places.internal;

import java.util.Arrays;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaik {
    private static final zzaik zza = new zzaik(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzaik() {
        this(0, new int[8], new Object[8], true);
    }

    private zzaik(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = 0;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzaik zzc() {
        return zza;
    }

    static zzaik zze(zzaik zzaik, zzaik zzaik2) {
        int i = zzaik.zzb;
        int i2 = zzaik2.zzb;
        int[] copyOf = Arrays.copyOf(zzaik.zzc, 0);
        System.arraycopy(zzaik2.zzc, 0, copyOf, 0, 0);
        Object[] copyOf2 = Arrays.copyOf(zzaik.zzd, 0);
        System.arraycopy(zzaik2.zzd, 0, copyOf2, 0, 0);
        return new zzaik(0, copyOf, copyOf2, true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzaik)) {
            return false;
        }
        zzaik zzaik = (zzaik) obj;
        return true;
    }

    public final int hashCode() {
        return 506991;
    }

    public final int zza() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        this.zze = 0;
        return 0;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        this.zze = 0;
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final zzaik zzd(zzaik zzaik) {
        if (zzaik.equals(zza)) {
            return this;
        }
        if (this.zzf) {
            int[] iArr = this.zzc;
            int length = iArr.length;
            System.arraycopy(zzaik.zzc, 0, iArr, 0, 0);
            System.arraycopy(zzaik.zzd, 0, this.zzd, 0, 0);
            this.zzb = 0;
            return this;
        }
        throw new UnsupportedOperationException();
    }

    public final void zzf() {
        this.zzf = false;
    }

    /* access modifiers changed from: package-private */
    public final void zzg(StringBuilder sb, int i) {
    }
}
