package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzw extends PlusCode {
    private final String zza;
    private final String zzb;

    zzw(@Nullable String str, @Nullable String str2) {
        this.zza = str;
        this.zzb = str2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PlusCode)) {
            return false;
        }
        PlusCode plusCode = (PlusCode) obj;
        String str = this.zza;
        if (str != null ? str.equals(plusCode.getCompoundCode()) : plusCode.getCompoundCode() == null) {
            String str2 = this.zzb;
            if (str2 != null ? str2.equals(plusCode.getGlobalCode()) : plusCode.getGlobalCode() == null) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public final String getCompoundCode() {
        return this.zza;
    }

    @Nullable
    public final String getGlobalCode() {
        return this.zzb;
    }

    public final String toString() {
        String str = this.zza;
        String str2 = this.zzb;
        return "PlusCode{compoundCode=" + str + ", globalCode=" + str2 + "}";
    }

    public final int hashCode() {
        int i;
        String str = this.zza;
        int i2 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        String str2 = this.zzb;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return ((i ^ 1000003) * 1000003) ^ i2;
    }
}
