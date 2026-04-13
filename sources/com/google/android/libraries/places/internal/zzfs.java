package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfs {
    @Nullable
    private zza[] addressComponents;
    @Nullable
    private String businessStatus;
    @Nullable
    private Boolean curbsidePickup;
    @Nullable
    private zzc currentOpeningHours;
    @Nullable
    private Boolean delivery;
    @Nullable
    private Boolean dineIn;
    @Nullable
    private String formattedAddress;
    @Nullable
    private zzb geometry;
    @Nullable
    private String icon;
    @Nullable
    private String iconBackgroundColor;
    @Nullable
    private String iconMaskBaseUri;
    @Nullable
    private String internationalPhoneNumber;
    @Nullable
    private String name;
    @Nullable
    private zzc openingHours;
    @Nullable
    private zzd[] photos;
    @Nullable
    private String placeId;
    @Nullable
    private zze plusCode;
    @Nullable
    private Integer priceLevel;
    @Nullable
    private Double rating;
    @Nullable
    private Boolean reservable;
    @Nullable
    private zzc[] secondaryOpeningHours;
    @Nullable
    private Boolean servesBeer;
    @Nullable
    private Boolean servesBreakfast;
    @Nullable
    private Boolean servesBrunch;
    @Nullable
    private Boolean servesDinner;
    @Nullable
    private Boolean servesLunch;
    @Nullable
    private Boolean servesVegetarianFood;
    @Nullable
    private Boolean servesWine;
    @Nullable
    private Boolean takeout;
    @Nullable
    private String[] types;
    @Nullable
    private Integer userRatingsTotal;
    @Nullable
    private Integer utcOffset;
    @Nullable
    private String website;
    @Nullable
    private Boolean wheelchairAccessibleEntrance;

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zza {
        @Nullable
        private String longName;
        @Nullable
        private String shortName;
        @Nullable
        private String[] types;

        zza() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zza() {
            String[] strArr = this.types;
            if (strArr != null) {
                return zzjq.zzk(strArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzb() {
            return this.longName;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzc() {
            return this.shortName;
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zzb {
        @Nullable
        private zza location;
        @Nullable
        private C0074zzb viewport;

        /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
        public class zza {
            @Nullable
            private Double lat;
            @Nullable
            private Double lng;

            zza() {
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final Double zza() {
                return this.lat;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final Double zzb() {
                return this.lng;
            }
        }

        /* renamed from: com.google.android.libraries.places.internal.zzfs$zzb$zzb  reason: collision with other inner class name */
        /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
        public class C0074zzb {
            @Nullable
            private zza northeast;
            @Nullable
            private zza southwest;

            C0074zzb() {
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final zza zza() {
                return this.northeast;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final zza zzb() {
                return this.southwest;
            }
        }

        zzb() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zza zza() {
            return this.location;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final C0074zzb zzb() {
            return this.viewport;
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zzc {
        @Nullable
        private zza[] periods;
        @Nullable
        private zzb[] specialDays;
        @Nullable
        private String type;
        @Nullable
        private String[] weekdayText;

        /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
        public class zza {
            @Nullable
            private C0075zzc close;
            @Nullable
            private C0075zzc open;

            zza() {
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final C0075zzc zza() {
                return this.close;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final C0075zzc zzb() {
                return this.open;
            }
        }

        /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
        public class zzb {
            @Nullable
            private String date;
            @Nullable
            private Boolean exceptionalHours;

            zzb() {
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final Boolean zza() {
                return this.exceptionalHours;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final String zzb() {
                return this.date;
            }
        }

        /* renamed from: com.google.android.libraries.places.internal.zzfs$zzc$zzc  reason: collision with other inner class name */
        /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
        public class C0075zzc {
            @Nullable
            private String date;
            @Nullable
            private Integer day;
            @Nullable
            private String time;
            @Nullable
            private Boolean truncated;

            C0075zzc() {
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final Boolean zza() {
                return this.truncated;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final Integer zzb() {
                return this.day;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final String zzc() {
                return this.date;
            }

            /* access modifiers changed from: package-private */
            @Nullable
            public final String zzd() {
                return this.time;
            }
        }

        zzc() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zza() {
            zza[] zzaArr = this.periods;
            if (zzaArr != null) {
                return zzjq.zzk(zzaArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zzb() {
            zzb[] zzbArr = this.specialDays;
            if (zzbArr != null) {
                return zzjq.zzk(zzbArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zzc() {
            String[] strArr = this.weekdayText;
            if (strArr != null) {
                return zzjq.zzk(strArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzd() {
            return this.type;
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zzd {
        @Nullable
        private Integer height;
        @Nullable
        private String[] htmlAttributions;
        @Nullable
        private String photoReference;
        @Nullable
        private Integer width;

        zzd() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zza() {
            String[] strArr = this.htmlAttributions;
            if (strArr != null) {
                return zzjq.zzk(strArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final Integer zzb() {
            return this.height;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final Integer zzc() {
            return this.width;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzd() {
            return this.photoReference;
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zze {
        @Nullable
        private String compoundCode;
        @Nullable
        private String globalCode;

        zze() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zza() {
            return this.compoundCode;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzb() {
            return this.globalCode;
        }
    }

    zzfs() {
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzA() {
        return this.iconBackgroundColor;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzB() {
        return this.iconMaskBaseUri;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzC() {
        return this.internationalPhoneNumber;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzD() {
        return this.name;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzE() {
        return this.placeId;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzF() {
        return this.website;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzb zza() {
        return this.geometry;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzc zzb() {
        return this.currentOpeningHours;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzc zzc() {
        return this.openingHours;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zze zzd() {
        return this.plusCode;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zze() {
        zza[] zzaArr = this.addressComponents;
        if (zzaArr != null) {
            return zzjq.zzk(zzaArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zzf() {
        zzd[] zzdArr = this.photos;
        if (zzdArr != null) {
            return zzjq.zzk(zzdArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zzg() {
        zzc[] zzcArr = this.secondaryOpeningHours;
        if (zzcArr != null) {
            return zzjq.zzk(zzcArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zzh() {
        String[] strArr = this.types;
        if (strArr != null) {
            return zzjq.zzk(strArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzi() {
        return this.curbsidePickup;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzj() {
        return this.delivery;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzk() {
        return this.dineIn;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzl() {
        return this.reservable;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzm() {
        return this.servesBeer;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzn() {
        return this.servesBreakfast;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzo() {
        return this.servesDinner;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzp() {
        return this.servesLunch;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzq() {
        return this.servesVegetarianFood;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzr() {
        return this.servesWine;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzs() {
        return this.takeout;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Boolean zzt() {
        return this.wheelchairAccessibleEntrance;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Double zzu() {
        return this.rating;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Integer zzv() {
        return this.priceLevel;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Integer zzw() {
        return this.userRatingsTotal;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Integer zzx() {
        return this.utcOffset;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzy() {
        return this.businessStatus;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzz() {
        return this.formattedAddress;
    }
}
