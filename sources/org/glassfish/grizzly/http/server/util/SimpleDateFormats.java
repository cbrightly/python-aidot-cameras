package org.glassfish.grizzly.http.server.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.glassfish.grizzly.ThreadCache;

public final class SimpleDateFormats {
    private static final ThreadCache.CachedTypeIndex<SimpleDateFormats> CACHE_IDX = ThreadCache.obtainIndex(SimpleDateFormats.class, 1);
    private final SimpleDateFormat[] f;

    public static SimpleDateFormats create() {
        SimpleDateFormats formats = (SimpleDateFormats) ThreadCache.takeFromCache(CACHE_IDX);
        if (formats != null) {
            return formats;
        }
        return new SimpleDateFormats();
    }

    public SimpleDateFormats() {
        SimpleDateFormat[] simpleDateFormatArr = new SimpleDateFormat[3];
        this.f = simpleDateFormatArr;
        Locale locale = Locale.US;
        simpleDateFormatArr[0] = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", locale);
        simpleDateFormatArr[1] = new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", locale);
        simpleDateFormatArr[2] = new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", locale);
        simpleDateFormatArr[0].setTimeZone(TimeZone.getTimeZone("GMT"));
        simpleDateFormatArr[1].setTimeZone(TimeZone.getTimeZone("GMT"));
        simpleDateFormatArr[2].setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public SimpleDateFormat[] getFormats() {
        return this.f;
    }

    public void recycle() {
        ThreadCache.putToCache(CACHE_IDX, this);
    }
}
