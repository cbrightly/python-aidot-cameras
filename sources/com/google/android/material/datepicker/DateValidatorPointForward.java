package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.Arrays;

public class DateValidatorPointForward implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<DateValidatorPointForward> CREATOR = new Parcelable.Creator<DateValidatorPointForward>() {
        @NonNull
        public DateValidatorPointForward createFromParcel(@NonNull Parcel source) {
            return new DateValidatorPointForward(source.readLong());
        }

        @NonNull
        public DateValidatorPointForward[] newArray(int size) {
            return new DateValidatorPointForward[size];
        }
    };
    private final long point;

    private DateValidatorPointForward(long point2) {
        this.point = point2;
    }

    @NonNull
    public static DateValidatorPointForward from(long point2) {
        return new DateValidatorPointForward(point2);
    }

    @NonNull
    public static DateValidatorPointForward now() {
        return from(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    public boolean isValid(long date) {
        return date >= this.point;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(this.point);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DateValidatorPointForward)) {
            return false;
        }
        if (this.point == ((DateValidatorPointForward) o).point) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.point)});
    }
}
