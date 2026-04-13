package com.squareup.moshi.adapters;

import com.squareup.moshi.JsonDataException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: Iso8601Utils */
public final class a {
    static final TimeZone a = TimeZone.getTimeZone("GMT");

    public static String b(Date date) {
        Calendar calendar = new GregorianCalendar(a, Locale.US);
        calendar.setTime(date);
        StringBuilder formatted = new StringBuilder("yyyy-MM-ddThh:mm:ss.sssZ".length());
        d(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        d(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        d(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        d(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        d(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        d(formatted, calendar.get(13), "ss".length());
        formatted.append('.');
        d(formatted, calendar.get(14), "sss".length());
        formatted.append('Z');
        return formatted.toString();
    }

    public static Date e(String date) {
        int day;
        TimeZone timezone;
        String str = date;
        int offset = 0 + 4;
        try {
            int offset2 = f(str, 0, offset);
            if (a(str, offset, '-')) {
                offset++;
            }
            int offset3 = offset + 2;
            int offset4 = f(str, offset, offset3);
            if (a(str, offset3, '-')) {
                offset3++;
            }
            int offset5 = offset3 + 2;
            int day2 = f(str, offset3, offset5);
            int hour = 0;
            int minutes = 0;
            int seconds = 0;
            int milliseconds = 0;
            boolean hasT = a(str, offset5, 'T');
            if (!hasT && date.length() <= offset5) {
                return new GregorianCalendar(offset2, offset4 - 1, day2).getTime();
            }
            if (hasT) {
                int offset6 = offset5 + 1;
                int offset7 = offset6 + 2;
                hour = f(str, offset6, offset7);
                if (a(str, offset7, ':')) {
                    offset7++;
                }
                int offset8 = offset7 + 2;
                minutes = f(str, offset7, offset8);
                if (a(str, offset8, ':')) {
                    offset5 = offset8 + 1;
                } else {
                    offset5 = offset8;
                }
                if (date.length() > offset5) {
                    char c = str.charAt(offset5);
                    if (c == 'Z' || c == '+' || c == '-') {
                        day = day2;
                        char c2 = c;
                    } else {
                        int offset9 = offset5 + 2;
                        int offset10 = f(str, offset5, offset9);
                        if (offset10 <= 59 || offset10 >= 63) {
                            seconds = offset10;
                        } else {
                            seconds = 59;
                        }
                        if (a(str, offset9, '.')) {
                            int offset11 = offset9 + 1;
                            offset5 = c(str, offset11 + 1);
                            int parseEndOffset = Math.min(offset5, offset11 + 3);
                            day = day2;
                            char c3 = c;
                            milliseconds = (int) (Math.pow(10.0d, (double) (3 - (parseEndOffset - offset11))) * ((double) f(str, offset11, parseEndOffset)));
                            int i = offset5;
                        } else {
                            day = day2;
                            char c4 = c;
                            offset5 = offset9;
                        }
                    }
                } else {
                    day = day2;
                }
            } else {
                day = day2;
            }
            if (date.length() > offset5) {
                char timezoneIndicator = str.charAt(offset5);
                if (timezoneIndicator == 'Z') {
                    timezone = a;
                    char c5 = timezoneIndicator;
                } else {
                    if (timezoneIndicator != '+') {
                        if (timezoneIndicator != '-') {
                            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + timezoneIndicator + "'");
                        }
                    }
                    String timezoneOffset = str.substring(offset5);
                    if ("+0000".equals(timezoneOffset)) {
                        String str2 = timezoneOffset;
                    } else if ("+00:00".equals(timezoneOffset)) {
                        char c6 = timezoneIndicator;
                        String str3 = timezoneOffset;
                    } else {
                        String timezoneId = "GMT" + timezoneOffset;
                        TimeZone timezone2 = TimeZone.getTimeZone(timezoneId);
                        String act = timezone2.getID();
                        if (act.equals(timezoneId)) {
                            String str4 = timezoneOffset;
                        } else if (act.replace(":", "").equals(timezoneId)) {
                            char c7 = timezoneIndicator;
                        } else {
                            char c8 = timezoneIndicator;
                            StringBuilder sb = new StringBuilder();
                            String str5 = timezoneOffset;
                            sb.append("Mismatching time zone indicator: ");
                            sb.append(timezoneId);
                            sb.append(" given, resolves to ");
                            sb.append(timezone2.getID());
                            throw new IndexOutOfBoundsException(sb.toString());
                        }
                        timezone = timezone2;
                    }
                    timezone = a;
                }
                Calendar calendar = new GregorianCalendar(timezone);
                calendar.setLenient(false);
                calendar.set(1, offset2);
                calendar.set(2, offset4 - 1);
                calendar.set(5, day);
                calendar.set(11, hour);
                calendar.set(12, minutes);
                calendar.set(13, seconds);
                calendar.set(14, milliseconds);
                return calendar.getTime();
            }
            throw new IllegalArgumentException("No time zone indicator");
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new JsonDataException("Not an RFC 3339 date: " + str, e);
        }
    }

    private static boolean a(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int f(String value, int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int digit = beginIndex;
        int result = 0;
        if (digit < endIndex) {
            int i = digit + 1;
            int digit2 = Character.digit(value.charAt(digit), 10);
            if (digit2 >= 0) {
                result = -digit2;
                digit = i;
            } else {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
        }
        while (digit < endIndex) {
            int i2 = digit + 1;
            int digit3 = Character.digit(value.charAt(digit), 10);
            if (digit3 >= 0) {
                result = (result * 10) - digit3;
                digit = i2;
            } else {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
        }
        return -result;
    }

    private static void d(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }

    private static int c(String string, int offset) {
        for (int i = offset; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        return string.length();
    }
}
