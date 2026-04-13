package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.IsOpenRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzq extends IsOpenRequest.Builder {
    private Place zza;
    private String zzb;
    private long zzc;
    private CancellationToken zzd;
    private byte zze;

    zzq() {
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
        if (this.zze != 0) {
            return this.zzc;
        }
        throw new IllegalStateException("Property \"utcTimeMillis\" has not been set");
    }

    public final IsOpenRequest.Builder setCancellationToken(@Nullable CancellationToken cancellationToken) {
        this.zzd = cancellationToken;
        return this;
    }

    public final IsOpenRequest.Builder setPlace(Place place) {
        this.zza = place;
        return this;
    }

    public final IsOpenRequest.Builder setPlaceId(String str) {
        this.zzb = str;
        return this;
    }

    public final IsOpenRequest.Builder setUtcTimeMillis(long j) {
        this.zzc = j;
        this.zze = 1;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final IsOpenRequest zza() {
        if (this.zze == 1) {
            return new zzs(this.zza, this.zzb, this.zzc, this.zzd, (zzr) null);
        }
        throw new IllegalStateException("Missing required properties: utcTimeMillis");
    }
}
