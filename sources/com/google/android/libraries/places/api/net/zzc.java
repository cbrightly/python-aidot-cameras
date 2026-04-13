package com.google.android.libraries.places.api.net;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.PhotoMetadata;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzc extends FetchPhotoRequest {
    private final Integer zza;
    private final Integer zzb;
    private final PhotoMetadata zzc;
    private final CancellationToken zzd;

    /* synthetic */ zzc(Integer num, Integer num2, PhotoMetadata photoMetadata, CancellationToken cancellationToken, zzb zzb2) {
        this.zza = num;
        this.zzb = num2;
        this.zzc = photoMetadata;
        this.zzd = cancellationToken;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        r1 = r4.zzd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.libraries.places.api.net.FetchPhotoRequest
            r2 = 0
            if (r1 == 0) goto L_0x005b
            com.google.android.libraries.places.api.net.FetchPhotoRequest r5 = (com.google.android.libraries.places.api.net.FetchPhotoRequest) r5
            java.lang.Integer r1 = r4.zza
            if (r1 != 0) goto L_0x0016
            java.lang.Integer r1 = r5.getMaxWidth()
            if (r1 != 0) goto L_0x005a
        L_0x0015:
            goto L_0x0021
        L_0x0016:
            java.lang.Integer r3 = r5.getMaxWidth()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x005a
            goto L_0x0015
        L_0x0021:
            java.lang.Integer r1 = r4.zzb
            if (r1 != 0) goto L_0x002c
            java.lang.Integer r1 = r5.getMaxHeight()
            if (r1 != 0) goto L_0x005a
        L_0x002b:
            goto L_0x0037
        L_0x002c:
            java.lang.Integer r3 = r5.getMaxHeight()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x005a
            goto L_0x002b
        L_0x0037:
            com.google.android.libraries.places.api.model.PhotoMetadata r1 = r4.zzc
            com.google.android.libraries.places.api.model.PhotoMetadata r3 = r5.getPhotoMetadata()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x005a
            com.google.android.gms.tasks.CancellationToken r1 = r4.zzd
            if (r1 != 0) goto L_0x004e
            com.google.android.gms.tasks.CancellationToken r5 = r5.getCancellationToken()
            if (r5 != 0) goto L_0x0058
            goto L_0x0059
        L_0x004e:
            com.google.android.gms.tasks.CancellationToken r5 = r5.getCancellationToken()
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x0059
        L_0x0058:
            goto L_0x005a
        L_0x0059:
            return r0
        L_0x005a:
            return r2
        L_0x005b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.net.zzc.equals(java.lang.Object):boolean");
    }

    @Nullable
    public final CancellationToken getCancellationToken() {
        return this.zzd;
    }

    @IntRange(from = 1, to = 1600)
    @Nullable
    public final Integer getMaxHeight() {
        return this.zzb;
    }

    @IntRange(from = 1, to = 1600)
    @Nullable
    public final Integer getMaxWidth() {
        return this.zza;
    }

    public final PhotoMetadata getPhotoMetadata() {
        return this.zzc;
    }

    public final String toString() {
        Integer num = this.zza;
        Integer num2 = this.zzb;
        String obj = this.zzc.toString();
        String valueOf = String.valueOf(this.zzd);
        return "FetchPhotoRequest{maxWidth=" + num + ", maxHeight=" + num2 + ", photoMetadata=" + obj + ", cancellationToken=" + valueOf + "}";
    }

    public final int hashCode() {
        int i;
        int i2;
        Integer num = this.zza;
        int i3 = 0;
        if (num == null) {
            i = 0;
        } else {
            i = num.hashCode();
        }
        Integer num2 = this.zzb;
        if (num2 == null) {
            i2 = 0;
        } else {
            i2 = num2.hashCode();
        }
        int hashCode = ((((i ^ 1000003) * 1000003) ^ i2) * 1000003) ^ this.zzc.hashCode();
        CancellationToken cancellationToken = this.zzd;
        if (cancellationToken != null) {
            i3 = cancellationToken.hashCode();
        }
        return (hashCode * 1000003) ^ i3;
    }
}
