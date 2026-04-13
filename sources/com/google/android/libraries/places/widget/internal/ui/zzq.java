package com.google.android.libraries.places.widget.internal.ui;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.DiffUtil;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.internal.zzgt;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzq extends DiffUtil.ItemCallback {
    private zzq() {
    }

    public static final boolean zza(AutocompletePrediction autocompletePrediction, AutocompletePrediction autocompletePrediction2) {
        try {
            return autocompletePrediction.getPlaceId().equals(autocompletePrediction2.getPlaceId());
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @SuppressLint({"DiffUtilEquals"})
    public final /* synthetic */ boolean areContentsTheSame(Object obj, Object obj2) {
        return ((AutocompletePrediction) obj).equals((AutocompletePrediction) obj2);
    }

    public final /* bridge */ /* synthetic */ boolean areItemsTheSame(Object obj, Object obj2) {
        return zza((AutocompletePrediction) obj, (AutocompletePrediction) obj2);
    }

    /* synthetic */ zzq(zzp zzp) {
    }
}
