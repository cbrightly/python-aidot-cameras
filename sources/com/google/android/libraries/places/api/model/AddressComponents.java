package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class AddressComponents implements Parcelable {
    @RecentlyNonNull
    public static AddressComponents newInstance(@RecentlyNonNull List<AddressComponent> addressComponents) {
        return new zzag(addressComponents);
    }

    @RecentlyNonNull
    public abstract List<AddressComponent> asList();
}
