package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationToken;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzdv {
    private final zzgl zza;

    protected zzdv(zzgl zzgl) {
        this.zza = zzgl;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final CancellationToken zza() {
        return this.zza.getCancellationToken();
    }

    /* access modifiers changed from: protected */
    public final zzgl zzb() {
        return this.zza;
    }

    /* access modifiers changed from: protected */
    public abstract String zzc();

    /* access modifiers changed from: protected */
    public abstract Map zzd();
}
