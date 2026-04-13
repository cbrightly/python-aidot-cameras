package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzm extends FindCurrentPlaceRequest.Builder {
    private List zza;
    private CancellationToken zzb;

    zzm() {
    }

    @Nullable
    public final CancellationToken getCancellationToken() {
        return this.zzb;
    }

    public final FindCurrentPlaceRequest.Builder setCancellationToken(@Nullable CancellationToken cancellationToken) {
        this.zzb = cancellationToken;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final FindCurrentPlaceRequest.Builder zza(List list) {
        if (list != null) {
            this.zza = list;
            return this;
        }
        throw new NullPointerException("Null placeFields");
    }

    /* access modifiers changed from: package-private */
    public final FindCurrentPlaceRequest zzb() {
        List list = this.zza;
        if (list != null) {
            return new zzo(list, this.zzb, (zzn) null);
        }
        throw new IllegalStateException("Missing required properties: placeFields");
    }
}
