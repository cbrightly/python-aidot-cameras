package com.squareup.okhttp.internal.http;

/* compiled from: HeaderParser */
public final class d {
    public static int b(String input, int pos, String characters) {
        while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
            pos++;
        }
        return pos;
    }

    public static int c(String input, int pos) {
        while (pos < input.length() && ((c = input.charAt(pos)) == ' ' || c == 9)) {
            pos++;
        }
        return pos;
    }

    public static int a(String value, int defaultValue) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (seconds < 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
