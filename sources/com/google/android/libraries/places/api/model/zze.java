package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zze extends AutocompletePrediction {
    private final String zza;
    private final Integer zzb;
    private final List zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final List zzg;
    private final List zzh;
    private final List zzi;

    zze(String str, @Nullable Integer num, List list, String str2, String str3, String str4, @Nullable List list2, @Nullable List list3, @Nullable List list4) {
        if (str != null) {
            this.zza = str;
            this.zzb = num;
            if (list != null) {
                this.zzc = list;
                if (str2 != null) {
                    this.zzd = str2;
                    if (str3 != null) {
                        this.zze = str3;
                        if (str4 != null) {
                            this.zzf = str4;
                            this.zzg = list2;
                            this.zzh = list3;
                            this.zzi = list4;
                            return;
                        }
                        throw new NullPointerException("Null secondaryText");
                    }
                    throw new NullPointerException("Null primaryText");
                }
                throw new NullPointerException("Null fullText");
            }
            throw new NullPointerException("Null placeTypes");
        }
        throw new NullPointerException("Null placeId");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005d, code lost:
        r1 = r4.zzg;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0073, code lost:
        r1 = r4.zzh;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0089, code lost:
        r1 = r4.zzi;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0017, code lost:
        r1 = r4.zzb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.libraries.places.api.model.AutocompletePrediction
            r2 = 0
            if (r1 == 0) goto L_0x00a1
            com.google.android.libraries.places.api.model.AutocompletePrediction r5 = (com.google.android.libraries.places.api.model.AutocompletePrediction) r5
            java.lang.String r1 = r4.zza
            java.lang.String r3 = r5.getPlaceId()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            java.lang.Integer r1 = r4.zzb
            if (r1 != 0) goto L_0x0022
            java.lang.Integer r1 = r5.getDistanceMeters()
            if (r1 != 0) goto L_0x00a0
        L_0x0021:
            goto L_0x002d
        L_0x0022:
            java.lang.Integer r3 = r5.getDistanceMeters()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            goto L_0x0021
        L_0x002d:
            java.util.List r1 = r4.zzc
            java.util.List r3 = r5.getPlaceTypes()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            java.lang.String r1 = r4.zzd
            java.lang.String r3 = r5.zza()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            java.lang.String r1 = r4.zze
            java.lang.String r3 = r5.zzb()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            java.lang.String r1 = r4.zzf
            java.lang.String r3 = r5.zzc()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            java.util.List r1 = r4.zzg
            if (r1 != 0) goto L_0x0068
            java.util.List r1 = r5.zzd()
            if (r1 != 0) goto L_0x00a0
        L_0x0067:
            goto L_0x0073
        L_0x0068:
            java.util.List r3 = r5.zzd()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            goto L_0x0067
        L_0x0073:
            java.util.List r1 = r4.zzh
            if (r1 != 0) goto L_0x007e
            java.util.List r1 = r5.zze()
            if (r1 != 0) goto L_0x00a0
        L_0x007d:
            goto L_0x0089
        L_0x007e:
            java.util.List r3 = r5.zze()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a0
            goto L_0x007d
        L_0x0089:
            java.util.List r1 = r4.zzi
            if (r1 != 0) goto L_0x0094
            java.util.List r5 = r5.zzf()
            if (r5 != 0) goto L_0x009e
            goto L_0x009f
        L_0x0094:
            java.util.List r5 = r5.zzf()
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x009f
        L_0x009e:
            goto L_0x00a0
        L_0x009f:
            return r0
        L_0x00a0:
            return r2
        L_0x00a1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.model.zze.equals(java.lang.Object):boolean");
    }

    @Nullable
    public final Integer getDistanceMeters() {
        return this.zzb;
    }

    public final String getPlaceId() {
        return this.zza;
    }

    public final List<Place.Type> getPlaceTypes() {
        return this.zzc;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int hashCode = this.zza.hashCode() ^ 1000003;
        Integer num = this.zzb;
        int i4 = 0;
        if (num == null) {
            i = 0;
        } else {
            i = num.hashCode();
        }
        int hashCode2 = ((((((((((hashCode * 1000003) ^ i) * 1000003) ^ this.zzc.hashCode()) * 1000003) ^ this.zzd.hashCode()) * 1000003) ^ this.zze.hashCode()) * 1000003) ^ this.zzf.hashCode()) * 1000003;
        List list = this.zzg;
        if (list == null) {
            i2 = 0;
        } else {
            i2 = list.hashCode();
        }
        int i5 = (hashCode2 ^ i2) * 1000003;
        List list2 = this.zzh;
        if (list2 == null) {
            i3 = 0;
        } else {
            i3 = list2.hashCode();
        }
        int i6 = (i5 ^ i3) * 1000003;
        List list3 = this.zzi;
        if (list3 != null) {
            i4 = list3.hashCode();
        }
        return i6 ^ i4;
    }

    public final String toString() {
        String str = this.zza;
        Integer num = this.zzb;
        String obj = this.zzc.toString();
        String str2 = this.zzd;
        String str3 = this.zze;
        String str4 = this.zzf;
        String valueOf = String.valueOf(this.zzg);
        String valueOf2 = String.valueOf(this.zzh);
        String valueOf3 = String.valueOf(this.zzi);
        return "AutocompletePrediction{placeId=" + str + ", distanceMeters=" + num + ", placeTypes=" + obj + ", fullText=" + str2 + ", primaryText=" + str3 + ", secondaryText=" + str4 + ", fullTextMatchedSubstrings=" + valueOf + ", primaryTextMatchedSubstrings=" + valueOf2 + ", secondaryTextMatchedSubstrings=" + valueOf3 + "}";
    }

    /* access modifiers changed from: package-private */
    public final String zza() {
        return this.zzd;
    }

    /* access modifiers changed from: package-private */
    public final String zzb() {
        return this.zze;
    }

    /* access modifiers changed from: package-private */
    public final String zzc() {
        return this.zzf;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final List zzd() {
        return this.zzg;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final List zze() {
        return this.zzh;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final List zzf() {
        return this.zzi;
    }
}
