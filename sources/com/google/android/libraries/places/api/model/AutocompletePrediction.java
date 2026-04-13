package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class AutocompletePrediction implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public AutocompletePrediction build() {
            AutocompletePrediction zze = zze();
            setPlaceTypes(zzjq.zzj(zze.getPlaceTypes()));
            List zzd = zze.zzd();
            if (zzd != null) {
                zza(zzjq.zzj(zzd));
            }
            List zze2 = zze.zze();
            if (zze2 != null) {
                zzc(zzjq.zzj(zze2));
            }
            List zzf = zze.zzf();
            if (zzf != null) {
                zzd(zzjq.zzj(zzf));
            }
            return zze();
        }

        @RecentlyNullable
        public abstract Integer getDistanceMeters();

        @RecentlyNonNull
        public abstract String getFullText();

        @RecentlyNonNull
        public abstract List<Place.Type> getPlaceTypes();

        @RecentlyNonNull
        public abstract String getPrimaryText();

        @RecentlyNonNull
        public abstract String getSecondaryText();

        @RecentlyNonNull
        public abstract Builder setDistanceMeters(@Nullable Integer num);

        @RecentlyNonNull
        public abstract Builder setFullText(@RecentlyNonNull String str);

        @RecentlyNonNull
        public abstract Builder setPlaceTypes(@RecentlyNonNull List<Place.Type> list);

        @RecentlyNonNull
        public abstract Builder setPrimaryText(@RecentlyNonNull String str);

        @RecentlyNonNull
        public abstract Builder setSecondaryText(@RecentlyNonNull String str);

        @RecentlyNonNull
        public abstract Builder zza(@Nullable List list);

        @RecentlyNonNull
        public abstract Builder zzc(@Nullable List list);

        @RecentlyNonNull
        public abstract Builder zzd(@Nullable List list);

        /* access modifiers changed from: package-private */
        public abstract AutocompletePrediction zze();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String placeId) {
        zzd zzd = new zzd();
        zzd.zzb(placeId);
        zzd.setPlaceTypes(new ArrayList());
        zzd.setFullText("");
        zzd.setPrimaryText("");
        zzd.setSecondaryText("");
        return zzd;
    }

    private static final SpannableString zzg(String str, @Nullable List list, @Nullable CharacterStyle characterStyle) {
        SpannableString spannableString = new SpannableString(str);
        if (str.length() == 0 || characterStyle == null || list == null) {
            return spannableString;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            zzbk zzbk = (zzbk) it.next();
            spannableString.setSpan(CharacterStyle.wrap(characterStyle), zzbk.zzb(), zzbk.zzb() + zzbk.zza(), 0);
        }
        return spannableString;
    }

    @RecentlyNullable
    public abstract Integer getDistanceMeters();

    @RecentlyNonNull
    public SpannableString getFullText(@Nullable CharacterStyle matchStyle) {
        return zzg(zza(), zzd(), matchStyle);
    }

    @RecentlyNonNull
    public abstract String getPlaceId();

    @RecentlyNonNull
    public abstract List<Place.Type> getPlaceTypes();

    @RecentlyNonNull
    public SpannableString getPrimaryText(@Nullable CharacterStyle matchStyle) {
        return zzg(zzb(), zze(), matchStyle);
    }

    @RecentlyNonNull
    public SpannableString getSecondaryText(@Nullable CharacterStyle matchStyle) {
        return zzg(zzc(), zzf(), matchStyle);
    }

    /* access modifiers changed from: package-private */
    public abstract String zza();

    /* access modifiers changed from: package-private */
    public abstract String zzb();

    /* access modifiers changed from: package-private */
    public abstract String zzc();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract List zzd();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract List zze();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract List zzf();
}
