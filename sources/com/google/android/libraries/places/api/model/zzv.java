package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.PlusCode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzv extends PlusCode.Builder {
    private String zza;
    private String zzb;

    zzv() {
    }

    public final PlusCode build() {
        return new zzbc(this.zza, this.zzb);
    }

    @Nullable
    public final String getCompoundCode() {
        return this.zza;
    }

    @Nullable
    public final String getGlobalCode() {
        return this.zzb;
    }

    public final PlusCode.Builder setCompoundCode(@Nullable String str) {
        this.zza = str;
        return this;
    }

    public final PlusCode.Builder setGlobalCode(@Nullable String str) {
        this.zzb = str;
        return this;
    }
}
