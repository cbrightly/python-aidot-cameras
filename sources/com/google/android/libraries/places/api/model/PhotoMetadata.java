package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.internal.zziy;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class PhotoMetadata implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public PhotoMetadata build() {
            boolean z;
            PhotoMetadata zzb = zzb();
            int width = zzb.getWidth();
            boolean z2 = false;
            if (width >= 0) {
                z = true;
            } else {
                z = false;
            }
            zziy.zzl(z, "Width must not be < 0, but was: %s.", width);
            int height = zzb.getHeight();
            if (height >= 0) {
                z2 = true;
            }
            zziy.zzl(z2, "Height must not be < 0, but was: %s.", height);
            zziy.zzk(!TextUtils.isEmpty(zzb.zza()), "PhotoReference must not be null or empty.");
            return zzb;
        }

        @RecentlyNonNull
        public abstract String getAttributions();

        @IntRange(from = 0)
        public abstract int getHeight();

        @IntRange(from = 0)
        public abstract int getWidth();

        @RecentlyNonNull
        public abstract Builder setAttributions(@RecentlyNonNull String str);

        @RecentlyNonNull
        public abstract Builder setHeight(@IntRange(from = 0) int i);

        @RecentlyNonNull
        public abstract Builder setWidth(@IntRange(from = 0) int i);

        /* access modifiers changed from: package-private */
        public abstract PhotoMetadata zzb();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String photoReference) {
        zzq zzq = new zzq();
        zzq.zza(photoReference);
        zzq.setWidth(0);
        zzq.setHeight(0);
        zzq.setAttributions("");
        return zzq;
    }

    @RecentlyNonNull
    public abstract String getAttributions();

    @IntRange(from = 0)
    public abstract int getHeight();

    @IntRange(from = 0)
    public abstract int getWidth();

    @RecentlyNonNull
    public abstract String zza();
}
