package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import java.util.Comparator;

@SafeParcelable.Class(creator = "DetectedActivityCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class DetectedActivity extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<DetectedActivity> CREATOR = new zzl();
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    @NonNull
    public static final Comparator zza = new zzk();
    @SafeParcelable.Field(id = 1)
    int zzb;
    @SafeParcelable.Field(id = 2)
    int zzc;

    @SafeParcelable.Constructor
    public DetectedActivity(@SafeParcelable.Param(id = 1) int activityType, @SafeParcelable.Param(id = 2) int confidence) {
        this.zzb = activityType;
        this.zzc = confidence;
    }

    @ShowFirstParty
    public final boolean equals(@Nullable Object obj) {
        if (obj instanceof DetectedActivity) {
            DetectedActivity detectedActivity = (DetectedActivity) obj;
            if (this.zzb == detectedActivity.zzb && this.zzc == detectedActivity.zzc) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int getConfidence() {
        return this.zzc;
    }

    public int getType() {
        int i = this.zzb;
        if (i > 22 || i < 0) {
            return 4;
        }
        return i;
    }

    @ShowFirstParty
    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzb), Integer.valueOf(this.zzc));
    }

    @NonNull
    public String toString() {
        String str;
        int type = getType();
        switch (type) {
            case 0:
                str = "IN_VEHICLE";
                break;
            case 1:
                str = "ON_BICYCLE";
                break;
            case 2:
                str = "ON_FOOT";
                break;
            case 3:
                str = "STILL";
                break;
            case 4:
                str = LDNetUtil.NETWORKTYPE_INVALID;
                break;
            case 5:
                str = "TILTING";
                break;
            case 7:
                str = "WALKING";
                break;
            case 8:
                str = "RUNNING";
                break;
            case 16:
                str = "IN_ROAD_VEHICLE";
                break;
            case 17:
                str = "IN_RAIL_VEHICLE";
                break;
            default:
                str = Integer.toString(type);
                break;
        }
        int i = this.zzc;
        return "DetectedActivity [type=" + str + ", confidence=" + i + "]";
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        Preconditions.checkNotNull(out);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 1, this.zzb);
        SafeParcelWriter.writeInt(out, 2, this.zzc);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
