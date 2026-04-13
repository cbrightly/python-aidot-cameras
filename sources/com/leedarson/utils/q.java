package com.leedarson.utils;

import com.leedarson.bean.CalendarData;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import meshsdk.BaseResp;

/* compiled from: WeekCalendarUtil */
public class q {
    private static int a = 0;
    private static int b = 0;
    private static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean n(int year) {
        if (year % 100 == 0 && year % BaseResp.ERR_MSG_SEND_FAIL == 0) {
            return true;
        }
        if (year % 100 == 0 || year % 4 != 0) {
            return false;
        }
        return true;
    }

    public static int c(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11567, new Class[]{CalendarData.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        switch (day.month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                a = 31;
                break;
            case 2:
                if (!n(day.year)) {
                    a = 28;
                    break;
                } else {
                    a = 29;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                a = 30;
                break;
        }
        return a;
    }

    public static int j(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11568, new Class[]{CalendarData.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(day.year, day.month - 1, 1);
        int i = cal.get(7) - 1;
        b = i;
        return i;
    }

    public static int i(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11569, new Class[]{CalendarData.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return g(j(e(day)));
    }

    public static List<CalendarData> l(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11571, new Class[]{CalendarData.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<CalendarData> datas = new ArrayList<>();
        int weekdayOfFirstDayInMonth = j(day);
        int weekdayOfLastDayInMonth = i(day);
        datas.addAll(k(day));
        for (int i = 0; i < weekdayOfFirstDayInMonth; i++) {
            CalendarData lastDay = a(datas.get(0));
            lastDay.isLastMonthDay = true;
            datas.add(0, lastDay);
        }
        for (int i2 = 0; i2 < 6 - weekdayOfLastDayInMonth; i2++) {
            CalendarData nextday = b(datas.get(datas.size() - 1));
            nextday.isNextMonthDay = true;
            datas.add(nextday);
        }
        return datas;
    }

    public static List<CalendarData> k(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11572, new Class[]{CalendarData.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<CalendarData> datas = new ArrayList<>();
        int monthDay = c(day);
        for (int i = 0; i < monthDay; i++) {
            datas.add(new CalendarData(day.year, day.month, i + 1));
        }
        return datas;
    }

    public static Map<Integer, List> m(List<CalendarData> datas) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{datas}, (Object) null, changeQuickRedirect, true, 11573, new Class[]{List.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        Map<Integer, List> map = new LinkedHashMap<>();
        int weekSize = datas.size() / 7;
        for (int i = 0; i < weekSize; i++) {
            List<CalendarData> week = new ArrayList<>();
            for (int j = i * 7; j < (i * 7) + 7; j++) {
                week.add(datas.get(j));
                map.put(Integer.valueOf(i), week);
            }
        }
        return map;
    }

    public static int f(Map<Integer, List> map, CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{map, day}, (Object) null, changeQuickRedirect, true, 11574, new Class[]{Map.class, CalendarData.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int position = -1;
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            position++;
            List<CalendarData> list = entry.getValue();
            int i = 0;
            while (true) {
                if (i < list.size()) {
                    if (list.get(i).day == day.day && list.get(i).month == day.month) {
                        return position;
                    }
                    i++;
                }
            }
        }
        return 0;
    }

    public static CalendarData a(CalendarData theday) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{theday}, (Object) null, changeQuickRedirect, true, 11575, new Class[]{CalendarData.class}, CalendarData.class);
        if (proxy.isSupported) {
            return (CalendarData) proxy.result;
        }
        CalendarData lastday = new CalendarData();
        lastday.week = g(theday.week);
        CalendarData theDayOfLastMonth = d(theday);
        int i = theday.day;
        if (i == 1) {
            lastday.day = c(theDayOfLastMonth);
            lastday.month = theDayOfLastMonth.month;
            lastday.year = theDayOfLastMonth.year;
        } else {
            lastday.day = i - 1;
            lastday.month = theday.month;
            lastday.year = theday.year;
        }
        return lastday;
    }

    public static CalendarData b(CalendarData theday) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{theday}, (Object) null, changeQuickRedirect, true, 11576, new Class[]{CalendarData.class}, CalendarData.class);
        if (proxy.isSupported) {
            return (CalendarData) proxy.result;
        }
        CalendarData nextday = new CalendarData();
        CalendarData theDayOfNextMonth = e(theday);
        nextday.week = h(theday.week);
        if (theday.day == c(theday)) {
            nextday.day = 1;
            nextday.month = theDayOfNextMonth.month;
            nextday.year = theDayOfNextMonth.year;
        } else {
            nextday.day = theday.day + 1;
            nextday.month = theday.month;
            nextday.year = theday.year;
        }
        return nextday;
    }

    public static int g(int week) {
        if (week == 0) {
            return 6;
        }
        return week - 1;
    }

    public static int h(int week) {
        if (week == 6) {
            return 0;
        }
        return week + 1;
    }

    public static CalendarData e(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11577, new Class[]{CalendarData.class}, CalendarData.class);
        if (proxy.isSupported) {
            return (CalendarData) proxy.result;
        }
        CalendarData data = new CalendarData();
        int month = day.month;
        if (month == 12) {
            data.month = 1;
            data.year = day.year + 1;
            data.day = day.day;
        } else {
            data.month = month + 1;
            data.year = day.year;
            data.day = day.day;
        }
        return data;
    }

    public static CalendarData d(CalendarData day) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{day}, (Object) null, changeQuickRedirect, true, 11578, new Class[]{CalendarData.class}, CalendarData.class);
        if (proxy.isSupported) {
            return (CalendarData) proxy.result;
        }
        CalendarData data = new CalendarData();
        int month = day.month;
        if (month == 1) {
            data.month = 12;
            data.year = day.year - 1;
            data.day = day.day;
        } else {
            data.month = month - 1;
            data.year = day.year;
            data.day = day.day;
        }
        return data;
    }
}
