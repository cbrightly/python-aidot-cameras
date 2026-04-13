package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class CalendarData {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int day;
    public boolean isLastMonthDay = false;
    public boolean isNextMonthDay = false;
    public int month;
    public int week = -1;
    public int year;

    public CalendarData() {
    }

    public CalendarData(int year2, int month2, int day2) {
        this.year = year2;
        this.month = month2;
        this.day = day2;
    }

    public boolean isSameDay(CalendarData data) {
        return data.day == this.day && data.month == this.month && data.year == this.year;
    }

    public boolean isFuture(CalendarData date) {
        if (date == null) {
            return false;
        }
        int i = this.year;
        int i2 = date.year;
        if (i > i2) {
            return true;
        }
        if (i == i2 && this.month > date.month) {
            return true;
        }
        if (i == i2 && this.month == date.month && this.day > date.day) {
            return true;
        }
        return false;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 999, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "CalendarData{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", isNextMonthDay=" + this.isNextMonthDay + ", isLastMonthDay=" + this.isLastMonthDay + '}';
    }
}
