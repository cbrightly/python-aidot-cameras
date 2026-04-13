package org.apache.httpcore.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: HttpDateGenerator */
public class f {
    public static final TimeZone a = TimeZone.getTimeZone("GMT");
    private final DateFormat b;
    private long c = 0;
    private String d = null;

    public f() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        this.b = simpleDateFormat;
        simpleDateFormat.setTimeZone(a);
    }

    public synchronized String a() {
        long now = System.currentTimeMillis();
        if (now - this.c > 1000) {
            this.d = this.b.format(new Date(now));
            this.c = now;
        }
        return this.d;
    }
}
