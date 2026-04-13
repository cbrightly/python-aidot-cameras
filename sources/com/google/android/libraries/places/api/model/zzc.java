package com.google.android.libraries.places.api.model;

import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzc extends AddressComponents {
    private final List zza;

    zzc(List list) {
        if (list != null) {
            this.zza = list;
            return;
        }
        throw new NullPointerException("Null asList");
    }

    public final List<AddressComponent> asList() {
        return this.zza;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AddressComponents) {
            return this.zza.equals(((AddressComponents) obj).asList());
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode() ^ 1000003;
    }

    public final String toString() {
        String obj = this.zza.toString();
        return "AddressComponents{asList=" + obj + "}";
    }
}
