package com.google.android.libraries.places.api.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import java.util.ArrayList;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzax implements Parcelable.Creator {
    zzax() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        Integer num;
        Integer num2;
        Parcel parcel2 = parcel;
        String readString = parcel.readInt() == 0 ? parcel.readString() : null;
        AddressComponents addressComponents = (AddressComponents) parcel2.readParcelable(Place.class.getClassLoader());
        ArrayList readArrayList = parcel2.readArrayList(Place.class.getClassLoader());
        Place.BusinessStatus businessStatus = (Place.BusinessStatus) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        OpeningHours openingHours = (OpeningHours) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue2 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue3 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Integer valueOf = parcel.readInt() == 0 ? Integer.valueOf(parcel.readInt()) : null;
        String readString2 = parcel.readInt() == 0 ? parcel.readString() : null;
        String readString3 = parcel.readInt() == 0 ? parcel.readString() : null;
        LatLng latLng = (LatLng) parcel2.readParcelable(Place.class.getClassLoader());
        String readString4 = parcel.readInt() == 0 ? parcel.readString() : null;
        OpeningHours openingHours2 = (OpeningHours) parcel2.readParcelable(Place.class.getClassLoader());
        String readString5 = parcel.readInt() == 0 ? parcel.readString() : null;
        ArrayList readArrayList2 = parcel2.readArrayList(Place.class.getClassLoader());
        PlusCode plusCode = (PlusCode) parcel2.readParcelable(Place.class.getClassLoader());
        Integer valueOf2 = parcel.readInt() == 0 ? Integer.valueOf(parcel.readInt()) : null;
        Double valueOf3 = parcel.readInt() == 0 ? Double.valueOf(parcel.readDouble()) : null;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue4 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        ArrayList readArrayList3 = parcel2.readArrayList(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue5 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue6 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue7 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue8 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue9 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue10 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue11 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue12 = (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader());
        ArrayList readArrayList4 = parcel2.readArrayList(Place.class.getClassLoader());
        if (parcel.readInt() == 0) {
            num = Integer.valueOf(parcel.readInt());
        } else {
            num = null;
        }
        if (parcel.readInt() == 0) {
            num2 = Integer.valueOf(parcel.readInt());
        } else {
            num2 = null;
        }
        return new zzay(readString, addressComponents, readArrayList, businessStatus, booleanPlaceAttributeValue, openingHours, booleanPlaceAttributeValue2, booleanPlaceAttributeValue3, valueOf, readString2, readString3, latLng, readString4, openingHours2, readString5, readArrayList2, plusCode, valueOf2, valueOf3, booleanPlaceAttributeValue4, readArrayList3, booleanPlaceAttributeValue5, booleanPlaceAttributeValue6, booleanPlaceAttributeValue7, booleanPlaceAttributeValue8, booleanPlaceAttributeValue9, booleanPlaceAttributeValue10, booleanPlaceAttributeValue11, booleanPlaceAttributeValue12, readArrayList4, num, num2, (LatLngBounds) parcel2.readParcelable(Place.class.getClassLoader()), (Uri) parcel2.readParcelable(Place.class.getClassLoader()), (Place.BooleanPlaceAttributeValue) parcel2.readParcelable(Place.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzay[i];
    }
}
