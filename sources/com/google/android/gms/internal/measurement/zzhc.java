package com.google.android.gms.internal.measurement;

import android.content.Context;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhc extends zzhz {
    private final Context zza;
    private final zzim zzb;

    zzhc(Context context, @Nullable zzim zzim) {
        this.zza = context;
        this.zzb = zzim;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0017, code lost:
        r1 = r4.zzb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.gms.internal.measurement.zzhz
            r2 = 0
            if (r1 == 0) goto L_0x002e
            com.google.android.gms.internal.measurement.zzhz r5 = (com.google.android.gms.internal.measurement.zzhz) r5
            android.content.Context r1 = r4.zza
            android.content.Context r3 = r5.zza()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x002e
            com.google.android.gms.internal.measurement.zzim r1 = r4.zzb
            if (r1 != 0) goto L_0x0022
            com.google.android.gms.internal.measurement.zzim r5 = r5.zzb()
            if (r5 != 0) goto L_0x002c
            goto L_0x002d
        L_0x0022:
            com.google.android.gms.internal.measurement.zzim r5 = r5.zzb()
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x002d
        L_0x002c:
            goto L_0x002e
        L_0x002d:
            return r0
        L_0x002e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhc.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i;
        int hashCode = this.zza.hashCode() ^ 1000003;
        zzim zzim = this.zzb;
        if (zzim == null) {
            i = 0;
        } else {
            i = zzim.hashCode();
        }
        return (hashCode * 1000003) ^ i;
    }

    public final String toString() {
        String obj = this.zza.toString();
        String valueOf = String.valueOf(this.zzb);
        return "FlagsContext{context=" + obj + ", hermeticFileOverrides=" + valueOf + "}";
    }

    /* access modifiers changed from: package-private */
    public final Context zza() {
        return this.zza;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzim zzb() {
        return this.zzb;
    }
}
