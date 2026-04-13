package com.google.android.libraries.places.internal;

import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.DayOfWeek;
import com.google.android.libraries.places.api.model.LocalDate;
import com.google.android.libraries.places.api.model.LocalTime;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Period;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.PlusCode;
import com.google.android.libraries.places.api.model.SpecialDay;
import com.google.android.libraries.places.api.model.TimeOfWeek;
import com.google.android.libraries.places.internal.zzfs;
import com.luck.picture.lib.config.PictureMimeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfp {
    private static final zzjt zza;
    private static final zzjt zzb;
    private static final zzjt zzc;

    static {
        zzjs zzjs = new zzjs();
        zzjs.zza("OPERATIONAL", Place.BusinessStatus.OPERATIONAL);
        zzjs.zza("CLOSED_TEMPORARILY", Place.BusinessStatus.CLOSED_TEMPORARILY);
        zzjs.zza("CLOSED_PERMANENTLY", Place.BusinessStatus.CLOSED_PERMANENTLY);
        zza = zzjs.zzb();
        zzjs zzjs2 = new zzjs();
        zzjs2.zza(PlaceTypes.ACCOUNTING, Place.Type.ACCOUNTING);
        zzjs2.zza(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_1, Place.Type.ADMINISTRATIVE_AREA_LEVEL_1);
        zzjs2.zza(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_2, Place.Type.ADMINISTRATIVE_AREA_LEVEL_2);
        zzjs2.zza(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_3, Place.Type.ADMINISTRATIVE_AREA_LEVEL_3);
        zzjs2.zza(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_4, Place.Type.ADMINISTRATIVE_AREA_LEVEL_4);
        zzjs2.zza(PlaceTypes.ADMINISTRATIVE_AREA_LEVEL_5, Place.Type.ADMINISTRATIVE_AREA_LEVEL_5);
        zzjs2.zza(PlaceTypes.AIRPORT, Place.Type.AIRPORT);
        zzjs2.zza(PlaceTypes.AMUSEMENT_PARK, Place.Type.AMUSEMENT_PARK);
        zzjs2.zza(PlaceTypes.AQUARIUM, Place.Type.AQUARIUM);
        zzjs2.zza(PlaceTypes.ARCHIPELAGO, Place.Type.ARCHIPELAGO);
        zzjs2.zza(PlaceTypes.ART_GALLERY, Place.Type.ART_GALLERY);
        zzjs2.zza(PlaceTypes.ATM, Place.Type.ATM);
        zzjs2.zza(PlaceTypes.BAKERY, Place.Type.BAKERY);
        zzjs2.zza(PlaceTypes.BANK, Place.Type.BANK);
        zzjs2.zza(PlaceTypes.BAR, Place.Type.BAR);
        zzjs2.zza(PlaceTypes.BEAUTY_SALON, Place.Type.BEAUTY_SALON);
        zzjs2.zza(PlaceTypes.BICYCLE_STORE, Place.Type.BICYCLE_STORE);
        zzjs2.zza(PlaceTypes.BOOK_STORE, Place.Type.BOOK_STORE);
        zzjs2.zza(PlaceTypes.BOWLING_ALLEY, Place.Type.BOWLING_ALLEY);
        zzjs2.zza(PlaceTypes.BUS_STATION, Place.Type.BUS_STATION);
        zzjs2.zza(PlaceTypes.CAFE, Place.Type.CAFE);
        zzjs2.zza(PlaceTypes.CAMPGROUND, Place.Type.CAMPGROUND);
        zzjs2.zza(PlaceTypes.CAR_DEALER, Place.Type.CAR_DEALER);
        zzjs2.zza(PlaceTypes.CAR_RENTAL, Place.Type.CAR_RENTAL);
        zzjs2.zza(PlaceTypes.CAR_REPAIR, Place.Type.CAR_REPAIR);
        zzjs2.zza(PlaceTypes.CAR_WASH, Place.Type.CAR_WASH);
        zzjs2.zza(PlaceTypes.CASINO, Place.Type.CASINO);
        zzjs2.zza(PlaceTypes.CEMETERY, Place.Type.CEMETERY);
        zzjs2.zza(PlaceTypes.CHURCH, Place.Type.CHURCH);
        zzjs2.zza(PlaceTypes.CITY_HALL, Place.Type.CITY_HALL);
        zzjs2.zza(PlaceTypes.CLOTHING_STORE, Place.Type.CLOTHING_STORE);
        zzjs2.zza(PlaceTypes.COLLOQUIAL_AREA, Place.Type.COLLOQUIAL_AREA);
        zzjs2.zza(PlaceTypes.CONTINENT, Place.Type.CONTINENT);
        zzjs2.zza(PlaceTypes.CONVENIENCE_STORE, Place.Type.CONVENIENCE_STORE);
        zzjs2.zza(PlaceTypes.COUNTRY, Place.Type.COUNTRY);
        zzjs2.zza(PlaceTypes.COURTHOUSE, Place.Type.COURTHOUSE);
        zzjs2.zza(PlaceTypes.DENTIST, Place.Type.DENTIST);
        zzjs2.zza(PlaceTypes.DEPARTMENT_STORE, Place.Type.DEPARTMENT_STORE);
        zzjs2.zza(PlaceTypes.DOCTOR, Place.Type.DOCTOR);
        zzjs2.zza(PlaceTypes.DRUGSTORE, Place.Type.DRUGSTORE);
        zzjs2.zza(PlaceTypes.ELECTRICIAN, Place.Type.ELECTRICIAN);
        zzjs2.zza(PlaceTypes.ELECTRONICS_STORE, Place.Type.ELECTRONICS_STORE);
        zzjs2.zza(PlaceTypes.EMBASSY, Place.Type.EMBASSY);
        zzjs2.zza(PlaceTypes.ESTABLISHMENT, Place.Type.ESTABLISHMENT);
        zzjs2.zza(PlaceTypes.FINANCE, Place.Type.FINANCE);
        zzjs2.zza(PlaceTypes.FIRE_STATION, Place.Type.FIRE_STATION);
        zzjs2.zza(PlaceTypes.FLOOR, Place.Type.FLOOR);
        zzjs2.zza(PlaceTypes.FLORIST, Place.Type.FLORIST);
        zzjs2.zza(PlaceTypes.FOOD, Place.Type.FOOD);
        zzjs2.zza(PlaceTypes.FUNERAL_HOME, Place.Type.FUNERAL_HOME);
        zzjs2.zza(PlaceTypes.FURNITURE_STORE, Place.Type.FURNITURE_STORE);
        zzjs2.zza(PlaceTypes.GAS_STATION, Place.Type.GAS_STATION);
        zzjs2.zza(PlaceTypes.GENERAL_CONTRACTOR, Place.Type.GENERAL_CONTRACTOR);
        zzjs2.zza(PlaceTypes.GEOCODE, Place.Type.GEOCODE);
        zzjs2.zza("grocery_or_supermarket", Place.Type.GROCERY_OR_SUPERMARKET);
        zzjs2.zza(PlaceTypes.GYM, Place.Type.GYM);
        zzjs2.zza(PlaceTypes.HAIR_CARE, Place.Type.HAIR_CARE);
        zzjs2.zza(PlaceTypes.HARDWARE_STORE, Place.Type.HARDWARE_STORE);
        zzjs2.zza(PlaceTypes.HEALTH, Place.Type.HEALTH);
        zzjs2.zza(PlaceTypes.HINDU_TEMPLE, Place.Type.HINDU_TEMPLE);
        zzjs2.zza(PlaceTypes.HOME_GOODS_STORE, Place.Type.HOME_GOODS_STORE);
        zzjs2.zza(PlaceTypes.HOSPITAL, Place.Type.HOSPITAL);
        zzjs2.zza(PlaceTypes.INSURANCE_AGENCY, Place.Type.INSURANCE_AGENCY);
        zzjs2.zza(PlaceTypes.INTERSECTION, Place.Type.INTERSECTION);
        zzjs2.zza(PlaceTypes.JEWELRY_STORE, Place.Type.JEWELRY_STORE);
        zzjs2.zza(PlaceTypes.LAUNDRY, Place.Type.LAUNDRY);
        zzjs2.zza(PlaceTypes.LAWYER, Place.Type.LAWYER);
        zzjs2.zza(PlaceTypes.LIBRARY, Place.Type.LIBRARY);
        zzjs2.zza(PlaceTypes.LIGHT_RAIL_STATION, Place.Type.LIGHT_RAIL_STATION);
        zzjs2.zza(PlaceTypes.LIQUOR_STORE, Place.Type.LIQUOR_STORE);
        zzjs2.zza(PlaceTypes.LOCAL_GOVERNMENT_OFFICE, Place.Type.LOCAL_GOVERNMENT_OFFICE);
        zzjs2.zza(PlaceTypes.LOCALITY, Place.Type.LOCALITY);
        zzjs2.zza(PlaceTypes.LOCKSMITH, Place.Type.LOCKSMITH);
        zzjs2.zza(PlaceTypes.LODGING, Place.Type.LODGING);
        zzjs2.zza(PlaceTypes.MEAL_DELIVERY, Place.Type.MEAL_DELIVERY);
        zzjs2.zza(PlaceTypes.MEAL_TAKEAWAY, Place.Type.MEAL_TAKEAWAY);
        zzjs2.zza(PlaceTypes.MOSQUE, Place.Type.MOSQUE);
        zzjs2.zza(PlaceTypes.MOVIE_RENTAL, Place.Type.MOVIE_RENTAL);
        zzjs2.zza(PlaceTypes.MOVIE_THEATER, Place.Type.MOVIE_THEATER);
        zzjs2.zza(PlaceTypes.MOVING_COMPANY, Place.Type.MOVING_COMPANY);
        zzjs2.zza(PlaceTypes.MUSEUM, Place.Type.MUSEUM);
        zzjs2.zza(PlaceTypes.NATURAL_FEATURE, Place.Type.NATURAL_FEATURE);
        zzjs2.zza(PlaceTypes.NEIGHBORHOOD, Place.Type.NEIGHBORHOOD);
        zzjs2.zza(PlaceTypes.NIGHT_CLUB, Place.Type.NIGHT_CLUB);
        zzjs2.zza(PlaceTypes.PAINTER, Place.Type.PAINTER);
        zzjs2.zza(PlaceTypes.PARK, Place.Type.PARK);
        zzjs2.zza(PlaceTypes.PARKING, Place.Type.PARKING);
        zzjs2.zza(PlaceTypes.PET_STORE, Place.Type.PET_STORE);
        zzjs2.zza(PlaceTypes.PHARMACY, Place.Type.PHARMACY);
        zzjs2.zza(PlaceTypes.PHYSIOTHERAPIST, Place.Type.PHYSIOTHERAPIST);
        zzjs2.zza(PlaceTypes.PLACE_OF_WORSHIP, Place.Type.PLACE_OF_WORSHIP);
        zzjs2.zza(PlaceTypes.PLUMBER, Place.Type.PLUMBER);
        zzjs2.zza(PlaceTypes.PLUS_CODE, Place.Type.PLUS_CODE);
        zzjs2.zza(PlaceTypes.POINT_OF_INTEREST, Place.Type.POINT_OF_INTEREST);
        zzjs2.zza(PlaceTypes.POLICE, Place.Type.POLICE);
        zzjs2.zza(PlaceTypes.POLITICAL, Place.Type.POLITICAL);
        zzjs2.zza(PlaceTypes.POST_BOX, Place.Type.POST_BOX);
        zzjs2.zza(PlaceTypes.POST_OFFICE, Place.Type.POST_OFFICE);
        zzjs2.zza(PlaceTypes.POSTAL_CODE_PREFIX, Place.Type.POSTAL_CODE_PREFIX);
        zzjs2.zza(PlaceTypes.POSTAL_CODE_SUFFIX, Place.Type.POSTAL_CODE_SUFFIX);
        zzjs2.zza(PlaceTypes.POSTAL_CODE, Place.Type.POSTAL_CODE);
        zzjs2.zza(PlaceTypes.POSTAL_TOWN, Place.Type.POSTAL_TOWN);
        zzjs2.zza(PlaceTypes.PREMISE, Place.Type.PREMISE);
        zzjs2.zza(PlaceTypes.PRIMARY_SCHOOL, Place.Type.PRIMARY_SCHOOL);
        zzjs2.zza(PlaceTypes.REAL_ESTATE_AGENCY, Place.Type.REAL_ESTATE_AGENCY);
        zzjs2.zza(PlaceTypes.RESTAURANT, Place.Type.RESTAURANT);
        zzjs2.zza(PlaceTypes.ROOFING_CONTRACTOR, Place.Type.ROOFING_CONTRACTOR);
        zzjs2.zza(PlaceTypes.ROOM, Place.Type.ROOM);
        zzjs2.zza(PlaceTypes.ROUTE, Place.Type.ROUTE);
        zzjs2.zza(PlaceTypes.RV_PARK, Place.Type.RV_PARK);
        zzjs2.zza(PlaceTypes.SCHOOL, Place.Type.SCHOOL);
        zzjs2.zza(PlaceTypes.SECONDARY_SCHOOL, Place.Type.SECONDARY_SCHOOL);
        zzjs2.zza(PlaceTypes.SHOE_STORE, Place.Type.SHOE_STORE);
        zzjs2.zza(PlaceTypes.SHOPPING_MALL, Place.Type.SHOPPING_MALL);
        zzjs2.zza(PlaceTypes.SPA, Place.Type.SPA);
        zzjs2.zza(PlaceTypes.STADIUM, Place.Type.STADIUM);
        zzjs2.zza(PlaceTypes.STORAGE, Place.Type.STORAGE);
        zzjs2.zza(PlaceTypes.STORE, Place.Type.STORE);
        zzjs2.zza(PlaceTypes.STREET_ADDRESS, Place.Type.STREET_ADDRESS);
        zzjs2.zza(PlaceTypes.STREET_NUMBER, Place.Type.STREET_NUMBER);
        zzjs2.zza(PlaceTypes.SUBLOCALITY_LEVEL_1, Place.Type.SUBLOCALITY_LEVEL_1);
        zzjs2.zza(PlaceTypes.SUBLOCALITY_LEVEL_2, Place.Type.SUBLOCALITY_LEVEL_2);
        zzjs2.zza(PlaceTypes.SUBLOCALITY_LEVEL_3, Place.Type.SUBLOCALITY_LEVEL_3);
        zzjs2.zza(PlaceTypes.SUBLOCALITY_LEVEL_4, Place.Type.SUBLOCALITY_LEVEL_4);
        zzjs2.zza(PlaceTypes.SUBLOCALITY_LEVEL_5, Place.Type.SUBLOCALITY_LEVEL_5);
        zzjs2.zza(PlaceTypes.SUBLOCALITY, Place.Type.SUBLOCALITY);
        zzjs2.zza(PlaceTypes.SUBPREMISE, Place.Type.SUBPREMISE);
        zzjs2.zza(PlaceTypes.SUBWAY_STATION, Place.Type.SUBWAY_STATION);
        zzjs2.zza(PlaceTypes.SUPERMARKET, Place.Type.SUPERMARKET);
        zzjs2.zza(PlaceTypes.SYNAGOGUE, Place.Type.SYNAGOGUE);
        zzjs2.zza(PlaceTypes.TAXI_STAND, Place.Type.TAXI_STAND);
        zzjs2.zza(PlaceTypes.TOURIST_ATTRACTION, Place.Type.TOURIST_ATTRACTION);
        zzjs2.zza(PlaceTypes.TOWN_SQUARE, Place.Type.TOWN_SQUARE);
        zzjs2.zza(PlaceTypes.TRAIN_STATION, Place.Type.TRAIN_STATION);
        zzjs2.zza(PlaceTypes.TRANSIT_STATION, Place.Type.TRANSIT_STATION);
        zzjs2.zza(PlaceTypes.TRAVEL_AGENCY, Place.Type.TRAVEL_AGENCY);
        zzjs2.zza(PlaceTypes.UNIVERSITY, Place.Type.UNIVERSITY);
        zzjs2.zza(PlaceTypes.VETERINARY_CARE, Place.Type.VETERINARY_CARE);
        zzjs2.zza(PlaceTypes.ZOO, Place.Type.ZOO);
        zzb = zzjs2.zzb();
        zzjs zzjs3 = new zzjs();
        zzjs3.zza("ACCESS", OpeningHours.HoursType.ACCESS);
        zzjs3.zza("BREAKFAST", OpeningHours.HoursType.BREAKFAST);
        zzjs3.zza("BRUNCH", OpeningHours.HoursType.BRUNCH);
        zzjs3.zza("DELIVERY", OpeningHours.HoursType.DELIVERY);
        zzjs3.zza("DINNER", OpeningHours.HoursType.DINNER);
        zzjs3.zza("DRIVE_THROUGH", OpeningHours.HoursType.DRIVE_THROUGH);
        zzjs3.zza("HAPPY_HOUR", OpeningHours.HoursType.HAPPY_HOUR);
        zzjs3.zza("KITCHEN", OpeningHours.HoursType.KITCHEN);
        zzjs3.zza("LUNCH", OpeningHours.HoursType.LUNCH);
        zzjs3.zza("ONLINE_SERVICE_HOURS", OpeningHours.HoursType.ONLINE_SERVICE_HOURS);
        zzjs3.zza("PICKUP", OpeningHours.HoursType.PICKUP);
        zzjs3.zza("SENIOR_HOURS", OpeningHours.HoursType.SENIOR_HOURS);
        zzjs3.zza("TAKEOUT", OpeningHours.HoursType.TAKEOUT);
        zzc = zzjs3.zzb();
    }

    zzfp() {
    }

    @VisibleForTesting
    @Nullable
    static LocalDate zza(@Nullable String str) {
        if (str == null) {
            return null;
        }
        try {
            return LocalDate.newInstance(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(5, 7)), Integer.parseInt(str.substring(8, 10)));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unable to convert %s to LocalDate; date should be in format YYYY-MM-DD.", new Object[]{str}), e);
        }
    }

    static Place.BooleanPlaceAttributeValue zzb(@Nullable Boolean bool) {
        if (bool == null) {
            return Place.BooleanPlaceAttributeValue.UNKNOWN;
        }
        if (bool.booleanValue()) {
            return Place.BooleanPlaceAttributeValue.TRUE;
        }
        return Place.BooleanPlaceAttributeValue.FALSE;
    }

    @VisibleForTesting
    @Nullable
    static TimeOfWeek zzc(@Nullable zzfs.zzc.C0075zzc zzc2) {
        String format;
        DayOfWeek dayOfWeek;
        LocalDate localDate = null;
        if (zzc2 == null) {
            return null;
        }
        try {
            Integer zzb2 = zzc2.zzb();
            zziy.zzc(zzb2, "Unable to convert Pablo response to TimeOfWeek: The \"day\" field is missing.");
            String zzd = zzc2.zzd();
            zziy.zzc(zzd, "Unable to convert Pablo response to TimeOfWeek: The \"time\" field is missing.");
            boolean z = true;
            format = String.format("Unable to convert %s to LocalTime, must be of format \"hhmm\".", new Object[]{zzd});
            if (zzd.length() != 4) {
                z = false;
            }
            zziy.zze(z, format);
            LocalTime newInstance = LocalTime.newInstance(Integer.parseInt(zzd.substring(0, 2)), Integer.parseInt(zzd.substring(2, 4)));
            try {
                localDate = zza(zzc2.zzc());
            } catch (IllegalArgumentException e) {
            }
            switch (zzb2.intValue()) {
                case 0:
                    dayOfWeek = DayOfWeek.SUNDAY;
                    break;
                case 1:
                    dayOfWeek = DayOfWeek.MONDAY;
                    break;
                case 2:
                    dayOfWeek = DayOfWeek.TUESDAY;
                    break;
                case 3:
                    dayOfWeek = DayOfWeek.WEDNESDAY;
                    break;
                case 4:
                    dayOfWeek = DayOfWeek.THURSDAY;
                    break;
                case 5:
                    dayOfWeek = DayOfWeek.FRIDAY;
                    break;
                case 6:
                    dayOfWeek = DayOfWeek.SATURDAY;
                    break;
                default:
                    throw new IllegalArgumentException("pabloDayOfWeek can only be an integer between 0 and 6");
            }
            TimeOfWeek.Builder builder = TimeOfWeek.builder(dayOfWeek, newInstance);
            builder.setDate(localDate);
            builder.setTruncated(Boolean.TRUE.equals(zzc2.zza()));
            return builder.build();
        } catch (NumberFormatException e2) {
            throw new IllegalArgumentException(format, e2);
        } catch (NullPointerException e3) {
            throw new IllegalArgumentException(e3.getMessage(), e3);
        }
    }

    static List zzd(@Nullable List list) {
        return list != null ? list : new ArrayList();
    }

    @Nullable
    static List zze(@Nullable List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        zzko zzp = ((zzjq) list).listIterator(0);
        while (zzp.hasNext()) {
            String str = (String) zzp.next();
            zzjt zzjt = zzb;
            if (zzjt.containsKey(str)) {
                arrayList.add((Place.Type) zzjt.get(str));
            } else {
                z = true;
            }
        }
        if (z) {
            arrayList.add(Place.Type.OTHER);
        }
        return arrayList;
    }

    static final Place zzf(@Nullable zzfs zzfs, @Nullable List list) {
        AddressComponents addressComponents;
        LatLngBounds latLngBounds;
        LatLng latLng;
        Uri uri;
        Integer num;
        ArrayList arrayList;
        PlusCode plusCode;
        PhotoMetadata photoMetadata;
        String str;
        int i;
        int i2;
        AddressComponent addressComponent;
        Place.Builder builder = Place.builder();
        builder.setAttributions(list);
        if (zzfs != null) {
            zzjq zze = zzfs.zze();
            ArrayList arrayList2 = null;
            if (zze == null) {
                addressComponents = null;
            } else {
                ArrayList arrayList3 = new ArrayList();
                zzko zzp = zze.listIterator(0);
                while (zzp.hasNext()) {
                    zzfs.zza zza2 = (zzfs.zza) zzp.next();
                    if (zza2 == null) {
                        addressComponent = null;
                    } else {
                        try {
                            String zzb2 = zza2.zzb();
                            if (zzb2 != null) {
                                zzjq zza3 = zza2.zza();
                                if (zza3 != null) {
                                    AddressComponent.Builder builder2 = AddressComponent.builder(zzb2, zza3);
                                    builder2.setShortName(zza2.zzc());
                                    addressComponent = builder2.build();
                                } else {
                                    throw null;
                                }
                            } else {
                                throw null;
                            }
                        } catch (IllegalStateException | NullPointerException e) {
                            throw zzg(String.format("AddressComponent not properly defined (%s).", new Object[]{e.getMessage()}));
                        }
                    }
                    zzj(arrayList3, addressComponent);
                }
                addressComponents = AddressComponents.newInstance(arrayList3);
            }
            zzfs.zzb zza4 = zzfs.zza();
            if (zza4 != null) {
                latLng = zzh(zza4.zza());
                zzfs.zzb.C0074zzb zzb3 = zza4.zzb();
                if (zzb3 == null) {
                    latLngBounds = null;
                } else {
                    LatLng zzh = zzh(zzb3.zzb());
                    LatLng zzh2 = zzh(zzb3.zza());
                    latLngBounds = zzh != null ? zzh2 == null ? null : new LatLngBounds(zzh, zzh2) : null;
                }
            } else {
                latLng = null;
                latLngBounds = null;
            }
            String zzF = zzfs.zzF();
            if (zzF != null) {
                uri = Uri.parse(zzF);
            } else {
                uri = null;
            }
            String zzB = zzfs.zzB();
            String concat = zzB != null ? zzB.concat(PictureMimeType.PNG) : null;
            String zzA = zzfs.zzA();
            if (zzA != null) {
                try {
                    num = Integer.valueOf(Color.parseColor(zzA));
                } catch (IllegalArgumentException e2) {
                    num = null;
                }
            } else {
                num = null;
            }
            builder.setAddress(zzfs.zzz());
            builder.setAddressComponents(addressComponents);
            builder.setBusinessStatus((Place.BusinessStatus) zza.getOrDefault(zzfs.zzy(), (Object) null));
            builder.setCurbsidePickup(zzb(zzfs.zzi()));
            builder.setCurrentOpeningHours(zzi(zzfs.zzb()));
            builder.setDelivery(zzb(zzfs.zzj()));
            builder.setDineIn(zzb(zzfs.zzk()));
            builder.setIconBackgroundColor(num);
            builder.setIconUrl(concat);
            builder.setId(zzfs.zzE());
            builder.setLatLng(latLng);
            builder.setName(zzfs.zzD());
            builder.setOpeningHours(zzi(zzfs.zzc()));
            builder.setPhoneNumber(zzfs.zzC());
            zzjq zzf = zzfs.zzf();
            if (zzf != null) {
                arrayList = new ArrayList();
                zzko zzp2 = zzf.listIterator(0);
                while (zzp2.hasNext()) {
                    zzfs.zzd zzd = (zzfs.zzd) zzp2.next();
                    if (zzd == null) {
                        photoMetadata = null;
                    } else {
                        String zzd2 = zzd.zzd();
                        if (!TextUtils.isEmpty(zzd2)) {
                            Integer zzb4 = zzd.zzb();
                            Integer zzc2 = zzd.zzc();
                            PhotoMetadata.Builder builder3 = PhotoMetadata.builder(zzd2);
                            zzjq zza5 = zzd.zza();
                            if (zza5 == null || zza5.isEmpty()) {
                                str = "";
                            } else {
                                str = zzit.zzb(", ").zzc().zze(zza5);
                            }
                            builder3.setAttributions(str);
                            if (zzb4 == null) {
                                i = 0;
                            } else {
                                i = zzb4.intValue();
                            }
                            builder3.setHeight(i);
                            if (zzc2 == null) {
                                i2 = 0;
                            } else {
                                i2 = zzc2.intValue();
                            }
                            builder3.setWidth(i2);
                            photoMetadata = builder3.build();
                        } else {
                            throw zzg("Photo reference not provided for a PhotoMetadata result.");
                        }
                    }
                    zzj(arrayList, photoMetadata);
                }
            } else {
                arrayList = null;
            }
            builder.setPhotoMetadatas(arrayList);
            zzfs.zze zzd3 = zzfs.zzd();
            if (zzd3 == null) {
                plusCode = null;
            } else {
                PlusCode.Builder builder4 = PlusCode.builder();
                builder4.setCompoundCode(zzd3.zza());
                builder4.setGlobalCode(zzd3.zzb());
                plusCode = builder4.build();
            }
            builder.setPlusCode(plusCode);
            builder.setPriceLevel(zzfs.zzv());
            builder.setRating(zzfs.zzu());
            builder.setReservable(zzb(zzfs.zzl()));
            zzjq zzg = zzfs.zzg();
            if (zzg != null) {
                ArrayList arrayList4 = new ArrayList();
                zzko zzp3 = zzg.listIterator(0);
                while (zzp3.hasNext()) {
                    zzj(arrayList4, zzi((zzfs.zzc) zzp3.next()));
                }
                if (!arrayList4.isEmpty()) {
                    arrayList2 = arrayList4;
                }
            }
            builder.setSecondaryOpeningHours(arrayList2);
            builder.setServesBeer(zzb(zzfs.zzm()));
            builder.setServesBreakfast(zzb(zzfs.zzn()));
            builder.setServesBrunch(zzb(zzfs.zzn()));
            builder.setServesDinner(zzb(zzfs.zzo()));
            builder.setServesLunch(zzb(zzfs.zzp()));
            builder.setServesVegetarianFood(zzb(zzfs.zzq()));
            builder.setServesWine(zzb(zzfs.zzr()));
            builder.setTakeout(zzb(zzfs.zzs()));
            builder.setTypes(zze(zzfs.zzh()));
            builder.setUserRatingsTotal(zzfs.zzw());
            builder.setUtcOffsetMinutes(zzfs.zzx());
            builder.setViewport(latLngBounds);
            builder.setWebsiteUri(uri);
            builder.setWheelchairAccessibleEntrance(zzb(zzfs.zzt()));
        }
        return builder.build();
    }

    private static ApiException zzg(String str) {
        return new ApiException(new Status(8, "Unexpected server error: ".concat(String.valueOf(str))));
    }

    @Nullable
    private static LatLng zzh(@Nullable zzfs.zzb.zza zza2) {
        if (zza2 == null) {
            return null;
        }
        Double zza3 = zza2.zza();
        Double zzb2 = zza2.zzb();
        if (zza3 == null || zzb2 == null) {
            return null;
        }
        return new LatLng(zza3.doubleValue(), zzb2.doubleValue());
    }

    @Nullable
    private static OpeningHours zzi(@Nullable zzfs.zzc zzc2) {
        ArrayList arrayList;
        SpecialDay specialDay;
        Period period;
        if (zzc2 == null) {
            return null;
        }
        OpeningHours.Builder builder = OpeningHours.builder();
        zzjq zza2 = zzc2.zza();
        if (zza2 != null) {
            arrayList = new ArrayList();
            zzko zzp = zza2.listIterator(0);
            while (zzp.hasNext()) {
                zzfs.zzc.zza zza3 = (zzfs.zzc.zza) zzp.next();
                if (zza3 != null) {
                    Period.Builder builder2 = Period.builder();
                    builder2.setOpen(zzc(zza3.zzb()));
                    builder2.setClose(zzc(zza3.zza()));
                    period = builder2.build();
                } else {
                    period = null;
                }
                zzj(arrayList, period);
            }
        } else {
            arrayList = null;
        }
        builder.setPeriods(zzd(arrayList));
        builder.setWeekdayText(zzd(zzc2.zzc()));
        builder.setHoursType((OpeningHours.HoursType) zzc.getOrDefault(zzc2.zzd(), (Object) null));
        zzjq zzb2 = zzc2.zzb();
        ArrayList arrayList2 = new ArrayList();
        if (zzb2 != null) {
            zzko zzp2 = zzb2.listIterator(0);
            while (zzp2.hasNext()) {
                zzfs.zzc.zzb zzb3 = (zzfs.zzc.zzb) zzp2.next();
                if (zzb3 == null) {
                    specialDay = null;
                } else {
                    try {
                        LocalDate zza4 = zza(zzb3.zzb());
                        if (zza4 != null) {
                            SpecialDay.Builder builder3 = SpecialDay.builder(zza4);
                            builder3.setExceptional(Boolean.TRUE.equals(zzb3.zza()));
                            specialDay = builder3.build();
                        } else {
                            throw null;
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        specialDay = null;
                    }
                }
                zzj(arrayList2, specialDay);
            }
        }
        builder.setSpecialDays(arrayList2);
        return builder.build();
    }

    private static boolean zzj(Collection collection, @Nullable Object obj) {
        if (obj != null) {
            return collection.add(obj);
        }
        return false;
    }
}
