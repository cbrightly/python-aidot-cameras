package com.google.android.libraries.places.api.model;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzaa extends SpecialDay {
    private final LocalDate zza;
    private final boolean zzb;

    zzaa(LocalDate localDate, boolean z) {
        if (localDate != null) {
            this.zza = localDate;
            this.zzb = z;
            return;
        }
        throw new NullPointerException("Null date");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SpecialDay) {
            SpecialDay specialDay = (SpecialDay) obj;
            return this.zza.equals(specialDay.getDate()) && this.zzb == specialDay.isExceptional();
        }
    }

    public final LocalDate getDate() {
        return this.zza;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ (true != this.zzb ? 1237 : 1231);
    }

    public final boolean isExceptional() {
        return this.zzb;
    }

    public final String toString() {
        String obj = this.zza.toString();
        boolean z = this.zzb;
        return "SpecialDay{date=" + obj + ", exceptional=" + z + "}";
    }
}
