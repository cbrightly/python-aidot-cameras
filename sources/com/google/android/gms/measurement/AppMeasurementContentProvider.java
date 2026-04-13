package com.google.android.gms.measurement;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcl;
import com.google.android.gms.measurement.internal.zzge;

@Deprecated
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public class AppMeasurementContentProvider extends ContentProvider {
    public void attachInfo(@NonNull Context context, @NonNull ProviderInfo info) {
        super.attachInfo(context, info);
        if ("com.google.android.gms.measurement.google_measurement_service".equals(info.authority)) {
            throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a missing applicationId variable in application's build.gradle.");
        }
    }

    public int delete(@NonNull Uri uri, @Nullable String str, @NonNull String[] strArr) {
        return 0;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @NonNull ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        Context context = getContext();
        Preconditions.checkNotNull(context);
        zzge.zzp(context, (zzcl) null, (Long) null);
        return false;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @NonNull String[] strArr, @Nullable String str, @NonNull String[] strArr2, @Nullable String str2) {
        return null;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @NonNull String[] strArr) {
        return 0;
    }
}
