package com.google.android.libraries.places.api.model;

import android.net.Uri;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzt extends Place {
    private final Place.BooleanPlaceAttributeValue zzA;
    private final Place.BooleanPlaceAttributeValue zzB;
    private final Place.BooleanPlaceAttributeValue zzC;
    private final List zzD;
    private final Integer zzE;
    private final Integer zzF;
    private final LatLngBounds zzG;
    private final Uri zzH;
    private final Place.BooleanPlaceAttributeValue zzI;
    private final String zza;
    private final AddressComponents zzb;
    private final List zzc;
    private final Place.BusinessStatus zzd;
    private final Place.BooleanPlaceAttributeValue zze;
    private final OpeningHours zzf;
    private final Place.BooleanPlaceAttributeValue zzg;
    private final Place.BooleanPlaceAttributeValue zzh;
    private final Integer zzi;
    private final String zzj;
    private final String zzk;
    private final LatLng zzl;
    private final String zzm;
    private final OpeningHours zzn;
    private final String zzo;
    private final List zzp;
    private final PlusCode zzq;
    private final Integer zzr;
    private final Double zzs;
    private final Place.BooleanPlaceAttributeValue zzt;
    private final List zzu;
    private final Place.BooleanPlaceAttributeValue zzv;
    private final Place.BooleanPlaceAttributeValue zzw;
    private final Place.BooleanPlaceAttributeValue zzx;
    private final Place.BooleanPlaceAttributeValue zzy;
    private final Place.BooleanPlaceAttributeValue zzz;

    zzt(@Nullable String str, @Nullable AddressComponents addressComponents, @Nullable List list, @Nullable Place.BusinessStatus businessStatus, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue, @Nullable OpeningHours openingHours, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue2, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue3, @Nullable Integer num, @Nullable String str2, @Nullable String str3, @Nullable LatLng latLng, @Nullable String str4, @Nullable OpeningHours openingHours2, @Nullable String str5, @Nullable List list2, @Nullable PlusCode plusCode, @Nullable Integer num2, @Nullable Double d, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue4, @Nullable List list3, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue5, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue6, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue7, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue8, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue9, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue10, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue11, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue12, @Nullable List list4, @Nullable Integer num3, @Nullable Integer num4, @Nullable LatLngBounds latLngBounds, @Nullable Uri uri, Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue13) {
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue14 = booleanPlaceAttributeValue;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue15 = booleanPlaceAttributeValue2;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue16 = booleanPlaceAttributeValue3;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue17 = booleanPlaceAttributeValue4;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue18 = booleanPlaceAttributeValue5;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue19 = booleanPlaceAttributeValue6;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue20 = booleanPlaceAttributeValue7;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue21 = booleanPlaceAttributeValue8;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue22 = booleanPlaceAttributeValue9;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue23 = booleanPlaceAttributeValue10;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue24 = booleanPlaceAttributeValue11;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue25 = booleanPlaceAttributeValue12;
        Place.BooleanPlaceAttributeValue booleanPlaceAttributeValue26 = booleanPlaceAttributeValue13;
        this.zza = str;
        this.zzb = addressComponents;
        this.zzc = list;
        this.zzd = businessStatus;
        if (booleanPlaceAttributeValue14 != null) {
            this.zze = booleanPlaceAttributeValue14;
            this.zzf = openingHours;
            if (booleanPlaceAttributeValue15 != null) {
                this.zzg = booleanPlaceAttributeValue15;
                if (booleanPlaceAttributeValue16 != null) {
                    this.zzh = booleanPlaceAttributeValue16;
                    this.zzi = num;
                    this.zzj = str2;
                    this.zzk = str3;
                    this.zzl = latLng;
                    this.zzm = str4;
                    this.zzn = openingHours2;
                    this.zzo = str5;
                    this.zzp = list2;
                    this.zzq = plusCode;
                    this.zzr = num2;
                    this.zzs = d;
                    if (booleanPlaceAttributeValue17 != null) {
                        this.zzt = booleanPlaceAttributeValue17;
                        this.zzu = list3;
                        if (booleanPlaceAttributeValue18 != null) {
                            this.zzv = booleanPlaceAttributeValue18;
                            if (booleanPlaceAttributeValue19 != null) {
                                this.zzw = booleanPlaceAttributeValue19;
                                if (booleanPlaceAttributeValue20 != null) {
                                    this.zzx = booleanPlaceAttributeValue20;
                                    if (booleanPlaceAttributeValue21 != null) {
                                        this.zzy = booleanPlaceAttributeValue21;
                                        if (booleanPlaceAttributeValue22 != null) {
                                            this.zzz = booleanPlaceAttributeValue22;
                                            if (booleanPlaceAttributeValue23 != null) {
                                                this.zzA = booleanPlaceAttributeValue23;
                                                if (booleanPlaceAttributeValue24 != null) {
                                                    this.zzB = booleanPlaceAttributeValue24;
                                                    if (booleanPlaceAttributeValue25 != null) {
                                                        this.zzC = booleanPlaceAttributeValue25;
                                                        this.zzD = list4;
                                                        this.zzE = num3;
                                                        this.zzF = num4;
                                                        this.zzG = latLngBounds;
                                                        this.zzH = uri;
                                                        if (booleanPlaceAttributeValue26 != null) {
                                                            this.zzI = booleanPlaceAttributeValue26;
                                                            return;
                                                        }
                                                        throw new NullPointerException("Null wheelchairAccessibleEntrance");
                                                    }
                                                    throw new NullPointerException("Null takeout");
                                                }
                                                throw new NullPointerException("Null servesWine");
                                            }
                                            throw new NullPointerException("Null servesVegetarianFood");
                                        }
                                        throw new NullPointerException("Null servesLunch");
                                    }
                                    throw new NullPointerException("Null servesDinner");
                                }
                                throw new NullPointerException("Null servesBrunch");
                            }
                            throw new NullPointerException("Null servesBreakfast");
                        }
                        throw new NullPointerException("Null servesBeer");
                    }
                    throw new NullPointerException("Null reservable");
                }
                throw new NullPointerException("Null dineIn");
            }
            throw new NullPointerException("Null delivery");
        }
        throw new NullPointerException("Null curbsidePickup");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0179, code lost:
        r1 = r4.zzs;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x019b, code lost:
        r1 = r4.zzu;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0211, code lost:
        r1 = r4.zzD;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0227, code lost:
        r1 = r4.zzE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x023d, code lost:
        r1 = r4.zzF;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0253, code lost:
        r1 = r4.zzG;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0269, code lost:
        r1 = r4.zzH;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006f, code lost:
        r1 = r4.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009d, code lost:
        r1 = r4.zzi;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b3, code lost:
        r1 = r4.zzj;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c9, code lost:
        r1 = r4.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00df, code lost:
        r1 = r4.zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00f5, code lost:
        r1 = r4.zzm;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x010b, code lost:
        r1 = r4.zzn;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0121, code lost:
        r1 = r4.zzo;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0137, code lost:
        r1 = r4.zzp;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x014d, code lost:
        r1 = r4.zzq;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0163, code lost:
        r1 = r4.zzr;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.libraries.places.api.model.Place
            r2 = 0
            if (r1 == 0) goto L_0x028d
            com.google.android.libraries.places.api.model.Place r5 = (com.google.android.libraries.places.api.model.Place) r5
            java.lang.String r1 = r4.zza
            if (r1 != 0) goto L_0x0016
            java.lang.String r1 = r5.getAddress()
            if (r1 != 0) goto L_0x028c
        L_0x0015:
            goto L_0x0021
        L_0x0016:
            java.lang.String r3 = r5.getAddress()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0015
        L_0x0021:
            com.google.android.libraries.places.api.model.AddressComponents r1 = r4.zzb
            if (r1 != 0) goto L_0x002c
            com.google.android.libraries.places.api.model.AddressComponents r1 = r5.getAddressComponents()
            if (r1 != 0) goto L_0x028c
        L_0x002b:
            goto L_0x0037
        L_0x002c:
            com.google.android.libraries.places.api.model.AddressComponents r3 = r5.getAddressComponents()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x002b
        L_0x0037:
            java.util.List r1 = r4.zzc
            if (r1 != 0) goto L_0x0042
            java.util.List r1 = r5.getAttributions()
            if (r1 != 0) goto L_0x028c
        L_0x0041:
            goto L_0x004d
        L_0x0042:
            java.util.List r3 = r5.getAttributions()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0041
        L_0x004d:
            com.google.android.libraries.places.api.model.Place$BusinessStatus r1 = r4.zzd
            if (r1 != 0) goto L_0x0058
            com.google.android.libraries.places.api.model.Place$BusinessStatus r1 = r5.getBusinessStatus()
            if (r1 != 0) goto L_0x028c
        L_0x0057:
            goto L_0x0063
        L_0x0058:
            com.google.android.libraries.places.api.model.Place$BusinessStatus r3 = r5.getBusinessStatus()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0057
        L_0x0063:
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zze
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getCurbsidePickup()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.OpeningHours r1 = r4.zzf
            if (r1 != 0) goto L_0x007a
            com.google.android.libraries.places.api.model.OpeningHours r1 = r5.getCurrentOpeningHours()
            if (r1 != 0) goto L_0x028c
        L_0x0079:
            goto L_0x0085
        L_0x007a:
            com.google.android.libraries.places.api.model.OpeningHours r3 = r5.getCurrentOpeningHours()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0079
        L_0x0085:
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzg
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getDelivery()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzh
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getDineIn()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            java.lang.Integer r1 = r4.zzi
            if (r1 != 0) goto L_0x00a8
            java.lang.Integer r1 = r5.getIconBackgroundColor()
            if (r1 != 0) goto L_0x028c
        L_0x00a7:
            goto L_0x00b3
        L_0x00a8:
            java.lang.Integer r3 = r5.getIconBackgroundColor()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x00a7
        L_0x00b3:
            java.lang.String r1 = r4.zzj
            if (r1 != 0) goto L_0x00be
            java.lang.String r1 = r5.getIconUrl()
            if (r1 != 0) goto L_0x028c
        L_0x00bd:
            goto L_0x00c9
        L_0x00be:
            java.lang.String r3 = r5.getIconUrl()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x00bd
        L_0x00c9:
            java.lang.String r1 = r4.zzk
            if (r1 != 0) goto L_0x00d4
            java.lang.String r1 = r5.getId()
            if (r1 != 0) goto L_0x028c
        L_0x00d3:
            goto L_0x00df
        L_0x00d4:
            java.lang.String r3 = r5.getId()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x00d3
        L_0x00df:
            com.google.android.gms.maps.model.LatLng r1 = r4.zzl
            if (r1 != 0) goto L_0x00ea
            com.google.android.gms.maps.model.LatLng r1 = r5.getLatLng()
            if (r1 != 0) goto L_0x028c
        L_0x00e9:
            goto L_0x00f5
        L_0x00ea:
            com.google.android.gms.maps.model.LatLng r3 = r5.getLatLng()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x00e9
        L_0x00f5:
            java.lang.String r1 = r4.zzm
            if (r1 != 0) goto L_0x0100
            java.lang.String r1 = r5.getName()
            if (r1 != 0) goto L_0x028c
        L_0x00ff:
            goto L_0x010b
        L_0x0100:
            java.lang.String r3 = r5.getName()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x00ff
        L_0x010b:
            com.google.android.libraries.places.api.model.OpeningHours r1 = r4.zzn
            if (r1 != 0) goto L_0x0116
            com.google.android.libraries.places.api.model.OpeningHours r1 = r5.getOpeningHours()
            if (r1 != 0) goto L_0x028c
        L_0x0115:
            goto L_0x0121
        L_0x0116:
            com.google.android.libraries.places.api.model.OpeningHours r3 = r5.getOpeningHours()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0115
        L_0x0121:
            java.lang.String r1 = r4.zzo
            if (r1 != 0) goto L_0x012c
            java.lang.String r1 = r5.getPhoneNumber()
            if (r1 != 0) goto L_0x028c
        L_0x012b:
            goto L_0x0137
        L_0x012c:
            java.lang.String r3 = r5.getPhoneNumber()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x012b
        L_0x0137:
            java.util.List r1 = r4.zzp
            if (r1 != 0) goto L_0x0142
            java.util.List r1 = r5.getPhotoMetadatas()
            if (r1 != 0) goto L_0x028c
        L_0x0141:
            goto L_0x014d
        L_0x0142:
            java.util.List r3 = r5.getPhotoMetadatas()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0141
        L_0x014d:
            com.google.android.libraries.places.api.model.PlusCode r1 = r4.zzq
            if (r1 != 0) goto L_0x0158
            com.google.android.libraries.places.api.model.PlusCode r1 = r5.getPlusCode()
            if (r1 != 0) goto L_0x028c
        L_0x0157:
            goto L_0x0163
        L_0x0158:
            com.google.android.libraries.places.api.model.PlusCode r3 = r5.getPlusCode()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0157
        L_0x0163:
            java.lang.Integer r1 = r4.zzr
            if (r1 != 0) goto L_0x016e
            java.lang.Integer r1 = r5.getPriceLevel()
            if (r1 != 0) goto L_0x028c
        L_0x016d:
            goto L_0x0179
        L_0x016e:
            java.lang.Integer r3 = r5.getPriceLevel()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x016d
        L_0x0179:
            java.lang.Double r1 = r4.zzs
            if (r1 != 0) goto L_0x0184
            java.lang.Double r1 = r5.getRating()
            if (r1 != 0) goto L_0x028c
        L_0x0183:
            goto L_0x018f
        L_0x0184:
            java.lang.Double r3 = r5.getRating()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0183
        L_0x018f:
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzt
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getReservable()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            java.util.List r1 = r4.zzu
            if (r1 != 0) goto L_0x01a6
            java.util.List r1 = r5.getSecondaryOpeningHours()
            if (r1 != 0) goto L_0x028c
        L_0x01a5:
            goto L_0x01b1
        L_0x01a6:
            java.util.List r3 = r5.getSecondaryOpeningHours()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x01a5
        L_0x01b1:
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzv
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesBeer()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzw
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesBreakfast()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzx
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesBrunch()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzy
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesDinner()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzz
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesLunch()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzA
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesVegetarianFood()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzB
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getServesWine()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzC
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r3 = r5.getTakeout()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            java.util.List r1 = r4.zzD
            if (r1 != 0) goto L_0x021c
            java.util.List r1 = r5.getTypes()
            if (r1 != 0) goto L_0x028c
        L_0x021b:
            goto L_0x0227
        L_0x021c:
            java.util.List r3 = r5.getTypes()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x021b
        L_0x0227:
            java.lang.Integer r1 = r4.zzE
            if (r1 != 0) goto L_0x0232
            java.lang.Integer r1 = r5.getUserRatingsTotal()
            if (r1 != 0) goto L_0x028c
        L_0x0231:
            goto L_0x023d
        L_0x0232:
            java.lang.Integer r3 = r5.getUserRatingsTotal()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0231
        L_0x023d:
            java.lang.Integer r1 = r4.zzF
            if (r1 != 0) goto L_0x0248
            java.lang.Integer r1 = r5.getUtcOffsetMinutes()
            if (r1 != 0) goto L_0x028c
        L_0x0247:
            goto L_0x0253
        L_0x0248:
            java.lang.Integer r3 = r5.getUtcOffsetMinutes()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x0247
        L_0x0253:
            com.google.android.gms.maps.model.LatLngBounds r1 = r4.zzG
            if (r1 != 0) goto L_0x025e
            com.google.android.gms.maps.model.LatLngBounds r1 = r5.getViewport()
            if (r1 != 0) goto L_0x028c
        L_0x025d:
            goto L_0x0269
        L_0x025e:
            com.google.android.gms.maps.model.LatLngBounds r3 = r5.getViewport()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x028c
            goto L_0x025d
        L_0x0269:
            android.net.Uri r1 = r4.zzH
            if (r1 != 0) goto L_0x0274
            android.net.Uri r1 = r5.getWebsiteUri()
            if (r1 != 0) goto L_0x027e
            goto L_0x027f
        L_0x0274:
            android.net.Uri r3 = r5.getWebsiteUri()
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x027f
        L_0x027e:
            goto L_0x028c
        L_0x027f:
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r1 = r4.zzI
            com.google.android.libraries.places.api.model.Place$BooleanPlaceAttributeValue r5 = r5.getWheelchairAccessibleEntrance()
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x028c
            return r0
        L_0x028c:
            return r2
        L_0x028d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.model.zzt.equals(java.lang.Object):boolean");
    }

    @Nullable
    public String getAddress() {
        return this.zza;
    }

    @Nullable
    public AddressComponents getAddressComponents() {
        return this.zzb;
    }

    @Nullable
    public List<String> getAttributions() {
        return this.zzc;
    }

    @Nullable
    public Place.BusinessStatus getBusinessStatus() {
        return this.zzd;
    }

    public Place.BooleanPlaceAttributeValue getCurbsidePickup() {
        return this.zze;
    }

    @Nullable
    public OpeningHours getCurrentOpeningHours() {
        return this.zzf;
    }

    public Place.BooleanPlaceAttributeValue getDelivery() {
        return this.zzg;
    }

    public Place.BooleanPlaceAttributeValue getDineIn() {
        return this.zzh;
    }

    @ColorInt
    @Nullable
    public Integer getIconBackgroundColor() {
        return this.zzi;
    }

    @Nullable
    public String getIconUrl() {
        return this.zzj;
    }

    @Nullable
    public String getId() {
        return this.zzk;
    }

    @Nullable
    public LatLng getLatLng() {
        return this.zzl;
    }

    @Nullable
    public String getName() {
        return this.zzm;
    }

    @Nullable
    public OpeningHours getOpeningHours() {
        return this.zzn;
    }

    @Nullable
    public String getPhoneNumber() {
        return this.zzo;
    }

    @Nullable
    public List<PhotoMetadata> getPhotoMetadatas() {
        return this.zzp;
    }

    @Nullable
    public PlusCode getPlusCode() {
        return this.zzq;
    }

    @IntRange(from = 0, to = 4)
    @Nullable
    public Integer getPriceLevel() {
        return this.zzr;
    }

    @FloatRange(from = 1.0d, to = 5.0d)
    @Nullable
    public Double getRating() {
        return this.zzs;
    }

    public Place.BooleanPlaceAttributeValue getReservable() {
        return this.zzt;
    }

    @Nullable
    public List<OpeningHours> getSecondaryOpeningHours() {
        return this.zzu;
    }

    public Place.BooleanPlaceAttributeValue getServesBeer() {
        return this.zzv;
    }

    public Place.BooleanPlaceAttributeValue getServesBreakfast() {
        return this.zzw;
    }

    public Place.BooleanPlaceAttributeValue getServesBrunch() {
        return this.zzx;
    }

    public Place.BooleanPlaceAttributeValue getServesDinner() {
        return this.zzy;
    }

    public Place.BooleanPlaceAttributeValue getServesLunch() {
        return this.zzz;
    }

    public Place.BooleanPlaceAttributeValue getServesVegetarianFood() {
        return this.zzA;
    }

    public Place.BooleanPlaceAttributeValue getServesWine() {
        return this.zzB;
    }

    public Place.BooleanPlaceAttributeValue getTakeout() {
        return this.zzC;
    }

    @Nullable
    public List<Place.Type> getTypes() {
        return this.zzD;
    }

    @IntRange(from = 0)
    @Nullable
    public Integer getUserRatingsTotal() {
        return this.zzE;
    }

    @Nullable
    public Integer getUtcOffsetMinutes() {
        return this.zzF;
    }

    @Nullable
    public LatLngBounds getViewport() {
        return this.zzG;
    }

    @Nullable
    public Uri getWebsiteUri() {
        return this.zzH;
    }

    public Place.BooleanPlaceAttributeValue getWheelchairAccessibleEntrance() {
        return this.zzI;
    }

    public final String toString() {
        String str = this.zza;
        String valueOf = String.valueOf(this.zzb);
        String valueOf2 = String.valueOf(this.zzc);
        String valueOf3 = String.valueOf(this.zzd);
        String obj = this.zze.toString();
        String valueOf4 = String.valueOf(this.zzf);
        String obj2 = this.zzg.toString();
        String obj3 = this.zzh.toString();
        Integer num = this.zzi;
        String str2 = this.zzj;
        String str3 = this.zzk;
        String valueOf5 = String.valueOf(this.zzl);
        String str4 = this.zzm;
        String valueOf6 = String.valueOf(this.zzn);
        String str5 = this.zzo;
        String valueOf7 = String.valueOf(this.zzp);
        String valueOf8 = String.valueOf(this.zzq);
        Integer num2 = this.zzr;
        Double d = this.zzs;
        String obj4 = this.zzt.toString();
        String valueOf9 = String.valueOf(this.zzu);
        String obj5 = this.zzv.toString();
        String obj6 = this.zzw.toString();
        String obj7 = this.zzx.toString();
        String obj8 = this.zzy.toString();
        String obj9 = this.zzz.toString();
        String obj10 = this.zzA.toString();
        String obj11 = this.zzB.toString();
        String obj12 = this.zzC.toString();
        String valueOf10 = String.valueOf(this.zzD);
        Integer num3 = this.zzE;
        Integer num4 = this.zzF;
        String valueOf11 = String.valueOf(this.zzG);
        String valueOf12 = String.valueOf(this.zzH);
        String obj13 = this.zzI.toString();
        return "Place{address=" + str + ", addressComponents=" + valueOf + ", attributions=" + valueOf2 + ", businessStatus=" + valueOf3 + ", curbsidePickup=" + obj + ", currentOpeningHours=" + valueOf4 + ", delivery=" + obj2 + ", dineIn=" + obj3 + ", iconBackgroundColor=" + num + ", iconUrl=" + str2 + ", id=" + str3 + ", latLng=" + valueOf5 + ", name=" + str4 + ", openingHours=" + valueOf6 + ", phoneNumber=" + str5 + ", photoMetadatas=" + valueOf7 + ", plusCode=" + valueOf8 + ", priceLevel=" + num2 + ", rating=" + d + ", reservable=" + obj4 + ", secondaryOpeningHours=" + valueOf9 + ", servesBeer=" + obj5 + ", servesBreakfast=" + obj6 + ", servesBrunch=" + obj7 + ", servesDinner=" + obj8 + ", servesLunch=" + obj9 + ", servesVegetarianFood=" + obj10 + ", servesWine=" + obj11 + ", takeout=" + obj12 + ", types=" + valueOf10 + ", userRatingsTotal=" + num3 + ", utcOffsetMinutes=" + num4 + ", viewport=" + valueOf11 + ", websiteUri=" + valueOf12 + ", wheelchairAccessibleEntrance=" + obj13 + "}";
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        String str = this.zza;
        int i21 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        AddressComponents addressComponents = this.zzb;
        if (addressComponents == null) {
            i2 = 0;
        } else {
            i2 = addressComponents.hashCode();
        }
        int i22 = i ^ 1000003;
        List list = this.zzc;
        if (list == null) {
            i3 = 0;
        } else {
            i3 = list.hashCode();
        }
        int i23 = ((((i22 * 1000003) ^ i2) * 1000003) ^ i3) * 1000003;
        Place.BusinessStatus businessStatus = this.zzd;
        if (businessStatus == null) {
            i4 = 0;
        } else {
            i4 = businessStatus.hashCode();
        }
        int hashCode = (((i23 ^ i4) * 1000003) ^ this.zze.hashCode()) * 1000003;
        OpeningHours openingHours = this.zzf;
        if (openingHours == null) {
            i5 = 0;
        } else {
            i5 = openingHours.hashCode();
        }
        int hashCode2 = (((((hashCode ^ i5) * 1000003) ^ this.zzg.hashCode()) * 1000003) ^ this.zzh.hashCode()) * 1000003;
        Integer num = this.zzi;
        if (num == null) {
            i6 = 0;
        } else {
            i6 = num.hashCode();
        }
        int i24 = (hashCode2 ^ i6) * 1000003;
        String str2 = this.zzj;
        if (str2 == null) {
            i7 = 0;
        } else {
            i7 = str2.hashCode();
        }
        int i25 = (i24 ^ i7) * 1000003;
        String str3 = this.zzk;
        if (str3 == null) {
            i8 = 0;
        } else {
            i8 = str3.hashCode();
        }
        int i26 = (i25 ^ i8) * 1000003;
        LatLng latLng = this.zzl;
        if (latLng == null) {
            i9 = 0;
        } else {
            i9 = latLng.hashCode();
        }
        int i27 = (i26 ^ i9) * 1000003;
        String str4 = this.zzm;
        if (str4 == null) {
            i10 = 0;
        } else {
            i10 = str4.hashCode();
        }
        int i28 = (i27 ^ i10) * 1000003;
        OpeningHours openingHours2 = this.zzn;
        if (openingHours2 == null) {
            i11 = 0;
        } else {
            i11 = openingHours2.hashCode();
        }
        int i29 = (i28 ^ i11) * 1000003;
        String str5 = this.zzo;
        if (str5 == null) {
            i12 = 0;
        } else {
            i12 = str5.hashCode();
        }
        int i30 = (i29 ^ i12) * 1000003;
        List list2 = this.zzp;
        if (list2 == null) {
            i13 = 0;
        } else {
            i13 = list2.hashCode();
        }
        int i31 = (i30 ^ i13) * 1000003;
        PlusCode plusCode = this.zzq;
        if (plusCode == null) {
            i14 = 0;
        } else {
            i14 = plusCode.hashCode();
        }
        int i32 = (i31 ^ i14) * 1000003;
        Integer num2 = this.zzr;
        if (num2 == null) {
            i15 = 0;
        } else {
            i15 = num2.hashCode();
        }
        int i33 = (i32 ^ i15) * 1000003;
        Double d = this.zzs;
        int hashCode3 = (((i33 ^ (d == null ? 0 : d.hashCode())) * 1000003) ^ this.zzt.hashCode()) * 1000003;
        List list3 = this.zzu;
        if (list3 == null) {
            i16 = 0;
        } else {
            i16 = list3.hashCode();
        }
        int hashCode4 = (((((((((((((((((hashCode3 ^ i16) * 1000003) ^ this.zzv.hashCode()) * 1000003) ^ this.zzw.hashCode()) * 1000003) ^ this.zzx.hashCode()) * 1000003) ^ this.zzy.hashCode()) * 1000003) ^ this.zzz.hashCode()) * 1000003) ^ this.zzA.hashCode()) * 1000003) ^ this.zzB.hashCode()) * 1000003) ^ this.zzC.hashCode()) * 1000003;
        List list4 = this.zzD;
        if (list4 == null) {
            i17 = 0;
        } else {
            i17 = list4.hashCode();
        }
        int i34 = (hashCode4 ^ i17) * 1000003;
        Integer num3 = this.zzE;
        if (num3 == null) {
            i18 = 0;
        } else {
            i18 = num3.hashCode();
        }
        int i35 = (i34 ^ i18) * 1000003;
        Integer num4 = this.zzF;
        if (num4 == null) {
            i19 = 0;
        } else {
            i19 = num4.hashCode();
        }
        int i36 = (i35 ^ i19) * 1000003;
        LatLngBounds latLngBounds = this.zzG;
        if (latLngBounds == null) {
            i20 = 0;
        } else {
            i20 = latLngBounds.hashCode();
        }
        int i37 = (i36 ^ i20) * 1000003;
        Uri uri = this.zzH;
        if (uri != null) {
            i21 = uri.hashCode();
        }
        return ((i37 ^ i21) * 1000003) ^ this.zzI.hashCode();
    }
}
