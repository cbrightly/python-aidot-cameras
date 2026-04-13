package com.leedarson.utils;

import android.content.Context;
import com.leedarson.R$string;
import com.leedarson.bean.CalendarData;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import timber.log.a;

/* compiled from: DateUtil */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String i(int sec) {
        String minuteStr;
        String sceondStr;
        String minuteStr2;
        String sceondStr2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(sec)}, (Object) null, changeQuickRedirect, true, 11477, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (sec <= 0) {
            return "00:00";
        }
        int minute = sec / 60;
        if (minute < 60) {
            int second = sec % 60;
            if (minute < 10) {
                minuteStr2 = "0" + minute;
            } else {
                minuteStr2 = String.valueOf(minute);
            }
            if (second < 10) {
                sceondStr2 = "0" + second;
            } else {
                sceondStr2 = String.valueOf(second);
            }
            return minuteStr2 + ":" + sceondStr2;
        }
        int hour = minute / 60;
        if (hour > 99) {
            return "99:59:59";
        }
        int minute2 = minute % 60;
        int second2 = (sec - (hour * 3600)) - (minute2 * 60);
        if (hour < 10) {
            String str = "0" + hour;
        } else {
            String hourStr = String.valueOf(hour);
        }
        if (minute2 < 10) {
            minuteStr = "0" + minute2;
        } else {
            minuteStr = String.valueOf(minute2);
        }
        if (second2 < 10) {
            sceondStr = "0" + second2;
        } else {
            sceondStr = String.valueOf(second2);
        }
        return minuteStr + ":" + sceondStr;
    }

    public static String c(int time) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(time)}, (Object) null, changeQuickRedirect, true, 11478, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int min = time / 60;
        int sec = time % 60;
        if (time < 0) {
            return String.format(Locale.US, "%02d:%02d", new Object[]{0, 0});
        }
        return String.format(Locale.US, "%02d:%02d", new Object[]{Integer.valueOf(min), Integer.valueOf(sec)});
    }

    public static String j(long time, String format) {
        Object[] objArr = {new Long(time), format};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11480, new Class[]{Long.TYPE, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static String d(Context context, long time, String str) {
        StringBuilder sb;
        StringBuilder sb2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Long(time), str}, (Object) null, changeQuickRedirect, true, 11481, new Class[]{Context.class, Long.TYPE, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int hour = mCalendar.get(10);
        int minute = mCalendar.get(12);
        int apm = mCalendar.get(9);
        if (hour < 10) {
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(hour);
        String hourStr = sb.toString();
        if (minute < 10) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(minute);
        String minuteStr = sb2.toString();
        a.b g = a.g("get12format");
        g.h("get12format:hour= " + hourStr + "：" + minuteStr, new Object[0]);
        if ("00".equals(hourStr)) {
            hourStr = "12";
        }
        if (apm == 1) {
            return hourStr + ":" + minuteStr + " " + context.getString(R$string.time_pm);
        }
        return hourStr + ":" + minuteStr + " " + context.getString(R$string.time_am);
    }

    public static String g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11482, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat(TimeUtils.YYYY_MM_DD).format(new Date());
    }

    public static long b(String date, String format) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{date, format}, (Object) null, changeQuickRedirect2, true, 11483, new Class[]{cls, cls}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(date));
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String e(CalendarData calendarData) {
        StringBuilder sb;
        StringBuilder sb2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{calendarData}, (Object) null, changeQuickRedirect, true, 11485, new Class[]{CalendarData.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String sYear = String.valueOf(calendarData.year);
        String sMonth = String.valueOf(calendarData.month);
        String sDay = String.valueOf(calendarData.day);
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        objArr[0] = sYear;
        if (2 > sMonth.length()) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(sMonth);
        objArr[1] = sb.toString();
        if (2 > sDay.length()) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(sDay);
        objArr[2] = sb2.toString();
        return String.format(locale, "%s-%s-%s", objArr);
    }

    public static int a(String DATE1, String DATE2) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{DATE1, DATE2}, (Object) null, changeQuickRedirect, true, 11486, new Class[]{cls, cls}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        SimpleDateFormat df = new SimpleDateFormat(TimeUtils.YYYY_MM_DD);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            }
            if (dt1.getTime() < dt2.getTime()) {
                return -1;
            }
            return 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public static String f(com.ldf.calendar.model.a calendarDate) {
        StringBuilder sb;
        StringBuilder sb2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{calendarDate}, (Object) null, changeQuickRedirect, true, 11487, new Class[]{com.ldf.calendar.model.a.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (calendarDate == null) {
            return "";
        }
        String sYear = String.valueOf(calendarDate.year);
        String sMonth = String.valueOf(calendarDate.month);
        String sDay = String.valueOf(calendarDate.day);
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        objArr[0] = sYear;
        if (2 > sMonth.length()) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(sMonth);
        objArr[1] = sb.toString();
        if (2 > sDay.length()) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(sDay);
        objArr[2] = sb2.toString();
        return String.format(locale, "%s-%s-%s", objArr);
    }

    public static boolean h(com.ldf.calendar.model.a currentDate, com.ldf.calendar.model.a today) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{currentDate, today}, (Object) null, changeQuickRedirect, true, 11489, new Class[]{com.ldf.calendar.model.a.class, com.ldf.calendar.model.a.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (currentDate == null) {
            return false;
        }
        return currentDate.getYear() > today.getYear() || (currentDate.getYear() == today.getYear() && currentDate.getMonth() > today.getMonth()) || (currentDate.getYear() == today.getYear() && currentDate.getMonth() == today.getMonth() && currentDate.getDay() > today.getDay());
    }
}
