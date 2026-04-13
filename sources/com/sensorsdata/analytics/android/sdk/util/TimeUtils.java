package com.sensorsdata.analytics.android.sdk.util;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.github.druk.dnssd.DNSSD;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static Map<String, ThreadLocal<SimpleDateFormat>> formatMaps = new HashMap();

    public static String formatTime(long timeMillis, String patten) {
        if (TextUtils.isEmpty(patten)) {
            patten = YYYY_MM_DD_HH_MM_SS_SSS;
        }
        SimpleDateFormat simpleDateFormat = getDateFormat(patten, Locale.getDefault());
        if (simpleDateFormat == null) {
            return "";
        }
        try {
            return simpleDateFormat.format(Long.valueOf(timeMillis));
        } catch (IllegalArgumentException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static String formatDate(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS_SSS);
    }

    public static String formatDate(Date date, String patten) {
        return formatDate(date, patten, Locale.getDefault());
    }

    public static String formatDate(Date date, Locale locale) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS_SSS, locale);
    }

    public static String formatDate(Date date, String patten, Locale locale) {
        if (TextUtils.isEmpty(patten)) {
            patten = YYYY_MM_DD_HH_MM_SS_SSS;
        }
        SimpleDateFormat simpleDateFormat = getDateFormat(patten, locale);
        if (simpleDateFormat == null) {
            return "";
        }
        try {
            return simpleDateFormat.format(date);
        } catch (IllegalArgumentException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static boolean isDateValid(Date date) {
        try {
            return date.after(getDateFormat(YYYY_MM_DD_HH_MM_SS_SSS, Locale.getDefault()).parse("2015-05-15 10:24:00.000"));
        } catch (ParseException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static boolean isDateValid(long time) {
        try {
            Date baseDate = getDateFormat(YYYY_MM_DD_HH_MM_SS_SSS, Locale.getDefault()).parse("2015-05-15 10:24:00.000");
            if (baseDate != null && baseDate.getTime() < time) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static JSONObject formatDate(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new JSONObject();
        }
        try {
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = jsonObject.get(key);
                if (value instanceof Date) {
                    jsonObject.put(key, (Object) formatDate((Date) value, Locale.CHINA));
                }
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        return jsonObject;
    }

    public static Integer getZoneOffset() {
        try {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            return Integer.valueOf((-(cal.get(15) + cal.get(16))) / DNSSD.DNSSD_DEFAULT_TIMEOUT);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return null;
        }
    }

    public static double duration(long startTime, long endTime) {
        long duration = endTime - startTime;
        if (duration < 0 || duration > CostTimeUtil.DAY) {
            return 0.0d;
        }
        float durationFloat = ((float) duration) / 1000.0f;
        try {
            return Double.parseDouble(String.format(Locale.CHINA, "%.3f", new Object[]{Float.valueOf(durationFloat)}));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return 0.0d;
        }
    }

    private static synchronized SimpleDateFormat getDateFormat(final String patten, final Locale locale) {
        SimpleDateFormat simpleDateFormat;
        synchronized (TimeUtils.class) {
            ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = formatMaps.get(patten);
            if (dateFormatThreadLocal == null) {
                dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
                    /* access modifiers changed from: protected */
                    public SimpleDateFormat initialValue() {
                        try {
                            if (locale == null) {
                                return new SimpleDateFormat(patten, Locale.getDefault());
                            }
                            return new SimpleDateFormat(patten, locale);
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                            return null;
                        }
                    }
                };
                if (dateFormatThreadLocal.get() != null) {
                    formatMaps.put(patten, dateFormatThreadLocal);
                }
            }
            simpleDateFormat = dateFormatThreadLocal.get();
        }
        return simpleDateFormat;
    }
}
