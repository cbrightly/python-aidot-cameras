package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmy extends zzna {
    final char[] zza = new char[512];

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzmy(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.libraries.places.internal.zzmx r4 = new com.google.android.libraries.places.internal.zzmx
            java.lang.String r5 = "0123456789ABCDEF"
            char[] r5 = r5.toCharArray()
            java.lang.String r0 = "base16()"
            r4.<init>(r0, r5)
            r5 = 0
            r3.<init>(r4, r5)
            r5 = 512(0x200, float:7.175E-43)
            char[] r5 = new char[r5]
            r3.zza = r5
            char[] r5 = r4.zzf
            int r5 = r5.length
            r0 = 0
            r1 = 16
            if (r5 != r1) goto L_0x0023
            r5 = 1
            goto L_0x0024
        L_0x0023:
            r5 = r0
        L_0x0024:
            com.google.android.libraries.places.internal.zziy.zzd(r5)
        L_0x0027:
            r5 = 256(0x100, float:3.59E-43)
            if (r0 >= r5) goto L_0x0044
            char[] r5 = r3.zza
            int r1 = r0 >>> 4
            char r1 = r4.zza(r1)
            r5[r0] = r1
            char[] r5 = r3.zza
            r1 = r0 | 256(0x100, float:3.59E-43)
            r2 = r0 & 15
            char r2 = r4.zza(r2)
            r5[r1] = r2
            int r0 = r0 + 1
            goto L_0x0027
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzmy.<init>(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public final void zza(Appendable appendable, byte[] bArr, int i, int i2) {
        zziy.zzi(0, i2, bArr.length);
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = bArr[i3] & 255;
            appendable.append(this.zza[b]);
            appendable.append(this.zza[b | 256]);
        }
    }
}
