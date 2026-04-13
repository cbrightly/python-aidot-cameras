package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "TileCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class Tile extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<Tile> CREATOR = new zzz();
    @SafeParcelable.Field(id = 4)
    @Nullable
    public final byte[] data;
    @SafeParcelable.Field(id = 3)
    public final int height;
    @SafeParcelable.Field(id = 2)
    public final int width;

    @SafeParcelable.Constructor
    public Tile(@SafeParcelable.Param(id = 2) int width2, @SafeParcelable.Param(id = 3) int height2, @SafeParcelable.Param(id = 4) @Nullable byte[] data2) {
        this.width = width2;
        this.height = height2;
        this.data = data2;
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 2, this.width);
        SafeParcelWriter.writeInt(out, 3, this.height);
        SafeParcelWriter.writeByteArray(out, 4, this.data, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
