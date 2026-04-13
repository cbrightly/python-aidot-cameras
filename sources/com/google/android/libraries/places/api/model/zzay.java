package com.google.android.libraries.places.api.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzay extends zzt {
    public static final Parcelable.Creator<zzay> CREATOR = new zzax();

    zzay(@Nullable String str, @Nullable AddressComponents addressComponents, @Nullable List list, @Nullable Place.BusinessStatus businessStatus, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue, @Nullable OpeningHours openingHours, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue2, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue3, @Nullable Integer num, @Nullable String str2, @Nullable String str3, @Nullable LatLng latLng, @Nullable String str4, @Nullable OpeningHours openingHours2, @Nullable String str5, @Nullable List list2, @Nullable PlusCode plusCode, @Nullable Integer num2, @Nullable Double d, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue4, @Nullable List list3, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue5, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue6, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue7, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue8, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue9, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue10, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue11, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue12, @Nullable List list4, @Nullable Integer num3, @Nullable Integer num4, @Nullable LatLngBounds latLngBounds, @Nullable Uri uri, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue13) {
        super(str, addressComponents, list, businessStatus, booleanPlaceAttributeValue, openingHours, booleanPlaceAttributeValue2, booleanPlaceAttributeValue3, num, str2, str3, latLng, str4, openingHours2, str5, list2, plusCode, num2, d, booleanPlaceAttributeValue4, list3, booleanPlaceAttributeValue5, booleanPlaceAttributeValue6, booleanPlaceAttributeValue7, booleanPlaceAttributeValue8, booleanPlaceAttributeValue9, booleanPlaceAttributeValue10, booleanPlaceAttributeValue11, booleanPlaceAttributeValue12, list4, num3, num4, latLngBounds, uri, booleanPlaceAttributeValue13);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        if (getAddress() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getAddress());
        }
        parcel.writeParcelable(getAddressComponents(), i);
        parcel.writeList(getAttributions());
        parcel.writeParcelable(getBusinessStatus(), i);
        parcel.writeParcelable(getCurbsidePickup(), i);
        parcel.writeParcelable(getCurrentOpeningHours(), i);
        parcel.writeParcelable(getDelivery(), i);
        parcel.writeParcelable(getDineIn(), i);
        if (getIconBackgroundColor() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeInt(getIconBackgroundColor().intValue());
        }
        if (getIconUrl() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getIconUrl());
        }
        if (getId() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getId());
        }
        parcel.writeParcelable(getLatLng(), i);
        if (getName() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getName());
        }
        parcel.writeParcelable(getOpeningHours(), i);
        if (getPhoneNumber() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getPhoneNumber());
        }
        parcel.writeList(getPhotoMetadatas());
        parcel.writeParcelable(getPlusCode(), i);
        if (getPriceLevel() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeInt(getPriceLevel().intValue());
        }
        if (getRating() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeDouble(getRating().doubleValue());
        }
        parcel.writeParcelable(getReservable(), i);
        parcel.writeList(getSecondaryOpeningHours());
        parcel.writeParcelable(getServesBeer(), i);
        parcel.writeParcelable(getServesBreakfast(), i);
        parcel.writeParcelable(getServesBrunch(), i);
        parcel.writeParcelable(getServesDinner(), i);
        parcel.writeParcelable(getServesLunch(), i);
        parcel.writeParcelable(getServesVegetarianFood(), i);
        parcel.writeParcelable(getServesWine(), i);
        parcel.writeParcelable(getTakeout(), i);
        parcel.writeList(getTypes());
        if (getUserRatingsTotal() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeInt(getUserRatingsTotal().intValue());
        }
        if (getUtcOffsetMinutes() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeInt(getUtcOffsetMinutes().intValue());
        }
        parcel.writeParcelable(getViewport(), i);
        parcel.writeParcelable(getWebsiteUri(), i);
        parcel.writeParcelable(getWheelchairAccessibleEntrance(), i);
    }
}
