package com.google.android.libraries.places.internal;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.zzbj;
import com.google.android.libraries.places.api.model.zzbk;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import com.google.android.libraries.places.internal.zzei;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzev {
    zzev() {
    }

    public static final FindAutocompletePredictionsResponse zza(zzeu zzeu) {
        int zza = zzft.zza(zzeu.status);
        if (!PlacesStatusCodes.isError(zza)) {
            ArrayList arrayList = new ArrayList();
            zzei[] zzeiArr = zzeu.predictions;
            if (zzeiArr != null) {
                for (zzei zzei : zzeiArr) {
                    if (zzei == null || TextUtils.isEmpty(zzei.zzf())) {
                        throw new ApiException(new Status(8, "Unexpected server error: Place ID not provided for an autocomplete prediction result"));
                    }
                    AutocompletePrediction.Builder builder = AutocompletePrediction.builder(zzei.zzf());
                    builder.setDistanceMeters(zzei.zzd());
                    builder.setPlaceTypes(zzfp.zzd(zzfp.zze(zzei.zzc())));
                    builder.setFullText(zzjd.zzb(zzei.zze()));
                    builder.zza(zzb(zzei.zzb()));
                    zzei.zza zza2 = zzei.zza();
                    if (zza2 != null) {
                        builder.setPrimaryText(zzjd.zzb(zza2.zzc()));
                        builder.zzc(zzb(zza2.zza()));
                        builder.setSecondaryText(zzjd.zzb(zza2.zzd()));
                        builder.zzd(zzb(zza2.zzb()));
                    }
                    arrayList.add(builder.build());
                }
            }
            return FindAutocompletePredictionsResponse.newInstance(arrayList);
        }
        throw new ApiException(new Status(zza, zzft.zzb(zzeu.status, zzeu.errorMessage)));
    }

    @Nullable
    private static List zzb(@Nullable List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        zzko zzp = ((zzjq) list).listIterator(0);
        while (zzp.hasNext()) {
            zzei.zzb zzb = (zzei.zzb) zzp.next();
            Status status = new Status(8, "Unexpected server error: Place ID not provided for an autocomplete prediction result");
            if (zzb != null) {
                Integer num = zzb.offset;
                Integer num2 = zzb.length;
                if (num == null || num2 == null) {
                    throw new ApiException(status);
                }
                zzbj zzc = zzbk.zzc();
                zzc.zzb(num.intValue());
                zzc.zza(num2.intValue());
                arrayList.add(zzc.zzc());
            } else {
                throw new ApiException(status);
            }
        }
        return arrayList;
    }
}
