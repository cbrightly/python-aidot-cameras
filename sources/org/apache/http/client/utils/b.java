package org.apache.http.client.utils;

import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* compiled from: DateUtils */
public final class b {
    private static final String[] a = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    private static final Date b;
    public static final TimeZone c;

    static {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        c = timeZone;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        b = calendar.getTime();
    }

    public static Date a(String dateValue, String[] dateFormats) {
        return b(dateValue, dateFormats, (Date) null);
    }

    public static Date b(String dateValue, String[] dateFormats, Date startDate) {
        org.apache.http.util.a.i(dateValue, "Date value");
        String[] localDateFormats = dateFormats != null ? dateFormats : a;
        Date localStartDate = startDate != null ? startDate : b;
        String v = dateValue;
        if (v.length() > 1 && v.startsWith("'") && v.endsWith("'")) {
            v = v.substring(1, v.length() - 1);
        }
        for (String dateFormat : localDateFormats) {
            SimpleDateFormat dateParser = a.a(dateFormat);
            dateParser.set2DigitYearStart(localStartDate);
            ParsePosition pos = new ParsePosition(0);
            Date result = dateParser.parse(v, pos);
            if (pos.getIndex() != 0) {
                return result;
            }
        }
        return null;
    }

    /* compiled from: DateUtils */
    public static final class a {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> a = new ThreadLocal<>();

        public static SimpleDateFormat a(String pattern) {
            ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> threadLocal = a;
            SoftReference<Map<String, SimpleDateFormat>> ref = threadLocal.get();
            Map<String, SimpleDateFormat> formats = ref == null ? null : ref.get();
            if (formats == null) {
                formats = new HashMap<>();
                threadLocal.set(new SoftReference(formats));
            }
            SimpleDateFormat format = formats.get(pattern);
            if (format != null) {
                return format;
            }
            SimpleDateFormat format2 = new SimpleDateFormat(pattern, Locale.US);
            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
            formats.put(pattern, format2);
            return format2;
        }
    }
}
