package com.yanzhenjie.andserver.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: HttpDateFormat */
public final class d {
    private static final SimpleDateFormat[] a;
    private static final TimeZone b;
    private static final SimpleDateFormat c;

    static {
        Locale locale = Locale.US;
        a = new SimpleDateFormat[]{new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", locale), new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", locale), new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", locale)};
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        b = timeZone;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", locale);
        c = simpleDateFormat;
        simpleDateFormat.setTimeZone(timeZone);
    }

    public static String a(long value) {
        String format;
        synchronized (d.class) {
            format = c.format(new Date(value));
        }
        return format;
    }

    public static long b(String value) {
        Date date = null;
        for (SimpleDateFormat format : a) {
            try {
                date = format.parse(value);
            } catch (ParseException e) {
            }
        }
        if (date == null) {
            return -1;
        }
        return date.getTime();
    }
}
