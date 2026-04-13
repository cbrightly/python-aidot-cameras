package com.google.android.libraries.places.api.net;

import com.google.android.libraries.places.api.model.Place;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzh extends FetchPlaceResponse {
    private final Place zza;

    zzh(Place place) {
        if (place != null) {
            this.zza = place;
            return;
        }
        throw new NullPointerException("Null place");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof FetchPlaceResponse) {
            return this.zza.equals(((FetchPlaceResponse) obj).getPlace());
        }
        return false;
    }

    public final Place getPlace() {
        return this.zza;
    }

    public final int hashCode() {
        return this.zza.hashCode() ^ 1000003;
    }

    public final String toString() {
        String obj = this.zza.toString();
        return "FetchPlaceResponse{place=" + obj + "}";
    }
}
