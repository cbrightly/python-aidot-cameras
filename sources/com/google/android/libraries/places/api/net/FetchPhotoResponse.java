package com.google.android.libraries.places.api.net;

import android.graphics.Bitmap;
import androidx.annotation.RecentlyNonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FetchPhotoResponse {
    @RecentlyNonNull
    public static FetchPhotoResponse newInstance(@RecentlyNonNull Bitmap bitmap) {
        return new zzd(bitmap);
    }

    @RecentlyNonNull
    public abstract Bitmap getBitmap();
}
