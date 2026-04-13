package com.google.android.libraries.places.api.model;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzs extends Place.Builder {
    private Place.BooleanPlaceAttributeValue zzA;
    private Place.BooleanPlaceAttributeValue zzB;
    private Place.BooleanPlaceAttributeValue zzC;
    private List zzD;
    private Integer zzE;
    private Integer zzF;
    private LatLngBounds zzG;
    private Uri zzH;
    private Place.BooleanPlaceAttributeValue zzI;
    private String zza;
    private AddressComponents zzb;
    private List zzc;
    private Place.BusinessStatus zzd;
    private Place.BooleanPlaceAttributeValue zze;
    private OpeningHours zzf;
    private Place.BooleanPlaceAttributeValue zzg;
    private Place.BooleanPlaceAttributeValue zzh;
    private Integer zzi;
    private String zzj;
    private String zzk;
    private LatLng zzl;
    private String zzm;
    private OpeningHours zzn;
    private String zzo;
    private List zzp;
    private PlusCode zzq;
    private Integer zzr;
    private Double zzs;
    private Place.BooleanPlaceAttributeValue zzt;
    private List zzu;
    private Place.BooleanPlaceAttributeValue zzv;
    private Place.BooleanPlaceAttributeValue zzw;
    private Place.BooleanPlaceAttributeValue zzx;
    private Place.BooleanPlaceAttributeValue zzy;
    private Place.BooleanPlaceAttributeValue zzz;

    zzs() {
    }

    @Nullable
    public final String getAddress() {
        return this.zza;
    }

    @Nullable
    public final AddressComponents getAddressComponents() {
        return this.zzb;
    }

    @Nullable
    public final List<String> getAttributions() {
        return this.zzc;
    }

    @Nullable
    public final Place.BusinessStatus getBusinessStatus() {
        return this.zzd;
    }

    public final Place.BooleanPlaceAttributeValue getCurbsidePickup() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zze;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"curbsidePickup\" has not been set");
    }

    @Nullable
    public final OpeningHours getCurrentOpeningHours() {
        return this.zzf;
    }

    public final Place.BooleanPlaceAttributeValue getDelivery() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzg;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"delivery\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getDineIn() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzh;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"dineIn\" has not been set");
    }

    @Nullable
    public final Integer getIconBackgroundColor() {
        return this.zzi;
    }

    @Nullable
    public final String getIconUrl() {
        return this.zzj;
    }

    @Nullable
    public final String getId() {
        return this.zzk;
    }

    @Nullable
    public final LatLng getLatLng() {
        return this.zzl;
    }

    @Nullable
    public final String getName() {
        return this.zzm;
    }

    @Nullable
    public final OpeningHours getOpeningHours() {
        return this.zzn;
    }

    @Nullable
    public final String getPhoneNumber() {
        return this.zzo;
    }

    @Nullable
    public final List<PhotoMetadata> getPhotoMetadatas() {
        return this.zzp;
    }

    @Nullable
    public final PlusCode getPlusCode() {
        return this.zzq;
    }

    @Nullable
    public final Integer getPriceLevel() {
        return this.zzr;
    }

    @Nullable
    public final Double getRating() {
        return this.zzs;
    }

    public final Place.BooleanPlaceAttributeValue getReservable() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzt;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"reservable\" has not been set");
    }

    @Nullable
    public final List<OpeningHours> getSecondaryOpeningHours() {
        return this.zzu;
    }

    public final Place.BooleanPlaceAttributeValue getServesBeer() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzv;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesBeer\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesBreakfast() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzw;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesBreakfast\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesBrunch() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzx;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesBrunch\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesDinner() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzy;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesDinner\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesLunch() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzz;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesLunch\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesVegetarianFood() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzA;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesVegetarianFood\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getServesWine() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzB;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"servesWine\" has not been set");
    }

    public final Place.BooleanPlaceAttributeValue getTakeout() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzC;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"takeout\" has not been set");
    }

    @Nullable
    public final List<Place.Type> getTypes() {
        return this.zzD;
    }

    @Nullable
    public final Integer getUserRatingsTotal() {
        return this.zzE;
    }

    @Nullable
    public final Integer getUtcOffsetMinutes() {
        return this.zzF;
    }

    @Nullable
    public final LatLngBounds getViewport() {
        return this.zzG;
    }

    @Nullable
    public final Uri getWebsiteUri() {
        return this.zzH;
    }

    public final Place.BooleanPlaceAttributeValue getWheelchairAccessibleEntrance() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue = this.zzI;
        if (booleanPlaceAttributeValue != null) {
            return booleanPlaceAttributeValue;
        }
        throw new IllegalStateException("Property \"wheelchairAccessibleEntrance\" has not been set");
    }

    public final Place.Builder setAddress(@Nullable String str) {
        this.zza = str;
        return this;
    }

    public final Place.Builder setAddressComponents(@Nullable AddressComponents addressComponents) {
        this.zzb = addressComponents;
        return this;
    }

    public final Place.Builder setAttributions(@Nullable List<String> list) {
        this.zzc = list;
        return this;
    }

    public final Place.Builder setBusinessStatus(@Nullable Place.BusinessStatus businessStatus) {
        this.zzd = businessStatus;
        return this;
    }

    public final Place.Builder setCurbsidePickup(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zze = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null curbsidePickup");
    }

    public final Place.Builder setCurrentOpeningHours(@Nullable OpeningHours openingHours) {
        this.zzf = openingHours;
        return this;
    }

    public final Place.Builder setDelivery(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzg = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null delivery");
    }

    public final Place.Builder setDineIn(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzh = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null dineIn");
    }

    public final Place.Builder setIconBackgroundColor(@Nullable Integer num) {
        this.zzi = num;
        return this;
    }

    public final Place.Builder setIconUrl(@Nullable String str) {
        this.zzj = str;
        return this;
    }

    public final Place.Builder setId(@Nullable String str) {
        this.zzk = str;
        return this;
    }

    public final Place.Builder setLatLng(@Nullable LatLng latLng) {
        this.zzl = latLng;
        return this;
    }

    public final Place.Builder setName(@Nullable String str) {
        this.zzm = str;
        return this;
    }

    public final Place.Builder setOpeningHours(@Nullable OpeningHours openingHours) {
        this.zzn = openingHours;
        return this;
    }

    public final Place.Builder setPhoneNumber(@Nullable String str) {
        this.zzo = str;
        return this;
    }

    public final Place.Builder setPhotoMetadatas(@Nullable List<PhotoMetadata> list) {
        this.zzp = list;
        return this;
    }

    public final Place.Builder setPlusCode(@Nullable PlusCode plusCode) {
        this.zzq = plusCode;
        return this;
    }

    public final Place.Builder setPriceLevel(@Nullable Integer num) {
        this.zzr = num;
        return this;
    }

    public final Place.Builder setRating(@Nullable Double d) {
        this.zzs = d;
        return this;
    }

    public final Place.Builder setReservable(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzt = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null reservable");
    }

    public final Place.Builder setSecondaryOpeningHours(@Nullable List<OpeningHours> list) {
        this.zzu = list;
        return this;
    }

    public final Place.Builder setServesBeer(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzv = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesBeer");
    }

    public final Place.Builder setServesBreakfast(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzw = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesBreakfast");
    }

    public final Place.Builder setServesBrunch(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzx = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesBrunch");
    }

    public final Place.Builder setServesDinner(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzy = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesDinner");
    }

    public final Place.Builder setServesLunch(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzz = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesLunch");
    }

    public final Place.Builder setServesVegetarianFood(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzA = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesVegetarianFood");
    }

    public final Place.Builder setServesWine(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzB = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null servesWine");
    }

    public final Place.Builder setTakeout(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzC = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null takeout");
    }

    public final Place.Builder setTypes(@Nullable List<Place.Type> list) {
        this.zzD = list;
        return this;
    }

    public final Place.Builder setUserRatingsTotal(@Nullable Integer num) {
        this.zzE = num;
        return this;
    }

    public final Place.Builder setUtcOffsetMinutes(@Nullable Integer num) {
        this.zzF = num;
        return this;
    }

    public final Place.Builder setViewport(@Nullable LatLngBounds latLngBounds) {
        this.zzG = latLngBounds;
        return this;
    }

    public final Place.Builder setWebsiteUri(@Nullable Uri uri) {
        this.zzH = uri;
        return this;
    }

    public final Place.Builder setWheelchairAccessibleEntrance(Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue) {
        if (booleanPlaceAttributeValue != null) {
            this.zzI = booleanPlaceAttributeValue;
            return this;
        }
        throw new NullPointerException("Null wheelchairAccessibleEntrance");
    }

    /* access modifiers changed from: package-private */
    public final Place zza() {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue2;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue3;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue4;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue5;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue6;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue7;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue8;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue9;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue10;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue11;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue12;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue13 = this.zze;
        if (booleanPlaceAttributeValue13 == null || (booleanPlaceAttributeValue = this.zzg) == null || (booleanPlaceAttributeValue2 = this.zzh) == null || (booleanPlaceAttributeValue3 = this.zzt) == null || (booleanPlaceAttributeValue4 = this.zzv) == null || (booleanPlaceAttributeValue5 = this.zzw) == null || (booleanPlaceAttributeValue6 = this.zzx) == null || (booleanPlaceAttributeValue7 = this.zzy) == null || (booleanPlaceAttributeValue8 = this.zzz) == null || (booleanPlaceAttributeValue9 = this.zzA) == null || (booleanPlaceAttributeValue10 = this.zzB) == null || (booleanPlaceAttributeValue11 = this.zzC) == null || (booleanPlaceAttributeValue12 = this.zzI) == null) {
            StringBuilder sb = new StringBuilder();
            if (this.zze == null) {
                sb.append(" curbsidePickup");
            }
            if (this.zzg == null) {
                sb.append(" delivery");
            }
            if (this.zzh == null) {
                sb.append(" dineIn");
            }
            if (this.zzt == null) {
                sb.append(" reservable");
            }
            if (this.zzv == null) {
                sb.append(" servesBeer");
            }
            if (this.zzw == null) {
                sb.append(" servesBreakfast");
            }
            if (this.zzx == null) {
                sb.append(" servesBrunch");
            }
            if (this.zzy == null) {
                sb.append(" servesDinner");
            }
            if (this.zzz == null) {
                sb.append(" servesLunch");
            }
            if (this.zzA == null) {
                sb.append(" servesVegetarianFood");
            }
            if (this.zzB == null) {
                sb.append(" servesWine");
            }
            if (this.zzC == null) {
                sb.append(" takeout");
            }
            if (this.zzI == null) {
                sb.append(" wheelchairAccessibleEntrance");
            }
            throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
        }
        return new zzay(this.zza, this.zzb, this.zzc, this.zzd, booleanPlaceAttributeValue13, this.zzf, booleanPlaceAttributeValue, booleanPlaceAttributeValue2, this.zzi, this.zzj, this.zzk, this.zzl, this.zzm, this.zzn, this.zzo, this.zzp, this.zzq, this.zzr, this.zzs, booleanPlaceAttributeValue3, this.zzu, booleanPlaceAttributeValue4, booleanPlaceAttributeValue5, booleanPlaceAttributeValue6, booleanPlaceAttributeValue7, booleanPlaceAttributeValue8, booleanPlaceAttributeValue9, booleanPlaceAttributeValue10, booleanPlaceAttributeValue11, this.zzD, this.zzE, this.zzF, this.zzG, this.zzH, booleanPlaceAttributeValue12);
    }
}
