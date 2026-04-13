package com.squareup.okhttp.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: HttpDate */
public final class g {
    /* access modifiers changed from: private */
    public static final TimeZone a = TimeZone.getTimeZone("GMT");
    private static final ThreadLocal<DateFormat> b = new a();
    private static final String[] c;
    private static final DateFormat[] d;

    static {
        String[] strArr = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        c = strArr;
        d = new DateFormat[strArr.length];
    }

    /* compiled from: HttpDate */
    public static final class a extends ThreadLocal<DateFormat> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            rfc1123.setLenient(false);
            rfc1123.setTimeZone(g.a);
            return rfc1123;
        }
    }

    public static Date b(String value) {
        if (value.length() == 0) {
            return null;
        }
        ParsePosition position = new ParsePosition(0);
        Date result = b.get().parse(value, position);
        if (position.getIndex() == value.length()) {
            return result;
        }
        String[] strArr = c;
        synchronized (strArr) {
            int count = strArr.length;
            for (int i = 0; i < count; i++) {
                DateFormat[] dateFormatArr = d;
                DateFormat format = dateFormatArr[i];
                if (format == null) {
                    format = new SimpleDateFormat(c[i], Locale.US);
                    format.setTimeZone(a);
                    dateFormatArr[i] = format;
                }
                position.setIndex(0);
                Date result2 = format.parse(value, position);
                if (position.getIndex() != 0) {
                    return result2;
                }
            }
            return null;
        }
    }
}
