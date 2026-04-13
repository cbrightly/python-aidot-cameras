package com.google.android.libraries.places.internal;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhx implements zzhq {
    public static final /* synthetic */ int zza = 0;
    private static final zzjq zzb = zzjq.zzn(Place.Field.ID, Place.Field.TYPES);
    private final PlacesClient zzc;
    private final zzhj zzd;
    private final AutocompleteSessionToken zze;
    @Nullable
    private zzht zzf;
    @Nullable
    private zzhu zzg;

    public zzhx(PlacesClient placesClient, zzhj zzhj, AutocompleteSessionToken autocompleteSessionToken) {
        this.zzc = placesClient;
        this.zzd = zzhj;
        this.zze = autocompleteSessionToken;
    }

    public final Task zza(AutocompletePrediction autocompletePrediction) {
        List<Place.Type> list = null;
        if (zzb.containsAll(this.zzd.zzj())) {
            Place.Builder builder = Place.builder();
            builder.setId(autocompletePrediction.getPlaceId());
            if (!autocompletePrediction.getPlaceTypes().isEmpty()) {
                list = autocompletePrediction.getPlaceTypes();
            }
            builder.setTypes(list);
            return Tasks.forResult(FetchPlaceResponse.newInstance(builder.build()));
        }
        zzhu zzhu = this.zzg;
        if (zzhu != null) {
            if (zzhu.zzb().equals(autocompletePrediction.getPlaceId())) {
                Task zzc2 = zzhu.zzc();
                if (zzc2 != null) {
                    return zzc2;
                }
                throw null;
            }
            zzhu.zza().cancel();
        }
        zzhp zzhp = new zzhp(new CancellationTokenSource(), autocompletePrediction.getPlaceId());
        this.zzg = zzhp;
        PlacesClient placesClient = this.zzc;
        FetchPlaceRequest.Builder builder2 = FetchPlaceRequest.builder(autocompletePrediction.getPlaceId(), this.zzd.zzj());
        builder2.setSessionToken(this.zze);
        builder2.setCancellationToken(zzhp.zza().getToken());
        Task<TContinuationResult> continueWithTask = placesClient.fetchPlace(builder2.build()).continueWithTask(new zzhr(zzhp));
        zzhp.zzd(continueWithTask);
        return continueWithTask;
    }

    public final Task zzb(String str) {
        zziy.zzd(!TextUtils.isEmpty(str));
        zzht zzht = this.zzf;
        if (zzht != null) {
            if (zzht.zzb().equals(str)) {
                Task zzc2 = zzht.zzc();
                if (zzc2 != null) {
                    return zzc2;
                }
                throw null;
            }
            zzht.zza().cancel();
        }
        zzho zzho = new zzho(new CancellationTokenSource(), str);
        this.zzf = zzho;
        PlacesClient placesClient = this.zzc;
        FindAutocompletePredictionsRequest.Builder builder = FindAutocompletePredictionsRequest.builder();
        builder.setQuery(str);
        builder.setLocationBias(this.zzd.zzc());
        builder.setLocationRestriction(this.zzd.zzd());
        builder.setCountries((List<String>) this.zzd.zzi());
        builder.setTypeFilter(this.zzd.zze());
        builder.setTypesFilter(this.zzd.zzk());
        builder.setSessionToken(this.zze);
        builder.setCancellationToken(zzho.zza().getToken());
        Task<TContinuationResult> continueWithTask = placesClient.findAutocompletePredictions(builder.build()).continueWithTask(new zzhs(zzho));
        zzho.zzd(continueWithTask);
        return continueWithTask;
    }

    public final void zzc() {
        zzht zzht = this.zzf;
        if (zzht != null) {
            zzht.zza().cancel();
        }
        zzhu zzhu = this.zzg;
        if (zzhu != null) {
            zzhu.zza().cancel();
        }
        this.zzf = null;
        this.zzg = null;
    }
}
