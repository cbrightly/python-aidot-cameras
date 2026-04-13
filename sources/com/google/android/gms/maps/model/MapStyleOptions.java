package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.IOUtils;
import java.io.IOException;

@SafeParcelable.Class(creator = "MapStyleOptionsCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class MapStyleOptions extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<MapStyleOptions> CREATOR = new zzh();
    private static final String zza = MapStyleOptions.class.getSimpleName();
    @SafeParcelable.Field(getter = "getJson", id = 2)
    private String zzb;

    @SafeParcelable.Constructor
    public MapStyleOptions(@SafeParcelable.Param(id = 2) @NonNull String json) {
        Preconditions.checkNotNull(json, "json must not be null");
        this.zzb = json;
    }

    @NonNull
    public static MapStyleOptions loadRawResourceStyle(@NonNull Context clientContext, int resourceId) {
        try {
            return new MapStyleOptions(new String(IOUtils.readInputStreamFully(clientContext.getResources().openRawResource(resourceId)), "UTF-8"));
        } catch (IOException e) {
            String obj = e.toString();
            throw new Resources.NotFoundException("Failed to read resource " + resourceId + ": " + obj);
        }
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeString(out, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
