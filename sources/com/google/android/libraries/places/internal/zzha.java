package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzha extends zzhj {
    private final AutocompleteActivityMode zza;
    private final zzjq zzb;
    private final zzhh zzc;
    private final String zzd;
    private final String zze;
    private final LocationBias zzf;
    private final LocationRestriction zzg;
    private final zzjq zzh;
    private final TypeFilter zzi;
    private final zzjq zzj;
    private final int zzk;
    private final int zzl;

    zzha(AutocompleteActivityMode autocompleteActivityMode, zzjq zzjq, zzhh zzhh, @Nullable String str, @Nullable String str2, @Nullable LocationBias locationBias, @Nullable LocationRestriction locationRestriction, zzjq zzjq2, @Nullable TypeFilter typeFilter, zzjq zzjq3, int i, int i2) {
        if (autocompleteActivityMode != null) {
            this.zza = autocompleteActivityMode;
            if (zzjq != null) {
                this.zzb = zzjq;
                if (zzhh != null) {
                    this.zzc = zzhh;
                    this.zzd = str;
                    this.zze = str2;
                    this.zzf = locationBias;
                    this.zzg = locationRestriction;
                    if (zzjq2 != null) {
                        this.zzh = zzjq2;
                        this.zzi = typeFilter;
                        if (zzjq3 != null) {
                            this.zzj = zzjq3;
                            this.zzk = i;
                            this.zzl = i2;
                            return;
                        }
                        throw new NullPointerException("Null typesFilter");
                    }
                    throw new NullPointerException("Null countries");
                }
                throw new NullPointerException("Null origin");
            }
            throw new NullPointerException("Null placeFields");
        }
        throw new NullPointerException("Null mode");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        r1 = r4.zzd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
        r1 = r4.zze;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        r1 = r4.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        r1 = r4.zzg;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0093, code lost:
        r1 = r4.zzi;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.libraries.places.internal.zzhj
            r2 = 0
            if (r1 == 0) goto L_0x00c7
            com.google.android.libraries.places.internal.zzhj r5 = (com.google.android.libraries.places.internal.zzhj) r5
            com.google.android.libraries.places.widget.model.AutocompleteActivityMode r1 = r4.zza
            com.google.android.libraries.places.widget.model.AutocompleteActivityMode r3 = r5.zzh()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            com.google.android.libraries.places.internal.zzjq r1 = r4.zzb
            com.google.android.libraries.places.internal.zzjq r3 = r5.zzj()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            com.google.android.libraries.places.internal.zzhh r1 = r4.zzc
            com.google.android.libraries.places.internal.zzhh r3 = r5.zzf()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            java.lang.String r1 = r4.zzd
            if (r1 != 0) goto L_0x003a
            java.lang.String r1 = r5.zzm()
            if (r1 != 0) goto L_0x00c6
        L_0x0039:
            goto L_0x0045
        L_0x003a:
            java.lang.String r3 = r5.zzm()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            goto L_0x0039
        L_0x0045:
            java.lang.String r1 = r4.zze
            if (r1 != 0) goto L_0x0050
            java.lang.String r1 = r5.zzl()
            if (r1 != 0) goto L_0x00c6
        L_0x004f:
            goto L_0x005b
        L_0x0050:
            java.lang.String r3 = r5.zzl()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            goto L_0x004f
        L_0x005b:
            com.google.android.libraries.places.api.model.LocationBias r1 = r4.zzf
            if (r1 != 0) goto L_0x0066
            com.google.android.libraries.places.api.model.LocationBias r1 = r5.zzc()
            if (r1 != 0) goto L_0x00c6
        L_0x0065:
            goto L_0x0071
        L_0x0066:
            com.google.android.libraries.places.api.model.LocationBias r3 = r5.zzc()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            goto L_0x0065
        L_0x0071:
            com.google.android.libraries.places.api.model.LocationRestriction r1 = r4.zzg
            if (r1 != 0) goto L_0x007c
            com.google.android.libraries.places.api.model.LocationRestriction r1 = r5.zzd()
            if (r1 != 0) goto L_0x00c6
        L_0x007b:
            goto L_0x0087
        L_0x007c:
            com.google.android.libraries.places.api.model.LocationRestriction r3 = r5.zzd()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            goto L_0x007b
        L_0x0087:
            com.google.android.libraries.places.internal.zzjq r1 = r4.zzh
            com.google.android.libraries.places.internal.zzjq r3 = r5.zzi()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            com.google.android.libraries.places.api.model.TypeFilter r1 = r4.zzi
            if (r1 != 0) goto L_0x009e
            com.google.android.libraries.places.api.model.TypeFilter r1 = r5.zze()
            if (r1 != 0) goto L_0x00a8
            goto L_0x00a9
        L_0x009e:
            com.google.android.libraries.places.api.model.TypeFilter r3 = r5.zze()
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x00a9
        L_0x00a8:
            goto L_0x00c6
        L_0x00a9:
            com.google.android.libraries.places.internal.zzjq r1 = r4.zzj
            com.google.android.libraries.places.internal.zzjq r3 = r5.zzk()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c6
            int r1 = r4.zzk
            int r3 = r5.zza()
            if (r1 != r3) goto L_0x00c6
            int r1 = r4.zzl
            int r5 = r5.zzb()
            if (r1 != r5) goto L_0x00c6
            return r0
        L_0x00c6:
            return r2
        L_0x00c7:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzha.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int hashCode = ((((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode()) * 1000003) ^ this.zzc.hashCode();
        String str = this.zzd;
        int i5 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        int i6 = ((hashCode * 1000003) ^ i) * 1000003;
        String str2 = this.zze;
        if (str2 == null) {
            i2 = 0;
        } else {
            i2 = str2.hashCode();
        }
        int i7 = (i6 ^ i2) * 1000003;
        LocationBias locationBias = this.zzf;
        if (locationBias == null) {
            i3 = 0;
        } else {
            i3 = locationBias.hashCode();
        }
        int i8 = (i7 ^ i3) * 1000003;
        LocationRestriction locationRestriction = this.zzg;
        if (locationRestriction == null) {
            i4 = 0;
        } else {
            i4 = locationRestriction.hashCode();
        }
        int hashCode2 = (((i8 ^ i4) * 1000003) ^ this.zzh.hashCode()) * 1000003;
        TypeFilter typeFilter = this.zzi;
        if (typeFilter != null) {
            i5 = typeFilter.hashCode();
        }
        return ((((((hashCode2 ^ i5) * 1000003) ^ this.zzj.hashCode()) * 1000003) ^ this.zzk) * 1000003) ^ this.zzl;
    }

    public final String toString() {
        String obj = this.zza.toString();
        String obj2 = this.zzb.toString();
        String obj3 = this.zzc.toString();
        String str = this.zzd;
        String str2 = this.zze;
        String valueOf = String.valueOf(this.zzf);
        String valueOf2 = String.valueOf(this.zzg);
        String obj4 = this.zzh.toString();
        String valueOf3 = String.valueOf(this.zzi);
        String obj5 = this.zzj.toString();
        int i = this.zzk;
        int i2 = this.zzl;
        return "AutocompleteOptions{mode=" + obj + ", placeFields=" + obj2 + ", origin=" + obj3 + ", initialQuery=" + str + ", hint=" + str2 + ", locationBias=" + valueOf + ", locationRestriction=" + valueOf2 + ", countries=" + obj4 + ", typeFilter=" + valueOf3 + ", typesFilter=" + obj5 + ", primaryColor=" + i + ", primaryColorDark=" + i2 + "}";
    }

    public final int zza() {
        return this.zzk;
    }

    public final int zzb() {
        return this.zzl;
    }

    @Nullable
    public final LocationBias zzc() {
        return this.zzf;
    }

    @Nullable
    public final LocationRestriction zzd() {
        return this.zzg;
    }

    @Deprecated
    @Nullable
    public final TypeFilter zze() {
        return this.zzi;
    }

    public final zzhh zzf() {
        return this.zzc;
    }

    public final zzhi zzg() {
        return new zzgz(this, (zzgy) null);
    }

    public final AutocompleteActivityMode zzh() {
        return this.zza;
    }

    public final zzjq zzi() {
        return this.zzh;
    }

    public final zzjq zzj() {
        return this.zzb;
    }

    public final zzjq zzk() {
        return this.zzj;
    }

    @Nullable
    public final String zzl() {
        return this.zze;
    }

    @Nullable
    public final String zzm() {
        return this.zzd;
    }
}
