package org.glassfish.grizzly.http.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.grizzly.utils.Charsets;

public final class FastHttpDateFormat {
    private static final String ASCII_CHARSET_NAME = Charsets.ASCII_CHARSET.name();
    private static final int CACHE_SIZE = 1000;
    private static final ThreadLocal<SimpleDateFormatter> FORMAT = new ThreadLocal<SimpleDateFormatter>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormatter initialValue() {
            return new SimpleDateFormatter();
        }
    };
    private static final ThreadLocal FORMATS = new ThreadLocal() {
        /* access modifiers changed from: protected */
        public Object initialValue() {
            SimpleDateFormat[] f = new SimpleDateFormat[3];
            Locale locale = Locale.US;
            f[0] = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", locale);
            f[0].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            f[1] = new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", locale);
            f[1].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            f[2] = new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", locale);
            f[2].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            return f;
        }
    };
    private static final SimpleDateFormatter FORMATTER = new SimpleDateFormatter();
    /* access modifiers changed from: private */
    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");
    private static String cachedStringDate;
    private static final StringBuffer currentDateBuffer = new StringBuffer();
    private static byte[] currentDateBytes;
    private static volatile byte[] dateBytesForCachedStringDate;
    private static final ConcurrentMap<Long, String> formatCache = new ConcurrentHashMap(1000, 0.75f, 64);
    private static final AtomicBoolean isGeneratingNow = new AtomicBoolean();
    private static volatile long nextGeneration;
    private static final ConcurrentMap<String, Long> parseCache = new ConcurrentHashMap(1000, 0.75f, 64);

    public static final class SimpleDateFormatter {
        private final Date date = new Date();
        private final SimpleDateFormat f;
        private final FieldPosition pos = new FieldPosition(-1);

        public SimpleDateFormatter() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            this.f = simpleDateFormat;
            simpleDateFormat.setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
        }

        public String format(long timeMillis) {
            this.date.setTime(timeMillis);
            return this.f.format(this.date);
        }

        public StringBuffer formatTo(long timeMillis, StringBuffer buffer) {
            this.date.setTime(timeMillis);
            return this.f.format(this.date, buffer, this.pos);
        }
    }

    public static String getCurrentDate() {
        byte[] currentDateBytesNow = getCurrentDateBytes();
        if (currentDateBytesNow != dateBytesForCachedStringDate) {
            try {
                cachedStringDate = new String(currentDateBytesNow, ASCII_CHARSET_NAME);
                dateBytesForCachedStringDate = currentDateBytesNow;
            } catch (UnsupportedEncodingException e) {
            }
        }
        return cachedStringDate;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0022, code lost:
        if (r4.compareAndSet(false, true) != false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getCurrentDateBytes() {
        /*
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = nextGeneration
            long r2 = r0 - r2
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x004c
            r4 = 5000(0x1388, double:2.4703E-320)
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            r5 = 0
            if (r4 > 0) goto L_0x0024
            java.util.concurrent.atomic.AtomicBoolean r4 = isGeneratingNow
            boolean r6 = r4.get()
            if (r6 != 0) goto L_0x004c
            r6 = 1
            boolean r4 = r4.compareAndSet(r5, r6)
            if (r4 == 0) goto L_0x004c
        L_0x0024:
            java.lang.ThreadLocal<org.glassfish.grizzly.http.util.FastHttpDateFormat$SimpleDateFormatter> r4 = FORMAT
            monitor-enter(r4)
            long r6 = nextGeneration     // Catch:{ all -> 0x0049 }
            int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0042
            java.lang.StringBuffer r6 = currentDateBuffer     // Catch:{ all -> 0x0049 }
            r6.setLength(r5)     // Catch:{ all -> 0x0049 }
            org.glassfish.grizzly.http.util.FastHttpDateFormat$SimpleDateFormatter r7 = FORMATTER     // Catch:{ all -> 0x0049 }
            r7.formatTo(r0, r6)     // Catch:{ all -> 0x0049 }
            byte[] r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.toCheckedByteArray(r6)     // Catch:{ all -> 0x0049 }
            currentDateBytes = r6     // Catch:{ all -> 0x0049 }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 + r0
            nextGeneration = r6     // Catch:{ all -> 0x0049 }
        L_0x0042:
            java.util.concurrent.atomic.AtomicBoolean r6 = isGeneratingNow     // Catch:{ all -> 0x0049 }
            r6.set(r5)     // Catch:{ all -> 0x0049 }
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            goto L_0x004c
        L_0x0049:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            throw r5
        L_0x004c:
            byte[] r4 = currentDateBytes
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.FastHttpDateFormat.getCurrentDateBytes():byte[]");
    }

    public static String formatDate(long value, DateFormat threadLocalFormat) {
        String newDate;
        long value2 = (value / 1000) * 1000;
        Long longValue = Long.valueOf(value2);
        String cachedDate = (String) formatCache.get(longValue);
        if (cachedDate != null) {
            return cachedDate;
        }
        if (threadLocalFormat != null) {
            newDate = threadLocalFormat.format(Long.valueOf(value2));
        } else {
            newDate = FORMAT.get().format(value2);
        }
        updateFormatCache(longValue, newDate);
        return newDate;
    }

    public static long parseDate(String value, DateFormat[] threadLocalformats) {
        long date;
        Long cachedDate = (Long) parseCache.get(value);
        if (cachedDate != null) {
            return cachedDate.longValue();
        }
        if (threadLocalformats != null) {
            date = internalParseDate(value, threadLocalformats);
        } else {
            date = internalParseDate(value, (SimpleDateFormat[]) FORMATS.get());
        }
        if (date != -1) {
            updateParseCache(value, Long.valueOf(date));
        }
        return date;
    }

    private static long internalParseDate(String value, DateFormat[] formats) {
        int i = 0;
        while (i < formats.length) {
            try {
                return formats[i].parse(value).getTime();
            } catch (ParseException e) {
                i++;
            }
        }
        return -1;
    }

    private static void updateFormatCache(Long key, String value) {
        if (value != null) {
            ConcurrentMap<Long, String> concurrentMap = formatCache;
            if (concurrentMap.size() > 1000) {
                concurrentMap.clear();
            }
            concurrentMap.put(key, value);
        }
    }

    private static void updateParseCache(String key, Long value) {
        ConcurrentMap<String, Long> concurrentMap = parseCache;
        if (concurrentMap.size() > 1000) {
            concurrentMap.clear();
        }
        concurrentMap.put(key, value);
    }
}
