package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;

@SafeParcelable.Class(creator = "StampStyleCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StampStyle extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StampStyle> CREATOR = new zzo();
    @SafeParcelable.Field(getter = "getWrappedStampBinder", id = 2, type = "android.os.IBinder")
    @NonNull
    protected final BitmapDescriptor zza;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static abstract class Builder<T extends Builder<T>> {
        BitmapDescriptor zza;

        Builder() {
        }

        /* access modifiers changed from: protected */
        public abstract T self();

        public T stamp(BitmapDescriptor stamp) {
            this.zza = stamp;
            return self();
        }
    }

    @SafeParcelable.Constructor
    StampStyle(@SafeParcelable.Param(id = 2) IBinder iBinder) {
        this.zza = new BitmapDescriptor(IObjectWrapper.Stub.asInterface(iBinder));
    }

    @NonNull
    public BitmapDescriptor getStamp() {
        return this.zza;
    }

    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zza.zza().asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    protected StampStyle(@NonNull BitmapDescriptor stamp) {
        this.zza = stamp;
    }
}
