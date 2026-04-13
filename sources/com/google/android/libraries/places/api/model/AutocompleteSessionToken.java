package com.google.android.libraries.places.api.model;

import android.os.ParcelUuid;
import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;
import com.google.auto.value.AutoValue;
import java.util.UUID;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class AutocompleteSessionToken implements Parcelable {
    @RecentlyNonNull
    public static AutocompleteSessionToken newInstance() {
        return new zzam(new ParcelUuid(UUID.randomUUID()));
    }

    @RecentlyNonNull
    public final String toString() {
        return zza().toString();
    }

    /* access modifiers changed from: package-private */
    public abstract ParcelUuid zza();
}
