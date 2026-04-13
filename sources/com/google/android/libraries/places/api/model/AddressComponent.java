package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class AddressComponent implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public AddressComponent build() {
            AddressComponent zzc = zzc();
            zziy.zzk(!zzc.getName().isEmpty(), "Name must not be empty.");
            List<String> types = zzc.getTypes();
            for (String isEmpty : types) {
                zziy.zzk(!TextUtils.isEmpty(isEmpty), "Types must not contain null or empty values.");
            }
            zzb(zzjq.zzj(types));
            return zzc();
        }

        @RecentlyNullable
        public abstract String getShortName();

        @RecentlyNonNull
        public abstract Builder setShortName(@Nullable String str);

        /* access modifiers changed from: package-private */
        public abstract Builder zzb(List list);

        /* access modifiers changed from: package-private */
        public abstract AddressComponent zzc();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String name, @RecentlyNonNull List<String> types) {
        zza zza = new zza();
        zza.zza(name);
        zza.zzb(types);
        return zza;
    }

    @RecentlyNonNull
    public abstract String getName();

    @RecentlyNullable
    public abstract String getShortName();

    @RecentlyNonNull
    public abstract List<String> getTypes();
}
