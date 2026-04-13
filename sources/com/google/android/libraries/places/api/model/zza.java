package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.AddressComponent;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zza extends AddressComponent.Builder {
    private String zza;
    private String zzb;
    private List zzc;

    zza() {
    }

    @Nullable
    public final String getShortName() {
        return this.zzb;
    }

    public final AddressComponent.Builder setShortName(@Nullable String str) {
        this.zzb = str;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final AddressComponent.Builder zza(String str) {
        if (str != null) {
            this.zza = str;
            return this;
        }
        throw new NullPointerException("Null name");
    }

    /* access modifiers changed from: package-private */
    public final AddressComponent.Builder zzb(List list) {
        if (list != null) {
            this.zzc = list;
            return this;
        }
        throw new NullPointerException("Null types");
    }

    /* access modifiers changed from: package-private */
    public final AddressComponent zzc() {
        List list;
        String str = this.zza;
        if (str != null && (list = this.zzc) != null) {
            return new zzae(str, this.zzb, list);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" name");
        }
        if (this.zzc == null) {
            sb.append(" types");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
