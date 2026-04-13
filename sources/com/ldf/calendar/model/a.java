package com.ldf.calendar.model;

import android.util.Log;
import java.io.Serializable;
import java.util.Calendar;

/* compiled from: CalendarDate */
public class a implements Serializable {
    private static final long serialVersionUID = 1;
    public int day;
    public int month;
    public int year;

    public a(int year2, int month2, int day2) {
        if (month2 > 12) {
            month2 = 1;
            year2++;
        } else if (month2 < 1) {
            month2 = 12;
            year2--;
        }
        this.year = year2;
        this.month = month2;
        this.day = day2;
    }

    public a() {
        this.year = com.ldf.calendar.a.k();
        this.month = com.ldf.calendar.a.f();
        this.day = com.ldf.calendar.a.d();
    }

    public a modifyDay(int day2) {
        int lastMonthDays = com.ldf.calendar.a.g(this.year, this.month - 1);
        if (day2 > com.ldf.calendar.a.g(this.year, this.month)) {
            a modifyDate = new a(this.year, this.month, this.day);
            Log.e("ldf", "移动天数过大");
            return modifyDate;
        } else if (day2 > 0) {
            return new a(this.year, this.month, day2);
        } else {
            if (day2 > 0 - lastMonthDays) {
                return new a(this.year, this.month - 1, lastMonthDays + day2);
            }
            a modifyDate2 = new a(this.year, this.month, this.day);
            Log.e("ldf", "移动天数过大");
            return modifyDate2;
        }
    }

    public a modifyWeek(int offset) {
        a result = new a();
        Calendar c = Calendar.getInstance();
        c.set(1, this.year);
        c.set(2, this.month - 1);
        c.set(5, this.day);
        c.add(5, offset * 7);
        result.setYear(c.get(1));
        result.setMonth(c.get(2) + 1);
        result.setDay(c.get(5));
        return result;
    }

    public a modifyMonth(int offset) {
        a result = new a();
        int addToMonth = this.month + offset;
        int i = 12;
        if (offset > 0) {
            if (addToMonth > 12) {
                result.setYear(this.year + ((addToMonth - 1) / 12));
                if (addToMonth % 12 != 0) {
                    i = addToMonth % 12;
                }
                result.setMonth(i);
            } else {
                result.setYear(this.year);
                result.setMonth(addToMonth);
            }
        } else if (addToMonth == 0) {
            result.setYear(this.year - 1);
            result.setMonth(12);
        } else if (addToMonth < 0) {
            result.setYear((this.year + (addToMonth / 12)) - 1);
            int month2 = 12 - (Math.abs(addToMonth) % 12);
            if (month2 != 0) {
                i = month2;
            }
            result.setMonth(i);
        } else {
            result.setYear(this.year);
            if (addToMonth != 0) {
                i = addToMonth;
            }
            result.setMonth(i);
        }
        return result;
    }

    public String toString() {
        return this.year + "-" + this.month + "-" + this.day;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public boolean equals(a date) {
        if (date != null && getYear() == date.getYear() && getMonth() == date.getMonth() && getDay() == date.getDay()) {
            return true;
        }
        return false;
    }

    public a cloneSelf() {
        return new a(this.year, this.month, this.day);
    }
}
