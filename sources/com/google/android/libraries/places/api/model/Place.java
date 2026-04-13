package com.google.android.libraries.places.api.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzjd;
import com.google.android.libraries.places.internal.zzjq;
import com.google.android.libraries.places.internal.zzkc;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class Place implements Parcelable {
    public static final int PRICE_LEVEL_MAX_VALUE = 4;
    public static final int PRICE_LEVEL_MIN_VALUE = 0;
    public static final double RATING_MAX_VALUE = 5.0d;
    public static final double RATING_MIN_VALUE = 1.0d;

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public enum BooleanPlaceAttributeValue implements Parcelable {
        UNKNOWN,
        TRUE,
        FALSE;
        
        @RecentlyNonNull
        public static final Parcelable.Creator<BooleanPlaceAttributeValue> CREATOR = null;

        static {
            CREATOR = new zzbr();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@RecentlyNonNull Parcel dest, int i) {
            dest.writeString(name());
        }
    }

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public Place build() {
            Place zza = zza();
            List<String> attributions = zza.getAttributions();
            if (attributions != null) {
                for (String isEmpty : attributions) {
                    zziy.zzk(!TextUtils.isEmpty(isEmpty), "Attributions must not contain null or empty values.");
                }
            }
            Integer priceLevel = zza.getPriceLevel();
            if (priceLevel != null) {
                zziy.zzm(zzkc.zzb(0, 4).zzd(priceLevel), "Price Level must not be out-of-range: %s to %s, but was: %s.", 0, 4, priceLevel);
            }
            Double rating = zza.getRating();
            if (rating != null) {
                Double valueOf = Double.valueOf(1.0d);
                Double valueOf2 = Double.valueOf(5.0d);
                zziy.zzm(zzkc.zzb(valueOf, valueOf2).zzd(rating), "Rating must not be out-of-range: %s to %s, but was: %s.", valueOf, valueOf2, rating);
            }
            Integer userRatingsTotal = zza.getUserRatingsTotal();
            if (userRatingsTotal == null || zzkc.zza(0).zzd(userRatingsTotal)) {
                if (attributions != null) {
                    setAttributions(zzjq.zzj(attributions));
                }
                List<PhotoMetadata> photoMetadatas = zza.getPhotoMetadatas();
                if (photoMetadatas != null) {
                    setPhotoMetadatas(zzjq.zzj(photoMetadatas));
                }
                List<Type> types = zza.getTypes();
                if (types != null) {
                    setTypes(zzjq.zzj(types));
                }
                List<OpeningHours> secondaryOpeningHours = zza.getSecondaryOpeningHours();
                if (secondaryOpeningHours != null) {
                    setSecondaryOpeningHours(zzjq.zzj(secondaryOpeningHours));
                }
                return zza();
            }
            throw new IllegalStateException(zzjd.zza("User Ratings Total must not be < 0, but was: %s.", userRatingsTotal));
        }

        @RecentlyNullable
        public abstract String getAddress();

        @RecentlyNullable
        public abstract AddressComponents getAddressComponents();

        @RecentlyNullable
        public abstract List<String> getAttributions();

        @RecentlyNullable
        public abstract BusinessStatus getBusinessStatus();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getCurbsidePickup();

        @RecentlyNullable
        public abstract OpeningHours getCurrentOpeningHours();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getDelivery();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getDineIn();

        @ColorInt
        @RecentlyNullable
        public abstract Integer getIconBackgroundColor();

        @RecentlyNullable
        public abstract String getIconUrl();

        @RecentlyNullable
        public abstract String getId();

        @RecentlyNullable
        public abstract LatLng getLatLng();

        @RecentlyNullable
        public abstract String getName();

        @RecentlyNullable
        public abstract OpeningHours getOpeningHours();

        @RecentlyNullable
        public abstract String getPhoneNumber();

        @RecentlyNullable
        public abstract List<PhotoMetadata> getPhotoMetadatas();

        @RecentlyNullable
        public abstract PlusCode getPlusCode();

        @IntRange(from = 0, to = 4)
        @RecentlyNullable
        public abstract Integer getPriceLevel();

        @FloatRange(from = 1.0d, to = 5.0d)
        @RecentlyNullable
        public abstract Double getRating();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getReservable();

        @RecentlyNullable
        public abstract List<OpeningHours> getSecondaryOpeningHours();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesBeer();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesBreakfast();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesBrunch();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesDinner();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesLunch();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesVegetarianFood();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getServesWine();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getTakeout();

        @RecentlyNullable
        public abstract List<Type> getTypes();

        @IntRange(from = 0)
        @RecentlyNullable
        public abstract Integer getUserRatingsTotal();

        @RecentlyNullable
        public abstract Integer getUtcOffsetMinutes();

        @RecentlyNullable
        public abstract LatLngBounds getViewport();

        @RecentlyNullable
        public abstract Uri getWebsiteUri();

        @RecentlyNonNull
        public abstract BooleanPlaceAttributeValue getWheelchairAccessibleEntrance();

        @RecentlyNonNull
        public abstract Builder setAddress(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setAddressComponents(@Nullable AddressComponents addressComponents);

        @RecentlyNonNull
        public abstract Builder setAttributions(@Nullable List<String> list);

        @RecentlyNonNull
        public abstract Builder setBusinessStatus(@Nullable BusinessStatus businessStatus);

        @RecentlyNonNull
        public abstract Builder setCurbsidePickup(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setCurrentOpeningHours(@Nullable OpeningHours openingHours);

        @RecentlyNonNull
        public abstract Builder setDelivery(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setDineIn(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setIconBackgroundColor(@Nullable Integer num);

        @RecentlyNonNull
        public abstract Builder setIconUrl(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setId(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setLatLng(@Nullable LatLng latLng);

        @RecentlyNonNull
        public abstract Builder setName(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setOpeningHours(@Nullable OpeningHours openingHours);

        @RecentlyNonNull
        public abstract Builder setPhoneNumber(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setPhotoMetadatas(@Nullable List<PhotoMetadata> list);

        @RecentlyNonNull
        public abstract Builder setPlusCode(@Nullable PlusCode plusCode);

        @RecentlyNonNull
        public abstract Builder setPriceLevel(@IntRange(from = 0, to = 4) @Nullable Integer num);

        @RecentlyNonNull
        public abstract Builder setRating(@FloatRange(from = 1.0d, to = 5.0d) @Nullable Double d);

        @RecentlyNonNull
        public abstract Builder setReservable(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setSecondaryOpeningHours(@Nullable List<OpeningHours> list);

        @RecentlyNonNull
        public abstract Builder setServesBeer(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesBreakfast(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesBrunch(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesDinner(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesLunch(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesVegetarianFood(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setServesWine(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setTakeout(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        @RecentlyNonNull
        public abstract Builder setTypes(@Nullable List<Type> list);

        @RecentlyNonNull
        public abstract Builder setUserRatingsTotal(@IntRange(from = 0) @Nullable Integer num);

        @RecentlyNonNull
        public abstract Builder setUtcOffsetMinutes(@Nullable Integer num);

        @RecentlyNonNull
        public abstract Builder setViewport(@Nullable LatLngBounds latLngBounds);

        @RecentlyNonNull
        public abstract Builder setWebsiteUri(@Nullable Uri uri);

        @RecentlyNonNull
        public abstract Builder setWheelchairAccessibleEntrance(@RecentlyNonNull BooleanPlaceAttributeValue booleanPlaceAttributeValue);

        /* access modifiers changed from: package-private */
        public abstract Place zza();
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public enum BusinessStatus implements Parcelable {
        OPERATIONAL,
        CLOSED_TEMPORARILY,
        CLOSED_PERMANENTLY;
        
        @RecentlyNonNull
        public static final Parcelable.Creator<BusinessStatus> CREATOR = null;

        static {
            CREATOR = new zzbs();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@RecentlyNonNull Parcel dest, int i) {
            dest.writeString(name());
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public enum Field implements Parcelable {
        ADDRESS,
        ADDRESS_COMPONENTS,
        BUSINESS_STATUS,
        CURBSIDE_PICKUP,
        CURRENT_OPENING_HOURS,
        DELIVERY,
        DINE_IN,
        ICON_BACKGROUND_COLOR,
        ICON_URL,
        ID,
        LAT_LNG,
        NAME,
        OPENING_HOURS,
        PHONE_NUMBER,
        PHOTO_METADATAS,
        PLUS_CODE,
        PRICE_LEVEL,
        RATING,
        RESERVABLE,
        SECONDARY_OPENING_HOURS,
        SERVES_BEER,
        SERVES_BREAKFAST,
        SERVES_BRUNCH,
        SERVES_DINNER,
        SERVES_LUNCH,
        SERVES_VEGETARIAN_FOOD,
        SERVES_WINE,
        TAKEOUT,
        TYPES,
        USER_RATINGS_TOTAL,
        UTC_OFFSET,
        VIEWPORT,
        WEBSITE_URI,
        WHEELCHAIR_ACCESSIBLE_ENTRANCE;
        
        @RecentlyNonNull
        public static final Parcelable.Creator<Field> CREATOR = null;

        static {
            CREATOR = new zzbt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@RecentlyNonNull Parcel dest, int i) {
            dest.writeString(name());
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public enum Type implements Parcelable {
        OTHER,
        ACCOUNTING,
        ADMINISTRATIVE_AREA_LEVEL_1,
        ADMINISTRATIVE_AREA_LEVEL_2,
        ADMINISTRATIVE_AREA_LEVEL_3,
        ADMINISTRATIVE_AREA_LEVEL_4,
        ADMINISTRATIVE_AREA_LEVEL_5,
        AIRPORT,
        AMUSEMENT_PARK,
        AQUARIUM,
        ARCHIPELAGO,
        ART_GALLERY,
        ATM,
        BAKERY,
        BANK,
        BAR,
        BEAUTY_SALON,
        BICYCLE_STORE,
        BOOK_STORE,
        BOWLING_ALLEY,
        BUS_STATION,
        CAFE,
        CAMPGROUND,
        CAR_DEALER,
        CAR_RENTAL,
        CAR_REPAIR,
        CAR_WASH,
        CASINO,
        CEMETERY,
        CHURCH,
        CITY_HALL,
        CLOTHING_STORE,
        COLLOQUIAL_AREA,
        CONTINENT,
        CONVENIENCE_STORE,
        COUNTRY,
        COURTHOUSE,
        DENTIST,
        DEPARTMENT_STORE,
        DOCTOR,
        DRUGSTORE,
        ELECTRICIAN,
        ELECTRONICS_STORE,
        EMBASSY,
        ESTABLISHMENT,
        FINANCE,
        FIRE_STATION,
        FLOOR,
        FLORIST,
        FOOD,
        FUNERAL_HOME,
        FURNITURE_STORE,
        GAS_STATION,
        GENERAL_CONTRACTOR,
        GEOCODE,
        GROCERY_OR_SUPERMARKET,
        GYM,
        HAIR_CARE,
        HARDWARE_STORE,
        HEALTH,
        HINDU_TEMPLE,
        HOME_GOODS_STORE,
        HOSPITAL,
        INSURANCE_AGENCY,
        INTERSECTION,
        JEWELRY_STORE,
        LAUNDRY,
        LAWYER,
        LIBRARY,
        LIGHT_RAIL_STATION,
        LIQUOR_STORE,
        LOCAL_GOVERNMENT_OFFICE,
        LOCALITY,
        LOCKSMITH,
        LODGING,
        MEAL_DELIVERY,
        MEAL_TAKEAWAY,
        MOSQUE,
        MOVIE_RENTAL,
        MOVIE_THEATER,
        MOVING_COMPANY,
        MUSEUM,
        NATURAL_FEATURE,
        NEIGHBORHOOD,
        NIGHT_CLUB,
        PAINTER,
        PARK,
        PARKING,
        PET_STORE,
        PHARMACY,
        PHYSIOTHERAPIST,
        PLACE_OF_WORSHIP,
        PLUMBER,
        PLUS_CODE,
        POINT_OF_INTEREST,
        POLICE,
        POLITICAL,
        POST_BOX,
        POST_OFFICE,
        POSTAL_CODE_PREFIX,
        POSTAL_CODE_SUFFIX,
        POSTAL_CODE,
        POSTAL_TOWN,
        PREMISE,
        PRIMARY_SCHOOL,
        REAL_ESTATE_AGENCY,
        RESTAURANT,
        ROOFING_CONTRACTOR,
        ROOM,
        ROUTE,
        RV_PARK,
        SCHOOL,
        SECONDARY_SCHOOL,
        SHOE_STORE,
        SHOPPING_MALL,
        SPA,
        STADIUM,
        STORAGE,
        STORE,
        STREET_ADDRESS,
        STREET_NUMBER,
        SUBLOCALITY_LEVEL_1,
        SUBLOCALITY_LEVEL_2,
        SUBLOCALITY_LEVEL_3,
        SUBLOCALITY_LEVEL_4,
        SUBLOCALITY_LEVEL_5,
        SUBLOCALITY,
        SUBPREMISE,
        SUBWAY_STATION,
        SUPERMARKET,
        SYNAGOGUE,
        TAXI_STAND,
        TOURIST_ATTRACTION,
        TOWN_SQUARE,
        TRAIN_STATION,
        TRANSIT_STATION,
        TRAVEL_AGENCY,
        UNIVERSITY,
        VETERINARY_CARE,
        ZOO;
        
        @RecentlyNonNull
        public static final Parcelable.Creator<Type> CREATOR = null;

        static {
            CREATOR = new zzbu();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@RecentlyNonNull Parcel dest, int i) {
            dest.writeString(name());
        }
    }

    @RecentlyNonNull
    public static Builder builder() {
        zzs zzs = new zzs();
        BooleanPlaceAttributeValue booleanPlaceAttributeValue = BooleanPlaceAttributeValue.UNKNOWN;
        zzs.setCurbsidePickup(booleanPlaceAttributeValue);
        zzs.setDelivery(booleanPlaceAttributeValue);
        zzs.setDineIn(booleanPlaceAttributeValue);
        zzs.setReservable(booleanPlaceAttributeValue);
        zzs.setServesBeer(booleanPlaceAttributeValue);
        zzs.setServesBreakfast(booleanPlaceAttributeValue);
        zzs.setServesBrunch(booleanPlaceAttributeValue);
        zzs.setServesDinner(booleanPlaceAttributeValue);
        zzs.setServesLunch(booleanPlaceAttributeValue);
        zzs.setServesVegetarianFood(booleanPlaceAttributeValue);
        zzs.setServesWine(booleanPlaceAttributeValue);
        zzs.setTakeout(booleanPlaceAttributeValue);
        zzs.setWheelchairAccessibleEntrance(booleanPlaceAttributeValue);
        return zzs;
    }

    @RecentlyNullable
    public abstract String getAddress();

    @RecentlyNullable
    public abstract AddressComponents getAddressComponents();

    @RecentlyNullable
    public abstract List<String> getAttributions();

    @RecentlyNullable
    public abstract BusinessStatus getBusinessStatus();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getCurbsidePickup();

    @RecentlyNullable
    public abstract OpeningHours getCurrentOpeningHours();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getDelivery();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getDineIn();

    @ColorInt
    @RecentlyNullable
    public abstract Integer getIconBackgroundColor();

    @RecentlyNullable
    public abstract String getIconUrl();

    @RecentlyNullable
    public abstract String getId();

    @RecentlyNullable
    public abstract LatLng getLatLng();

    @RecentlyNullable
    public abstract String getName();

    @RecentlyNullable
    public abstract OpeningHours getOpeningHours();

    @RecentlyNullable
    public abstract String getPhoneNumber();

    @RecentlyNullable
    public abstract List<PhotoMetadata> getPhotoMetadatas();

    @RecentlyNullable
    public abstract PlusCode getPlusCode();

    @IntRange(from = 0, to = 4)
    @RecentlyNullable
    public abstract Integer getPriceLevel();

    @FloatRange(from = 1.0d, to = 5.0d)
    @RecentlyNullable
    public abstract Double getRating();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getReservable();

    @RecentlyNullable
    public abstract List<OpeningHours> getSecondaryOpeningHours();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesBeer();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesBreakfast();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesBrunch();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesDinner();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesLunch();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesVegetarianFood();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getServesWine();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getTakeout();

    @RecentlyNullable
    public abstract List<Type> getTypes();

    @IntRange(from = 0)
    @RecentlyNullable
    public abstract Integer getUserRatingsTotal();

    @RecentlyNullable
    public abstract Integer getUtcOffsetMinutes();

    @RecentlyNullable
    public abstract LatLngBounds getViewport();

    @RecentlyNullable
    public abstract Uri getWebsiteUri();

    @RecentlyNonNull
    public abstract BooleanPlaceAttributeValue getWheelchairAccessibleEntrance();

    @Deprecated
    @RecentlyNullable
    public Boolean isOpen() {
        return isOpen(System.currentTimeMillis());
    }

    @Deprecated
    @RecentlyNullable
    public Boolean isOpen(long utcTimeMillis) {
        return zzbq.zzb(this, utcTimeMillis);
    }
}
