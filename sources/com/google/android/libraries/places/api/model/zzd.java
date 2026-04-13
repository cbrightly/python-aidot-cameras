package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzd extends AutocompletePrediction.Builder {
    private String zza;
    private Integer zzb;
    private List zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private List zzg;
    private List zzh;
    private List zzi;

    zzd() {
    }

    @Nullable
    public final Integer getDistanceMeters() {
        return this.zzb;
    }

    public final String getFullText() {
        String str = this.zzd;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Property \"fullText\" has not been set");
    }

    public final List<Place.Type> getPlaceTypes() {
        List<Place.Type> list = this.zzc;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"placeTypes\" has not been set");
    }

    public final String getPrimaryText() {
        String str = this.zze;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Property \"primaryText\" has not been set");
    }

    public final String getSecondaryText() {
        String str = this.zzf;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Property \"secondaryText\" has not been set");
    }

    public final AutocompletePrediction.Builder setDistanceMeters(@Nullable Integer num) {
        this.zzb = num;
        return this;
    }

    public final AutocompletePrediction.Builder setFullText(String str) {
        if (str != null) {
            this.zzd = str;
            return this;
        }
        throw new NullPointerException("Null fullText");
    }

    public final AutocompletePrediction.Builder setPlaceTypes(List<Place.Type> list) {
        if (list != null) {
            this.zzc = list;
            return this;
        }
        throw new NullPointerException("Null placeTypes");
    }

    public final AutocompletePrediction.Builder setPrimaryText(String str) {
        if (str != null) {
            this.zze = str;
            return this;
        }
        throw new NullPointerException("Null primaryText");
    }

    public final AutocompletePrediction.Builder setSecondaryText(String str) {
        if (str != null) {
            this.zzf = str;
            return this;
        }
        throw new NullPointerException("Null secondaryText");
    }

    public final AutocompletePrediction.Builder zza(@Nullable List list) {
        this.zzg = list;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final AutocompletePrediction.Builder zzb(String str) {
        if (str != null) {
            this.zza = str;
            return this;
        }
        throw new NullPointerException("Null placeId");
    }

    public final AutocompletePrediction.Builder zzc(@Nullable List list) {
        this.zzh = list;
        return this;
    }

    public final AutocompletePrediction.Builder zzd(@Nullable List list) {
        this.zzi = list;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final AutocompletePrediction zze() {
        List list;
        String str;
        String str2;
        String str3;
        String str4 = this.zza;
        if (str4 != null && (list = this.zzc) != null && (str = this.zzd) != null && (str2 = this.zze) != null && (str3 = this.zzf) != null) {
            return new zzai(str4, this.zzb, list, str, str2, str3, this.zzg, this.zzh, this.zzi);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" placeId");
        }
        if (this.zzc == null) {
            sb.append(" placeTypes");
        }
        if (this.zzd == null) {
            sb.append(" fullText");
        }
        if (this.zze == null) {
            sb.append(" primaryText");
        }
        if (this.zzf == null) {
            sb.append(" secondaryText");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
