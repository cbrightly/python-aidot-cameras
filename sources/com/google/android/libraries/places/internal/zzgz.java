package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgz extends zzhi {
    private AutocompleteActivityMode zza;
    private zzjq zzb;
    private zzhh zzc;
    private String zzd;
    private String zze;
    private LocationBias zzf;
    private LocationRestriction zzg;
    private zzjq zzh;
    private TypeFilter zzi;
    private zzjq zzj;
    private int zzk;
    private int zzl;
    private byte zzm;

    zzgz() {
    }

    /* synthetic */ zzgz(zzhj zzhj, zzgy zzgy) {
        this.zza = zzhj.zzh();
        this.zzb = zzhj.zzj();
        this.zzc = zzhj.zzf();
        this.zzd = zzhj.zzm();
        this.zze = zzhj.zzl();
        this.zzf = zzhj.zzc();
        this.zzg = zzhj.zzd();
        this.zzh = zzhj.zzi();
        this.zzi = zzhj.zze();
        this.zzj = zzhj.zzk();
        this.zzk = zzhj.zza();
        this.zzl = zzhj.zzb();
        this.zzm = 3;
    }

    public final zzhi zza(List list) {
        this.zzh = zzjq.zzj(list);
        return this;
    }

    public final zzhi zzb(@Nullable String str) {
        this.zze = str;
        return this;
    }

    public final zzhi zzc(@Nullable String str) {
        this.zzd = str;
        return this;
    }

    public final zzhi zzd(@Nullable LocationBias locationBias) {
        this.zzf = locationBias;
        return this;
    }

    public final zzhi zze(@Nullable LocationRestriction locationRestriction) {
        this.zzg = locationRestriction;
        return this;
    }

    public final zzhi zzf(AutocompleteActivityMode autocompleteActivityMode) {
        if (autocompleteActivityMode != null) {
            this.zza = autocompleteActivityMode;
            return this;
        }
        throw new NullPointerException("Null mode");
    }

    public final zzhi zzg(zzhh zzhh) {
        if (zzhh != null) {
            this.zzc = zzhh;
            return this;
        }
        throw new NullPointerException("Null origin");
    }

    public final zzhi zzh(List list) {
        this.zzb = zzjq.zzj(list);
        return this;
    }

    public final zzhi zzi(int i) {
        this.zzk = i;
        this.zzm = (byte) (this.zzm | 1);
        return this;
    }

    public final zzhi zzj(int i) {
        this.zzl = i;
        this.zzm = (byte) (this.zzm | 2);
        return this;
    }

    public final zzhi zzk(@Nullable TypeFilter typeFilter) {
        this.zzi = typeFilter;
        return this;
    }

    public final zzhi zzl(List list) {
        this.zzj = zzjq.zzj(list);
        return this;
    }

    public final zzhj zzm() {
        AutocompleteActivityMode autocompleteActivityMode;
        zzjq zzjq;
        zzhh zzhh;
        zzjq zzjq2;
        zzjq zzjq3;
        if (this.zzm == 3 && (autocompleteActivityMode = this.zza) != null && (zzjq = this.zzb) != null && (zzhh = this.zzc) != null && (zzjq2 = this.zzh) != null && (zzjq3 = this.zzj) != null) {
            return new zzhc(autocompleteActivityMode, zzjq, zzhh, this.zzd, this.zze, this.zzf, this.zzg, zzjq2, this.zzi, zzjq3, this.zzk, this.zzl);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" mode");
        }
        if (this.zzb == null) {
            sb.append(" placeFields");
        }
        if (this.zzc == null) {
            sb.append(" origin");
        }
        if (this.zzh == null) {
            sb.append(" countries");
        }
        if (this.zzj == null) {
            sb.append(" typesFilter");
        }
        if ((this.zzm & 1) == 0) {
            sb.append(" primaryColor");
        }
        if ((this.zzm & 2) == 0) {
            sb.append(" primaryColorDark");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
