package androidx.activity.result;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint({"BanParcelableUsage"})
public final class ActivityResult implements Parcelable {
    @NonNull
    public static final Parcelable.Creator<ActivityResult> CREATOR = new Parcelable.Creator<ActivityResult>() {
        public ActivityResult createFromParcel(@NonNull Parcel in) {
            return new ActivityResult(in);
        }

        public ActivityResult[] newArray(int size) {
            return new ActivityResult[size];
        }
    };
    @Nullable
    private final Intent mData;
    private final int mResultCode;

    public ActivityResult(int resultCode, @Nullable Intent data) {
        this.mResultCode = resultCode;
        this.mData = data;
    }

    ActivityResult(Parcel in) {
        this.mResultCode = in.readInt();
        this.mData = in.readInt() == 0 ? null : (Intent) Intent.CREATOR.createFromParcel(in);
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    @Nullable
    public Intent getData() {
        return this.mData;
    }

    public String toString() {
        return "ActivityResult{resultCode=" + resultCodeToString(this.mResultCode) + ", data=" + this.mData + '}';
    }

    @NonNull
    public static String resultCodeToString(int resultCode) {
        switch (resultCode) {
            case -1:
                return "RESULT_OK";
            case 0:
                return "RESULT_CANCELED";
            default:
                return String.valueOf(resultCode);
        }
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.mResultCode);
        dest.writeInt(this.mData == null ? 0 : 1);
        Intent intent = this.mData;
        if (intent != null) {
            intent.writeToParcel(dest, flags);
        }
    }

    public int describeContents() {
        return 0;
    }
}
