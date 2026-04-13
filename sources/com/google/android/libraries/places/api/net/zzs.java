package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.Place;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzs extends IsOpenRequest {
    private final Place zza;
    private final String zzb;
    private final long zzc;
    private final CancellationToken zzd;

    /* synthetic */ zzs(Place place, String str, long j, CancellationToken cancellationToken, zzr zzr) {
        this.zza = place;
        this.zzb = str;
        this.zzc = j;
        this.zzd = cancellationToken;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0041, code lost:
        r1 = r7.zzd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 != r7) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r8 instanceof com.google.android.libraries.places.api.net.IsOpenRequest
            r2 = 0
            if (r1 == 0) goto L_0x0059
            com.google.android.libraries.places.api.net.IsOpenRequest r8 = (com.google.android.libraries.places.api.net.IsOpenRequest) r8
            com.google.android.libraries.places.api.model.Place r1 = r7.zza
            if (r1 != 0) goto L_0x0016
            com.google.android.libraries.places.api.model.Place r1 = r8.getPlace()
            if (r1 != 0) goto L_0x0058
        L_0x0015:
            goto L_0x0021
        L_0x0016:
            com.google.android.libraries.places.api.model.Place r3 = r8.getPlace()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0058
            goto L_0x0015
        L_0x0021:
            java.lang.String r1 = r7.zzb
            if (r1 != 0) goto L_0x002c
            java.lang.String r1 = r8.getPlaceId()
            if (r1 != 0) goto L_0x0058
        L_0x002b:
            goto L_0x0037
        L_0x002c:
            java.lang.String r3 = r8.getPlaceId()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0058
            goto L_0x002b
        L_0x0037:
            long r3 = r7.zzc
            long r5 = r8.getUtcTimeMillis()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0058
            com.google.android.gms.tasks.CancellationToken r1 = r7.zzd
            if (r1 != 0) goto L_0x004c
            com.google.android.gms.tasks.CancellationToken r8 = r8.getCancellationToken()
            if (r8 != 0) goto L_0x0056
            goto L_0x0057
        L_0x004c:
            com.google.android.gms.tasks.CancellationToken r8 = r8.getCancellationToken()
            boolean r8 = r1.equals(r8)
            if (r8 != 0) goto L_0x0057
        L_0x0056:
            goto L_0x0058
        L_0x0057:
            return r0
        L_0x0058:
            return r2
        L_0x0059:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.net.zzs.equals(java.lang.Object):boolean");
    }

    @Nullable
    public final CancellationToken getCancellationToken() {
        return this.zzd;
    }

    @Nullable
    public final Place getPlace() {
        return this.zza;
    }

    @Nullable
    public final String getPlaceId() {
        return this.zzb;
    }

    public final long getUtcTimeMillis() {
        return this.zzc;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String str = this.zzb;
        long j = this.zzc;
        String valueOf2 = String.valueOf(this.zzd);
        return "IsOpenRequest{place=" + valueOf + ", placeId=" + str + ", utcTimeMillis=" + j + ", cancellationToken=" + valueOf2 + "}";
    }

    public final int hashCode() {
        int i;
        int i2;
        Place place = this.zza;
        int i3 = 0;
        if (place == null) {
            i = 0;
        } else {
            i = place.hashCode();
        }
        String str = this.zzb;
        if (str == null) {
            i2 = 0;
        } else {
            i2 = str.hashCode();
        }
        int i4 = i ^ 1000003;
        long j = this.zzc;
        long j2 = j ^ (j >>> 32);
        CancellationToken cancellationToken = this.zzd;
        if (cancellationToken != null) {
            i3 = cancellationToken.hashCode();
        }
        return (((((i4 * 1000003) ^ i2) * 1000003) ^ ((int) j2)) * 1000003) ^ i3;
    }
}
