package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class Month implements Comparable<Month>, Parcelable {
    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator<Month>() {
        @NonNull
        public Month createFromParcel(@NonNull Parcel source) {
            return Month.create(source.readInt(), source.readInt());
        }

        @NonNull
        public Month[] newArray(int size) {
            return new Month[size];
        }
    };
    final int daysInMonth;
    final int daysInWeek;
    @NonNull
    private final Calendar firstOfMonth;
    @Nullable
    private String longName;
    final int month;
    final long timeInMillis;
    final int year;

    private Month(@NonNull Calendar rawCalendar) {
        rawCalendar.set(5, 1);
        Calendar dayCopy = UtcDates.getDayCopy(rawCalendar);
        this.firstOfMonth = dayCopy;
        this.month = dayCopy.get(2);
        this.year = dayCopy.get(1);
        this.daysInWeek = dayCopy.getMaximum(7);
        this.daysInMonth = dayCopy.getActualMaximum(5);
        this.timeInMillis = dayCopy.getTimeInMillis();
    }

    @NonNull
    static Month create(long timeInMillis2) {
        Calendar calendar = UtcDates.getUtcCalendar();
        calendar.setTimeInMillis(timeInMillis2);
        return new Month(calendar);
    }

    @NonNull
    static Month create(int year2, int month2) {
        Calendar calendar = UtcDates.getUtcCalendar();
        calendar.set(1, year2);
        calendar.set(2, month2);
        return new Month(calendar);
    }

    @NonNull
    static Month current() {
        return new Month(UtcDates.getTodayCalendar());
    }

    /* access modifiers changed from: package-private */
    public int daysFromStartOfWeekToFirstOfMonth() {
        int difference = this.firstOfMonth.get(7) - this.firstOfMonth.getFirstDayOfWeek();
        if (difference < 0) {
            return difference + this.daysInWeek;
        }
        return difference;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Month)) {
            return false;
        }
        Month that = (Month) o;
        if (this.month == that.month && this.year == that.year) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.month), Integer.valueOf(this.year)});
    }

    public int compareTo(@NonNull Month other) {
        return this.firstOfMonth.compareTo(other.firstOfMonth);
    }

    /* access modifiers changed from: package-private */
    public int monthsUntil(@NonNull Month other) {
        if (this.firstOfMonth instanceof GregorianCalendar) {
            return ((other.year - this.year) * 12) + (other.month - this.month);
        }
        throw new IllegalArgumentException("Only Gregorian calendars are supported.");
    }

    /* access modifiers changed from: package-private */
    public long getStableId() {
        return this.firstOfMonth.getTimeInMillis();
    }

    /* access modifiers changed from: package-private */
    public long getDay(int day) {
        Calendar dayCalendar = UtcDates.getDayCopy(this.firstOfMonth);
        dayCalendar.set(5, day);
        return dayCalendar.getTimeInMillis();
    }

    /* access modifiers changed from: package-private */
    public int getDayOfMonth(long date) {
        Calendar dayCalendar = UtcDates.getDayCopy(this.firstOfMonth);
        dayCalendar.setTimeInMillis(date);
        return dayCalendar.get(5);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Month monthsLater(int months) {
        Calendar laterMonth = UtcDates.getDayCopy(this.firstOfMonth);
        laterMonth.add(2, months);
        return new Month(laterMonth);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String getLongName(Context context) {
        if (this.longName == null) {
            this.longName = DateStrings.getYearMonth(context, this.firstOfMonth.getTimeInMillis());
        }
        return this.longName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
    }
}
