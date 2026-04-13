package org.apache.http.util;

/* compiled from: TextUtils */
public final class j {
    public static boolean c(CharSequence s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean b(CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(CharSequence s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
