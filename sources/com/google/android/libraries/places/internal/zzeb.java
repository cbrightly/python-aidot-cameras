package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.zzbq;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.IsOpenResponse;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzeb implements SuccessContinuation {
    public final /* synthetic */ Place zza;
    public final /* synthetic */ long zzb;
    public final /* synthetic */ TaskCompletionSource zzc;

    public /* synthetic */ zzeb(Place place, long j, TaskCompletionSource taskCompletionSource) {
        this.zza = place;
        this.zzb = j;
        this.zzc = taskCompletionSource;
    }

    public final Task then(Object obj) {
        Place place = this.zza;
        long j = this.zzb;
        TaskCompletionSource taskCompletionSource = this.zzc;
        Place place2 = ((FetchPlaceResponse) obj).getPlace();
        Place.BusinessStatus businessStatus = place2.getBusinessStatus();
        OpeningHours currentOpeningHours = place2.getCurrentOpeningHours();
        OpeningHours openingHours = place2.getOpeningHours();
        Integer utcOffsetMinutes = place2.getUtcOffsetMinutes();
        if (place != null) {
            if (utcOffsetMinutes == null) {
                utcOffsetMinutes = place.getUtcOffsetMinutes();
            }
            if (businessStatus == null) {
                businessStatus = place.getBusinessStatus();
            }
            if (currentOpeningHours == null) {
                currentOpeningHours = place.getCurrentOpeningHours();
            }
            if (openingHours == null) {
                openingHours = place.getOpeningHours();
            }
        }
        Place.Builder builder = Place.builder();
        builder.setBusinessStatus(businessStatus);
        builder.setCurrentOpeningHours(currentOpeningHours);
        builder.setOpeningHours(openingHours);
        builder.setUtcOffsetMinutes(utcOffsetMinutes);
        taskCompletionSource.setResult(IsOpenResponse.newInstance(zzbq.zza(builder.build(), j)));
        return taskCompletionSource.getTask();
    }
}
