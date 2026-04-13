package com.google.android.libraries.places.api.model;

import com.google.android.libraries.places.api.model.SpecialDay;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzz extends SpecialDay.Builder {
    private LocalDate zza;
    private boolean zzb;
    private byte zzc;

    zzz() {
    }

    public final SpecialDay build() {
        LocalDate localDate;
        if (this.zzc == 1 && (localDate = this.zza) != null) {
            return new zzbg(localDate, this.zzb);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" date");
        }
        if (this.zzc == 0) {
            sb.append(" exceptional");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }

    public final LocalDate getDate() {
        LocalDate localDate = this.zza;
        if (localDate != null) {
            return localDate;
        }
        throw new IllegalStateException("Property \"date\" has not been set");
    }

    public final boolean isExceptional() {
        if (this.zzc != 0) {
            return this.zzb;
        }
        throw new IllegalStateException("Property \"exceptional\" has not been set");
    }

    public final SpecialDay.Builder setDate(LocalDate localDate) {
        if (localDate != null) {
            this.zza = localDate;
            return this;
        }
        throw new NullPointerException("Null date");
    }

    public final SpecialDay.Builder setExceptional(boolean z) {
        this.zzb = z;
        this.zzc = 1;
        return this;
    }
}
