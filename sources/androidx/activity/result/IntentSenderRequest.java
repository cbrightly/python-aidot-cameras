package androidx.activity.result;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint({"BanParcelableUsage"})
public final class IntentSenderRequest implements Parcelable {
    @NonNull
    public static final Parcelable.Creator<IntentSenderRequest> CREATOR = new Parcelable.Creator<IntentSenderRequest>() {
        public IntentSenderRequest createFromParcel(Parcel in) {
            return new IntentSenderRequest(in);
        }

        public IntentSenderRequest[] newArray(int size) {
            return new IntentSenderRequest[size];
        }
    };
    @Nullable
    private final Intent mFillInIntent;
    private final int mFlagsMask;
    private final int mFlagsValues;
    @NonNull
    private final IntentSender mIntentSender;

    IntentSenderRequest(@NonNull IntentSender intentSender, @Nullable Intent intent, int flagsMask, int flagsValues) {
        this.mIntentSender = intentSender;
        this.mFillInIntent = intent;
        this.mFlagsMask = flagsMask;
        this.mFlagsValues = flagsValues;
    }

    @NonNull
    public IntentSender getIntentSender() {
        return this.mIntentSender;
    }

    @Nullable
    public Intent getFillInIntent() {
        return this.mFillInIntent;
    }

    public int getFlagsMask() {
        return this.mFlagsMask;
    }

    public int getFlagsValues() {
        return this.mFlagsValues;
    }

    IntentSenderRequest(@NonNull Parcel in) {
        this.mIntentSender = (IntentSender) in.readParcelable(IntentSender.class.getClassLoader());
        this.mFillInIntent = (Intent) in.readParcelable(Intent.class.getClassLoader());
        this.mFlagsMask = in.readInt();
        this.mFlagsValues = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(this.mIntentSender, flags);
        dest.writeParcelable(this.mFillInIntent, flags);
        dest.writeInt(this.mFlagsMask);
        dest.writeInt(this.mFlagsValues);
    }

    public static final class Builder {
        private Intent mFillInIntent;
        private int mFlagsMask;
        private int mFlagsValues;
        private IntentSender mIntentSender;

        public Builder(@NonNull IntentSender intentSender) {
            this.mIntentSender = intentSender;
        }

        public Builder(@NonNull PendingIntent pendingIntent) {
            this(pendingIntent.getIntentSender());
        }

        @NonNull
        public Builder setFillInIntent(@Nullable Intent fillInIntent) {
            this.mFillInIntent = fillInIntent;
            return this;
        }

        @NonNull
        public Builder setFlags(int values, int mask) {
            this.mFlagsValues = values;
            this.mFlagsMask = mask;
            return this;
        }

        @NonNull
        public IntentSenderRequest build() {
            return new IntentSenderRequest(this.mIntentSender, this.mFillInIntent, this.mFlagsMask, this.mFlagsValues);
        }
    }
}
