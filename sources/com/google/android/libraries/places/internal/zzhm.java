package com.google.android.libraries.places.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import androidx.annotation.StringRes;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhm {
    public static Status zza(Intent intent) {
        try {
            zziy.zzc(intent, "Intent must not be null.");
            Status status = (Status) intent.getParcelableExtra("places/status");
            zziy.zzc(status, "Intent expected to contain a Status, but doesn't.");
            return status;
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public static Place zzb(Intent intent) {
        try {
            zziy.zzc(intent, "Intent must not be null.");
            Place place = (Place) intent.getParcelableExtra("places/selected_place");
            zziy.zzc(place, "Intent expected to contain a Place, but doesn't.");
            return place;
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public static String zzc(Context context, @StringRes int i) {
        Locale locale;
        Locale locale2;
        if (Build.VERSION.SDK_INT < 24) {
            locale = context.getResources().getConfiguration().locale;
        } else {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        }
        if (Places.isInitialized()) {
            locale2 = Places.zzc().zzb();
        } else {
            locale2 = locale;
        }
        if (locale2.equals(locale)) {
            return context.getResources().getString(i);
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale2);
        return context.createConfigurationContext(configuration).getResources().getString(i);
    }
}
