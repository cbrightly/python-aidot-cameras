package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.R;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzfw {
    public static String zza(Context context) {
        try {
            return context.getResources().getResourcePackageName(R.string.common_google_play_services_unknown_issue);
        } catch (Resources.NotFoundException e) {
            return context.getPackageName();
        }
    }

    @Nullable
    public static final String zzb(String str, Resources resources, String str2) {
        int identifier = resources.getIdentifier(str, TypedValues.Custom.S_STRING, str2);
        if (identifier == 0) {
            return null;
        }
        try {
            return resources.getString(identifier);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }
}
