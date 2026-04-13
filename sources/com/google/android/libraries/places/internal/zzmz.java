package com.google.android.libraries.places.internal;

import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmz extends zzna {
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzmz(java.lang.String r2, java.lang.String r3, @javax.annotation.CheckForNull java.lang.Character r4) {
        /*
            r1 = this;
            com.google.android.libraries.places.internal.zzmx r0 = new com.google.android.libraries.places.internal.zzmx
            char[] r3 = r3.toCharArray()
            r0.<init>(r2, r3)
            r1.<init>(r0, r4)
            char[] r2 = r0.zzf
            int r2 = r2.length
            r3 = 64
            if (r2 != r3) goto L_0x0017
            r2 = 1
            goto L_0x0018
        L_0x0017:
            r2 = 0
        L_0x0018:
            com.google.android.libraries.places.internal.zziy.zzd(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzmz.<init>(java.lang.String, java.lang.String, java.lang.Character):void");
    }

    /* access modifiers changed from: package-private */
    public final void zza(Appendable appendable, byte[] bArr, int i, int i2) {
        int i3 = 0;
        zziy.zzi(0, i2, bArr.length);
        for (int i4 = i2; i4 >= 3; i4 -= 3) {
            int i5 = i3 + 1;
            int i6 = i5 + 1;
            zzmx zzmx = this.zzb;
            byte b = ((bArr[i3] & 255) << MappingData.PATH) | ((bArr[i5] & 255) << 8) | (bArr[i6] & 255);
            appendable.append(zzmx.zza(b >>> 18));
            appendable.append(this.zzb.zza((b >>> 12) & 63));
            appendable.append(this.zzb.zza((b >>> 6) & 63));
            appendable.append(this.zzb.zza(b & 63));
            i3 = i6 + 1;
        }
        if (i3 < i2) {
            zzc(appendable, bArr, i3, i2 - i3);
        }
    }
}
