package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzei {
    @Nullable
    private String description;
    @Nullable
    private Integer distanceMeters;
    @Nullable
    private zzb[] matchedSubstrings;
    @Nullable
    private String placeId;
    @Nullable
    private zza structuredFormatting;
    @Nullable
    private String[] types;

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zza {
        @Nullable
        private String mainText;
        @Nullable
        private zzb[] mainTextMatchedSubstrings;
        @Nullable
        private String secondaryText;
        @Nullable
        private zzb[] secondaryTextMatchedSubstrings;

        zza() {
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zza() {
            zzb[] zzbArr = this.mainTextMatchedSubstrings;
            if (zzbArr != null) {
                return zzjq.zzk(zzbArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final zzjq zzb() {
            zzb[] zzbArr = this.secondaryTextMatchedSubstrings;
            if (zzbArr != null) {
                return zzjq.zzk(zzbArr);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzc() {
            return this.mainText;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public final String zzd() {
            return this.secondaryText;
        }
    }

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public class zzb {
        @Nullable
        Integer length;
        @Nullable
        Integer offset;

        zzb() {
        }
    }

    zzei() {
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zza zza() {
        return this.structuredFormatting;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zzb() {
        zzb[] zzbArr = this.matchedSubstrings;
        if (zzbArr != null) {
            return zzjq.zzk(zzbArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzjq zzc() {
        String[] strArr = this.types;
        if (strArr != null) {
            return zzjq.zzk(strArr);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Integer zzd() {
        return this.distanceMeters;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zze() {
        return this.description;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzf() {
        return this.placeId;
    }
}
